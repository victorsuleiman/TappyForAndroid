package com.example.sampleproject.SupportClasses;

import android.widget.Button;

public class ButtonColor {
    private Button button;
    private int highlightColor;
    private int darkColor;
    private String buttonName;



    public ButtonColor(Button button, int highlightColor, int darkColor, String buttonName) {
        this.button = button;
        this.highlightColor = highlightColor;
        this.darkColor = darkColor;
        this.buttonName = buttonName;
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
}
