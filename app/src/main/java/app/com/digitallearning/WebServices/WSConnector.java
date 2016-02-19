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
// E/Sch_Mem_id: android test
//    02-01 16:36:10.328 25104-25271/app.com.digitallearning E/Mem_Sch_Id: Instructor
//    02-01 16:36:10.328 25104-25271/app.com.digitallearning E/title:  Music
//    02-01 16:36:10.329 25104-25271/app.com.digitallearning E/classtpye: one
//    02-01 16:36:10.329 25104-25271/app.com.digitallearning E/topic: 2155
//            02-01 16:36:10.329 25104-25271/app.com.digitallearning E/String Response?????: enter this method
 //   02-01 16:36:11.241 25104-25104/app.com.digitallearning E/MainResult: {"success":false,"data":"Parameter missing"}

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
        pairs.add(new BasicNameValuePair("enable_students_tab", ""));
        Log.e("enable_students_tab",""+"");
        pairs.add(new BasicNameValuePair("enable_chat", ""));
        Log.e("enable_chat",""+"");
        pairs.add(new BasicNameValuePair("enable_grades ", ""));
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
}
