package app.com.digitallearning.TeacherModule.Classes;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.andexert.library.RippleView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.ClassFragment;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/21/15.
 */
public class CreateClassFragment extends Fragment {
    View rootview;
    Button btnSave;
    RippleView ripple_main,ripple_main1,ripple_main2;
    String arrName,arrChildNAme,classtpye,topic,description,title,arrId,a,b;
    ProgressDialog dlg;
    EditText edt_title;
    static String Sch_Mem_id,userMem_Sch_Id,class_id;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin;
    ImageView back;
    int id;
    final static CharSequence[] classtype = {"Instructor","Blended","Self Paced"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_create_class, container, false);
        ripple_main=(RippleView) rootview.findViewById(R.id.ripple_main);
        btnSave=(Button)rootview.findViewById(R.id.btnSave);
        dlg=new ProgressDialog(getActivity());
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        ripple_main1=(RippleView) rootview.findViewById(R.id.ripple_main1);
        ripple_main2=(RippleView) rootview.findViewById(R.id.ripple_main2);
        edt_title=(EditText)rootview.findViewById(R.id.edt_title);
        arrName=getArguments().getString("arrName");

        Sch_Mem_id=getArguments().getString("Sch_Mem_id");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        arrId=getArguments().getString("arrId");
        Log.e("carrChildId",""+arrId);

        userMem_Sch_Id=getArguments().getString("Mem_Sch_Id");
        Log.e("Mem_Sch_Id",""+userMem_Sch_Id);

        class_id=getArguments().getString("classid");
        Log.e("class_id",""+class_id);

        a = class_id.replace("[", "");
        Log.e("a",""+a);
        b = a.replace("]", "");
        Log.e("b",""+b);

        List<String> myList = new ArrayList<String>(Arrays.asList(b.split(",")));

        Log.e("myList",""+myList);


        classtpye= ClassFragment.classtpye;
        Log.e("getValue",""+classtpye);

        topic=TopicFragment.topic;
        Log.e("getValuetopic",""+topic);


        description=DescriptionFragment.description;
        Log.e("descriptionInClass",""+description);


        arrChildNAme=getArguments().getString("arrChildNAme");

        Log.e("ARRNAMECreate",""+arrName);
        Log.e("ARRChildNAmeCreate",""+arrChildNAme);



       back=(ImageView) rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });

        ripple_main.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){

            @Override
            public void onComplete(RippleView rippleView) {
               /* FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                ClassTypeFragment classTypeFragment = new ClassTypeFragment();
                fragmentTransaction.replace(R.id.container, classTypeFragment).addToBackStack(null);
                fragmentTransaction.commit();*/

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Make your selection");
                builder.setItems(classtype, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        // Do something with the selection
                        //  mDoneButton.setText(items[item]);
                        Log.e("items[item]",""+classtype[item]);
                        if (classtype[item]=="Instructor"){
                            EditClassFragment.style="1";
                        }
                       else if (classtype[item]=="Blended"){
                            EditClassFragment.style="2";
                        }
                        else{
                            EditClassFragment.style="3";
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();



            }
        });


        ripple_main1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){

            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopicFragment topicFragment = new TopicFragment();
                Bundle bundle=new Bundle();
                id=1;
                bundle.putInt("id",id);
                bundle.putString("arrName",String.valueOf(arrName));
                bundle.putString("arrId",(arrId));
                bundle.putString("arrChildNAme",String.valueOf(arrChildNAme));
                fragmentTransaction.replace(R.id.container, topicFragment).addToBackStack(null);
                topicFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        ripple_main2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){

            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DescriptionFragment descriptionFragment = new DescriptionFragment();
                fragmentTransaction.replace(R.id.container, descriptionFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=edt_title.getText().toString();
                new CreateClass().execute(Sch_Mem_id, userMem_Sch_Id, title, classtpye, topic, description);
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




    class CreateClass extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.CreateClass(params[0],params[1],params[2],params[3],params[4],params[5]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading.....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("MainResult",""+result);
            if (result.contains("true")) {

               // updateCreateClass(result);
                Toast.makeText(getActivity(), "Class successfully created", Toast.LENGTH_SHORT).show();
                getFragmentManager().popBackStackImmediate();


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "Wrong User", Toast.LENGTH_SHORT).show();

            }
        }
}
    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        rellogin.setPivotX(pivot.x);
        rellogin.setPivotY(pivot.y);
        rellogin.setScaleX(scaleX);
        rellogin.setScaleY(scaleY);
    }
}