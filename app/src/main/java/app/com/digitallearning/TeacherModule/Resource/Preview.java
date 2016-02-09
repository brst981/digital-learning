package app.com.digitallearning.TeacherModule.Resource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/6/16.
 */
public class Preview extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.resource_edit, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

      //  headerTitle.setText("Add Class Resource");
        initData();
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

}
