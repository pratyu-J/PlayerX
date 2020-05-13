package com.example.playerx.Fragments;


import android.content.Intent;
import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import com.example.playerx.Adapters.MusicListAdapter;
import com.example.playerx.MediaPlayer;
import com.example.playerx.R;


import static com.example.playerx.Fragments.HomeFragment.musicFiles;


public class SearchFragment extends Fragment implements MenuItem.OnActionExpandListener, MusicListAdapter.OnSongClickListener {

    RecyclerView ResList;
    RecyclerView.LayoutManager layoutManager;


    MusicListAdapter musicListAdapter;

    public SearchFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_search, container, false);


        ResList = v.findViewById(R.id.res_list);
        layoutManager = new LinearLayoutManager(getActivity());
        musicListAdapter = new MusicListAdapter(getActivity(), HomeFragment.items,this);
        ResList.setLayoutManager(layoutManager);
        ResList.setAdapter(musicListAdapter);
        Toolbar toolbar = v.findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        toolbar.setTitle("Search ");

        setHasOptionsMenu(true);



        setMenuVisibility(true);


        return v;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        inflater.inflate(R.menu.search_expand, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.expSearch).getActionView();

        super.onCreateOptionsMenu(menu, inflater);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                musicListAdapter.getFilter().filter(newText);
                return false;
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.expSearch){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

   

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }


    @Override
    public void OnSongClick(int position) {
        Intent intent=new Intent(getActivity(),MediaPlayer.class);
        intent.putExtra("song_position",position);
        startActivity(intent);

    }
}
