package com.example.playerx.Fragments;


import android.os.Bundle;


import androidx.fragment.app.Fragment;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.playerx.Adapters.MusicListAdapter;
import com.example.playerx.R;


import static com.example.playerx.Fragments.HomeFragment.musicFiles;


public class SearchFragment extends Fragment {
    Button search;
    EditText song_to_find;
    String song;
    ListView ResList;

    String[] items;
    String[] res_songs;
    int same=0;


    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);

        search = v.findViewById(R.id.searchButton);
        song_to_find = v.findViewById(R.id.song_search);
        ResList = v.findViewById(R.id.res_list);

        items = new String[musicFiles.size()];
        for (int i = 0; i < musicFiles.size(); i++) {

            items[i] = musicFiles.get(i).getName().toString().replace(".mp3", "");

        }

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                song = song_to_find.getText().toString();
                Toast.makeText(getContext(), "You Searched for:" + song, Toast.LENGTH_SHORT).show();
                for (int i = 0; i < musicFiles.size(); i++) {
                    if (isSubstring(song, items[i])) {
                        same++;
                    }
                }
                res_songs=new String[same];

                for (int i = 0; i < musicFiles.size(); i++) {
                    int j=0;
                    if (isSubstring(song, items[i])) {
                        res_songs[j]=items[i];
                        j++;
                    }
                }

                MusicListAdapter musicListAdapter = new MusicListAdapter(getActivity(), res_songs);
                ResList.setAdapter(musicListAdapter);

            }
        });

        return v;
    }

    static boolean isSubstring(String s1, String s2) {
        int M = s1.length();
        int N = s2.length();

        /* A loop to slide pat[] one by one */
        for (int i = 0; i <= N - M; i++) {
            int j;

            /* For current index i, check for
            pattern match */
            for (j = 0; j < M; j++)
                if (s2.charAt(i + j) != s1.charAt(j))
                    break;

            if (j == M)
                return true;
        }

        return false;
    }



}
