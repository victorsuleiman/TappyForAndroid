package com.example.sampleproject.Models;

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
        setSongName(songName);
        setArtist(artist);
        setTune(tune);
        setCoverArt(coverArt);
        setHint(hint);

    }

    public String getSongName() {
        return songName;
    }
    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }
    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getTune() {
        return tune;
    }
    public void  setTune(int tune) { this.tune = tune; }



    public int getCoverArt() {
        return coverArt;
    }
    public void setCoverArt(int coverArt) {
        this.coverArt = coverArt;
    }
    public String getHint() {
        return hint;
    }
    public void setHint(String hint) {
        this.hint = hint;
    }


    //toString used for testing
    @NonNull
    @Override
    public String toString() {
        return songName + " " + artist + " " + tune + " " + coverArt + " " + hint;
    }
}