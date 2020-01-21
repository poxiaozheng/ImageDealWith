package com.example.imagedealwith.View;

import android.content.Context;
import android.support.v7.widget.AppCompatImageButton;
import android.util.AttributeSet;
import android.util.Log;
import com.example.imagedealwith.Model.ImageState;
import java.util.HashMap;

public class MultiStateImageButton extends AppCompatImageButton {

    private HashMap<Integer, ImageState> states = new HashMap<>();

    private OnStateChanged StateChangedHandler;

    private int CurrentState = -1;

    public MultiStateImageButton(Context context) {
        super(context);
    }

    public MultiStateImageButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MultiStateImageButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetStateChangedHandler(OnStateChanged handler) {
        StateChangedHandler = handler;
    }

    public void SetStates(ImageState ...SomeStates) {
        for(ImageState EachState : SomeStates) {
            states.put(EachState.getStateID(), EachState);
            if(CurrentState == -1) {
                ShowState(1);
            }
        }
    }

    public void ShowState(int WantToShowStateID) {
        ImageState WantState = states.get(WantToShowStateID);
        if(WantState == null) {
            Log.e("MultiStateImageButton", "找不到想要展示的State: " + WantToShowStateID);
            return;
        }
        setImageResource(WantState.getStateImageRes());
        CurrentState = WantToShowStateID;
        if(StateChangedHandler != null) {
            StateChangedHandler.Handle(WantToShowStateID);
        }
    }

    public interface OnStateChanged {
        void Handle(int NewStateID);
    }

    public int GetCurrentState() {
        return CurrentState;
    }

}
