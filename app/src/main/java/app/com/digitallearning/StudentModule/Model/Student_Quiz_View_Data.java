package app.com.digitallearning.StudentModule.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${PSR} on 3/30/16.
 */
public class Student_Quiz_View_Data implements Serializable {
    ArrayList<Student_Quiz_Info> quiz_info;
    public ArrayList<Student_Quiz_Info> getQuiz_info() {
        return quiz_info;
    }

    public void setQuiz_info(ArrayList<Student_Quiz_Info> quiz_info) {
        this.quiz_info = quiz_info;
    }




}
