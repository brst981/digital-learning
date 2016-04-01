package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/16/16.
 */
public class Quiz_Listing implements Serializable {

    private String last_modify;
    private String  quiz_id;
    private String ass_less_id;
    private String quiz_cid;
    private String api_quiz_id;
    private String quiz_desc;
    private String quiz_user_id;

    public String getDashboard_id() {
        return dashboard_id;
    }

    public void setDashboard_id(String dashboard_id) {
        this.dashboard_id = dashboard_id;
    }

    private String dashboard_id;

    private String quiz_name;
    public String getLast_modify() {
        return last_modify;
    }

    public void setLast_modify(String last_modify) {
        this.last_modify = last_modify;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getAss_less_id() {
        return ass_less_id;
    }

    public void setAss_less_id(String ass_less_id) {
        this.ass_less_id = ass_less_id;
    }

    public String getQuiz_cid() {
        return quiz_cid;
    }

    public void setQuiz_cid(String quiz_cid) {
        this.quiz_cid = quiz_cid;
    }

    public String getApi_quiz_id() {
        return api_quiz_id;
    }

    public void setApi_quiz_id(String api_quiz_id) {
        this.api_quiz_id = api_quiz_id;
    }

    public String getQuiz_desc() {
        return quiz_desc;
    }

    public void setQuiz_desc(String quiz_desc) {
        this.quiz_desc = quiz_desc;
    }

    public String getQuiz_user_id() {
        return quiz_user_id;
    }

    public void setQuiz_user_id(String quiz_user_id) {
        this.quiz_user_id = quiz_user_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }


}
