package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${PSR} on 3/25/16.
 */
public class Data_View_Quiz implements Serializable {

    public Data_Quiz_Info getmDataQuizInfo() {
        return mDataQuizInfo;
    }

    public void setmDataQuizInfo(Data_Quiz_Info mDataQuizInfo) {
        this.mDataQuizInfo = mDataQuizInfo;
    }

    public ArrayList<Data_Quiz_Question> getmQuizQuestions() {
        return mQuizQuestions;
    }

    public void setmQuizQuestions(ArrayList<Data_Quiz_Question> mQuizQuestions) {
        this.mQuizQuestions = mQuizQuestions;
    }

    private Data_Quiz_Info mDataQuizInfo;
    private ArrayList<Data_Quiz_Question> mQuizQuestions;
}
