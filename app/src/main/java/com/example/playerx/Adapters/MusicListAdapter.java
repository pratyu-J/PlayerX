package com.example.playerx.Adapters;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.playerx.R;
import com.example.playerx.utils.SongClass;

import java.util.ArrayList;
import java.util.Collection;

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> implements Filterable {

    private final Activity context;
    private final ArrayList<SongClass> songname;
    private final ArrayList<SongClass> fullList;

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView song;
        TextView info;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.songimage);
            song = itemView.findViewById(R.id.music_info);
            info = itemView.findViewById(R.id.info);
        }
    }



    public MusicListAdapter(Activity context1, ArrayList<SongClass> songname) {
        this.context = context1;
        this.songname = songname;
        fullList = new ArrayList<>(songname);

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
        ViewHolder vh =new ViewHolder(root);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull MusicListAdapter.ViewHolder holder, int position) {
        SongClass songClass = songname.get(position);

        holder.song.setText(songClass.getSong());
        holder.info.setText(songClass.getInfo());
        holder.imageView.setImageResource(R.drawable.ic_headset_black_24dp);


    }

    @Override
    public int getItemCount() {
        return songname.size();
    }
}
