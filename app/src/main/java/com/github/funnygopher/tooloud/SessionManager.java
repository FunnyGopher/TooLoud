package com.github.funnygopher.tooloud;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences sessionPref;
    SharedPreferences.Editor sessionEditor;
    Context context;
    int PRIVATE_MODE = 0;

    private static final String SESSION_PREF_NAME = "Session Preferences";
    private static final String IN_SESSION = "InSession";

    // The data keys
    public static final String KEY_ADDRESS = "address";

    public SessionManager(Context context) {
        this.context = context;
        sessionPref = this.context.getSharedPreferences(SESSION_PREF_NAME, PRIVATE_MODE);
        sessionEditor = sessionPref.edit();
    }

    // Adds all of the user data to the apps preferences
    public void createSession(String address) {
        sessionEditor.putBoolean(IN_SESSION, true);
        sessionEditor.putString(KEY_ADDRESS, address);
        sessionEditor.commit();
    }

    // Return information about the user
    public HashMap<String, String> getSessionPreferences() {
        HashMap<String, String> session = new HashMap<>();
        session.put(KEY_ADDRESS, sessionPref.getString(KEY_ADDRESS, null));
        return session;
    }

    // Removes the user's data from the preferences
    public void destroySession() {
        sessionEditor.clear();
        sessionEditor.commit();
    }

    public boolean inSession() {
        return sessionPref.getBoolean(IN_SESSION, false);
    }
}
