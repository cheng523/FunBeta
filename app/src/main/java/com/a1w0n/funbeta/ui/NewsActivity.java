package com.aiwan.funbeta.ui;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.aiwan.funbeta.db.ArtcileService;
import com.aiwan.funbeta.po.Article;
import com.aiwan.funbeta.tools.HtmlTool;
import com.android.ui.R;

public class NewsActivity extends Activity {

	private static final int MENU_SETTING = Menu.FIRST + 1;
	private static final int MENU_REFRESH = Menu.FIRST + 2;
	TextView titleTextView;
	TextView contentTextView;
	private String href;
	private String title;

	private ProgressDialog pd;
	private String content;
	private Spanned contentSpanned;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.news);

		titleTextView = (TextView) findViewById(R.id.news_title);
		contentTextView = (TextView) findViewById(R.id.news_content);

		Intent intent = getIntent();
		title = intent.getStringExtra("title");
		href = intent.getStringExtra("href");

		refreshData();

	}

	/**
	 * ����/ˢ�� ��ǰҳ��
	 * 
	 * @return
	 */
	private void refreshData() {

		titleTextView.setText(title);

		pd = ProgressDialog.show(this, null,
				getResources().getString(R.string.loading_tip));// ��ʾProgressBar

		new Thread(new Runnable() {
			@Override
			public void run() {
				loadContent();
				boolean showImage = PreferenceManager
						.getDefaultSharedPreferences(NewsActivity.this)
						.getBoolean("if_show_image", false);// ��ȡif_show_image��ֵ
				if (showImage) {// ��ʾͼƬ
					contentSpanned = Html.fromHtml(content, imageGetter, null);
				} else {// ����ʾͼƬ
					contentSpanned = Html.fromHtml(content);
				}
				handler.sendEmptyMessage(0);
			}

		}).start();
	}

	/**
	 * ������������
	 */
	private void loadContent() {
		if (!tryToLoadFromDataBase()) {// �ӱ��ؼ��ز��ɹ����������أ��������ص������ݴ�����ݿ�
			content = HtmlTool.getNewsContentHtmlFromMobilePage(href);
			Article a = new Article(Article.TYPE_CNBETA_MOBILE, href, title,
					content);
			ArtcileService.getInstance(NewsActivity.this).insert(a);

		}
	}

	/**
	 * ���Դ���ݿ��м�����������
	 * 
	 * @return �Ƿ�ɹ�����
	 */
	private boolean tryToLoadFromDataBase() {
		Article article = ArtcileService.getInstance(this)
				.getArticleByUrl(href);
		if (article == null) {
			return false;
		} else {
			content = article.getContent();
			return true;
		}
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (content == null) {
				Toast.makeText(NewsActivity.this,
						R.string.no_intenert_connection_tip, Toast.LENGTH_LONG)
						.show();
			} else {
				contentTextView.setText(contentSpanned);
			}
			pd.dismiss();
		}
	};
	/**
	 * ��ʾͼƬ��ImageGetter
	 */
	private Html.ImageGetter imageGetter = new Html.ImageGetter() {
		@Override
		public Drawable getDrawable(String source) {
			InputStream is = null;
			try {
				is = (InputStream) new URL(source).getContent();
				Drawable d = Drawable.createFromStream(is, "src");
				d.setBounds(0, 0, d.getIntrinsicWidth(), d.getIntrinsicHeight());
				is.close();
				return d;
			} catch (Exception e) {
				return null;
			}
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SETTING, 1,
				getResources().getString(R.string.setting)).setIcon(
				android.R.drawable.ic_menu_preferences);
		menu.add(Menu.NONE, MENU_REFRESH, 2,
				getResources().getString(R.string.refresh)).setIcon(
				R.drawable.ic_menu_refresh);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case MENU_SETTING:
			startActivity(new Intent(this, SettingActivity.class));
			break;
		case MENU_REFRESH:
			this.refreshData();
			break;
		}
		return false;
	}
}
