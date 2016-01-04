package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class ChangePictureFragment extends Fragment {
    View rootview;
    TextView headerTitle;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_change_pic, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Change Picture");
        return rootview;
    }
}