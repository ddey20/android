package ratingapp.ddey.com.dam_project.utils.others;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

import ratingapp.ddey.com.dam_project.activities.general.LoginActivity;
import ratingapp.ddey.com.dam_project.models.User;



public class Session {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private Context context;

    public Session (Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(Constants.PREFER_NAME, Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public SharedPreferences getPrefs() {
        return prefs;
    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void createUserLoginSession(User user){
        editor.putBoolean(Constants.IS_USER_LOGIN, true);
        editor.putString(Constants.KEY_NAME, user.getName());
        editor.putString(Constants.KEY_PASS, user.getPassword());
        editor.putString(Constants.KEY_EMAIL, user.getEmail());
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {


        HashMap<String, String> user = new HashMap<String, String>();

        user.put(Constants.KEY_EMAIL, prefs.getString(Constants.KEY_EMAIL, null));
        user.put(Constants.KEY_PASS, prefs.getString(Constants.KEY_PASS, null));

        return user;
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }


    public boolean isUserLoggedIn(){
        return prefs.getBoolean(Constants.IS_USER_LOGIN, false);
    }
}
