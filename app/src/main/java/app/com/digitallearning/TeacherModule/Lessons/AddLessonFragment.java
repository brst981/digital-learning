package app.com.digitallearning.TeacherModule.Lessons;

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

import java.text.SimpleDateFormat;
import java.util.Date;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/29/15.
 */
public class AddLessonFragment extends Fragment{
    View rootview;
    TextView headerTitle,txtDate;
    RippleView rippleViewPreview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_add_lesson, container, false);
        rippleViewPreview=(RippleView)rootview.findViewById(R.id.ripple_preview_lesson) ;
        txtDate=(TextView)rootview.findViewById(R.id.txt_date_lesson) ;

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
     //   getActivity().getActionBar().setHomeButtonEnabled(false);

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Add Lesson");


        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        txtDate.setText(dateFormat.format(date));
        rippleViewPreview.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PreviewLessonFragment classFragment = new PreviewLessonFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        return rootview;
    }
}
