package me.cheesy.cheeselauncher;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

public class HoverButton extends PopupWindow {
    private final Context mContext;

    public HoverButton(Context ctx) {
        mContext = ctx;
        init();
    }

    public void showHoverMenu() {
        ListView optionsListView = new ListView(mContext);
        String[] values = {"Credits", "Launcher Options"};
        optionsListView.setAdapter(new ArrayAdapter<String>(mContext,
                android.R.layout.simple_list_item_1, android.R.id.text1, values));

        optionsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                String text = textView.getText().toString();

                if (text.equals("Credits")) {
                    new AlertDialog.Builder(mContext).setTitle("Credits")
                            .setMessage("Built by @sliceofcode\nSpecial thanks to @zhuowei for BlockLauncher\n\n" +
                            "Uses FMOD!\nThis app is open source at: https://github.com/sliceofcode/cheeselauncher/")
                            .show();
                } else if (text.equals("Launcher Options")) {
                    Toast.makeText(mContext, "Not yet.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new AlertDialog.Builder(mContext).setTitle("HoverMenu")
                .setView(optionsListView).show();

    }

    public void init() {
        // Create layout and the button.
        LinearLayout layout = new LinearLayout(mContext);
        Button button = new Button(mContext);
        button.setText("M");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHoverMenu();
            }
        });
        layout.addView(button);
        setContentView(layout);

        // Set dimensions.
        setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
    }

    public Context getContext() { return mContext; }
}
