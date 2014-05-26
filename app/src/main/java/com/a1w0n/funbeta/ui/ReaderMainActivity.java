package com.a1w0n.funbeta.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.a1w0n.funbeta.R;
import com.a1w0n.funbeta.libs.pulltorefresh.PullToRefreshListView;
import com.a1w0n.funbeta.ui.Fragment.ReaderMainFragment;

import java.util.List;
import java.util.Map;

/**
 * 主Activity，就是个空壳而已。
 * 真正的内容和逻辑都在ReaderMainFragment
 */
public class ReaderMainActivity extends BaseActivity implements OnItemClickListener {

	private static final int MENU_SETTING = Menu.FIRST + 1;
	private static final int MENU_REFRESH = Menu.FIRST + 2;
	private static final int MENU_ABOUT = Menu.FIRST + 3;
	private static final int MENU_UPDATE_LOG = Menu.FIRST + 4;
	private static final int MENU_LOCAL_DATA = Menu.FIRST + 5;

	private int pageID;
	private ProgressDialog mProgressDialog;
	private PullToRefreshListView mNewsListView;
	private List<Map<String, String>> newsList;

    /**
     * Activity生命回调
     * 设置界面、初始化成员变量
     */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_readermain);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.ARM_mainContainer, new ReaderMainFragment()).commit();
        }

//		pageID = 0;
//		newsList = new ArrayList<Map<String, String>>();
//        initUIComponents();
//		getInitialNewsData();

	}

    /**
     * 初始化和配置UI控件，防止onCreate过于臃肿
     */
    private void initUIComponents() {
//        mNewsListView = (PullToRefreshListView) findViewById(R.id.newsListView);
//        mNewsListView.setOnItemClickListener(ReaderMainActivity.this);
//        mNewsListView.setMode(PullToRefreshBase.Mode.BOTH);
//
//        mNewsListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> refreshView) {
//
//            }
//        });


    }

	/**
	 * 
	 */
	private void loadMore() {
//		mProgressDialog = ProgressDialog.show(this, null, getResources()
//				.getString(R.string.loading_tip));
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				newsList.addAll(HtmlTool.getNewsListFromMobilePage(URL_MOBILE,
//						++pageID));
//				handler.sendEmptyMessage(0);
//			}
//		}).start();
//		//mNewsListView.removeFooterView(loadMoreButton);
	}

	/**
	 * 从网站上获取网页的源码，从而解析出新闻标题和内容
	 */
	private void getInitialNewsData() {
//		mProgressDialog = ProgressDialog.show(this, null, getResources()
//				.getString(R.string.loading_tip));
//		new Thread(new Runnable() {
//			@Override
//			public void run() {
//				newsList = (HtmlTool.getNewsListFromMobilePage(URL_MOBILE,
//						pageID));
//				handler.sendEmptyMessage(0);
//			}
//		}).start();
	}

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (newsList == null) {
				Toast.makeText(ReaderMainActivity.this,
						R.string.no_intenert_connection_tip, Toast.LENGTH_LONG)
						.show();
			} else {
				//mNewsListView.addFooterView(loadMoreButton);
				showNewsList();
				//mNewsListView.setSelection((pageID - 1) * 30 - 1);
			}
			mProgressDialog.dismiss();
		}
	};

	/**
	 * 
	 */
	private void showNewsList() {
		if (newsList == null || newsList.size() ==0) return;
		mNewsListView.setAdapter(new SimpleAdapter(this, newsList,
				R.layout.home_page_item, new String[] { "title", "href" },
				new int[] { R.id.homePage_itemTitleTextView,
						R.id.homePage_itemHrefTextView }));
	}

	@Override
	public void onItemClick(AdapterView<?> adapterView, View view,
			int position, long id) {

		String title = ((TextView) view
				.findViewById(R.id.homePage_itemTitleTextView)).getText()
				.toString();
		String href = ((TextView) view
				.findViewById(R.id.homePage_itemHrefTextView)).getText()
				.toString();

		Intent intent = new Intent(this, NewsActivity.class);
		intent.putExtra("title", title);
		intent.putExtra("href", href);
		startActivity(intent);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(Menu.NONE, MENU_SETTING, 1,
				getResources().getString(R.string.setting)).setIcon(
				android.R.drawable.ic_menu_preferences);
		menu.add(Menu.NONE, MENU_REFRESH, 2,
				getResources().getString(R.string.refresh)).setIcon(
				R.drawable.ic_menu_refresh);
		menu.add(Menu.NONE, MENU_ABOUT, 3,
				getResources().getString(R.string.about)).setIcon(
				android.R.drawable.ic_menu_info_details);
		menu.add(Menu.NONE, MENU_UPDATE_LOG, 4,
				getResources().getString(R.string.update_log)).setIcon(
				android.R.drawable.ic_menu_view);
		menu.add(Menu.NONE, MENU_LOCAL_DATA, 5,
				getResources().getString(R.string.open_local_data)).setIcon(
				android.R.drawable.ic_menu_recent_history);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent intent;
		switch (item.getItemId()) {
		case MENU_SETTING:
			startActivity(new Intent(this, SettingActivity.class));
			break;
		case MENU_REFRESH:
			this.getInitialNewsData();
			break;
		case MENU_ABOUT:
			intent = new Intent(this, ShowInformationActivity.class);
			intent.putExtra("key", ShowInformationActivity.KEY_ABOUT);
			startActivity(intent);
			break;
		case MENU_UPDATE_LOG:
			intent = new Intent(this, ShowInformationActivity.class);
			intent.putExtra("key", ShowInformationActivity.KEY_UPDATE_INF);
			startActivity(intent);
			break;
		case MENU_LOCAL_DATA:
			intent = new Intent(this, LocalArticlesListActivity.class);
			startActivity(intent);
			break;
		}
		return false;
	}
}