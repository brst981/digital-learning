package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/22/15.
 */
public class DeleteClassFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    RippleView ripple_delete;


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_delete_class, container, false);
        ripple_delete=(RippleView)rootview.findViewById(R.id.ripple_delete);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
       // activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      //  activity.getSupportActionBar().invalidateOptionsMenu();
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Delete");

        ripple_delete.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

            }
        });

        return rootview;
    }


}
