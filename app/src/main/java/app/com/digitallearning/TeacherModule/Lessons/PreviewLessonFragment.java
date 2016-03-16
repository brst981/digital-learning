package app.com.digitallearning.TeacherModule.Lessons;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/29/15.
 */
public class PreviewLessonFragment extends Fragment {
    View rootview;
    String textHeader, lessondescription, userid,url;
    ProgressDialog dlg;
    ImageView thumbnail;
    SharedPreferences preferences;
    TextView desciption, headerTitle;;
    String videoId;
    String img_url;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_preview_lesson, container, false);
        dlg=new ProgressDialog(getActivity());

        AppCompatActivity activity = (AppCompatActivity) getActivity();

        activity.getSupportActionBar().setTitle("");
        activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
        headerTitle = (TextView) activity.findViewById(R.id.mytext);
        headerTitle.setText("Preview Lesson");
        thumbnail=(ImageView)rootview.findViewById(R.id.thumbnail);


        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());

        desciption = (TextView) rootview.findViewById(R.id.desciption);
        userid = preferences.getString("Sch_Mem_id", "");
        Log.e("userid", "" + userid);
        lessondescription=getArguments().getString("lessondescription");
        desciption.setText(lessondescription);
        url=getArguments().getString("url");
        Log.e("Url",""+url);
        try {
            videoId=extractYoutubeId(url);

            img_url="http://img.youtube.com/vi/"+videoId+"/0.jpg";

            Picasso.with(getActivity()).load(img_url).placeholder(R.drawable.no_image_icon).into(thumbnail);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return rootview;
    }

    public String extractYoutubeId(String url) throws MalformedURLException {
        String query = new URL(url).getQuery();
        String[] param = url.split("/");
        String id = null;

        id = param[3];
        return id;
    }

}
