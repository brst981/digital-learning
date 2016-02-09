package app.com.digitallearning.TeacherModule.Classes;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.andexert.library.RippleView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassFragment;
import app.com.digitallearning.TeacherModule.Curriculum.CurriculumFragment;
import app.com.digitallearning.TeacherModule.Schedule.ScheduleFragment;

/**
 * Created by ${ShalviSharma} on 12/19/15.
 */
public class ClassesDetailFragment extends Fragment{
    View rootview;
    FloatingActionMenu menu_main;
    FloatingActionButton floatingActionButtonEdit,floatingActionButtonChange,floatingActionButtonDelete;
    TextView headerTitle;
    String title;
    RippleView rippleViewTeacherCollab,rippleViewCurriculum,ripple_teacher_syllabus,ripple_teacher_schedule;


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
        menu_main = (FloatingActionMenu)rootview.findViewById(R.id.menu_main);
        floatingActionButtonEdit=(FloatingActionButton)rootview.findViewById(R.id.menu_item_edit);
        floatingActionButtonChange=(FloatingActionButton)rootview.findViewById(R.id.menu_item_change_pic);
        floatingActionButtonDelete=(FloatingActionButton)rootview.findViewById(R.id.menu_item_delete);
        rippleViewTeacherCollab = (RippleView)rootview.findViewById(R.id.ripple_teacher_collaboration) ;
        rippleViewCurriculum = (RippleView)rootview.findViewById(R.id. ripple_teacher_curriculum) ;
        ripple_teacher_syllabus=(RippleView) rootview.findViewById(R.id.ripple_teacher_syllabus);
        ripple_teacher_schedule=(RippleView) rootview.findViewById(R.id.ripple_teacher_schedule);
        ripple_teacher_schedule.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ScheduleFragment scheduleFragment = new ScheduleFragment();
                fragmentTransaction.replace(R.id.container, scheduleFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        title= ClassFragment.titleheader;
        Log.e("DetailClasstitle",""+title);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);



        createCustomAnimation();
        initData();

        return rootview;
    }

    private void initData() {

        headerTitle.setText(title);


        floatingActionButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                EditClassFragment classFragment = new EditClassFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();*/
               /* Intent intent=new Intent(getActivity(),EditClassFragment.class);
                startActivity(intent);*/
            }
        });



        floatingActionButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ChangePictureFragment classFragment = new ChangePictureFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        floatingActionButtonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DeleteClassFragment classFragment = new DeleteClassFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


        rippleViewTeacherCollab.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TeacherCollabrationFragment classFragment = new TeacherCollabrationFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        rippleViewCurriculum.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CurriculumFragment classFragment = new CurriculumFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        ripple_teacher_syllabus.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager=getFragmentManager();
                FragmentTransaction fragmentTransaction=fragmentManager.beginTransaction();
                SyllabusFragment syllabusFragment=new SyllabusFragment();
                fragmentTransaction.replace(R.id.container, syllabusFragment).addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
    }

    private void createCustomAnimation() {
        AnimatorSet set = new AnimatorSet();

        ObjectAnimator scaleOutX = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleX", 1.0f, 0.2f);
        ObjectAnimator scaleOutY = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleY", 1.0f, 0.2f);

        ObjectAnimator scaleInX = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleX", 0.2f, 1.0f);
        ObjectAnimator scaleInY = ObjectAnimator.ofFloat(menu_main.getMenuIconView(), "scaleY", 0.2f, 1.0f);

        scaleOutX.setDuration(50);
        scaleOutY.setDuration(50);

        scaleInX.setDuration(150);
        scaleInY.setDuration(150);

        scaleInX.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                menu_main.getMenuIconView().setImageResource(menu_main.isOpened()
                        ? R.drawable.ic_edit : R.drawable.ic_close);
            }
        });

        set.play(scaleOutX).with(scaleOutY);
        set.play(scaleInX).with(scaleInY).after(scaleOutX);
        set.setInterpolator(new OvershootInterpolator(2));

        menu_main.setIconToggleAnimatorSet(set);
    }
}
