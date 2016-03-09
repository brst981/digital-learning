package app.com.digitallearning.TeacherModule.Lessons;

import android.app.Activity;
import android.os.Bundle;
import android.widget.VideoView;

import app.com.digitallearning.R;

/**
 * Created by ${PSR} on 3/8/16.
 */
public class LessonVideo extends Activity {
    VideoView video;
    String videourl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        video = (VideoView) findViewById(R.id.videoViewRelative);
        videourl=getIntent().getStringExtra("video_url");
        video.setVideoPath(videourl);

    }
}