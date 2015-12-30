package app.com.digitallearning.TeacherModule.Resource;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.andexert.library.RippleView;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/23/15.
 */
public class ResourceFragment extends Fragment {
    View rootview;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    RippleView rippleViewCreate;
    TextView headerTitle;
    String textHeader;


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
        mRecyclerView = (RecyclerView) rootview.findViewById(R.id.recycler_view_resource);
        rippleViewCreate = (RippleView) rootview.findViewById(R.id.ripple_create_resource);

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("CLASS RESOURCES");
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        initData();
        return rootview;
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

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;

        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.resource_item_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            // holder.imageView.setImageResource(arrayListImage.get(position));
           holder.textView.setText(textHeader);
        }

        @Override
        public int getItemCount() {
            return 7;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            /* ImageView imageView;
             TextView textView;
             RippleView relativeLayout;
             LinearLayout imageButtonAnswer;
             FrameLayout frameLayout;*/
            TextView textView;
            RippleView relativeLayout;

            public ViewHolder(View itemView) {
                super(itemView);
               /* relativeLayout = (RippleView) itemView.findViewById(R.id.relative_video_it);
                imageView = (ImageView) itemView.findViewById(R.id.img_it_Video);
                textView=(TextView)itemView.findViewById(R.id.txt_it_duration);
                imageButtonAnswer=(LinearLayout)itemView.findViewById(R.id.img_answers);
                textView.setText("22m");
                frameLayout =(FrameLayout)itemView.findViewById(R.id.relative_play_img);
                frameLayout.setOnClickListener(this);

                imageButtonAnswer.setOnClickListener(this);*/

                textView=(TextView)itemView.findViewById(R.id.text_view);


                relativeLayout = (RippleView) itemView.findViewById(R.id.relative_resource);
                relativeLayout.setOnRippleCompleteListener(new RippleView.OnRippleCompleteListener() {
                    @Override
                    public void onComplete(RippleView rippleView) {
                        int position = getLayoutPosition(); // gets item position
                        Toast.makeText(getActivity(), "clicked" + " " + position, Toast.LENGTH_SHORT).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        ResourceDetailFragment resourceDetailFragment = new ResourceDetailFragment();
                        /*Bundle savedInstanceState = new Bundle();
                        savedInstanceState.putString("HEADER_TEXT", textHeader);
                        savedInstanceState.putInt("POSITION", position);*/
                        fragmentTransaction.replace(R.id.container, resourceDetailFragment).addToBackStack(null);
                        /*lessonDetailFragment.setArguments(savedInstanceState);*/
                        fragmentTransaction.commit();

                    }
                });
            }

            /*@Override
            public void onClick(View v) {
                int position = getLayoutPosition();
                int id = v.getId();
                switch (id){
                    case R.id.relative_play_img:
                        Intent i = new Intent(getActivity(),VideoViewActivity.class);
                        i.putExtra("position",""+position);
                        startActivity(i);
                        break;
                    case R.id.img_answers:
                        // Toast.makeText(getActivity(),"Clicked"+" "+position,Toast.LENGTH_SHORT).show();
                        if (hasCamera()) {
                            timestamp = "ic_plus_";
                            timestamp = new SimpleDateFormat("MM-dd-yyyy_HH-mm-ss")
                                    .format(Calendar.getInstance().getTime());
                            File filepath = Environment.getExternalStorageDirectory();
                            File dir = new File(filepath.getAbsolutePath() + "/Demosvideo/");
                            dir.mkdirs();

                            Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                            File mediaFile = new File(Environment
                                    .getExternalStorageDirectory().getAbsolutePath()
                                    + "/Demosvideo/Video_" + timestamp + ".mp4");
                            if (mediaFile != null) {
                                Uri fileUri = Uri.fromFile(mediaFile);

                                intent.putExtra("Camera.Camera_Facing_Front", ic_plus_);
                                intent.putExtra("Camera.Camera_Facing_Back", 0);
                                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 30);
                                intent.putExtra(MediaStore.EXTRA_SIZE_LIMIT, 102400);
                                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0);
                                startActivityForResult(intent, VIDEO_CAPTURE);
                            }
                        } else {
                            Toast.makeText(getActivity(), "No Media player found ",
                                    Toast.LENGTH_SHORT).show();
                        }

                        break;
                }


            }*/
        }
    }
}
