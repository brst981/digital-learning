package app.com.digitallearning.TeacherModule.Students;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 3/16/16.
 */
public class ArchieveStudentDetail extends Fragment {
    View rootview;
    ImageView image;
    TextView headerTitle;
    TextView strname,stremail,struserid,strpassword,strstatus;
    String name,email,userId,password,status;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.archieve_student_detail, container, false);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Student Details");

        name=getArguments().getString("name");
        email=getArguments().getString("email");
        userId=getArguments().getString("userId");
        password=getArguments().getString("password");
        status=getArguments().getString("status");
        Log.e("Status",""+status);

        strname=(TextView)rootview.findViewById(R.id.strname);
        stremail=(TextView)rootview.findViewById(R.id.stremail);
        struserid=(TextView)rootview.findViewById(R.id.struserid);
        strpassword=(TextView)rootview.findViewById(R.id.strpassword);
        strstatus=(TextView)rootview.findViewById(R.id.strstatus);

        strname.setText(name);
        stremail.setText(email);
        struserid.setText(userId);
        strpassword.setText(password);

        if(status.equals("1")){

            strstatus.setText("Online");
        }
        else if(status.equals("0")){
            strstatus.setText("Offline");
        }



        return rootview;
    }
}