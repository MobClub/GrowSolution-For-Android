package com.mob.grow.sample;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mob.grow.GrowAPI;
import com.mob.grow.beans.TaskListData;
import com.mob.grow.utils.APICallBack;
import com.mob.tools.utils.DeviceHelper;
import com.mob.tools.utils.ResHelper;

public class InputDialog extends Dialog implements View.OnClickListener {

	private int type;
	private TextView title;
	private EditText edt;
	private String nickname;
	private String avatarUrl;

	public InputDialog(Context context) {
		super(context);
		initView();
	}

	public InputDialog(Context context, int themeResId) {
		super(context, themeResId);
		initView();
	}

	protected InputDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		initView();
	}

	private void initView() {
		getWindow().setBackgroundDrawable(new ColorDrawable(0x00000000));
		setContentView(R.layout.input_dialog);
		int divierId = getContext().getResources().getIdentifier("android:id/titleDivider", null, null);
		View divider = findViewById(divierId);
		if (null != divider) {
			divider.setBackgroundDrawable(new ColorDrawable(0x00000000));
		}

		findViewById(R.id.cancel).setOnClickListener(this);
		findViewById(R.id.comfirm).setOnClickListener(this);

		title = findViewById(R.id.title);
		edt = findViewById(R.id.edt);

		nickname = DeviceHelper.getInstance(getContext()).getAppName() + " test";
		avatarUrl = "http://download.sdk.mob.com/2018/02/06/12/1517890066763/2000_2000_79.49.jpg";
	}

	public InputDialog setType(int type) {
		this.type = type;
		if (type == 1) {
			title.setText(R.string.invite_code);
			edt.setHint(R.string.input_invite_hint);
		}
		return this;
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.cancel:
				dismiss();
				break;

			case R.id.comfirm:
				String edtStr = edt.getText().toString();
				if (TextUtils.isEmpty(edtStr)) {
					Toast.makeText(getContext(), "输入不能为空", Toast.LENGTH_SHORT).show();
					return;
				}
				if (type == 0) {
					GrowAPI.bindUser(edtStr, nickname, avatarUrl, new APICallBack<Void>() {
						@Override
						public void onStart() {

						}

						@Override
						public void onSuccess(Void aVoid) {
							Toast.makeText(getContext(), ResHelper.getStringRes(getContext(), "main_bind"),
									Toast.LENGTH_SHORT).show();
							dismiss();
						}

						@Override
						public void onFail(int errorCode, String errorInfo, Throwable t) {
							Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});

				} else {
					GrowAPI.bindInviteCode(edtStr, new APICallBack<TaskListData>() {
						@Override
						public void onStart() {

						}

						@Override
						public void onSuccess(TaskListData taskListData) {
							if (null != taskListData && null != taskListData.getData()) {
								Toast.makeText(getContext(), "绑定邀请码成功, 获得 " + taskListData.getData().size() + " 个任务奖励", Toast.LENGTH_LONG).show();
								dismiss();
							}
						}

						@Override
						public void onFail(int errorCode, String errorInfo, Throwable t) {
							Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
						}
					});
				}
				break;
		}
	}
}
