package app.com.digitallearning.TeacherModule.Grade;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 2/3/16.
 */
public class Grade_Details  extends Fragment {
    View rootview;
    LayoutInflater inflater;
    ListView list;
    TextView headerTitle;
    EditText edittotal;
    String textHeader;
    ArrayList<String> categoryName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.grade_details, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");

        headerTitle = (TextView) activity.findViewById(R.id.mytext);

        headerTitle.setText("Grade Details");

        initData();
        list=(ListView)rootview.findViewById(R.id.list);
        edittotal=(EditText)rootview.findViewById(R.id.edittotal);
        edittotal.setInputType(InputType.TYPE_NULL);

        edittotal.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                edittotal.setInputType(InputType.TYPE_CLASS_TEXT);
                return false;
            }
        });



        categoryName=new ArrayList<>();
        list.setAdapter(new careerAdapter(getActivity(), categoryName));
        return rootview;
    }
    private void initData() {
        textHeader ="Grade Details";


    }
    class careerAdapter extends BaseAdapter {
        Context context;
        ArrayList<String> categoryName;



        public careerAdapter(Context context, ArrayList<String> categoryName) {
            this.categoryName = categoryName;
            this.context = context;

        }


        @Override
        public int getCount() {

            return 17;
        }

        @Override
        public Object getItem(int position) {
            return categoryName.get(position);
        }


        @Override
        public long getItemId(int position) {
            return position;
        }


        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder view;  //=new ViewHolder();
            if (inflater == null) {
                inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            }


            if (convertView == null) {
                convertView = inflater.inflate(R.layout.listingdata, null);
                view = new ViewHolder();
             //   view.category = (TextView) convertView.findViewById(R.id.category);



                /*view.mainradiobutton.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int isChecked) {

                    }
                });*/


               // view.category.setText(categoryName.get(position));
               // Log.e("TEXT", "" + categoryName.get(position));

            /* inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
             convertView=inflater.inflate(R.layout.newcategory,parent,false);
             view = new ViewHolder();
             view.category=(TextView)convertView.findViewById(R.id.category);
             view.check=(CheckBox)convertView.findViewById(R.id.check);
             view.category.setText(categoryName.get(position));
             Log.e("TEXT",""+categoryName.get(position));*/

            }
            return convertView;
        }


    }

    static class ViewHolder {
        TextView category;
        RadioButton radiobutton;
        RadioGroup mainradiobutton;

    }

}

