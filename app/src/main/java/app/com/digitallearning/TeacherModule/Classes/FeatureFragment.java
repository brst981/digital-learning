package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/30/16.
 */
public class FeatureFragment extends Fragment {
    View rootview;
    RippleView ripple_main,ripple_main1,ripple_main2,ripple_main3,ripple_buttonSave;
    CheckBox checkbox1,checkbox2,checkbox3,checkbox4;
    public static String featurevalue;
    int c1,c2,c3,c4;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.feature_fragment, container, false);
        ripple_main=(RippleView)rootview.findViewById(R.id.ripple_main);
        ripple_main1=(RippleView)rootview.findViewById(R.id.ripple_main1);
        ripple_main2=(RippleView)rootview.findViewById(R.id.ripple_main2);
        ripple_main3=(RippleView)rootview.findViewById(R.id.ripple_main3);
        ripple_buttonSave=(RippleView)rootview.findViewById(R.id.ripple_buttonSave);

        checkbox1=(CheckBox)rootview.findViewById(R.id.checkbox1) ;
        checkbox2=(CheckBox)rootview.findViewById(R.id.checkbox2) ;
        checkbox3=(CheckBox)rootview.findViewById(R.id.checkbox3) ;
        checkbox4=(CheckBox)rootview.findViewById(R.id.checkbox4) ;


        ripple_main.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox1.setVisibility(View.VISIBLE);
                c1=1;
            }
        });

        ripple_main1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox2.setVisibility(View.VISIBLE);
                c2=1;
            }
        });

        ripple_main2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox3.setVisibility(View.VISIBLE);
                c3=1;
            }
        });

        ripple_main3.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                checkbox4.setVisibility(View.VISIBLE);
                c4=1;
            }
        });


        ripple_buttonSave.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {


            }
        });

        return rootview;
    }

}
