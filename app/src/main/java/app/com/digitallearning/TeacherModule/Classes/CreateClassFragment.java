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
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/21/15.
 */
public class CreateClassFragment extends Fragment {
    View rootview;
    Button btnSave;
    RippleView ripple_main,ripple_main1,ripple_main2;
    String arrName,arrChildNAme,classtpye,topic,description,title,arrId,a,b,what,get;
    ProgressDialog dlg;
    EditText edt_title;
    static String Sch_Mem_id,userMem_Sch_Id,class_id;
    ImageButton imageButtonZoomIn, imageButtonZoomOut;
    RelativeLayout rellogin;
    ImageView back;
    TextView selectedclass,selecteddes;
    int id,idres,id1;
    public static int slecectedclasstype;
    public static TextView selectedtopic;
    int fromcreate=10;
    final static CharSequence[] classtype = {"Instructor","Blended","Self Paced"};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_create_class, container, false);
        ripple_main=(RippleView) rootview.findViewById(R.id.ripple_main);
        btnSave=(Button)rootview.findViewById(R.id.btnSave);
        dlg=new ProgressDialog(getActivity());
        imageButtonZoomIn = (ImageButton)rootview. findViewById(R.id.img_zoom_in);
        selectedclass=(TextView)rootview.findViewById(R.id.selectedclass);
        selectedtopic=(TextView)rootview.findViewById(R.id.selectedtopic);
        selecteddes=(TextView)rootview.findViewById(R.id.selecteddes);

        imageButtonZoomOut = (ImageButton) rootview.findViewById(R.id.img_zoom_out);
        rellogin=(RelativeLayout)rootview.findViewById(R.id.rellogin) ;
        ripple_main1=(RippleView) rootview.findViewById(R.id.ripple_main1);
        ripple_main2=(RippleView) rootview.findViewById(R.id.ripple_main2);
        edt_title=(EditText)rootview.findViewById(R.id.edt_title);
        arrName=getArguments().getString("arrName");
        selecteddes.setText("");
        selectedclass.setText("");

        String abc=selecteddes.getText().toString();
        Log.e("abcswxwca",""+abc);
        Log.e("abc",""+ topic);

        idres=getArguments().getInt("id");
        //
        // id1=getArguments().getInt("id1");
        /*if(id1==16){
            TopicFragment.topic=" ";
            DescriptionFragment.description=" ";
        }*/

        /*if(idres==1){
            Log.e("slecectedclasstypefirst",""+slecectedclasstype);

            selecteddes.setText("");
            selectedclass.setText("");
              Log.e("idres",""+idres);
        }*/


       /*if (slecectedclasstype == 0) {
            selectedclass.setText("Instructor");
        } else if (slecectedclasstype == 1) {
            selectedclass.setText("Blended");
        }
        else if (slecectedclasstype == 2) {
            selectedclass.setText("Self Paced");
        }*/

        Sch_Mem_id=getArguments().getString("Sch_Mem_id");
        Log.e("Sch_Mem_id",""+Sch_Mem_id);

        arrId=getArguments().getString("arrId");
        Log.e("carrChildId",""+arrId);

        userMem_Sch_Id=getArguments().getString("Mem_Sch_Id");
        Log.e("Mem_Sch_Id",""+userMem_Sch_Id);

        class_id=getArguments().getString("classid");
        Log.e("class_id",""+class_id);




       /* a = class_id.replace("[", "");
        Log.e("a",""+a);
        b = a.replace("]", "");
        Log.e("b",""+b);

        List<String> myList = new ArrayList<String>(Arrays.asList(b.split(",")));

        Log.e("myList",""+myList);*/




        /*classtpye= ClassFragment.classtpye;
        Log.e("getValue",""+classtpye);
*/
        topic= TopicFragment.topic;
        Log.e("getValuetopic",""+topic);


        /*try{
            if(topic.equalsIgnoreCase("9")){
                Log.e("visithere","visithere");
                Log.e("OtherStrincreate",""+TopicFragment.otherstring);

                 what=selectedtopic.getText().toString();
                Log.e("what",""+what);

            }
            selectedtopic.setText(TopicFragment.otherstring);
        }catch (Exception e){}*/


        try{
        if(topic.equalsIgnoreCase("Art")){
            topic="1";
        }

        if(topic.equalsIgnoreCase("Theater")){
            topic="3";
        }
        if(topic.equalsIgnoreCase("Visual Art")){
            topic="5";
        }}
        catch (Exception e){}

       /* if (topic == "1") {

            selectedtopic.setText("Art");
        }
        else if(topic == "3"){
            selectedtopic.setText("Theater");
        }

        else if(topic == "5")
        {
            selectedtopic.setText("Visual Art");
        }*/

        description= DescriptionFragment.description;
        Log.e("descriptionInClass",""+description);


        arrChildNAme=getArguments().getString("arrChildNAme");

        Log.e("ARRNAMECreate",""+arrName);
        Log.e("ARRChildNAmeCreate",""+arrChildNAme);



       back=(ImageView) rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditClassFragment.style = " ";
                EditClassFragment.topic1=" ";
                DescriptionFragment.description=" ";
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
                        slecectedclasstype = item;
                        Log.e("slecectedclasstype", "" + slecectedclasstype);
                        Log.e("items[item]", "" + classtype[item]);
                        if (classtype[item] == "Instructor") {
                            EditClassFragment.style = "1";
                        } else if (classtype[item] == "Blended") {
                            EditClassFragment.style = "2";
                        } else {
                            EditClassFragment.style = "3";
                        }
                        if (EditClassFragment.style == "1") {
                            selectedclass.setText("Instructor");
                        } else if (EditClassFragment.style == "2") {
                            selectedclass.setText("Blended");
                        }
                        else if (EditClassFragment.style =="3") {
                            selectedclass.setText("Self Paced");
                        }
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();


            }
        });
        if (EditClassFragment.style == "1") {
            selectedclass.setText("Instructor");
        } else if (EditClassFragment.style == "2") {
            selectedclass.setText("Blended");
        }
        else if (EditClassFragment.style =="3") {
            selectedclass.setText("Self Paced");
        }

        ripple_main1.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){

            @Override
            public void onComplete(RippleView rippleView) {

                if(selectedtopic.getText().toString()!=null){

                    get=selectedtopic.getText().toString();
                    Log.e("get",""+get);
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                TopicFragment topicFragment = new TopicFragment();
                Bundle bundle=new Bundle();
                id=6;
                bundle.putInt("id1",id);
                bundle.putString("arrName",String.valueOf(arrName));
                bundle.putString("arrId",(arrId));
                try {
                    bundle.putString("get",(get));
                }
                catch (Exception e){}
                bundle.putString("arrChildNAme",String.valueOf(arrChildNAme));
                fragmentTransaction.replace(R.id.container, topicFragment).addToBackStack(null);
                topicFragment.setArguments(bundle);
                fragmentTransaction.commit();

            }});



        Log.e("selectedtopic",""+selectedtopic);
        ripple_main2.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener(){

            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                DescriptionFragment descriptionFragment = new DescriptionFragment();
                Bundle bundle=new Bundle();
                bundle.putInt("fromcreate",fromcreate);
                fragmentTransaction.replace(R.id.container, descriptionFragment).addToBackStack(null);
                descriptionFragment.setArguments(bundle);
                fragmentTransaction.commit();
            }
        });
        selecteddes.setText(DescriptionFragment.description);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title=edt_title.getText().toString();

                Log.e("srEditClass.style",""+EditClassFragment.style);
                Log.e("srtopic",""+topic);
                Log.e("srdescription",""+description);


                new CreateClass().execute(Sch_Mem_id, userMem_Sch_Id, title, EditClassFragment.style, topic, description);

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


    @Override
    public void onStart() {
        super.onStart();

        try{
        if(topic.equalsIgnoreCase("9")){
            selectedtopic.setText(TopicFragment.otherstring);
        }} catch (Exception e){}
        selectedtopic.setText(EditClassFragment.topic1);
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

               //updateCreateClass(result);

              //  Log.e("jhdj",""+"");
              // selectedclass.setText("");
                topic=" ";
                Log.e("topiconPost",""+topic);
                description=" ";
                Log.e("description",""+description);
                EditClassFragment.style=" ";
                TopicFragment.topic=" ";
                DescriptionFragment.description=" ";
                EditClassFragment.topic1=" ";
              //  TopicFragment.artthe=" ";
              //   TopicFragment.itemart=" ";
                selecteddes.setText(description);
                selectedtopic.setText(topic);
                try{
                    if(topic.equalsIgnoreCase("9")){
                        selectedtopic.setText(TopicFragment.otherstring);
                    }} catch (Exception e){}
                Log.e("EditClassFragment.style",""+EditClassFragment.style);
                Log.e("descriptionsd",""+description);
                Log.e("topicasd",""+topic);

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

 /*   @Override
    public void onStart() {
        super.onStart();


    @Override
    public void onPause() {
        super.onPause();
        selecteddes.setText("");
        selectedclass.setText("");
    }*/


}