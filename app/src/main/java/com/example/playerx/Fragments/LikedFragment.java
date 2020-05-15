package com.example.playerx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.playerx.Adapters.MusicListAdapter;
import com.example.playerx.MediaPlayer;
import com.example.playerx.R;

import static com.example.playerx.Adapters.MusicListAdapter.likedSongs;

public class LikedFragment extends Fragment implements MusicListAdapter.OnSongClickListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    MusicListAdapter likedAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_liked, container, false);

        recyclerView = v.findViewById(R.id.liked_list);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        likedAdapter = new MusicListAdapter(getActivity(), likedSongs, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(likedAdapter);


        return v;
    }

    @Override
    public void OnSongClick(int position) {
        Intent intent=new Intent(getActivity(), MediaPlayer.class);
        intent.putExtra("song_position",position);
        startActivity(intent);

    }


}
