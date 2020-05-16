package com.example.playerx.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.playerx.Adapters.MusicListAdapter;
import com.example.playerx.MediaPlayer;
import com.example.playerx.R;

import java.io.File;

import static com.example.playerx.Adapters.MusicListAdapter.likedSongs;
import static com.example.playerx.Fragments.HomeFragment.musicFiles;

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
    public void OnSongClick(String s) {
        int c = 0;
        Intent intent=new Intent(getActivity(),MediaPlayer.class);
        //Log.d("SONG POSITION", position + " pos");
        Toast.makeText(getActivity(), " " + s, Toast.LENGTH_SHORT).show();
        for(File f: musicFiles){
            c++;
            if(f.getName().equals(s+".mp3")){
                Log.d("filename",f.getName() );
                Log.d("filename", "pos: " + c );
                break;
            }
        }
        intent.putExtra("song_position", c-1);
        startActivity(intent);

    }

    /*@Override
    public void OnSongClick(int position) {
        Intent intent=new Intent(getActivity(), MediaPlayer.class);
        intent.putExtra("song_position",position);
        startActivity(intent);

    }*/


}
