package app.com.digitallearning.WebServices;

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

import app.com.digitallearning.Constant.AppConstant;

/**
 * @author Shalvi Sharma
 */
public class WSConnector {

	/*

	Login API

	*/
    public static String schoolListing() {

        String url = AppConstant.school_Listing;
        String result = WSAdapter.getJSONObject(url);
        return result;

    }


    public static String login(String name, String password,String schoolID) {

        String url = AppConstant.login;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("username", name));
        Log.e("username",""+name);
        pairs.add(new BasicNameValuePair("password", password));
        Log.e("password",""+password);
        pairs.add(new BasicNameValuePair("logid", schoolID));
        Log.e("schoolID",""+schoolID);
        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String CreateClass(String Sch_Mem_id,String Mem_Sch_Id,String title,String classtpye,String arrId,String description) {

        String url = AppConstant.create_Class;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", Sch_Mem_id));
        Log.e("Sch_Mem_id",""+Sch_Mem_id);
        pairs.add(new BasicNameValuePair("memschid", Mem_Sch_Id));
        Log.e("Mem_Sch_Id",""+Mem_Sch_Id);
        pairs.add(new BasicNameValuePair("classname", title));
        Log.e("title",""+title);
        pairs.add(new BasicNameValuePair("style", classtpye));
        Log.e("classtpye",""+classtpye);
        pairs.add(new BasicNameValuePair("topic", arrId));
        Log.e("topic",""+arrId);
        pairs.add(new BasicNameValuePair("desc", description));
        Log.e("description",""+description);
        pairs.add(new BasicNameValuePair("othertopicdata", ""));
        Log.e("othertopicdata","");
        String result = WSAdapter.postJSONObject(url, pairs);

        return result;

    }

    public static String Before_Class_Listing_DoIn() {

        String url = AppConstant.before_Class_Listing;

        String result = WSAdapter.getJSONObject(url);


        return result;

    }

