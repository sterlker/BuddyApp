package com.example.buddyapps2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class FriendDetailActivity extends AppCompatActivity {

    private EditText fNameEdit, mobileEdit, fEmailEdit, dobEdit, addressEdit;
    private Spinner genderEdit;
    Button deleteButton;
    private Friend selectedFriend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);
        initWidgets();
        checkForEditFriend();
    }
    private void initWidgets() {
        fNameEdit = findViewById(R.id.fName);
        mobileEdit = findViewById(R.id.fMobile);
        genderEdit = findViewById(R.id.fGender);
        fEmailEdit = findViewById(R.id.fEmail);
        dobEdit = findViewById(R.id.fDob);
        addressEdit = findViewById(R.id.fAddress);

        deleteButton = findViewById(R.id.deleteF_btn);
    }
    private void checkForEditFriend() {
        Intent previousIntent = getIntent();

        int passedFriendID = previousIntent.getIntExtra(Friend.FRIEND_EDIT_EXTRA, -1);
        selectedFriend = Friend.getFriendForID(passedFriendID);

        if(selectedFriend != null) {
            fNameEdit.setText(selectedFriend.getFriend_name());
            mobileEdit.setText(selectedFriend.getMobile());
            int position = getPositionForGender(selectedFriend.getGender());
            genderEdit.setSelection(position);
            fEmailEdit.setText(selectedFriend.getFriend_email());
            //dobEdit.setText( selectedFriend.getDob());
            dobEdit.setText(formatDate(selectedFriend.getDob()));
            addressEdit.setText(selectedFriend.getAddress());
        }
        else
        {
            deleteButton.setVisibility(View.INVISIBLE);
        }
    }

    public void saveFriend(View view) {
        DBHelper dbHelper = new DBHelper(this);
        String fname = String.valueOf(fNameEdit.getText());
        String mobile = String.valueOf(mobileEdit.getText());
        String gender = genderEdit.getSelectedItem().toString();
        String fEmail = String.valueOf(fEmailEdit.getText());
        String dobString = String.valueOf(dobEdit.getText());
        Date dob = parseDate(dobString);
        String address = String.valueOf(addressEdit.getText());

        if(selectedFriend == null) {
            int id = Friend.friendArrayList.size();
            Friend newFriend = new Friend(id, fname, mobile, gender, fEmail, dob, address);
            Friend.friendArrayList.add(newFriend);
            dbHelper.insertFriend(newFriend);
        }
        else {
            selectedFriend.setFriend_name(fname);
            selectedFriend.setMobile(mobile);
            selectedFriend.setGender(gender);
            selectedFriend.setFriend_email(fEmail);
            selectedFriend.setDob(dob);
            selectedFriend.setAddress(address);
            dbHelper.updateFriend(selectedFriend);
        }

        finish();
    }

    private void performDelete() {
        selectedFriend.setDeleted(new Date());
        DBHelper dbHelper = new DBHelper(this);
        dbHelper.updateFriend(selectedFriend);
        dbHelper.deleteFriend(selectedFriend.getFriend_id());
        finish();
    }

    // delete confirmation
    public void deleteFriend(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to delete this friend?")
                .setTitle("Confirm Delete");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Delete button
                performDelete();
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked Cancel button
                dialog.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private int getPositionForGender(String gender) {
        // Helper method to get the position of the gender in the spinner options
        String[] genders = getResources().getStringArray(R.array.gender_options);
        for (int i = 0; i < genders.length; i++) {
            if (genders[i].equals(gender)) {
                return i;
            }
        }
        return 0; // Default to the first item if not found
    }

    // date picker function untuk dob
    public void datePickerDialog(View view) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, dayOfMonth);

                // Update the dobEdit EditText with the selected date
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
                dobEdit.setText(dateFormat.format(calendar.getTime()));
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, dayOfMonth);
        datePickerDialog.show();
    }
    private String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
        return dateFormat.format(date);
    }

    private Date parseDate(String dateString) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy", Locale.US);
            return dateFormat.parse(dateString);
        } catch (ParseException | NullPointerException e) {
            return null;
        }
    }
}