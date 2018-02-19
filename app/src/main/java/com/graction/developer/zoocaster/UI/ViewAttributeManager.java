package com.graction.developer.zoocaster.UI;

import android.app.Activity;
import android.content.Context;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Graction06 on 2018-02-06.
 */

public class ViewAttributeManager {
    private final static ViewAttributeManager instance = new ViewAttributeManager();
    private InputMethodManager inputMethodManager;

    public static ViewAttributeManager getInstance() {
        return instance;
    }

    public EditText setDoneOption(Context context, EditText editText, TextView.OnEditorActionListener listener) {
        editText.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean result = false;
                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:
                    case EditorInfo.IME_ACTION_NEXT:
                        result = listener.onEditorAction(v, actionId, event);
                        break;
                }
                return result;
            }
        });
        return editText;
    }

    public void showKeyboard(Context context, EditText editText) {
        inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(editText, 0);
    }

}
