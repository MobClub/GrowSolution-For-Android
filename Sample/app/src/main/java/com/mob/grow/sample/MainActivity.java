package com.mob.grow.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.grow.gui.GROWGUI;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

public class MainActivity extends Activity implements View.OnClickListener {

	private String uid;
	private String nickname;
	private String avatarUrl;
	private EditText editText;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_main);

		editText = (EditText) findViewById(R.id.etuid);
		findViewById(R.id.main_growsdk).setOnClickListener(this);
		findViewById(R.id.sdk_api).setOnClickListener(this);

		uid = "20" + DeviceHelper.getInstance(this).getSerialno();
		nickname = DeviceHelper.getInstance(this).getAppName() + " test";
		avatarUrl = "http://download.sdk.mob.com/2018/02/06/12/1517890066763/2000_2000_79.49.jpg";
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_growsdk:
				if (!TextUtils.isEmpty(editText.getText())) {
					uid = editText.getText().toString();
				}
				GROWGUI.showNewsPage(this, uid, nickname, avatarUrl);
				break;
			case R.id.sdk_api:
				startActivity(new Intent(this, ApiListActivity.class));
				break;
		}
	}

}