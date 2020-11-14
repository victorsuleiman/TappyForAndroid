package com.example.sampleproject;

import com.example.sampleproject.R;

public class Song
{
    private String songName;
    private String artist;
    private String hintNo1;
    private String hintNo2;
    private int coverArt;
    private int tune;

    public Song() {
        songName = "In The End";
        artist = "Linkin Park";
        tune = R.raw.in_the_end_lp;
    }
    public Song(String songName, String artist, String hintNo1, String hintNo2, int tune)
    {
        this.songName = songName;
        this.artist = artist;
        this.hintNo1 = hintNo1;
        this.hintNo2 = hintNo2;
        this.tune = tune;
    }

    public String getSongName() {
        return songName;
    }

    public int getTune() {
        return tune;
    }


}