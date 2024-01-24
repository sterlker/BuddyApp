package com.example.buddyapps2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class FriendsFragment extends Fragment {

    private ListView friendListView;

    Activity context;
    DBHelper db;



    public FriendsFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_friends, container, false);
        context = getActivity();
        initWidgets(view);
        setFriendAdapter();
        loadFromDBToMemory();
        setOnClickListener();
        return view;
    }

    private void initWidgets(View view) {
        friendListView = view.findViewById(R.id.friendListView);
    }
    private void setFriendAdapter() {
        FriendAdapter friendAdapter = new FriendAdapter(requireContext(), Friend.nonDeletedFriends());
        friendListView.setAdapter(friendAdapter);
    }

    public void loadFromDBToMemory() {
        db = new DBHelper(requireContext());
        db.populateFriendListArray();
        setFriendAdapter();
    }
    private void setOnClickListener() {
        friendListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Friend selectedFriend = (Friend) friendListView.getItemAtPosition(position);
                Intent editFriendIntent = new Intent(requireContext(), FriendDetailActivity.class);
                editFriendIntent.putExtra(Friend.FRIEND_EDIT_EXTRA, selectedFriend.getFriend_id());
                startActivity(editFriendIntent);
            }
        });
    }

    public void onResume() {
        super.onResume();
        setFriendAdapter();
    }

    // To add new friends
    public void onStart() {
        super.onStart();
        Button btn = (Button) context.findViewById(R.id.newFriend);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendDetailActivity.class);
                startActivity(intent);
            }
        });
    }
}