package app.com.digitallearning.TeacherModule.Curriculum;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/21/15.
 */
public class CurriculumFragment extends Fragment {
    View rootview;
    TextView headerTitle;
    String title;
    RelativeLayout relative_topic;
    RippleView ripple_teacher_country,ripple_teacher_Description,ripple_teacher_schedule,ripple_teacher_syllabus,ripple_GradeTo;
    final CharSequence[] items = {
            "India", "Australia", "Canada","America","Newzealand","abcd","Sunday","Every Day","Every Weekday"};

    final CharSequence[] items1 = {
            "Other", "K", "1","2","3","4","5","6","7","8","9","10","11","12","HigherEd"};
    final CharSequence[] items2 = {
            "Other", "K", "1","2","3","4","5","6","7","8","9","10","11","12","HigherEd"};
    final CharSequence[] library = {"Personal","School","Cummunity"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_curriculum, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        relative_topic=(RelativeLayout)rootview.findViewById(R.id.relative_topic);
        ripple_teacher_country=(RippleView)rootview.findViewById(R.id.ripple_teacher_country);
        ripple_teacher_schedule=(RippleView)rootview.findViewById(R.id.ripple_teacher_schedule);
        ripple_teacher_Description=(RippleView)rootview.findViewById(R.id.ripple_teacher_Description);
        ripple_GradeTo=(RippleView)rootview.findViewById(R.id.ripple_GradeTo);
        ripple_teacher_syllabus=(RippleView)rootview.findViewById(R.id.ripple_teacher_syllabus);


        ripple_GradeTo.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items2, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+items2[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });



        relative_topic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopicFragment topicFragment = new TopicFragment();
                fragmentTransaction.replace(R.id.container, topicFragment).addToBackStack(null);
                fragmentTransaction.commit();*/
            }
        });


        ripple_teacher_schedule.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(library, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+library[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                LibraryFragment libraryFragment = new LibraryFragment();
                fragmentTransaction.replace(R.id.container, libraryFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });*/

        ripple_teacher_syllabus.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+items1[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


        ripple_teacher_country.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+items[item]);
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        ripple_teacher_Description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CurriculumDescription curriculumDescription = new CurriculumDescription();
                fragmentTransaction.replace(R.id.container, curriculumDescription).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        initData();

        return rootview;
    }

    private void initData() {

        headerTitle.setText("Curriculum");
    }
}