package app.com.digitallearning.TeacherModule.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class ScheduleDescriptionFragment  extends Fragment {
    View rootview;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.schedule_desc_fragment, container, false);
        registerResources(rootview);





        return rootview;
    }

    public void registerResources(View rootview){


    }

}
