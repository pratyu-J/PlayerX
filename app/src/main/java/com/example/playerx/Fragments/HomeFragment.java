package com.example.playerx.Fragments;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.playerx.Adapters.MusicListAdapter;
import com.example.playerx.R;
import com.example.playerx.utils.SongClass;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.prefs.Preferences;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment {

    public static final String[] PERMISSIONS = {Manifest.permission.READ_EXTERNAL_STORAGE};
    private static final int REQ_PERMISSION = 100;
    private static final int PERMISSION_COUNT = 1;
    RecyclerView musicList;
    RecyclerView.LayoutManager layoutManager;

    public  static ArrayList<SongClass> items;
    Activity activity;
    MusicListAdapter musicListAdapter ;
    SharedPreferences shared;

    private boolean isPlayerInit;
    // MusicListAdapter adapter = new MusicListAdapter();
    public static List<File> musicFiles= new ArrayList<>();
    private Handler progressBarbHandler = new Handler();
    ProgressBar progress;


    public HomeFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        musicList = v.findViewById(R.id.list);
        musicList.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());

        progress = v.findViewById(R.id.progress);


        progress.setIndeterminate(true);
        progress.setVisibility(View.VISIBLE);


        if(!isPermissionDenied()){
            fillMusicList();

        }

        /*new Thread(new Runnable(){

            @Override
            public void run() {
                while(fillMusicList()== false){

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    progressBarbHandler.post(new Runnable() {
                        @Override
                        public void run() {

                        }
                    });
                }

            }
        });*/
        return v;
    }




    public boolean fillMusicList(){



                progress.setVisibility(View.VISIBLE);
                musicFiles = findSongs(Environment.getExternalStorageDirectory());
                items = new ArrayList<SongClass>();

                for(int i=0; i<musicFiles.size(); i++){
                    String name = musicFiles.get(i).getName().toString().replace(".mp3", "");
                    items.add(new SongClass(name, "2020"));

                }

                musicListAdapter = new MusicListAdapter(getActivity(), items);
                musicList.setLayoutManager(layoutManager);
                musicList.setAdapter(musicListAdapter);
                musicListAdapter.notifyDataSetChanged();
                progress.setVisibility(View.GONE);

                //storing in sharedPreferences
 /*               List<String> titles = new ArrayList<>(items);
        shared = getActivity().getSharedPreferences("App_settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
                Set<String> mTitles = new HashSet<String>();
                mTitles.addAll(titles);
                editor.putStringSet("list", mTitles);
                editor.apply();
        Log.d("storesharedPreferences",""+ mTitles);*/


       // PreferenceManager.getDefaultSharedPreferences(getContext()).edit().


        /*ArrayAdapter<String> songAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        musicList.setAdapter(songAdapter);*/

        return true;
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
            if(ActivityCompat.checkSelfPermission(getContext(),android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
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


    /*public void revisiting(){
        Set<String> retrieve = shared.getStringSet("list", null);

        ArrayList<String> ret = new ArrayList<>() ;
        ret.addAll(retrieve);

        Log.d("retriveshared",""+ retrieve);
        musicListAdapter = new MusicListAdapter(getActivity(), ret);
        musicList.setAdapter(musicListAdapter);

    }*/


}
