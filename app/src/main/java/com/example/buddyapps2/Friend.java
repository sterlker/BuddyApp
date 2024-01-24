package com.example.buddyapps2;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Friend {

    public static ArrayList<Friend> friendArrayList = new ArrayList<>();
    public static String FRIEND_EDIT_EXTRA = "friendEdit";
    private int friend_id;
    private String friend_name;
    private String mobile;
    private String gender;
    private String friend_email;
    private Date dob;
    private String address;
    private Date deleted;

    private static final DateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");

    public Friend(int friend_id, String friend_name, String mobile, String gender, String friend_email, Date dob, String address, Date deleted) {
        this.friend_id = friend_id;
        this.friend_name = friend_name;
        this.mobile = mobile;
        this.gender = gender;
        this.friend_email = friend_email;
        this.dob = dob;
        this.address = address;
        this.deleted = deleted;
    }

    public Friend(int friend_id, String friend_name, String mobile, String gender, String friend_email, Date dob, String address) {
        this.friend_id = friend_id;
        this.friend_name = friend_name;
        this.mobile = mobile;
        this.gender = gender;
        this.friend_email = friend_email;
        this.dob = dob;
        this.address = address;
        deleted = null;
    }

    public static Friend getFriendForID(int passedFriendID) {
        for (Friend friend : friendArrayList){
            if(friend.getFriend_id() == passedFriendID)
                return friend;
        }
        return null;
    }

    public static ArrayList<Friend> nonDeletedFriends() {
        ArrayList<Friend> nonDeleted = new ArrayList<>();
        for(Friend friend : friendArrayList) {
            if(friend.getDeleted() == null)
                nonDeleted.add(friend);
        }
        return nonDeleted;
    }

    public int getFriend_id() {
        return friend_id;
    }

    public void setFriend_id(int friend_id) {
        this.friend_id = friend_id;
    }

    public String getFriend_name() {
        return friend_name;
    }

    public void setFriend_name(String friend_name) {
        this.friend_name = friend_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFriend_email() {
        return friend_email;
    }

    public void setFriend_email(String friend_email) {
        this.friend_email = friend_email;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getDeleted() {
        return deleted;
    }

    public void setDeleted(Date deleted) {
        this.deleted = deleted;
    }

    private String getStringFromDate(Date date) {
        if (date == null)
            return null;
        return dateFormat.format(date);
    }

    private Date getDateFromString(String string) {
        try {
            return dateFormat.parse(string);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}

