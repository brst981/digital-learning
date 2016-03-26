package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/25/16.
 */
public class Data_Quiz_Question implements Serializable {
    private String question_id;
    private String question_name;
    private Data_Quiz_Option mQuiz_option;

    public Data_Quiz_Option getmQuiz_option() {
        return mQuiz_option;
    }

    public void setmQuiz_option(Data_Quiz_Option mQuiz_option) {
        this.mQuiz_option = mQuiz_option;
    }



    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }


}
