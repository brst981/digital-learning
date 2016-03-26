package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/25/16.
 */
public class Data_Option implements Serializable {

    private String option_id;
    private String quizzes_id;
    private String quiz_option;
    private String correct_answer;
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
