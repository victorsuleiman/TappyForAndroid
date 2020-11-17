package com.example.sampleproject;

import androidx.annotation.NonNull;

import com.example.sampleproject.R;

public class Song
{
    private String songName;
    private String artist;
    private int tune;
    private int coverArt;
    private String hint;

    public Song() {
        songName = "In The End";
        artist = "Linkin Park";
        tune = R.raw.in_the_end_lp;

    }
    public Song(String songName, String artist, int tune, int coverArt, String hint)
    {
        this.songName = songName;
        this.artist = artist;
        setTune(tune);
        this.coverArt = coverArt;
        this.hint = hint;

    }

    public String getSongName() {
        return songName;
    }

    public int getTune() {
        return tune;
    }
    public void  setTune(int tune) { this.tune = tune; }

    public String getArtist() {
        return artist;
    }

    public int getCoverArt() {
        return coverArt;
    }

    public String getHint() {
        return hint;
    }


    //toString used for testing
    @NonNull
    @Override
    public String toString() {
        return songName + " " + artist + " " + tune + " " + coverArt + " " + hint;
    }
}