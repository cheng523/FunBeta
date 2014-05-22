package com.aiwan.funbeta.ui;

import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.aiwan.funbeta.db.ArtcileService;
import com.android.ui.R;

public class LocalArticlesListActivity extends Activity implements
		OnItemClickListener, OnItemLongClickListener {

	private static final int MENU_SETTING = 1;
	private static final int MENU_REFRESH = 2;
	private static final int MENU_CLEAR_DATA = 3;

	private ListView newsListView;
	private List<Map<String, String>> newsList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lacal_data_news_list);

		newsListView = (ListView) findViewById(R.id.localDatalistView);

		refreshData();

		newsListView.setOnItemClickListener(this);
		newsListView.setOnItemLongClickListener(this);
	}

	/**
	 * ����/ˢ�����
	 */
	private void refreshData() {
		newsList = ArtcileService.getInstance(this).getLocalArtclesList();
		newsListView.setAdapter(new SimpleAdapter(this, newsList,
				R.layout.local_data_item, new String[] { "title", "url" },
				new int[] { R.id.localData_itemTitleTextView,
						R.id.localData_itemHrefTextView }));

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SETTING, 1,
				getResources().getString(R.string.setting)).setIcon(
				android.R.drawable.ic_menu_preferences);
		menu.add(Menu.NONE, MENU_REFRESH, 2,
				getResources().getString(R.string.refresh)).setIcon(
				R.drawable.ic_menu_refresh);
		menu.add(Menu.NONE, MENU_CLEAR_DATA, 3,
				getResources().getString(R.string.clear_local_data)).setIcon(
				android.R.drawable.ic_menu_close_clear_cancel);
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
		case MENU_CLEAR_DATA:
			tryToClear();
			break;
		}
		return false;
	}

	/**
	 * 
	 */
	private void tryToClear() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.delete_tip));
		builder.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ArtcileService.getInstance(
								LocalArticlesListActivity.this).deleteAll();
						refreshData();
					}

				});
		builder.setNegativeButton(getResources().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// do nothing~
					}
				});
		builder.show();
	}

	/**
	 * ����¼�
	 */
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		String title = ((TextView) view
				.findViewById(R.id.localData_itemTitleTextView)).getText()
				.toString();
		String href = ((TextView) view
				.findViewById(R.id.localData_itemHrefTextView)).getText()
				.toString();

		Intent intent = new Intent(this, NewsActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("href", href);
		startActivity(intent);

	}

	/**
	 * �����¼�
	 */
	@Override
	public boolean onItemLongClick(AdapterView<?> adapterView, View view,
			int position, long id) {
		final String href = ((TextView) view
				.findViewById(R.id.localData_itemHrefTextView)).getText()
				.toString();
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage(getResources().getString(R.string.delete_tip));
		builder.setPositiveButton(getResources().getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ArtcileService.getInstance(
								LocalArticlesListActivity.this).deleteByUrl(
								href);
						refreshData();
					}

				});
		builder.setNegativeButton(getResources().getString(R.string.cancel),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// do nothing~
					}
				});
		builder.show();

		return false;
	}
}
