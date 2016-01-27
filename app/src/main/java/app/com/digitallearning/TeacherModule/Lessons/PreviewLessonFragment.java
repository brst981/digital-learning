package app.com.digitallearning.TeacherModule.Lessons;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.com.digitallearning.R;

/**
 * Created by ${ShalviSharma} on 12/29/15.
 */
public class PreviewLessonFragment extends Fragment{
    View rootview;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootview = inflater.inflate(R.layout.fragment_preview_lesson, container, false);
        return rootview;
    }
}
