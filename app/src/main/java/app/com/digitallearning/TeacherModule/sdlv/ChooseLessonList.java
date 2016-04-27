package app.com.digitallearning.TeacherModule.sdlv;

/**
 * Created by yuyidong on 16/1/25.
 */
public class ChooseLessonList {
    private final String name;



    public ChooseLessonList(String name,String data) {
        this(name);

    }

    public ChooseLessonList(String name) {
        this.name = name;

    }

    public String getName() {
        return name;
    }



}
