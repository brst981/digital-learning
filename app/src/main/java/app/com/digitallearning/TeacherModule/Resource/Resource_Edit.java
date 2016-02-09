package app.com.digitallearning.TeacherModule.Resource;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Resource_Edit extends Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader;
    RippleView ripple_preview_lesson;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.resource_edit, container, false);
        ripple_preview_lesson=(RippleView)rootview.findViewById(R.id.ripple_preview_lesson);
        ripple_preview_lesson.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Preview preview = new Preview();
                fragmentTransaction.replace(R.id.container, preview).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Add Class Resource");
        initData();
        return rootview;

    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

}
