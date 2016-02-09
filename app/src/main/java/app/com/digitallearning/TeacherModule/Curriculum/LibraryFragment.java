package app.com.digitallearning.TeacherModule.Curriculum;

import android.graphics.PointF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassFragment;

/**
 * Created by ${PSR} on 2/6/16.
 */
public class LibraryFragment extends Fragment {
    View rootview;
    int a;
    RelativeLayout relative_header;
    RippleView ripple_main,ripple_main1,ripple_main2,rippleSave;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    CheckBox checkBox1,checkBox2,checkBox3;
    ImageView back;
    public static  String selectedLibrary;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.libraryfragment, container, false);
        relative_header=(RelativeLayout)rootview.findViewById(R.id.relative_header1);
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);

        ripple_main=(RippleView)rootview.findViewById(R.id.ripple_main);
        rippleSave=(RippleView)rootview.findViewById(R.id.rippleSave);
        checkBox1=(CheckBox)rootview.findViewById(R.id.checkbox1) ;
        checkBox2=(CheckBox)rootview.findViewById(R.id.checkbox2) ;
        checkBox3=(CheckBox)rootview.findViewById(R.id.checkbox3) ;
        ripple_main1=(RippleView)rootview.findViewById(R.id.ripple_main1);
        ripple_main2=(RippleView)rootview.findViewById(R.id.ripple_main2);
        back=(ImageView) rootview.findViewById(R.id.back);
        //  a = getArguments().getInt("ID");

        Log.e("ID",""+a);
        if(a==1){
            relative_header.setVisibility(View.GONE);
        }
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        ripple_main.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
//                if(ClassFragment.classtpye.equals(""))
                selectedLibrary="1";
                Log.e("inClassType",""+ClassFragment.classtpye);
                checkBox1.setVisibility(View.VISIBLE);
                checkBox2.setVisibility(View.GONE);
                checkBox3.setVisibility(View.GONE);

              /*  FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateClassFragment createClassFragment = new CreateClassFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Instructor","Instructor");
                fragmentTransaction.replace(R.id.container, createClassFragment).addToBackStack(null);
                createClassFragment.setArguments(bundle);
                fragmentTransaction.commit();*/
            }
        });
        ripple_main1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                selectedLibrary="2";
                checkBox2.setVisibility(View.VISIBLE);
                checkBox1.setVisibility(View.GONE);
                checkBox3.setVisibility(View.GONE);


              /*  FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateClassFragment createClassFragment = new CreateClassFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Blended","Blended");
                fragmentTransaction.replace(R.id.container, createClassFragment).addToBackStack(null);
                createClassFragment.setArguments(bundle);
                fragmentTransaction.commit();*/
            }
        });
        ripple_main2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                selectedLibrary="3";
                checkBox3.setVisibility(View.VISIBLE);
                checkBox1.setVisibility(View.GONE);
                checkBox2.setVisibility(View.GONE);


               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                CreateClassFragment createClassFragment = new CreateClassFragment();
                Bundle bundle=new Bundle();
                bundle.putString("Self-Paced","Self-Paced");
                fragmentTransaction.replace(R.id.container, createClassFragment).addToBackStack(null);
                createClassFragment.setArguments(bundle);
                fragmentTransaction.commit();*/
            }
        });
        rippleSave.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                getFragmentManager().popBackStackImmediate();

//                FragmentManager fragmentManager = getFragmentManager();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                EditClassFragment createClassFragment = new EditClassFragment();
//
//                fragmentTransaction.replace(R.id.container, createClassFragment).addToBackStack(null);
//
//                fragmentTransaction.commit();
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
        return rootview;
    }
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        relative_header.setPivotX(pivot.x);
        relative_header.setPivotY(pivot.y);
        relative_header.setScaleX(scaleX);
        relative_header.setScaleY(scaleY);
    }

}

