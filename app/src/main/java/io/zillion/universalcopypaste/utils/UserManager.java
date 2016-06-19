package io.zillion.universalcopypaste.utils;

import android.content.Context;

import com.anupcowkur.reservoir.Reservoir;
import com.google.gson.reflect.TypeToken;
import io.zillion.universalcopypaste.R;
import java.lang.reflect.Type;

/**
 * Created by jafarnaqvi on 21/05/16.
 */
public class UserManager {

    private Context context;
    private String Key;

    public UserManager(Context context) {

        this.context = context;
        Key = context.getResources().getString(R.string.UserKey);

//        generateDefaultUser();

    }


    private void generateDefaultUser() {

        if (getUser() == null) {

            User user = User.getInstance();
            user.setId("2");
            user.setAdd1("91springboard");
            user.setAdd2("Plot no 23");
            user.setAdd3("Gurgaon");
            user.setEmail("jafar912@gmail.com");
            user.setMobile("8431240688");
            user.setToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpZCI6MiwibW9iaWxlIjo4NDMxMjQwNjg4LCJpYXQiOjE0NjQ2ODI3MjF9.j3Xff4Ru8Y0SBg53egD7078efwD2qymwPzpY9EinSb0");

            saveUser(user);
        }

    }


    public void saveUser(User user) {
        try {
            Reservoir.put(Key, user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User getUser() {

        Type resultType = new TypeToken<User>() {
        }.getType();
        try {
            return Reservoir.get(Key, resultType);
        } catch (Exception e) {
            //failure
        }

        return null;
    }
}
