package app.com.digitallearning.TeacherModule.Students;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Archieve_Student;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 1/1/16.
 */
public class ArchivedFragment extends Fragment {
    View rootview;
    private ListView mListView;
    ListViewAdapter mAdapter;
    String  cla_classid, userid,unarch_stu_id;
    SharedPreferences preferences;
    ArrayList<Archieve_Student> archivelist = new ArrayList<Archieve_Student>();
    ProgressDialog dlg;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_archived, container, false);
        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);
        dlg=new ProgressDialog(getActivity());
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Bundle bundle=new Bundle();
                bundle.putString("name",archivelist.get(position).getStudent_Name());
                bundle.putString("email",archivelist.get(position).getStudent_Email_id());
                bundle.putString("userId",archivelist.get(position).getStudent_Mem_Userid());
                bundle.putString("password",archivelist.get(position).getStudent_Password());
                bundle.putString("status",archivelist.get(position).getStudent_Mem_Online());
                ArchieveStudentDetail archieveStudentDetail = new ArchieveStudentDetail();
                fragmentTransaction.replace(R.id.container, archieveStudentDetail).addToBackStack(null);
                archieveStudentDetail.setArguments(bundle);
                fragmentTransaction.commit();


            }
        });
        mListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.e("ListView", "OnTouch");
                return false;
            }
        });
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                return true;
            }
        });
        mListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                Log.e("ListView", "onScrollStateChanged");
                ((SwipeLayout)(mListView.getChildAt(scrollState - mListView.getFirstVisiblePosition()))).open(true);
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.e("ListView", "onItemSelected:" + position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.e("ListView", "onNothingSelected:");
            }
        });

        return rootview;
    }

    class ListViewAdapter extends BaseSwipeAdapter {
        private Context mContext;

        public ListViewAdapter(Context mContext) {
            this.mContext = mContext;
        }

        @Override
        public int getSwipeLayoutResourceId(int position) {
            return R.id.swipe;
        }



        @Override
        public View generateView(int position, ViewGroup parent) {
            View v = LayoutInflater.from(mContext).inflate(R.layout.archived_items, null);
            SwipeLayout swipeLayout = (SwipeLayout)v.findViewById(getSwipeLayoutResourceId(position));
            swipeLayout.addSwipeListener(new SimpleSwipeListener() {
                @Override
                public void onOpen(SwipeLayout layout) {

                }
            });
            swipeLayout.setOnDoubleClickListener(new SwipeLayout.DoubleClickListener() {
                @Override
                public void onDoubleClick(SwipeLayout layout, boolean surface) {
                    Toast.makeText(mContext, "DoubleClick", Toast.LENGTH_SHORT).show();
                }
            });
            v.findViewById(R.id.delete).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(mContext, "click delete", Toast.LENGTH_SHORT).show();
                }
            });
            return v;
        }

        @Override
        public void fillValues(final int position, View convertView) {
             TextView firstlastname = (TextView)convertView.findViewById(R.id.text_enrolled_name);
            firstlastname.setText(archivelist.get(position).getStudent_Name()+" "+archivelist.get(position).getStudent_LastName());
            TextView archive=(TextView)convertView.findViewById(R.id.archive);
            archive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unarch_stu_id=archivelist.get(position).getStudent_Memid();

                    new Unarchieve_Student(position).execute(cla_classid,unarch_stu_id);
                }
            });

            TextView delete=(TextView)convertView.findViewById(R.id.delete);
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unarch_stu_id=archivelist.get(position).getStudent_Memid();
                    new Delete_Student(position).execute(cla_classid,unarch_stu_id);
                }
            });
        }

        @Override
        public int getCount() {
            return archivelist.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }
    class Get_archive_Student extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Get_archive_Student(params[0],params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Loading....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("StudentList", "" + result);
            if (result.contains("true")) {

                updateGet_archive_Student(result);


            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }
        private void updateGet_archive_Student(String success) {

            try {
                JSONObject jsonObject = new JSONObject(success);
                JSONArray arr = jsonObject.getJSONArray("data");
                for (int i = 0; i < arr.length(); i++) {
                    JSONObject obj = arr.getJSONObject(i);
                    Archieve_Student archieve_student = new Archieve_Student();
                    archieve_student.setStudent_Name(obj.optString("Name"));
                    archieve_student.setStudent_Memid(obj.getString("Mem_id"));
                    archieve_student.setStudent_Mem_Userid(obj.getString("Mem_Userid"));
                    archieve_student.setStudent_Password(obj.getString("Password"));
                    archieve_student.setStudent_Mem_Type(obj.getString("Mem_Type"));
                    archieve_student.setStudent_gradebookview(obj.getString("gradebookview"));
                    archieve_student.setStudent_LastName(obj.getString("LastName"));
                    archieve_student.setStudent_Email_id(obj.getString("Email_id"));
                    archieve_student.setStudent_Mem_Online(obj.getString("Mem_Online"));

                    archivelist.add(archieve_student);

                }
                mListView.setAdapter(mAdapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }




    class Unarchieve_Student extends AsyncTask<String, Integer, String> {
        private int pos;

        public Unarchieve_Student(int pos) {
            this.pos = pos;

        }

        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Unarchieve_Student(params[0],params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Archive Loading....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("StudentList", "" + result);
            if (result.contains("true")) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Unarchived successfully").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                dialog.dismiss();


                               /// mListView.setAdapter(mAdapter);

                                archivelist.remove(pos);
                                mAdapter = new ListViewAdapter(getActivity());

                                mListView.setAdapter(mAdapter);

                            }
                        });
                AlertDialog dialog = alertDialog.create();
                dialog.show();

                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);

            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }

    }



    class Delete_Student extends AsyncTask<String, Integer, String> {
        private int pos;

        public Delete_Student(int pos) {
            this.pos = pos;

        }

        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_Student(params[0],params[1]);

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dlg.setMessage("Archive Loading....");
            dlg.setCancelable(false);
            dlg.show();


        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dlg.dismiss();
            Log.e("StudentList", "" + result);
            if (result.contains("true")) {

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
                alertDialog.setMessage("Deleted successfully").setCancelable(false)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

                                dialog.dismiss();


                                /// mListView.setAdapter(mAdapter);

                                archivelist.remove(pos);
                                mAdapter = new ListViewAdapter(getActivity());

                                mListView.setAdapter(mAdapter);

                            }
                        });
                AlertDialog dialog = alertDialog.create();
                dialog.show();

                TextView messageText = (TextView) dialog
                        .findViewById(android.R.id.message);
                messageText.setGravity(Gravity.CENTER);

            } else if (result.contains("false")) {
                Toast.makeText(getActivity(), "No data", Toast.LENGTH_SHORT).show();

            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        new Get_archive_Student().execute(userid,cla_classid);

    }
}
