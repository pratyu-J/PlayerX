package com.example.playerx.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.playerx.Adapters.MusicListAdapter;
import com.example.playerx.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

public class HomeFragment extends Fragment {

    public static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQ_PERMISSION = 100;
    private static final int PERMISSION_COUNT = 1;
    ListView musicList;
    String[] items;

    private boolean isPlayerInit;
    // MusicListAdapter adapter = new MusicListAdapter();
    public List<File> musicFiles= new ArrayList<>();

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        musicList = v.findViewById(R.id.list);


        return v;
    }


    public void fillMusicList(){
        musicFiles = findSongs(Environment.getExternalStorageDirectory());
        items = new String[musicFiles.size()];

        for(int i=0; i<musicFiles.size(); i++){

            items[i] = musicFiles.get(i).getName().toString().replace(".mp3", "");

        }

        MusicListAdapter musicListAdapter = new MusicListAdapter(getActivity(), items);
        musicList.setAdapter(musicListAdapter);

        /*ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        musicList.setAdapter(songAdapter);*/
    }

    public ArrayList<File> findSongs(File file){

        ArrayList<File> songlist = new ArrayList<>();
        File[] files = file.listFiles();

        for(File singleFile: files){

            if(singleFile.isDirectory() && !singleFile.isHidden()){
                songlist.addAll(findSongs(singleFile));
            }
            else {
                if(singleFile.getName().endsWith(".mp3")){
                    songlist.add(singleFile);
                }
            }

        }
        return songlist;
    }

    @SuppressLint("NewApi")
    public boolean isPermissionDenied(){
        for( int i= 0; i< PERMISSION_COUNT; i++ ){
            if(getActivity().checkSelfPermission(PERMISSIONS[i]) != PackageManager.PERMISSION_GRANTED){
                return true;
            }

        }
        return false;
    }

    @SuppressLint("NewApi")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(isPermissionDenied()){
            ((ActivityManager) (getActivity().getSystemService(ACTIVITY_SERVICE))).clearApplicationUserData();
            getActivity().recreate();
        }
        else {
            onResume();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && isPermissionDenied()){
            requestPermissions(PERMISSIONS, REQ_PERMISSION);
            return;
        }

        if(!isPlayerInit){
            fillMusicList();

           /* adapter.setData(musicFiles);
            musicList.setAdapter(adapter);*/
            isPlayerInit = true;
        }
    }


}
