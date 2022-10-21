package ht.mesajem.mesajem.Models;


import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

@Parcel(analyze = Delivery.class)
@ParseClassName("Delivery")
public class Delivery extends ParseObject {


    public static final String KEY_BODY = "body";
    public static final String KEY_SUBJECT = "subject";
    public static final String KEY_USER ="userd";
    public static final String KEY_STATUS ="status";
    public static final String KEY_FULLNAME = "fullname";


    public Delivery(){

    }

    public String getFullname(){
        return getString(KEY_FULLNAME);
    }

    public String getBody(){
        return getString(KEY_BODY);
    }
    public String getSubject(){
        return getString(KEY_SUBJECT);
    }

    public void setFullname(String fullname){
        put(KEY_FULLNAME,fullname);
    }


    public ParseUser getUserd(){
        return getParseUser(KEY_USER);
    }
    public void setUserd(ParseUser user){
        put(KEY_USER, user);
    }

    public Boolean getStatus(){return  getBoolean(KEY_STATUS);}
    public void setStatus(Boolean status){put(KEY_STATUS,status);}
}
