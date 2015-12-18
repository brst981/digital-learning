package app.com.digitallearning;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.andexert.library.RippleView;

import app.com.digitallearning.StudentModule.StudentLoginActivity;
import app.com.digitallearning.TeacherModule.TeacherLoginActivity;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class LoginActivity extends Activity {
    RippleView rippleViewTeacher,rippleViewStudent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rippleViewTeacher = (RippleView)findViewById(R.id.ripple_teacher);
        rippleViewStudent = (RippleView)findViewById(R.id.ripple_student);
        rippleViewTeacher.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(LoginActivity.this, TeacherLoginActivity.class);
                startActivity(intent);

            }
        });

        rippleViewStudent.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(LoginActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
