package app.com.digitallearning.TeacherModule.Resource;

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
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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

import com.andexert.library.RippleView;
import com.daimajia.swipe.SimpleSwipeListener;
import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.daimajia.swipe.util.Attributes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import app.com.digitallearning.R;
import app.com.digitallearning.TeacherModule.Model.Resource_Data;
import app.com.digitallearning.WebServices.WSConnector;

/**
 * Created by ${ShalviSharma} on 12/23/15.
 */
public class ResourceFragment extends Fragment {
    View rootview;
    SwipeRefreshLayout swipeRefreshLayout;
    RippleView rippleViewCreate;
    TextView headerTitle;
    String textHeader;
    private ListView mListView;
    ListViewAdapter mAdapter;
    ProgressDialog dlg;
    SharedPreferences preferences;
    String Sch_Mem_id, cla_classid,deleteresourceId;
    ArrayList<Resource_Data> resourcedataList = new ArrayList<Resource_Data>();

    public static ResourceFragment newInstance() {
        ResourceFragment mFragment = new ResourceFragment();
        /*Bundle mBundle = new Bundle();
        mBundle.putString(TEXT_FRAGMENT, text);
        mFragment.setArguments(mBundle);*/
        return mFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_resource, container, false);
        rippleViewCreate = (RippleView) rootview.findViewById(R.id.ripple_create_resource);
        dlg=new ProgressDialog(getActivity());
        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Sch_Mem_id = preferences.getString("Sch_Mem_id", "");
        Log.e("Sch_Mem_id", "" + Sch_Mem_id);

        cla_classid = preferences.getString("cla_classid", "");
        Log.e("cla_classid", "" + cla_classid);

        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.getSupportActionBar().setTitle("");
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Class Resource");
        initData();
        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorlima);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshItems();
            }
        });
        mListView = (ListView)rootview.findViewById(R.id.listview_archieved);
        mAdapter = new ListViewAdapter(getActivity());
        mListView.setAdapter(mAdapter);
        mAdapter.setMode(Attributes.Mode.Single);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                Resource_View resource_view = new Resource_View();
                Bundle args=new Bundle();
                args.putString("title",resourcedataList.get(position).getTitle());
                args.putString("description",resourcedataList.get(position).getDescription());
                fragmentTransaction.replace(R.id.container, resource_view).addToBackStack(null);
                resource_view.setArguments(args);
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
    @Override
    public void onResume() {
        super.onResume();
        resourcedataList.clear();
        new Resource_Listing().execute(cla_classid, Sch_Mem_id);
        Log.e("Resume","Resume");
    }
    private void initData() {
        textHeader ="sdhfygsjdgf";

        rippleViewCreate.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
            @Override
            public void onComplete(RippleView rippleView) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                AddResourceFragment classFragment = new AddResourceFragment();
                fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                fragmentTransaction.commit();
            }
        });


    }
    private void refreshItems() {

        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {

        swipeRefreshLayout.setRefreshing(false);
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
            View v = LayoutInflater.from(mContext).inflate(R.layout.resource_item_list, null);
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
            v.findViewById(R.id.archive).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    /*FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Resource_Edit classFragment = new Resource_Edit();
                    Bundle bundle=new Bundle();
                    bundle.putString("title",resourcedataList.get(position).getTitle());
                    bundle.putString("description",resourcedataList.get(position).getDescription());
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    classFragment.setArguments(bundle);
                    fragmentTransaction.commit();*/
                }
            });
            return v;
        }

        @Override
        public void fillValues(final int position, View convertView) {
            final int pos=position;

            TextView title=(TextView)convertView.findViewById(R.id.quizname);
            title.setText(resourcedataList.get(position).getTitle());
            TextView archive=(TextView)convertView.findViewById(R.id.archive);
            TextView delete=(TextView)convertView.findViewById(R.id.delete);
            archive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    Resource_Edit classFragment = new Resource_Edit();
                    Bundle bundle=new Bundle();
                    bundle.putString("resourceId",resourcedataList.get(position).getResourceId());
                    bundle.putString("title",resourcedataList.get(position).getTitle());
                    Log.e("titlesend",""+resourcedataList.get(position).getTitle());
                    bundle.putString("description",resourcedataList.get(position).getDescription());
                    fragmentTransaction.replace(R.id.container, classFragment).addToBackStack(null);
                    classFragment.setArguments(bundle);
                    fragmentTransaction.commit();
                }
            });
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    deleteresourceId= resourcedataList.get(position).getResourceId();
                    new Delete_Resource(position).execute(Sch_Mem_id,deleteresourceId);
                }
            });
        }

        @Override
        public int getCount() {
            return resourcedataList.size();
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




    class Resource_Listing extends AsyncTask<String, Integer, String> {


        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Resource_Listing(params[0], params[1]);

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
            Log.e("Get_LessonAPI", "" + result);

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
                    resource_data.setResourceId(obj.optString("Res_Id"));
                    resource_data.setTitle(obj.getString("title"));
                    resource_data.setDescription(obj.getString("desc"));
                    resourcedataList.add(resource_data);

                    mListView.setAdapter(mAdapter);
                    mAdapter.setMode(Attributes.Mode.Single);

                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }



    class Delete_Resource extends AsyncTask<String, Integer, String> {

        private int pos;

        public Delete_Resource(int pos) {
            this.pos = pos;

        }

        @Override
        protected String doInBackground(String... params) {

            return WSConnector.Delete_Resource(params[0], params[1]);

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
            Log.e("Get_LessonAPI", "" + result);

            if (result.contains("false")) {
                Toast.makeText(getActivity(),"Wrong user",Toast.LENGTH_SHORT).show();

            } else if (result.contains("true")) {
                resourcedataList.remove(pos);
                mAdapter = new ListViewAdapter(getActivity());

                mListView.setAdapter(mAdapter);
            }
        }

    }
}

