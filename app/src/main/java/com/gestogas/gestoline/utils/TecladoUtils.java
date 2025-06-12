package com.gestogas.gestoline.utils;

import android.app.Activity;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

public class TecladoUtils {

    public static void handleTouchEvent(Activity activity, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View currentFocus = activity.getCurrentFocus();

            if (currentFocus instanceof EditText) {
                Rect outRect = new Rect();
                currentFocus.getGlobalVisibleRect(outRect);

                if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    View touchedView = findViewAtPosition(activity, (int) event.getRawX(), (int) event.getRawY());
                    // Solo ocultar el teclado si NO tocó otro EditText
                    if (!(touchedView instanceof EditText)) {
                        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
                        if (imm != null) {
                            imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
                        }
                        currentFocus.clearFocus();
                    }
                }
            }
        }
    }

    private static View findViewAtPosition(Activity activity, int x, int y) {
        View rootView = activity.getWindow().getDecorView().getRootView();
        return findViewAt(rootView, x, y);
    }

    private static View findViewAt(View view, int x, int y) {
        if (!(view instanceof ViewGroup)) {
            return view;
        }

        ViewGroup group = (ViewGroup) view;
        for (int i = 0; i < group.getChildCount(); i++) {
            View child = group.getChildAt(i);
            Rect rect = new Rect();
            child.getGlobalVisibleRect(rect);

            if (rect.contains(x, y)) {
                return findViewAt(child, x, y);
            }
        }

        return view;
    }
}
