package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/22/16.
 */
public class Lesson_Info implements Serializable {
    private  String  les_id;
    private  String lesson_name;

    public String getLes_id() {
        return les_id;
    }

    public void setLes_id(String les_id) {
        this.les_id = les_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }


}
