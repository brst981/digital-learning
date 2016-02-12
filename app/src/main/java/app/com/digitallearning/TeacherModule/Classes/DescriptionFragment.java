package app.com.digitallearning.TeacherModule.Classes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/30/16.
 */
public class DescriptionFragment extends Fragment {
    View rootview;
    Button done;
    EditText desc;
    public static String description;
    ImageView back;
    int fromdes=1;
    int fromcreate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_description, container, false);
        try{
            fromcreate=getArguments().getInt("fromcreate");
        }
        catch (Exception e){

        }


        Log.e("fromcreate",""+fromcreate);
        desc=(EditText)rootview.findViewById(R.id.desc);
        done=(Button)rootview.findViewById(R.id.done);
        back=(ImageView) rootview.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ;

                if(fromcreate==10){
                    description=desc.getText().toString();
                    Log.e("staticsdescription",""+description);
                    EditClassFragment.des=desc.getText().toString();
                    Log.e("descriptionFrag",""+EditClassFragment.des);
                    getFragmentManager().popBackStackImmediate();
                }
                else{
                    Intent save=new Intent(getActivity(),EditClassFragment.class);
                    save.putExtra("fromdes",fromdes);
                    Log.e("fromdes",""+fromdes);
                    startActivity(save);
                    getActivity().finish();
                }
               //
            }
        });

        return rootview;
    }

}
