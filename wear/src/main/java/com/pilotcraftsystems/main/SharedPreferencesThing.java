package com.pilotcraftsystems.main;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by csastudent2015 on 5/24/16.
 */
public class SharedPreferencesThing {
        public static final String KEY_PREFS_SMS_BODY = "sms_body";
        private final String APP_SHARED_PREFS = SharedPreferencesThing.class.getSimpleName(); //  Name of the file -.xml
        private SharedPreferences _sharedPrefs;
        private SharedPreferences.Editor _prefsEditor;

        public SharedPreferencesThing(Context context) {
            this._sharedPrefs = context.getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
            this._prefsEditor = _sharedPrefs.edit();
        }

        public String getSmsBody() {
            return _sharedPrefs.getString(KEY_PREFS_SMS_BODY, "");
        }

        public void saveSmsBody(String text) {
            _prefsEditor.putString(KEY_PREFS_SMS_BODY, text);
            _prefsEditor.commit();
        }

    HashMap<String, Boolean> name = new HashMap<String, Boolean>();
}

