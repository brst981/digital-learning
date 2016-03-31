package app.com.digitallearning.StudentModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/30/16.
 */
public class Student_Quiz_Option implements Serializable {


    private String ans_id;
    private String element_id;
    private String quizzes_id;
    private String quiz_option;
    private String correct_answer;

    public String getAns_id() {
        return ans_id;
    }

    public void setAns_id(String ans_id) {
        this.ans_id = ans_id;
    }

    public String getElement_id() {
        return element_id;
    }

    public void setElement_id(String element_id) {
        this.element_id = element_id;
    }

    public String getQuizzes_id() {
        return quizzes_id;
    }

    public void setQuizzes_id(String quizzes_id) {
        this.quizzes_id = quizzes_id;
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
