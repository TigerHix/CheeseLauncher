package me.cheesy.cheeselauncher;

import android.content.Context;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class HoverButton extends PopupWindow {
    private final Context mContext;

    public HoverButton(Context ctx) {
        mContext = ctx;
        init();
    }

    public void init() {
        // Create layout and the button.
        LinearLayout layout = new LinearLayout(mContext);
        Button button = new Button(mContext);
        button.setText("M");
        layout.addView(button);
        setContentView(layout);

        // Set dimensions.
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public Context getContext() { return mContext; }
}
