package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/18/16.
 */
public class View_Quiz_Listing implements Serializable {

    private String quiz_name;
    private String quiz_description;
    private String quizid;
    private String option_id;
    private String quizzes_id;
    private String question_name;
    private String quiz_option;
    private String correct_answer;



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

    public String getQuizid() {
        return quizid;
    }

    public void setQuizid(String quizid) {
        this.quizid = quizid;
    }

    public String getOption_id() {
        return option_id;
    }

    public void setOption_id(String option_id) {
        this.option_id = option_id;
    }

    public String getQuizzes_id() {
        return quizzes_id;
    }

    public void setQuizzes_id(String quizzes_id) {
        this.quizzes_id = quizzes_id;
    }

    public String getQuestion_name() {
        return question_name;
    }

    public void setQuestion_name(String question_name) {
        this.question_name = question_name;
    }

    public String getQuiz_option() {
        return quiz_option;
    }

    public void setQuiz_option(String quiz_option) {
        this.quiz_option = quiz_option;
    }

    public String getCorrect_answer() {
        return correct_answer;
    }

    public void setCorrect_answer(String correct_answer) {
        this.correct_answer = correct_answer;
    }




}
