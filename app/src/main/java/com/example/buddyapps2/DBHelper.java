package com.example.buddyapps2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.text.*;
import java.util.*;

public class DBHelper extends SQLiteOpenHelper {

    SQLiteDatabase db;
    private static final String DATABASE_NAME ="buddy_database.db";
    private static final int DATABASE_VERSION=1;

    private static final String TABLE_FRIEND="friend";

    private static final String KEY_FID="friend_id";
    private static final String KEY_NAME="friend_name";
    private static final String KEY_MOBILE="mobile";
    private static final String KEY_GENDER="gender";
    private static final String KEY_F_EMAIL="friend_email";
    private static final String KEY_DOB="dob";
    private static final String KEY_ADDRESS="address";
    private static final String KEY_DELETED="deleted";

    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuilder Query_Table;
        Query_Table = new StringBuilder()
                .append("CREATE TABLE ")
                .append(TABLE_FRIEND)
                .append("(")
                .append(KEY_FID)
                .append(" INTEGER PRIMARY KEY AUTOINCREMENT, ")
                .append(KEY_NAME)
                .append(" TEXT, ")
                .append(KEY_MOBILE)
                .append(" TEXT, ")
                .append(KEY_GENDER)
                .append(" TEXT, ")
                .append(KEY_F_EMAIL)
                .append(" TEXT, ")
                .append(KEY_DOB)
                .append(" TEXT, ")
                .append(KEY_ADDRESS)
                .append(" TEXT, ")
                .append(KEY_DELETED)
                .append(" TEXT)");

        db.execSQL(Query_Table.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIEND);
        onCreate(db);
    }

    public void insertFriend(Friend friend) {

        db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_FID,friend.getFriend_id());
        values.put(KEY_NAME,friend.getFriend_name());
        values.put(KEY_MOBILE,friend.getMobile());
        values.put(KEY_GENDER,friend.getGender());
        values.put(KEY_F_EMAIL,friend.getFriend_email());
        values.put(KEY_DOB, getStringFromDate(friend.getDob()));
        values.put(KEY_ADDRESS,friend.getAddress());
        values.put(KEY_DELETED, getStringFromDate(friend.getDeleted()));

        db.insert(TABLE_FRIEND,null,values);
//        if(FriendAdapter.adapterInstance != null) {
//            FriendAdapter.adapterInstance.notifyDataSetChange();
//        }
        db.close();
    }

    public void populateFriendListArray() {
        db = this.getReadableDatabase();

        Friend.friendArrayList.clear();

        try(Cursor result = db.rawQuery("SELECT * FROM " + TABLE_FRIEND, null)) {
            if(result.getCount() != 0) {
                while (result.moveToNext()) {
                    int id = result.getInt(0);
                    String fname = result.getString(1);
                    String mobile = result.getString(2);
                    String gender = result.getString(3);
                    String fEmail = result.getString(4);
                    long dobMillis = result.getLong(5);
                    String address = result.getString(6);
                    String stringDeleted = result.getString(7);
                    Date deleted = getDateFromString(stringDeleted);
                    Date dob = new Date(dobMillis);
                    Friend friend = new Friend(id, fname, mobile, gender, fEmail, dob, address, deleted);
                    Friend.friendArrayList.add(friend);
                }
            }
        }
        db.close();
    }

    public void updateFriend(Friend friend) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_FID, friend.getFriend_id());
        values.put(KEY_NAME,friend.getFriend_name());
        values.put(KEY_MOBILE,friend.getMobile());
        values.put(KEY_GENDER,friend.getGender());
        values.put(KEY_F_EMAIL,friend.getFriend_email());
        values.put(KEY_DOB, getStringFromDate(friend.getDob()));
        values.put(KEY_ADDRESS,friend.getAddress());
        values.put(KEY_DELETED, getStringFromDate(friend.getDeleted()));

        db.update(TABLE_FRIEND, values,KEY_FID + " =?", new String[]{String.valueOf(friend.getFriend_id())});
        db.close();
    }

    public void deleteFriend(int friendId) {
        db = this.getWritableDatabase();

        db.delete(TABLE_FRIEND, KEY_FID + "=?", new String[]{String.valueOf(friendId)});
        db.close();
    }


    // function untuk dapatkan date format
    private String getStringFromDate(Date date) {
        if(date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string)
    {
        try {
            return dateFormat.parse(string);
        }
        catch (ParseException | NullPointerException e) {
            return null;
        }
    }

}
