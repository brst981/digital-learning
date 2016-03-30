package app.com.digitallearning.StudentModule.StudentResource;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.andexert.library.RippleView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Resource_Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${PSR} on 1/28/16.
 */
public class StudentResourceFragment extends  Fragment {
    View rootview;
    TextView headerTitle;
    String textHeader,cls_clsid,Sch_Mem_id;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private MyRecyclerViewAdapter mAdapter;
    ProgressDialog dlg;
    ArrayList<Resource_Data> resourcedataList = new ArrayList<Resource_Data>();
    SharedPreferences preferences;

    public static StudentResourceFragment newInstance() {
        StudentResourceFragment mFragment = new StudentResourceFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_resource_student, container, false);
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.myrecycler);
        dlg=new ProgressDialog(getActivity());
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Class Resources");


        initData();

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);


        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        preferences= PreferenceManager.getDefaultSharedPreferences(getActivity());
        cls_clsid=preferences.getString("cls_clsid","");
        Sch_Mem_id=preferences.getString("Sch_Mem_id","");

        new Resource_Listing().execute(cls_clsid,Sch_Mem_id);
        return rootview;
    }

    private void initData() {
        textHeader ="sdhfygsjdgf";




    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {

        private Context mContext;
        private LayoutInflater inflater;
        ArrayList<String> arrSchoolList;
        ArrayList<String> arrSchoolImg;
        ArrayList<String> arrSchoolId;



        public MyRecyclerViewAdapter() {

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.resource_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {


           holder.stresttext.setText(resourcedataList.get(position).getTitle());

        }

        @Override
        public int getItemCount() {
            return resourcedataList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{

            RippleView ripple_res;
            ImageView stresimg;
            TextView stresttext;



            public ViewHolder(View itemView) {
                super(itemView);
                ripple_res = (RippleView) itemView.findViewById(R.id.ripple_res);
                ripple_res.setOnClickListener(this);
                stresttext=(TextView) itemView.findViewById(R.id.stresttext);

                /*relativeLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                        Intent i = new Intent(getActivity(), VideoViewActivity.class);
                        i.putExtra("position", "" + position);
                        startActivity(i);
                    }
                });*/
            }

            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                Log.e("positionGetLay",""+position);
                int id = v.getId();
                switch (id){
                    case R.id.ripple_res:


                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            Bundle bundle=new Bundle();
                            bundle.putString("studentdescription",resourcedataList.get(position).getDescription());
                            Student_Resource_Description student_resource_description = new Student_Resource_Description();
                            fragmentTransaction.replace(android.R.id.content, student_resource_description).addToBackStack(null);
                            student_resource_description.setArguments(bundle);
                            fragmentTransaction.commit();


                        break;
                }

            }
        }
    }




    class Resource_Listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Student_Get_Resource(params[0], params[1]);

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
            Log.e("StudentResource_Listing", "" + result);

            if (result.contains("false")) {

                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("No data").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                dialog.dismiss();
                            }
                        });

                AlertDialog dialog = alertDialog.create();
                dialog.show();
                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);


            } else if (result.contains("true")) {
                updateGet_Lesson(result);


            }
        }

        private void updateGet_Lesson(String success) {

            try {
//{"success":true,"data":[{"Res_Id":"2756","title":"we","desc":"are"},{"Res_Id":"2757","title":"jas","desc":"Kaur"}]}
                JSONObject jsonObject = new JSONObject(success);

                JSONArray arr = jsonObject.optJSONArray("data");
                Log.e("arr", " " + arr);


                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);

                    Resource_Data resource_data = new Resource_Data();
                    resource_data.setResourceId(obj.optString("res_id"));
                    resource_data.setTitle(obj.getString("res_name"));
                    resource_data.setDescription(obj.getString("res_desc"));
                    resourcedataList.add(resource_data);

                    mRecyclerView.setAdapter(mAdapter);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
