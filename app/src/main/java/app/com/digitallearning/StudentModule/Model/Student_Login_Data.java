package app.com.digitallearning.StudentModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/19/16.
 */
public class Student_Login_Data implements Serializable {

    private String class_id;
    private String class_name;
    private String subject;
    private String students;

    public String getClassdesc() {
        return classdesc;
    }

    public void setClassdesc(String classdesc) {
        this.classdesc = classdesc;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getCla_Id() {
        return Cla_Id;
    }

    public void setCla_Id(String cla_Id) {
        Cla_Id = cla_Id;
    }

    private String classdesc;
    private String topicname;
    private String Cla_Id;



    public String getClass_id() {
        return class_id;
    }

    public void setClass_id(String class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStudents() {
        return students;
    }

    public void setStudents(String students) {
        this.students = students;
    }


}
