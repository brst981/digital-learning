package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/18/16.
 */
public class View_Quiz_Listing_Questions implements Serializable {
    private String question_id;
    private String question_nameString;
    public String getQuestion_id() {
        return question_id;
    }

    public void setQuestion_id(String question_id) {
        this.question_id = question_id;
    }

    public String getQuestion_nameString() {
        return question_nameString;
    }

    public void setQuestion_nameString(String question_nameString) {
        this.question_nameString = question_nameString;
    }


}
