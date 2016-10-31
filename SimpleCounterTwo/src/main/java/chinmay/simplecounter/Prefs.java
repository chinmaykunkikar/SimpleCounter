package chinmay.simplecounter;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Prefs extends PreferenceActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        // TODO Toggle vibration
        // TODO Toggle brightness
        // TODO Use fragments and other shit that makes it stable
        // STOPSHIP: 23-08-2016
    }
}
