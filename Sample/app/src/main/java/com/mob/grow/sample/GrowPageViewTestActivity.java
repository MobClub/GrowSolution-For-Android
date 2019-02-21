package com.mob.grow.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.mob.grow.GrowUser;
import com.mob.grow.entity.UserBrief;
import com.mob.grow.gui.GROWGUI;
import com.mob.grow.gui.views.NewsPageView;
import com.mob.tools.utils.DeviceHelper;

public class GrowPageViewTestActivity extends Activity {

	private String uid;
	private String nickname;
	private String avatarUrl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activit_test_view);

		LinearLayout linearLayout = findViewById(R.id.rootll);

		UserBrief ub = GrowUser.getInstance().getUserBrief();
		if (null == ub) {
			uid = "20" + DeviceHelper.getInstance(this).getSerialno();
			nickname = DeviceHelper.getInstance(this).getAppName() + " test";
			avatarUrl = "http://download.sdk.mob.com/2018/02/06/12/1517890066763/2000_2000_79.49.jpg";
		} else {
			uid = ub.uid;
			nickname = ub.nickname;
			avatarUrl = ub.avatarUrl;
		}

		int type = getIntent().getIntExtra("type", NewsPageView.NEWS_VIEW);
		NewsPageView newsPageView = GROWGUI.getNewsPageView(this, uid, nickname, avatarUrl, type,
				false, false);

		linearLayout.addView(newsPageView);

		// 自定义跳转
		newsPageView.setTaskListener(new NewsPageView.TaskListener() {
			@Override
			public void readNewsCallBack() {
				GrowPageViewTestActivity.this.finish();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		System.out.println(" data " + data);
	}
}
