package app.com.digitallearning.StudentModule;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Info  extends Fragment {
    View rootview;
    LinearLayout deslin;
    ImageButton back;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.info, container, false);
        back=(ImageButton)rootview.findViewById(R.id.back);
        deslin=(LinearLayout)rootview.findViewById(R.id.deslin) ;
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1.5f, 1.5f, new PointF(0, 0));
            }
        });

        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
            }
        });



        return rootview;

    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        deslin.setPivotX(pivot.x);
        deslin.setPivotY(pivot.y);
        deslin.setScaleX(scaleX);
        deslin.setScaleY(scaleY);
    }



}
