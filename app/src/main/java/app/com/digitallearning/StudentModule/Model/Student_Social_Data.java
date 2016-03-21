package app.com.digitallearning.StudentModule.Model;

import java.io.Serializable;

/**
 * Created by ${PSR} on 3/19/16.
 */
public class Student_Social_Data implements Serializable {

    public String getSocial_id() {
        return social_id;
    }

    public void setSocial_id(String social_id) {
        this.social_id = social_id;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    private String social_id;
    private String wechat;
    private String whatsapp;
}
