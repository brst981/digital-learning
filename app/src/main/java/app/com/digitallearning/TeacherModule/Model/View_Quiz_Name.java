package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/18/16.
 */
public class View_Quiz_Name implements Serializable {
    private String quiz_name;
    private String quiz_description;

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getAss_less_id() {
        return ass_less_id;
    }

    public void setAss_less_id(String ass_less_id) {
        this.ass_less_id = ass_less_id;
    }

    private String lesson_name;
    private String ass_less_id;
    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_description() {
        return quiz_description;
    }

    public void setQuiz_description(String quiz_description) {
        this.quiz_description = quiz_description;
    }

}
