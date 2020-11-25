package com.example.sampleproject.Models;

public class TapManModel {
    int id;
    String word;
    String hint;

    public TapManModel(int id, String word, String hint) {
        this.id = id;
        this.word = word;
        this.hint = hint;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}
