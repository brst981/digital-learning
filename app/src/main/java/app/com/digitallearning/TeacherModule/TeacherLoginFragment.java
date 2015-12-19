package app.com.digitallearning.TeacherModule;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class TeacherLoginFragment extends Fragment {
    RippleView rippleViewLogin;
    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_teacher_login, container, false);
        rippleViewLogin=(RippleView)rootview.findViewById(R.id.ripple_login);
        rippleViewLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassFragment classFragment = new ClassFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();



            }
        });

        return rootview;
    }
}
