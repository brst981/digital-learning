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
 * Created by ${ShalviSharma} on 12/28/15.
 */
public class ResourceDetailFragment extends Fragment {
    View rootview;

    TextView headerTitle;
    String txtHeader;
    int positionValue;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_resource_detail, container, false);


      /*  if (getArguments() != null) {
            Bundle bundle = this.getArguments();
            txtHeader = bundle.getString("HEADER_TEXT");
            positionValue = bundle.getInt("POSITION");
        }*/


      /*  Log.e("txtHeader",""+txtHeader);
        Log.e("positionValue",""+positionValue);*/
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("");

        return rootview;
    }


}
