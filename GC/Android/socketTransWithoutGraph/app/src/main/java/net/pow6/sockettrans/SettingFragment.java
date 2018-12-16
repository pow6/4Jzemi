package net.pow6.sockettrans;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import java.util.prefs.PreferencesFactory;

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_main);
    }
}
