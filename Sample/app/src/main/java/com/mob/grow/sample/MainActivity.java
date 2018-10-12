package com.mob.grow.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.mob.grow.gui.GROWGUI;
import com.mob.tools.utils.DeviceHelper;

public class MainActivity extends Activity implements View.OnClickListener{

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.main_growsdk).setOnClickListener(this);
		findViewById(R.id.first_growsdk).setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_growsdk:
				String uid = "10"+DeviceHelper.getInstance(this).getSerialno();
				EditText editText = (EditText) findViewById(R.id.etuid);
				if (!TextUtils.isEmpty(editText.getText())) {
					uid = editText.getText().toString();
				}
				String nickname = DeviceHelper.getInstance(this).getAppName() + " test";
				String avatarUrl = "http://download.sdk.mob.com/2018/02/06/12/1517890066763/2000_2000_79.49.jpg";
				GROWGUI.showNewsPage(this,uid, nickname, avatarUrl);
				break;
			case R.id.first_growsdk:
				startActivity(new Intent(this, FirstActivity.class));
				break;
		}
	}

}
