package app.com.digitallearning.TeacherModule.Curriculum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/6/16.
 */
public class CurriculumDescription extends Fragment {
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.curriculumdescription, container, false);
        registerResources(rootview);





        return rootview;
    }

    public void registerResources(View rootview){


    }

}
