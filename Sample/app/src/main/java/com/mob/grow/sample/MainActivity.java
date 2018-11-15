package com.mob.grow.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.grow.GrowAPI;
import com.mob.grow.beans.TaskListData;
import com.mob.grow.beans.TaskUserInfoData;
import com.mob.grow.gui.GROWGUI;
import com.mob.grow.utils.APICallBack;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

public class MainActivity extends Activity implements View.OnClickListener {

	private String uid;
	private String nickname;
	private String avatarUrl;
	private EditText editText;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		findViewById(R.id.main_growsdk).setOnClickListener(this);
		findViewById(R.id.first_growsdk).setOnClickListener(this);

		editText = (EditText) findViewById(R.id.etuid);

		findViewById(R.id.main_growsdkads_init).setOnClickListener(this);
		findViewById(R.id.main_growsdkads).setOnClickListener(this);
		findViewById(R.id.bind).setOnClickListener(this);
		findViewById(R.id.get_sign).setOnClickListener(this);
		findViewById(R.id.sign).setOnClickListener(this);
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
			case R.id.first_growsdk:
				startActivity(new Intent(this, FirstActivity.class));
				break;
			case R.id.main_growsdkads_init:
				GrowAPI.adsInit(new APICallBack<Void>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(Void aVoid) {
						Toast.makeText(MainActivity.this, ResHelper.getStringRes(MainActivity.this, "main_init"), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(MainActivity.this, errorInfo + " " + t.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
				break;
			case R.id.main_growsdkads:
				startActivity(new Intent(this, GrowAdsActivity.class));
				break;
			case R.id.bind:
				if (!TextUtils.isEmpty(editText.getText())) {
					uid = editText.getText().toString();
				}
				GrowAPI.bindUser(uid, nickname, avatarUrl, new APICallBack<Void>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(Void aVoid) {
						Toast.makeText(MainActivity.this, ResHelper.getStringRes(MainActivity.this, "main_bind"),
								Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
					}
				});

				break;
			case R.id.get_sign:
				GrowAPI.signInfo(new APICallBack<TaskUserInfoData>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(TaskUserInfoData taskUserInfoData) {
						if (null != taskUserInfoData && null != taskUserInfoData.getData()) {
							Toast.makeText(MainActivity.this, getString(ResHelper.getStringRes(MainActivity.this, "main_sign_info"),
									taskUserInfoData.getData().isSign, taskUserInfoData.getData().signCount), Toast.LENGTH_LONG).show();

						} else {
							errorToast();
						}
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(MainActivity.this, errorInfo + " " + t.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
				break;
			case R.id.sign:
				GrowAPI.sign(new APICallBack<TaskListData.Task>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(TaskListData.Task task) {
						if (null != task) {
							Toast.makeText(MainActivity.this, getString(ResHelper.getStringRes(MainActivity.this, "main_sign"),
									task.getIncome()), Toast.LENGTH_LONG).show();
						} else {
							errorToast();
						}
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(MainActivity.this, errorInfo + " " + t.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
				break;
		}
	}

	private void errorToast() {
		Toast.makeText(this, ResHelper.getStringRes(this, "growsdk_error"), Toast.LENGTH_SHORT).show();
	}
}