package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/25/16.
 */
public class Data_Quiz_Info implements Serializable {
    private String quizid;
    private String ass_less_id;
    private String lesson_name;
    private String api_quizid;
    private String quiz_name;
    private String quiz_desc;
    private String user_id;
    private String quiz_cid;
    private String last_modified;


    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getAss_less_id() {
        return ass_less_id;
    }

    public void setAss_less_id(String ass_less_id) {
        this.ass_less_id = ass_less_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getApi_quizid() {
        return api_quizid;
    }

    public void setApi_quizid(String api_quizid) {
        this.api_quizid = api_quizid;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getQuiz_desc() {
        return quiz_desc;
    }

    public void setQuiz_desc(String quiz_desc) {
        this.quiz_desc = quiz_desc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getQuiz_cid() {
        return quiz_cid;
    }

    public void setQuiz_cid(String quiz_cid) {
        this.quiz_cid = quiz_cid;
    }

    public String getLast_modified() {
        return last_modified;
    }

    public void setLast_modified(String last_modified) {
        this.last_modified = last_modified;
    }


}
