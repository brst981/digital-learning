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
 * Created by ${PSR} on 2/3/16.
 */
public class Resource_View extends Fragment {
    View rootview;
    TextView headerTitle,textdescription;
    String textHeader,title,description;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.resource_view, container, false);
        textdescription=(TextView)rootview.findViewById(R.id.textdescription) ;

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);
        title=getArguments().getString("title");
        description=getArguments().getString("description");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText(title);
        textdescription.setText(description);
        initData();

        return rootview;

    }

    private void initData() {
        textHeader =title;




    }

}
