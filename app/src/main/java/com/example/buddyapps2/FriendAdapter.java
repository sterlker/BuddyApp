package com.example.buddyapps2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class FriendAdapter extends ArrayAdapter<Friend> {

    //public static FriendAdapter adapterInstance;
    public FriendAdapter(Context context, List<Friend> friends)
    {
        super(context, 0, friends);
        //adapterInstance = this;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Friend friend = getItem(position);
        if(convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.friend_cell, parent, false);

        TextView name = convertView.findViewById(R.id.cellName);
        TextView mobile = convertView.findViewById(R.id.cellMobile);

        name.setText(friend.getFriend_name());
        mobile.setText(friend.getMobile());

        return convertView;
    }
}
