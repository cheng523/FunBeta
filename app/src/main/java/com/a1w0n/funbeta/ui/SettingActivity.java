package com.a1w0n.funbeta.ui;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import com.a1w0n.funbeta.R;

public class SettingActivity extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.setting);
	}

}
