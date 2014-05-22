package com.aiwan.funbeta.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.aiwan.funbeta.tools.TxtReader;
import com.android.ui.R;

/**
 * ������ʾ��Ϣ�����������Ϣ��������־��
 * 
 * @author Administrator
 * 
 */
public class ShowInformationActivity extends Activity {
	public static final int KEY_ABOUT = 67;
	public static final int KEY_UPDATE_INF = 71;
	public static final int KEY_NULL = 0;

	Button returnButton;
	TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.information);

		returnButton = (Button) findViewById(R.id.returnButton);
		textView = (TextView) findViewById(R.id.informationTextView);

		returnButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ShowInformationActivity.this.onBackPressed();
			}
		});

		Intent intent = getIntent();
		int key = intent.getIntExtra("key", KEY_NULL);

		switch (key) {
		case KEY_ABOUT:
			setTitle(getResources().getString(R.string.about));
			textView.setText(TxtReader.getString(getResources()
					.openRawResource(R.raw.about)));
			break;
		case KEY_UPDATE_INF:
			setTitle(getResources().getString(R.string.update_log));
			textView.setText(TxtReader.getString(getResources()
					.openRawResource(R.raw.update)));
			break;
		case KEY_NULL:
			super.onBackPressed();
			break;
		}
	}
}
