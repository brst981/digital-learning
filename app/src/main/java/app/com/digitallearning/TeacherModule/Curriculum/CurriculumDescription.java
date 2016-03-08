package app.com.digitallearning.TeacherModule.Curriculum;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/6/16.
 */
public class CurriculumDescription extends Fragment {
    View rootview;
    RippleView ripplesave;
    EditText description;
    String textDescription;
    RelativeLayout zoom;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.curriculumdescription, container, false);

        description=(EditText)rootview.findViewById(R.id.description);
        textDescription=getArguments().getString("textDescription");
        description.setText(textDescription);
        ripplesave=(RippleView)rootview.findViewById(R.id.ripplesave);
        ripplesave.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                CurriculumFragment.curriculumdes=description.getText().toString();
                getFragmentManager().popBackStackImmediate();
            }
        });

        imageButtonZoomIn = (ImageButton) rootview.findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton)rootview. findViewById(R.id.img_zoom_out);
        zoom = (RelativeLayout) rootview.findViewById(R.id.zoom);


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
        zoom.setPivotX(pivot.x);
        zoom.setPivotY(pivot.y);
        zoom.setScaleX(scaleX);
        zoom.setScaleY(scaleY);
    }
}
