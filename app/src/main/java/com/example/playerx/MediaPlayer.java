package com.example.playerx;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.playerx.Fragments.HomeFragment;
import com.example.playerx.utils.SongClass;

import java.io.File;

import static com.example.playerx.Fragments.HomeFragment.items;
import static com.example.playerx.Fragments.HomeFragment.musicFiles;

public class MediaPlayer extends AppCompatActivity {
    TextView songname;
    ImageView SongImage,prev,replay,play_pause,forward,next,playlist,addToPlayList;
    int selected_song;
    android.media.MediaPlayer mediaPlayer;
    Runnable runnable;
    Handler handler;
    SeekBar seekBar;
    String curr_song;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        songname=findViewById(R.id.songName);
        SongImage=findViewById(R.id.songImage);
        prev=findViewById(R.id.previous);
        replay=findViewById(R.id.replay_10);
        play_pause=findViewById(R.id.play_pause);
        forward=findViewById(R.id.forward_10);
        next=findViewById(R.id.next);
        playlist=findViewById(R.id.playlist);
        addToPlayList=findViewById(R.id.add_to_playlist);
        seekBar=findViewById(R.id.seek);
        handler=new Handler();
        SongImage.setImageResource(R.drawable.hp3);

        selected_song=getIntent().getIntExtra("song_position",0);
        Toast.makeText(this, "You Selected song"+curr_song , Toast.LENGTH_SHORT).show();

        startplaying( selected_song);

        mediaPlayer.setOnCompletionListener(new android.media.MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(android.media.MediaPlayer mp) {
                Toast.makeText(getApplicationContext(), "finished", Toast.LENGTH_SHORT).show();
                if (selected_song>=musicFiles.size()-1){
                    selected_song=0;
                }else selected_song++;
                songname.setText(getCurr_song(selected_song));
                startplaying(selected_song);
            }
        });
        play_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    play_pause.setImageResource(R.drawable.ic_play_arrow_black_24dp);
                }else{
                    mediaPlayer.start();
                    play_pause.setImageResource(R.drawable.ic_pause_black_24dp);
                    changeseekbar();
                }
            }
        });
        forward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()+5000);
            }
        });
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(mediaPlayer.getCurrentPosition()-5000);
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selected_song>0)
                {
                    selected_song--;
                }else selected_song=musicFiles.size()-1;
                songname.setText(getCurr_song(selected_song));
                startplaying(selected_song);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selected_song>=musicFiles.size()-1)
                {
                    selected_song=0;
                }else selected_song++;
                songname.setText(getCurr_song(selected_song));
                startplaying(selected_song);
            }
        });


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public String getCurr_song(int selected_song){
        curr_song=items.get(selected_song).getSong();
        return curr_song;
    }

    public void startplaying(int song){
        songname.setText(getCurr_song(selected_song));
        if(mediaPlayer==null){
            mediaPlayer = android.media.MediaPlayer.create(this, Uri.fromFile(musicFiles.get(song)));
        }
        if( mediaPlayer!=null && mediaPlayer.isPlaying()) {
            mediaPlayer.release();
            mediaPlayer = android.media.MediaPlayer.create(this, Uri.fromFile(musicFiles.get(song)));
        }

        mediaPlayer.setOnPreparedListener(new android.media.MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(android.media.MediaPlayer mp) {
                seekBar.setMax(mediaPlayer.getDuration());
                mediaPlayer.start();
                seekBar.setProgress(0);
                changeseekbar();
            }
        });

    }




    private void changeseekbar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());
        if(mediaPlayer.isPlaying()){
            runnable=new Runnable() {
                @Override
                public void run() {
                    changeseekbar();
                }

            };
            handler.postDelayed(runnable,1000);
        }
    }

}
