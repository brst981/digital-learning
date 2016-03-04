package app.com.digitallearning.Utill;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 3/3/16.
 */
public class DialogBuilder extends Dialog implements View.OnClickListener {
    private TextView art, theater, visual;
    private OnReceiveListner listner;
    private RecyclerView.ViewHolder viewHolder;
    private Context context;
    private String value;
    private int pos;

    /**
     * Create a Dialog window that uses the default dialog frame style.
     *
     * @param context The Context the Dialog is to run it.  In particular, it
     *                uses the window manager and theme in this context to
     *                present its UI.
     */
    public DialogBuilder(Context context, OnReceiveListner listner, RecyclerView.ViewHolder viewHolder, int pos) {
        super(context);

        this.listner = listner;
        this.viewHolder = viewHolder;
        this.context = context;
        this.pos = pos;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        setContentView(R.layout.dialogbuilder);
        art = (TextView) findViewById(R.id.art);
        theater = (TextView) findViewById(R.id.theater);
        visual = (TextView) findViewById(R.id.visual);

        art.setOnClickListener(this);
        theater.setOnClickListener(this);
        visual.setOnClickListener(this);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        dismiss();
        switch (v.getId()) {

            case R.id.art:
                value = "Art";
                if (listner != null)
                    listner.onReceive(viewHolder, pos, value);

                break;

            case R.id.theater:
                value = "Theater";
                if (listner != null)
                    listner.onReceive(viewHolder, pos, value);
                break;

            case R.id.visual:
                value = "Visual Art";
                if (listner != null)
                    listner.onReceive(viewHolder, pos, value);
                break;

        }
    }
}
