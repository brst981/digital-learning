package app.com.digitallearning.TeacherModule.sdlv;

/**
 * Created by yuyidong on 16/1/25.
 */
public class QQ {
    private final String name;
    private final String content;
    private final String time;
    private final int drawableRes;
    private final boolean isQun;
    private final int qunNumber;
    private final String classId;
    private final String classDescription;
    /*private final String lessonName;
    private final String lessonData;
    private final String lessonDataDetail;*/




    public QQ(String name, String content, String time, int drawableRes,String classId,String classDescription) {
        this(name, content, time, drawableRes, false, 1,classId,classDescription);
    }

    public QQ(String name, String content, String time, int drawableRes, boolean isQun, int qunNumber,String classId,String classDescrption) {
        this.name = name;
        this.content = content;
        this.time = time;
        this.drawableRes = drawableRes;
        this.isQun = isQun;
        this.qunNumber = qunNumber;
        this.classId=classId;
        this.classDescription=classDescrption;
    }


    /*public QQ(String lessonName, String lessonData,String lessonDataDetail) {
        this(lessonName, lessonData,lessonDataDetail);
    }

    public QQ(String lessonName, String lessonData,String lessonDataDetail) {
        this.lessonName = lessonName;
        this.lessonData = lessonData;
        this.lessonDataDetail=lessonDataDetail;


    }
*/

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public String getTime() {
        return time;
    }

    public int getDrawableRes() {
        return drawableRes;
    }

    public boolean isQun() {
        return isQun;
    }

    public int getQunNumber() {
        return qunNumber;
    }

    public String getClassId(){
        return classId;
    }
    public String getClassDescription(){
        return classDescription;
    }


/*
    public String getLessonName(){
        return lessonName;
    }
*/


}
