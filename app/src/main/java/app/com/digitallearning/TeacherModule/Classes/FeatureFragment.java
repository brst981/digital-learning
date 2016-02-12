package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/30/16.
 */
public class FeatureFragment extends Fragment {
    View rootview;
    CheckBox checkbox1,checkbox2,checkbox3,checkbox4;
    public static String featurevalue;
    int c1,c2,c3,c4;
    ImageButton back;
     public  static String cal,chat,grades,stu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.feature_fragment, container, false);
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        checkbox1=(CheckBox)rootview.findViewById(R.id.checkbox1) ;
        checkbox2=(CheckBox)rootview.findViewById(R.id.checkbox2) ;
        checkbox3=(CheckBox)rootview.findViewById(R.id.checkbox3) ;
        checkbox4=(CheckBox)rootview.findViewById(R.id.checkbox4) ;

/*
        ripple_main.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox1.setVisibility(View.VISIBLE);
                c1=1;
                if (checkbox1.isChecked()) {
                      cal="Calender";
                    Log.e("cal",""+cal);
                }
                else if (!checkbox1.isChecked()) {
                    cal="";

                }


            }
        });

        ripple_main1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox2.setVisibility(View.VISIBLE);
                c2=1;

                if (checkbox2.isChecked()) {
                     chat="Chat";
                    Log.e("chat",""+chat);
                }
                else if (!checkbox2 .isChecked()) {
                    chat="";

                }

            }
        });

        ripple_main2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox3.setVisibility(View.VISIBLE);
                c3=1;
                if (checkbox3.isChecked()) {
                     grades="Grades";
                    Log.e("grades",""+grades);
                }
                else if (!checkbox3 .isChecked()) {
                    grades="";

                }
            }
        });

        ripple_main3.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox4.setVisibility(View.VISIBLE);
                c4=1;

                if (checkbox4.isChecked()) {
                    stu="Student Tab";
                    Log.e("stu",""+stu);
                }
                else if (!checkbox4 .isChecked()) {
                    stu="";

                }

            }
        });


        ripple_buttonSave.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
             *//*   featurevalue=cal+","+chat+","+grades+","+stu;
                Log.e("FeatureValue",""+featurevalue);*//*

                getFragmentManager().popBackStackImmediate();
            }
        });*/


        return rootview;
    }

}
