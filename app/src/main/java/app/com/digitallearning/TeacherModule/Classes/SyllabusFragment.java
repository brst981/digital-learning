package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/27/16.
 */
public class SyllabusFragment extends Fragment{
    View rootview;
    RippleView ripple_edit_save;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_syllabus, container, false);
        ripple_edit_save=(RippleView)rootview.findViewById(R.id.ripple_edit_save);
        ripple_edit_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getFragmentManager().popBackStackImmediate();
            }
        });



        return rootview;
    }


}
