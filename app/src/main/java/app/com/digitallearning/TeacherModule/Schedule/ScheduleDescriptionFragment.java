package app.com.digitallearning.TeacherModule.Schedule;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/4/16.
 */
public class ScheduleDescriptionFragment  extends FragmentActivity {
    View rootview;
    RippleView ripple_edit_save;
    EditText scheduledesc;
    int viewsch,addsch;
    String desp;
    RelativeLayout reladdsch, relative_header;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_desc_fragment);

        scheduledesc=(EditText)findViewById(R.id.scheduledesc);
        ripple_edit_save=(RippleView)findViewById(R.id.ripple_edit_save);
        relative_header = (RelativeLayout) findViewById(R.id.relative_header);
        imageButtonZoomIn = (ImageButton) findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) findViewById(R.id.img_zoom_out);
        reladdsch = (RelativeLayout) findViewById(R.id.reladdsch);
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

        viewsch=getIntent().getIntExtra("viewsch",0);
        addsch=getIntent().getIntExtra("addsch",0);
        desp=getIntent().getStringExtra("desp");
        scheduledesc.setText(desp);

        ripple_edit_save.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                ViewSchedule.scheduledescription=scheduledesc.getText().toString();
                AddSchedule.scheduledescription=scheduledesc.getText().toString();
                Log.e("schduledescription",""+AddSchedule.scheduledescription);
               // getFragmentManager().popBackStackImmediate();
                if ((addsch==10)){
                    Intent backadsch=new Intent(ScheduleDescriptionFragment.this,AddSchedule.class);
                    startActivity(backadsch);
                    finish();
                }
                else if(viewsch==12){
                    Intent backadsch=new Intent(ScheduleDescriptionFragment.this,ViewSchedule.class);
                    startActivity(backadsch);
                    finish();
                }
            }
        });



    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        reladdsch.setPivotX(pivot.x);
        reladdsch.setPivotY(pivot.y);
        reladdsch.setScaleX(scaleX);
        reladdsch.setScaleY(scaleY);
    }

}
