package app.com.digitallearning.TeacherModule.Classes;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 1/30/16.
 */
public class DescriptionFragment extends Fragment {
    View rootview;
    Button done;
    EditText desc;
    static String description;
    RelativeLayout relback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_description, container, false);
        desc=(EditText)rootview.findViewById(R.id.desc);
        done=(Button)rootview.findViewById(R.id.done);
        relback=(RelativeLayout)rootview.findViewById(R.id.relback);
        relback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStackImmediate();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                description=desc.getText().toString();
                Log.e("descriptionFrag",""+description);
                getFragmentManager().popBackStackImmediate();
            }
        });

        return rootview;
    }

}
