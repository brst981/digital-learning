package app.com.digitallearning.StudentModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/29/16.
 */
public class StudentEnroll extends Fragment {
    View rootview;

    public static StudentEnroll newInstance() {
        StudentEnroll mFragment = new StudentEnroll();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_student_enroll, container, false);
        return  rootview;
    }
}