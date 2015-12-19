package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/19/15.
 */
public class ClassesDetailFragment extends Fragment {
    View rootview;

    public static ClassesDetailFragment newInstance() {
        ClassesDetailFragment mFragment = new ClassesDetailFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_class_details, container, false);
        return rootview;
    }
}
