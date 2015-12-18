package app.com.digitallearning.TeacherModule;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class TeacherLoginActivity extends Activity {
    RippleView rippleViewLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        rippleViewLogin=(RippleView)findViewById(R.id.ripple_login);
        rippleViewLogin.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(TeacherLoginActivity.this,NavigationActivity.class);
                startActivity(intent);


            }
        });
    }
}
