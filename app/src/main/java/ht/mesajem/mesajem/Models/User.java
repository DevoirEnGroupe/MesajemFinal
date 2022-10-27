package ht.mesajem.mesajem.Models;

import com.parse.ParseClassName;
import com.parse.ParseObject;

import org.parceler.Parcel;

@Parcel(analyze = User.class)
@ParseClassName("User")

public class User extends ParseObject {

    public static final String KEY_EMAIL = "email";


    public String getEmail(){
        return getString(KEY_EMAIL);
    }
    public void setEmail(String email){
        put(KEY_EMAIL,email);
    }
}
