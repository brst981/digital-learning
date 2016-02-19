package app.com.digitallearning.TeacherModule.Schedule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class ScheduleDescriptionFragment  extends Fragment {
    View rootview;
    RippleView ripple_edit_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.schedule_desc_fragment, container, false);
        registerResources(rootview);
        ripple_edit_save=(RippleView)rootview.findViewById(R.id.ripple_edit_save);





        return rootview;
    }

    public void registerResources(View rootview){


    }

}
