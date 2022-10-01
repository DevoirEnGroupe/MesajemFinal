package ht.mesajem.mesajem.Models;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;
import com.parse.ParseUser;

import org.parceler.Parcel;

import java.util.Date;


@Parcel(analyze = Post.class)
@ParseClassName("Post")
public class Post extends ParseObject {

    public static final String KEY_IMAGE ="image";
    public static final String KEY_USER ="user";
    public static final String KEY_PICKUP_DATE ="pickupdate";
    // public static final String KEY_OBJECT_ID = "objectId";
    public static final String KEY_STATUS = "status";
    public static final String KEY_ARRIVE_DATE ="arrivedate";
    public static final String KEY_ESTIMATE_DATE ="estimatedate";
    public static final String KEY_LOCATION ="location";
    public static final String KEY_FULL_NAME = "fullname";
    public static final String KEY_INSTITUT = "institutname";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_ADDRESSE = "addresse";
    public static final String KEY_POST_ACC = "postaccept";
    public static final String KEY_POST = "post";




    public ParseFile getKeyImage(){
        return getParseFile(KEY_IMAGE);
    }
    public void setKeyImage(ParseFile parseFile){
        put(KEY_IMAGE, parseFile);
    }

    public ParseUser getUser(){
        return getParseUser(KEY_USER);
    }
    public void setUser(ParseUser user){
        put(KEY_USER, user);
    }


    public Date getPickupdate(){
        return  getDate(KEY_PICKUP_DATE);
    }
    public void setPickupdate(Date pickupdate){
        put(KEY_PICKUP_DATE,pickupdate);
    }

    public Date getArrivedate(){
        return  getDate(KEY_ARRIVE_DATE);
    }
    public void setArrivedate(Date arrivedate) {
        put(KEY_ARRIVE_DATE, arrivedate);
    }
    public Date getEstimatedate(){
        return  getDate(KEY_ESTIMATE_DATE);
    }
    public void setEstimatedate(Date estimatedate) {
        put(KEY_ESTIMATE_DATE, estimatedate);
    }

    public Number getStatus(){
        return getNumber(KEY_STATUS);
    }
    public void setStatus(Number status){
        put(KEY_STATUS,status);
    }


    public String getFullname(){
        return getString(KEY_FULL_NAME);
    }
    public String getInstitut(){
        return getString(KEY_INSTITUT);
    }
    public String getEmail(){
        return getString(KEY_EMAIL);
    }
    public String getAddresse(){
        return getString(KEY_ADDRESSE);
    }
    public String getPostacc(){
        return getString(KEY_POST);
    }

    public void setFullname(String nom){
        put(KEY_FULL_NAME,nom);
    }
    public void setPostacc(String post){
        put(KEY_POST,post);
    }

    public void setPrenom(String institut){
        put(KEY_INSTITUT,institut);
    }


    public void setEmail(String email){
        put(KEY_EMAIL,email);
    }


    public void setAddresse(String addresse){
        put(KEY_ADDRESSE,addresse);
    }

    public ParseGeoPoint getLocation() {
        return getParseGeoPoint(KEY_LOCATION);
    }
    public void setLocation(ParseGeoPoint location){
        put(KEY_LOCATION,location);
    }


}
