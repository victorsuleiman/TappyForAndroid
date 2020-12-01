package com.example.sampleproject.SupportClasses;

import android.widget.Button;

public class ButtonAttribute {
    private Button button;
    private int highlightColor;
    private int darkColor;
    private String buttonName;
    private int sound;



    public ButtonAttribute(Button button, int highlightColor, int darkColor, String buttonName, int sound) {
        this.button = button;
        this.highlightColor = highlightColor;
        this.darkColor = darkColor;
        this.buttonName = buttonName;
        this.sound = sound;
    }

    public Button getButton() {
        return button;
    }

    public int getHighlightColor() {
        return highlightColor;
    }

    public int getDarkColor() {
        return darkColor;
    }

    public String getButtonName() {
        return buttonName;
    }

    public int getSound() {
        return sound;
    }
}
