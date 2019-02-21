package com.mob.grow.sample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.mob.grow.GrowAPI;
import com.mob.grow.beans.FriendListData;
import com.mob.grow.beans.TaskListData;
import com.mob.grow.beans.TaskUserInfoData;
import com.mob.grow.beans.UserData;
import com.mob.grow.gui.views.NewsPageView;
import com.mob.grow.utils.APICallBack;
import com.mob.tools.utils.ResHelper;

public class ApiListActivity extends Activity implements View.OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_api_list);

		findViewById(R.id.main_growsdkads_init).setOnClickListener(this);
		findViewById(R.id.main_growsdkads).setOnClickListener(this);
		findViewById(R.id.bind).setOnClickListener(this);
		findViewById(R.id.get_sign).setOnClickListener(this);
		findViewById(R.id.sign).setOnClickListener(this);
		findViewById(R.id.getInviteCode).setOnClickListener(this);
		findViewById(R.id.bindInviteCode).setOnClickListener(this);
		findViewById(R.id.getFirendList).setOnClickListener(this);
		findViewById(R.id.testview1).setOnClickListener(this);
		findViewById(R.id.testview2).setOnClickListener(this);
		findViewById(R.id.testview3).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.main_growsdkads_init:
				GrowAPI.adsInit(new APICallBack<Void>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(Void aVoid) {
						Toast.makeText(ApiListActivity.this, ResHelper.getStringRes(ApiListActivity.this, "main_init"), Toast.LENGTH_SHORT).show();
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(ApiListActivity.this, errorInfo + " " + t.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
				break;
			case R.id.main_growsdkads:
				startActivity(new Intent(this, GrowAdsActivity.class));
				break;
			case R.id.bind:
					new InputDialog(this).show();
				break;
			case R.id.get_sign:
				GrowAPI.signInfo(new APICallBack<TaskUserInfoData>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(TaskUserInfoData taskUserInfoData) {
						if (null != taskUserInfoData && null != taskUserInfoData.getData()) {
							Toast.makeText(ApiListActivity.this, getString(ResHelper.getStringRes(ApiListActivity.this, "main_sign_info"),
									taskUserInfoData.getData().isSign, taskUserInfoData.getData().signCount), Toast.LENGTH_LONG).show();

						} else {
							errorToast();
						}
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(ApiListActivity.this, errorInfo + " " + t.getMessage(), Toast.LENGTH_LONG).show();
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
							Toast.makeText(ApiListActivity.this, getString(ResHelper.getStringRes(ApiListActivity.this, "main_sign"),
									task.getIncome()), Toast.LENGTH_LONG).show();
						} else {
							errorToast();
						}
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						Toast.makeText(ApiListActivity.this, errorInfo + " " + t.getMessage(), Toast.LENGTH_LONG).show();
					}
				});
				break;
			case R.id.getInviteCode:
				GrowAPI.getInviteCode(new APICallBack<UserData>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(UserData userData) {
						if (null != userData && null != userData.getRes()) {
							Toast.makeText(ApiListActivity.this, "您的邀请码是：" + userData.getRes().getReferCode(), Toast.LENGTH_LONG).show();
						} else {
							errorToast();
						}
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						errorToast();
					}
				});
				break;
			case R.id.bindInviteCode:
				new InputDialog(this).setType(1).show();
				break;
			case R.id.getFirendList:
				GrowAPI.getFriendList(0, 10, new APICallBack<FriendListData>() {
					@Override
					public void onStart() {

					}

					@Override
					public void onSuccess(FriendListData friendListData) {
						if (null != friendListData && null != friendListData.getRes()) {
							Toast.makeText(ApiListActivity.this, "获取成功, 您有 " + friendListData.getData().size() + " 个好友", Toast.LENGTH_LONG).show();
						} else {
							errorToast();
						}
					}

					@Override
					public void onFail(int errorCode, String errorInfo, Throwable t) {
						errorToast();
					}
				});
				break;
			case R.id.testview1:
				Intent intent = new Intent(this, GrowPageViewTestActivity.class);
				intent.putExtra("type", NewsPageView.NEWS_VIEW);
				startActivity(intent);
				break;
			case R.id.testview2:
				Intent intent2 = new Intent(this, GrowPageViewTestActivity.class);
				intent2.putExtra("type", NewsPageView.VIDEO_VIEW);
				startActivity(intent2);
				break;
			case R.id.testview3:
				Intent intent3 = new Intent(this, GrowPageViewTestActivity.class);
				intent3.putExtra("type", NewsPageView.MINE_VIEW);
				startActivity(intent3);
				break;
		}
	}

	private void errorToast() {
		Toast.makeText(this, ResHelper.getStringRes(this, "growsdk_error"), Toast.LENGTH_SHORT).show();
	}

}