package app.com.digitallearning.TeacherModule.Curriculum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.curriculumdescription, container, false);
        registerResources(rootview);
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





        return rootview;
    }

    public void registerResources(View rootview){


    }

}
