package com.example.playerx.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playerx.R;
import com.example.playerx.utils.SongClass;

import java.util.ArrayList;
import java.util.Collection;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> implements Filterable {

    private final Activity context;
    private final ArrayList<SongClass> songname;
    private final ArrayList<SongClass> fullList;
    private final OnSongClickListener onSongClickListener;
    public static ArrayList<SongClass> likedSongs = new ArrayList<>();

    public interface OnSongClickListener{
        void OnSongClick(String name);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        OnSongClickListener onSongClickListener;
        ImageView imageView;
        TextView song;
        TextView info;
        ImageView likedEmpty;
        ImageView likedFill;
        LinearLayout listlayout;
        RelativeLayout relativeHeart;

        public ViewHolder(@NonNull View itemView,OnSongClickListener onSongClickListener) {
            super(itemView);

            imageView = itemView.findViewById(R.id.songimage);
            song = itemView.findViewById(R.id.music_info);
            info = itemView.findViewById(R.id.info);
            listlayout=itemView.findViewById(R.id.listlayout);
            likedEmpty = itemView.findViewById(R.id.like_btn_empty);
            likedFill = itemView.findViewById(R.id.like_btn_filled);
            relativeHeart = itemView.findViewById(R.id.relative_heart);

            this.onSongClickListener=onSongClickListener;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onSongClickListener.OnSongClick(this.song.getText().toString());
        }
    }



    public MusicListAdapter(Activity context1, ArrayList<SongClass> songname,OnSongClickListener onSongClickListener) {
        this.context = context1;
        this.songname = songname;
        fullList = new ArrayList<>(songname);
        this.onSongClickListener=onSongClickListener;


    }



    public Filter getFilter(){
        return exFilter;
    }


    Filter exFilter =new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            ArrayList<SongClass> filterList =new ArrayList<>();

            if(constraint == null || constraint.length()==0){
                filterList.addAll(fullList);
            }
            else{
                String filterPattern =constraint.toString().toLowerCase().trim();

                for(SongClass items: fullList){
                    if(items.getSong().toLowerCase().contains(filterPattern)){
                        filterList.add(items);
                    }
                }
            }

            FilterResults res = new FilterResults();
            res.values = filterList;
            return res;


        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            songname.clear();
            songname.addAll((Collection<? extends SongClass>) results.values);
            notifyDataSetChanged();
        }
    };

    /*public View getView(int position, View view, ViewGroup parent){

        LayoutInflater inflater = context.getLayoutInflater();
        View rowView = inflater.inflate(R.layout.music_list_layout,null, true);
        TextView name = rowView.findViewById(R.id.music_info);

        name.setText(songname.get(position));
        return rowView;
    }*/

    @NonNull
    @Override
    public MusicListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.music_list_layout, parent, false);
        ViewHolder vh =new ViewHolder(root,onSongClickListener);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MusicListAdapter.ViewHolder holder, final int position) {
        final SongClass songClass = songname.get(position);

        holder.song.setText(songClass.getSong());
        holder.info.setText(songClass.getInfo());
        holder.imageView.setImageResource(R.drawable.ic_headset_black_24dp);
        holder.likedEmpty.setImageResource(R.drawable.favorite_empty);
        holder.likedFill.setImageResource(R.drawable.ic_favorite_black_24dp);
        holder.relativeHeart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (holder.likedEmpty.getVisibility() == View.VISIBLE){
                    holder.likedEmpty.setVisibility(View.GONE);
                    holder.likedFill.setVisibility(View.VISIBLE);
                    likedSongs.add(songname.get(position));

                }
                else{
                    holder.likedEmpty.setVisibility(View.VISIBLE);
                    holder.likedFill.setVisibility(View.GONE);
                    likedSongs.remove(songname.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return songname.size();
    }


}

