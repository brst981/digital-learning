package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/22/16.
 */
public class Quiz_Info implements Serializable {


    private String  quiz_id;
    private String quiz_name;
    private String ass_less_id;
    private String get_point;
    private String total_point;

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getAss_less_id() {
        return ass_less_id;
    }

    public void setAss_less_id(String ass_less_id) {
        this.ass_less_id = ass_less_id;
    }

    public String getGet_point() {
        return get_point;
    }

    public void setGet_point(String get_point) {
        this.get_point = get_point;
    }

    public String getTotal_point() {
        return total_point;
    }

    public void setTotal_point(String total_point) {
        this.total_point = total_point;
    }

}
