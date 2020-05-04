package com.example.playerx.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.playerx.R;

public class MusicListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final String[] songname;

    public MusicListAdapter(Activity context1, String[] songname) {
        super(context1, R.layout.music_list_layout, songname);
        this.context = context1;
        this.songname = songname;
    }

    public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.music_list_layout,null, true);
        TextView name = rowView.findViewById(R.id.music_info);

        name.setText(songname[position]);
        return rowView;
    }
}
