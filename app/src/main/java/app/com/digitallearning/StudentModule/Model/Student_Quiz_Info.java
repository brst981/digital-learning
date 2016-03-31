package app.com.digitallearning.StudentModule.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${PSR} on 3/30/16.
 */
public class Student_Quiz_Info implements Serializable {
    private String master_id;
    private String quiz_name;
    private String quiz_desc;
    private String quizzes_id;
    private String quiz_question;
    ArrayList<Student_Quiz_Option> student_quiz_options;

    // private String quiz_option_data;
    public String getMaster_id() {
        return master_id;
    }

    public void setMaster_id(String master_id) {
        this.master_id = master_id;
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

    public String getQuizzes_id() {
        return quizzes_id;
    }

    public void setQuizzes_id(String quizzes_id) {
        this.quizzes_id = quizzes_id;
    }

    public String getQuiz_question() {
        return quiz_question;
    }

    public void setQuiz_question(String quiz_question) {
        this.quiz_question = quiz_question;
    }

//    public String getQuiz_option_data() {
//        return quiz_option_data;
//    }
//
//    public void setQuiz_option_data(String quiz_option_data) {
//        this.quiz_option_data = quiz_option_data;
//    }


    public ArrayList<Student_Quiz_Option> getStudent_quiz_options() {
        return student_quiz_options;
    }

    public void setStudent_quiz_options(ArrayList<Student_Quiz_Option> student_quiz_options) {
        this.student_quiz_options = student_quiz_options;
    }

}
