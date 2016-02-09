package app.com.digitallearning.StudentModule;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class StudentLoginFragment extends Fragment {
    RippleView rippleViewLogin;
    View rootview;
    ProgressDialog dlg;
    EditText edt_username, edt_pwd;
    ImageButton back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.activity_student_login, container, false);
        rippleViewLogin = (RippleView) rootview.findViewById(R.id.ripple_login);
        edt_username = (EditText) rootview.findViewById(R.id.edt_username);
        back=(ImageButton)rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        dlg = new ProgressDialog(getActivity());
        edt_pwd = (EditText) rootview.findViewById(R.id.edt_pwd);
        rippleViewLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                StudentClass studentClass = new StudentClass();
                fragmentTransaction.replace(R.id.container, studentClass).addToBackStack(null);
                fragmentTransaction.commit();


            }
        });

        return rootview;
    }
}