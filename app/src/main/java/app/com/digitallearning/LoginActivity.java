package app.com.digitallearning;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.andexert.library.RippleView;

import app.com.digitallearning.StudentModule.StudentLoginActivity;
import app.com.digitallearning.TeacherModule.TeacherLoginActivity;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class LoginActivity extends Activity {
    RippleView rippleViewTeacher,rippleViewStudent;
    String schoolID,schoolName;
    TextView text_school_name;
    ImageButton imageButtonZoomIn, imageButtonZoomOut,back;
    RelativeLayout rellogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        rellogin=(RelativeLayout)findViewById(R.id.rellogin) ;
        schoolID=getIntent().getStringExtra("SchoolID");
        Log.e("schoolID",""+schoolID);
        schoolName=getIntent().getStringExtra("SchoolName");
        Log.e("schoolName",""+schoolName);
        rippleViewTeacher = (RippleView)findViewById(R.id.ripple_teacher);
        back=(ImageButton)findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
        imageButtonZoomIn = (ImageButton) findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) findViewById(R.id.img_zoom_out);
        rippleViewStudent = (RippleView)findViewById(R.id.ripple_student);
        text_school_name=(TextView)findViewById(R.id.text_school_name);
        text_school_name.setText(schoolName);
        rippleViewTeacher.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(LoginActivity.this, TeacherLoginActivity.class);
                intent.putExtra("SchoolName",schoolName);
                intent.putExtra("SchoolID",schoolID);
                startActivity(intent);
                finish();

            }
        });

        rippleViewStudent.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                Intent intent = new Intent(LoginActivity.this, StudentLoginActivity.class);
                startActivity(intent);
            }
        });
        imageButtonZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1.5f, 1.5f, new PointF(0, 0));
            }
        });

        imageButtonZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoom(1f, 1f, new PointF(0, 0));
            }
        });


    }




    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rellogin.setPivotX(pivot.x);
        rellogin.setPivotY(pivot.y);
        rellogin.setScaleX(scaleX);
        rellogin.setScaleY(scaleY);
    }






}