    public static String edit_Class(String singleClassID, String userid,String schid) {

        String url = AppConstant.edit_Class;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("eid", singleClassID));
        Log.e("classId",""+singleClassID);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("schid", schid));
        Log.e("schid",""+schid);
        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String update_class(String classidgetupdate, String created,String Mem_Sch_Id, String classname, String sr_classtppeval, String sr_topic,String  sr_description, String check,String semeserval, String coursecodeval,String  cal,String  stu,String chat, String grades) {

        String url = AppConstant.update_Class;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classidgetupdate));
        Log.e("classId",""+classidgetupdate);
        pairs.add(new BasicNameValuePair("createdby", created));
        Log.e("userid",""+created);
        pairs.add(new BasicNameValuePair("schoolid", Mem_Sch_Id));
        Log.e("Mem_Sch_Id",""+Mem_Sch_Id);
        pairs.add(new BasicNameValuePair("classname", classname));
        Log.e("classname",""+classname);
        pairs.add(new BasicNameValuePair("style", sr_classtppeval));
        Log.e("sr_classtppeval",""+sr_classtppeval);
        pairs.add(new BasicNameValuePair("topic", sr_topic));
        Log.e("sr_topic",""+sr_topic);
        pairs.add(new BasicNameValuePair("desc", sr_description));
        Log.e("sr_description",""+sr_description);
        pairs.add(new BasicNameValuePair("show_links", check));
        Log.e("check",""+check);
        pairs.add(new BasicNameValuePair("semester", semeserval));
        Log.e("semeserval",""+semeserval);
        pairs.add(new BasicNameValuePair("coursecode", coursecodeval));
        Log.e("coursecodeval",""+coursecodeval);
        pairs.add(new BasicNameValuePair("othertopicdata", ""));
        Log.e("othertopicdata",""+"");
        pairs.add(new BasicNameValuePair("enable_calendar", cal));
        Log.e("cal",""+cal);
        pairs.add(new BasicNameValuePair("enable_students_tab",stu ));
        Log.e("enable_students_tab",""+"");
        pairs.add(new BasicNameValuePair("enable_chat", chat));
        Log.e("enable_chat",""+"");
        pairs.add(new BasicNameValuePair("enable_grades", grades));
        Log.e("userid",""+created);

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Delete_class(String cla_classid ,String Sch_Mem_id ,String Mem_Sch_Id , String password) {

        String url = AppConstant.delete_class;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cla_classid));
        Log.e("cid",""+cla_classid);
        pairs.add(new BasicNameValuePair("userid", Sch_Mem_id));
        Log.e("userid",""+Sch_Mem_id);
        pairs.add(new BasicNameValuePair("schid", Mem_Sch_Id));
        Log.e("schid",""+Mem_Sch_Id);
        pairs.add(new BasicNameValuePair("password", password));
        Log.e("password",""+password);

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Schedule_listing(String userid,String clsid) {

        String url = AppConstant.schedule_listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("clsid", clsid));
        Log.e("userid",""+userid);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Before_Edit_Schedule(String userid,String clsid,String timeId) {

        String url = AppConstant.before_Edit_Schedule;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("cid", clsid));
        pairs.add(new BasicNameValuePair("timeid", timeId));
        Log.e("userid",""+userid);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String update_Schedule(String cid ,String userid ,String day ,String str_hour ,String str_min ,String end_hour ,String en_min ,String timeid ,String location ,String desc) {

        String url = AppConstant.update_Schedule;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("day", day));
        Log.e("day",""+day);
        pairs.add(new BasicNameValuePair("str_hour", str_hour));
        Log.e("str_hour",""+str_hour);
        pairs.add(new BasicNameValuePair("str_min", str_min));
        Log.e("str_min",""+str_min);
        pairs.add(new BasicNameValuePair("end_hour", end_hour));
        Log.e("end_hour",""+end_hour);
        pairs.add(new BasicNameValuePair("en_min", en_min));
        Log.e("en_min",""+en_min);
        pairs.add(new BasicNameValuePair("timeid", timeid));
        Log.e("timeid",""+timeid);
        pairs.add(new BasicNameValuePair("location", location));
        Log.e("location",""+location);
        pairs.add(new BasicNameValuePair("desc", desc));
        Log.e("desc",""+desc);

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String add_Schedule(String cid ,String userid ,String day ,String str_hour ,String str_min ,String end_hour ,String en_min,String location ,String desc) {

        String url = AppConstant.add_Schedule;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("day", day));
        Log.e("day",""+day);
        pairs.add(new BasicNameValuePair("str_hour", str_hour));
        Log.e("str_hour",""+str_hour);
        pairs.add(new BasicNameValuePair("str_min", str_min));
        Log.e("str_min",""+str_min);
        pairs.add(new BasicNameValuePair("end_hour", end_hour));
        Log.e("end_hour",""+end_hour);
        pairs.add(new BasicNameValuePair("en_min", en_min));
        Log.e("en_min",""+en_min);
        pairs.add(new BasicNameValuePair("location", location));
        Log.e("location",""+location);
        pairs.add(new BasicNameValuePair("desc", desc));
        Log.e("desc",""+desc);

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String delete_Schedule(String del_id) {

        String url = AppConstant.delete_Schedule;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("del_id", del_id));
        Log.e("del_id",""+del_id);

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Add_syllabus(String  cid,String userid,String title,String desc) {

        String url = AppConstant.add_Syllabus;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("title", title));
        Log.e("title",""+title);
        pairs.add(new BasicNameValuePair("desc", desc));
        Log.e("desc",""+desc);

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Get_syllabus(String  cid,String userid) {

        String url = AppConstant.get_Syllabus;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("clsid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }
    public static String Update_Syllabus(String cla_classid,String Sch_Mem_id,String srtitle,String srdescription,String  sy_id) {

        String url = AppConstant.update_Syllabus;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cla_classid));
        pairs.add(new BasicNameValuePair("userid", Sch_Mem_id));
        pairs.add(new BasicNameValuePair("title", srtitle));
        pairs.add(new BasicNameValuePair("desc", srdescription));
        pairs.add(new BasicNameValuePair("sy_id", sy_id));
        Log.e("sy_id",""+sy_id);


        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }
    public static String Delete_syllabus(String sy_id) {

        String url = AppConstant.delete_Syllabus;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("sydid", sy_id));



        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Get_curriculum(String  cid,String userid) {

        String url = AppConstant.get_Curriculum;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }



    public static String Delete_Curriculum(String del_currid,String userid) {

        String url = AppConstant.delete_Curriculum;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("del_currid", del_currid));
        pairs.add(new BasicNameValuePair("userid", userid));


        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }
    public static String Country_list() {

        String url = AppConstant.country_list;

        String result = WSAdapter.getJSONObject(url);


        return result;

    }



    public static String Add_curriculum(String cid ,String userid ,String title ,String topic ,String desc ,String library ,String lo_age ,String hi_age ,String organization ,String country ,String state) {

        String url = AppConstant.add_Curriculum;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("title", title));
        pairs.add(new BasicNameValuePair("topic", topic));
        pairs.add(new BasicNameValuePair("desc", desc));
        pairs.add(new BasicNameValuePair("library", library));
        pairs.add(new BasicNameValuePair("lo_age", lo_age));
        pairs.add(new BasicNameValuePair("hi_age", hi_age));
        pairs.add(new BasicNameValuePair("organization", organization));
        pairs.add(new BasicNameValuePair("country", country));
        pairs.add(new BasicNameValuePair("state", state));


        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }
    public static String Update_curriculum(String cid ,String userid ,String title ,String topic ,String desc ,String library ,String lo_age ,String hi_age ,String organization ,String country ,String state,String curr_edit_id) {

        String url = AppConstant.edit_Curriculum;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("title", title));
        Log.e("title",""+title);
        pairs.add(new BasicNameValuePair("topic", topic));
        Log.e("topic",""+topic);
        pairs.add(new BasicNameValuePair("desc", desc));
        Log.e("desc",""+desc);
        pairs.add(new BasicNameValuePair("library", library));
        Log.e("library",""+library);
        pairs.add(new BasicNameValuePair("lo_age", lo_age));
        Log.e("lo_age",""+lo_age);
        pairs.add(new BasicNameValuePair("hi_age", hi_age));
        Log.e("hi_age",""+hi_age);
        pairs.add(new BasicNameValuePair("organization", organization));
        Log.e("organization",""+organization);
        pairs.add(new BasicNameValuePair("country", country));
        Log.e("country",""+country);
        pairs.add(new BasicNameValuePair("state", state));
        Log.e("state",""+state);
        pairs.add(new BasicNameValuePair("curr_edit_id", curr_edit_id));
        Log.e("curr_edit_id",""+curr_edit_id);


        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Get_image(String classid) {

        String url = AppConstant.getclass_image;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classid));
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;
    }



    public static String Get_Lesson(String  cid,String userid) {

        String url = AppConstant.get_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);

        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }


    public static String Add_Lesson(String  cid,String userid,String  title,String desc,String les_date,String video_url) {

        String url = AppConstant.create_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("title", title));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("desc", desc));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("les_date", les_date));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("video_url", video_url));
        Log.e("userid",""+userid);
        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }



    public static String Before_Lesson_Edit(String userid,String les_edit_id) {

        String url = AppConstant.before_edit_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("les_edit_id", les_edit_id));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }
    public static String Edit_Lesson(String  cid,String userid,String  title,String desc,String les_date,String lessonId,String video_url) {

        String url = AppConstant.create_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        pairs.add(new BasicNameValuePair("title", title));
        Log.e("title",""+title);
        pairs.add(new BasicNameValuePair("desc", desc));
        Log.e("desc",""+desc);
        pairs.add(new BasicNameValuePair("les_date", les_date));
        Log.e("les_date",""+les_date);
        pairs.add(new BasicNameValuePair("les_id", lessonId));
        Log.e("lessonId",""+lessonId);
        pairs.add(new BasicNameValuePair("video_url", video_url));
        Log.e("video_url",""+video_url);
        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }


    public static String Delete_Lesson(String userid,String del_les_id) {

        String url = AppConstant.delete_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("del_les_id", del_les_id));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }
    public static String Teacher_getProfile(String userid,String name) {

        String url = AppConstant.teacher_getProfile;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("username", name));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }
    public static String Add_Resource(String cid,String userid,String schid,String title,String desc) {

        String url = AppConstant.add_Resource;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("schid", schid));
        pairs.add(new BasicNameValuePair("title", title));
        pairs.add(new BasicNameValuePair("desc", desc));
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;
    }

    public static String Resource_Listing(String classid,String userid) {

        String url = AppConstant.resource_Listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("userid", userid));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Update_Resource(String classid,String userid,String schid,String title,String desc,String res_id) {

        String url = AppConstant.add_Resource;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", classid));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("schid", schid));
        pairs.add(new BasicNameValuePair("title", title));
        pairs.add(new BasicNameValuePair("desc", desc));
        pairs.add(new BasicNameValuePair("res_id", res_id));

        Log.e("classid",""+classid);
        Log.e("userid",""+userid);
        Log.e("schid",""+schid);
        Log.e("title",""+title);
        Log.e("desc",""+desc);
        Log.e("res_id",""+res_id);

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }


    public static String Delete_Resource(String userid,String del_res_id) {

        String url = AppConstant.resource_Listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("del_res_id", del_res_id));
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }


    public static String Add_Student(String userid,String classid,String schid,String first_name,String last_name,String email,String password,String created_date) {

        String url = AppConstant.add_student;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("schid", schid));
        pairs.add(new BasicNameValuePair("first_name", first_name));
        pairs.add(new BasicNameValuePair("last_name", last_name));
        pairs.add(new BasicNameValuePair("email", email));
        pairs.add(new BasicNameValuePair("password", password));
        pairs.add(new BasicNameValuePair("created_date", created_date));
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;
    }
    public static String Get_archive_Student(String userid,String classid) {

        String url = AppConstant.get_student;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("student_type", "0"));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Get_enroll_Student(String userid,String classid) {

        String url = AppConstant.get_student;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("student_type", "1"));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Unarchieve_Student(String classid,String unarch_stu_id) {

        String url = AppConstant.unarchieve_student;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("unarch_stu_id", unarch_stu_id));

        Log.e("unarch",""+classid);
        Log.e("unarchstu",""+unarch_stu_id);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Archieve_Student(String classid,String unarch_stu_id) {

        String url = AppConstant.unarchieve_student;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("arch_stu_id", unarch_stu_id));

        Log.e("unarch",""+classid);
        Log.e("unarchstu",""+unarch_stu_id);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Delete_Student(String classid,String unarch_stu_id) {

        String url = AppConstant.add_student;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("mem_id", unarch_stu_id));

        Log.e("unarch",""+classid);
        Log.e("unarchstu",""+unarch_stu_id);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }
    public static String Quiz_listing(String quizcid,String userid) {

        String url = AppConstant.quiz_listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("quizcid", quizcid));
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Add_Quiz(String classid,String userid,String schid,String lessonid,String quiz_title,String quiz_description,String qzdata,String last_modified) {
        String url = AppConstant.quiz_listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("schid", schid));
        pairs.add(new BasicNameValuePair("lessonid", lessonid));
        pairs.add(new BasicNameValuePair("quiz_title", quiz_title));
        pairs.add(new BasicNameValuePair("quiz_description", quiz_description));
        pairs.add(new BasicNameValuePair("qzdata", qzdata));
        pairs.add(new BasicNameValuePair("last_modified", last_modified));
        pairs.add(new BasicNameValuePair("user_type", "4"));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }
    public static String Quiz_view(String quizclassid,String userid,String quizid) {

        String url = AppConstant.quiz_listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("quizclassid", quizclassid));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("quizid", quizid));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }


    public static String Update_Quiz(String classid,String userid,String schid,String lessonid,String quiz_title,String quiz_description,String qzdata,String last_modified,String edit_quiz_id) {
        String url = AppConstant.quiz_listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("schid", schid));
        pairs.add(new BasicNameValuePair("lessonid", lessonid));
        pairs.add(new BasicNameValuePair("quiz_title", quiz_title));
        pairs.add(new BasicNameValuePair("quiz_description", quiz_description));
        pairs.add(new BasicNameValuePair("quiz_data", qzdata));
        pairs.add(new BasicNameValuePair("last_modified", last_modified));
        pairs.add(new BasicNameValuePair("edit_quiz_id",edit_quiz_id));
        pairs.add(new BasicNameValuePair("user_type", "4"));

        Log.e("classid",""+classid);
        Log.e("userid",""+userid);
        Log.e("schid",""+schid);
        Log.e("lessonid",""+lessonid);
        Log.e("quiz_title",""+quiz_title);
        Log.e("quiz_description",""+quiz_description);
        Log.e("qzdata",""+qzdata);
        Log.e("last_modified",""+last_modified);
        Log.e("edit_quiz_id",""+edit_quiz_id);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;
    }




    public static String Delete_quiz(String userid,String del_qm_id) {

        String url = AppConstant.quiz_listing;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("del_qm_id", del_qm_id));


        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Grade_lesson(String cid,String studid) {

        String url = AppConstant.grade_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        pairs.add(new BasicNameValuePair("studid", studid));


        Log.e("cid",""+cid);
        Log.e("studid",""+studid);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }









































    public static String Student_Info(String userid,String username) {

        String url = AppConstant.student_info;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();

        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("username", username));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Student_Get_Lesson(String cid) {

        String url = AppConstant.student_lesson;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        Log.e("cid",""+cid);


        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Student_Get_Resource(String  classid , String userid) {

        String url = AppConstant.student_resource;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classid));
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("classid",""+classid);


        String result = WSAdapter.postJSONObject(url, pairs);


        return result;

    }

    public static String Stuent_Quiz_listing(String cid ,String userid) {

        String url = AppConstant.student_quiz;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("cid", cid));
        pairs.add(new BasicNameValuePair("userid", userid));
        Log.e("userid",""+userid);
        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Student_Quiz_view(String cls_clsid,String quiz_id) {

        String url = AppConstant.student_quiz;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", cls_clsid));
        pairs.add(new BasicNameValuePair("quizid", quiz_id));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }


    public static String Student_Enroll_class(String classid ,String userid ,String classpassword) {

        String url = AppConstant.student_enroll;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("classid", classid));

        pairs.add(new BasicNameValuePair("userid", userid));



        pairs.add(new BasicNameValuePair("classpassword", classpassword));


        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }

    public static String Student_Perform_Quiz(String quizmasterid ,String todaydate ,String ipaddr,String timelast,String userid,String answerdata) {

        String url = AppConstant.student_quiz;
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        pairs.add(new BasicNameValuePair("quizmasterid", quizmasterid));
        pairs.add(new BasicNameValuePair("todaydate", todaydate));
        pairs.add(new BasicNameValuePair("ipaddr", ipaddr));
        pairs.add(new BasicNameValuePair("timelast", timelast));
        pairs.add(new BasicNameValuePair("userid", userid));
        pairs.add(new BasicNameValuePair("answerdata", answerdata));

        String result = WSAdapter.postJSONObject(url, pairs);
        return result;

    }




}
