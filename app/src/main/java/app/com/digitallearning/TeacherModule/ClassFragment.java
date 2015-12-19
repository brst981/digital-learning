package app.com.digitallearning.TeacherModule;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.digitallearning.R;
import app.com.digitallearning.RecyclerItemClickListener;

/**
 * Created by ${ShalviSharma} on 12/18/15.
 */
public class ClassFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    SwipeRefreshLayout swipeRefreshLayout;
    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_class, container, false);
        mRecyclerView = (RecyclerView)rootview.findViewById(R.id.recycler_view_class);
        swipeRefreshLayout = (SwipeRefreshLayout)rootview.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeColors(R.color.colorlima);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mAdapter = new MyRecyclerViewAdapter();
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               /* Toast.makeText(MainActivity.this,"clicked"+" "+position,Toast.LENGTH_SHORT).show();*/
                Intent intent = new Intent(getActivity(), NavigationActivity.class);
                startActivity(intent);

            }
        }));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Refresh items
                refreshItems();
            }
        });



        return rootview;
    }

    private void refreshItems() {
        // Load items
        // ...

        // Load complete
        onItemsLoadComplete();
    }

    private void onItemsLoadComplete() {
        // Update the adapter and notify data set changed
        // ...

        // Stop refresh animation
        swipeRefreshLayout.setRefreshing(false);
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
        // List<HomeModal> homeModals;
        private Context mContext;
        private LayoutInflater inflater;

        @Override
        public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.classes_item_list, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;

        }

        @Override
        public void onBindViewHolder(MyRecyclerViewAdapter.ViewHolder holder, int position) {
            // holder.imageView.setImageResource(arrayListImage.get(position));
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
