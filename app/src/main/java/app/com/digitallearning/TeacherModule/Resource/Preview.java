package app.com.digitallearning.TeacherModule.Resource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
    TextView headerTitle,edt_description_lesson;
    String textHeader,title ,des;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.preview_res, container, false);
        edt_description_lesson=(TextView)rootview.findViewById(R.id.edt_description_lesson);
        title=getArguments().getString("title");
        Log.e("titleres",""+title);
        des=getArguments().getString("des");

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText(title);
        initData();
        edt_description_lesson.setText(des);
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";
//



    }

}
