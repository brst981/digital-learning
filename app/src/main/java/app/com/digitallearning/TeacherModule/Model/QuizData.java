package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/8/16.
 */
public class QuizData implements Serializable {

    private String quizMasterId;
    private String quizName;
    private String quizDescription;




    public String getQuizMasterId() {
        return quizMasterId;
    }

    public void setQuizMasterId(String quizMasterId) {
        this.quizMasterId = quizMasterId;
    }

    public String getQuizName() {
        return quizName;
    }

    public void setQuizName(String quizName) {
        this.quizName = quizName;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

}
