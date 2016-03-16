package app.com.digitallearning.TeacherModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/15/16.
 */
public class Unarchieve_Student implements Serializable {

    private String Student_Name;
    private String Student_Memid;
    private String Student_Mem_Userid;
    private String Student_Password;
    private String Student_Mem_Type;
    private String Student_gradebookview;
    private String Student_LastName;
    private String Student_Email_id;
    private String Student_Mem_Online;

    public String getStudent_Name() {
        return Student_Name;
    }

    public void setStudent_Name(String student_Name) {
        Student_Name = student_Name;
    }

    public String getStudent_Memid() {
        return Student_Memid;
    }

    public void setStudent_Memid(String student_Memid) {
        Student_Memid = student_Memid;
    }

    public String getStudent_Mem_Userid() {
        return Student_Mem_Userid;
    }

    public void setStudent_Mem_Userid(String student_Mem_Userid) {
        Student_Mem_Userid = student_Mem_Userid;
    }

    public String getStudent_Password() {
        return Student_Password;
    }

    public void setStudent_Password(String student_Password) {
        Student_Password = student_Password;
    }

    public String getStudent_Mem_Type() {
        return Student_Mem_Type;
    }

    public void setStudent_Mem_Type(String student_Mem_Type) {
        Student_Mem_Type = student_Mem_Type;
    }

    public String getStudent_gradebookview() {
        return Student_gradebookview;
    }

    public void setStudent_gradebookview(String student_gradebookview) {
        Student_gradebookview = student_gradebookview;
    }

    public String getStudent_LastName() {
        return Student_LastName;
    }

    public void setStudent_LastName(String student_LastName) {
        Student_LastName = student_LastName;
    }

    public String getStudent_Email_id() {
        return Student_Email_id;
    }

    public void setStudent_Email_id(String student_Email_id) {
        Student_Email_id = student_Email_id;
    }

    public String getStudent_Mem_Online() {
        return Student_Mem_Online;
    }

    public void setStudent_Mem_Online(String student_Mem_Online) {
        Student_Mem_Online = student_Mem_Online;
    }


}
