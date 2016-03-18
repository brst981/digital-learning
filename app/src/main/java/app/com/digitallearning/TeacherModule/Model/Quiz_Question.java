package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/17/16.
 */
public class Quiz_Question implements Serializable {
    private String quiz_question;
    private String ans1;
    private String ans2;
    private String ans3;
    private String ans4;
    private boolean correctans;
    private String count;

    public String getQuiz_question() {
        return quiz_question;
    }

    public void setQuiz_question(String quiz_question) {
        this.quiz_question = quiz_question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public boolean isCorrectans() {
        return correctans;
    }

    public void setCorrectans(boolean correctans) {
        this.correctans = correctans;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }




}
