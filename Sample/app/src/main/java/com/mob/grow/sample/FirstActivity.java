package com.mob.grow.sample;

import android.app.Activity;
import android.os.Bundle;

import com.mob.grow.gui.GROWGUI;
import com.mob.grow.gui.views.NewsPageView;
import com.mob.tools.utils.DeviceHelper;

public class FirstActivity extends Activity {

	private NewsPageView newsPageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		String uid = "10"+ DeviceHelper.getInstance(this).getSerialno();
		String nickname = DeviceHelper.getInstance(this).getAppName() + " test";
		String avatarUrl = "http://download.sdk.mob.com/2018/02/06/12/1517890066763/2000_2000_79.49.jpg";
		newsPageView = GROWGUI.getNewsPageView(this, uid, nickname, avatarUrl, true);

		setContentView(newsPageView);
	}

	@Override
	protected void onDestroy() {
		newsPageView.onDestroy();
		super.onDestroy();
	}
}
