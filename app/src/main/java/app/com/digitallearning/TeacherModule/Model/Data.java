package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by ${PSR} on 3/8/16.
 */
public class Data implements Serializable {

    private String lessonId;
    private String lessonClassId;
    private String lessonName;
    private String lessonDate;
    private String description;
    private String lessonImage;
    private String imageThumbnail;
    private String videoUrl;
    private String videoThumbnail;
    private ArrayList<QuizData> quizData = new ArrayList<QuizData>();

    public String getLessonId() {
        return lessonId;
    }

    public void setLessonId(String lessonId) {
        this.lessonId = lessonId;
    }

    public String getLessonClassId() {
        return lessonClassId;
    }

    public void setLessonClassId(String lessonClassId) {
        this.lessonClassId = lessonClassId;
    }

    public String getLessonName() {
        return lessonName;
    }

    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }

    public String getLessonDate() {
        return lessonDate;
    }

    public void setLessonDate(String lessonDate) {
        this.lessonDate = lessonDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLessonImage() {
        return lessonImage;
    }

    public void setLessonImage(String lessonImage) {
        this.lessonImage = lessonImage;
    }

    public String getImageThumbnail() {
        return imageThumbnail;
    }

    public void setImageThumbnail(String imageThumbnail) {
        this.imageThumbnail = imageThumbnail;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getVideoThumbnail() {
        return videoThumbnail;
    }

    public void setVideoThumbnail(String videoThumbnail) {
        this.videoThumbnail = videoThumbnail;
    }

    public ArrayList<QuizData> getQuizData() {
        return quizData;
    }

    public void setQuizData(ArrayList<QuizData> quizData) {
        this.quizData = quizData;
    }








}
