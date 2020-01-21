package com.example.imagedealwith.Model;

public class ImageState {

    private int StateID;
    private int StateImageRes;

    public ImageState(int stateID, int stateImageRes) {
        StateID = stateID;
        StateImageRes = stateImageRes;
    }

    public int getStateID() {
        return StateID;
    }

    public int getStateImageRes() {
        return StateImageRes;
    }
}