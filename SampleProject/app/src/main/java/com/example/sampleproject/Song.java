package com.example.sampleproject;

import com.example.sampleproject.R;

public class Song
{
    private String songName;
    private String artist;
    private String hint;
    private int coverArt;
    private int tune;

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

}