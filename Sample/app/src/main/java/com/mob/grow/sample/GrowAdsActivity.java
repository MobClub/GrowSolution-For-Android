package com.mob.grow.sample;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.mob.grow.gui.mobads.GrowBannerAdView;
import com.mob.grow.gui.mobads.GrowInfoFlowAd;
import com.mob.grow.gui.mobads.GrowInfoFlowAdResponse;
import com.mob.grow.gui.mobads.GrowInterstitialAd;
import com.mob.grow.gui.mobads.GrowSplashAd;
import com.mob.grow.gui.mobads.GrowSplashAdListener;
import com.mob.grow.utils.APICallBack;
import com.mob.tools.gui.AsyncImageView;

import java.util.List;

public class GrowAdsActivity extends Activity {

	GrowBannerAdView bannerAdView;
	GrowInfoFlowAd infoFlowAd;
	GrowInterstitialAd interstitialAd;
	GrowSplashAd splashAd;
	FrameLayout splashfl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grow_ads);
		splashfl = (FrameLayout) findViewById(R.id.splashfl);

		final LinearLayout bannerll = (LinearLayout) findViewById(R.id.bannerll);

		bannerAdView = new GrowBannerAdView(this);

		bannerll.post(new Runnable() {
			@Override
			public void run() {
				LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(bannerll.getWidth(), bannerll.getHeight());
				bannerll.addView(bannerAdView, params);
			}
		});


		infoFlowAd = new GrowInfoFlowAd(this, new APICallBack<List<GrowInfoFlowAdResponse>>() {
			@Override
			public void onStart() {
				
			}

			@Override
			public void onSuccess(List<GrowInfoFlowAdResponse> growInfoFlowAdResponses) {
				if (null != growInfoFlowAdResponses && growInfoFlowAdResponses.size()> 0) {
					GrowInfoFlowAdResponse response = growInfoFlowAdResponses.get(0);
					AsyncImageView info_img = (AsyncImageView) findViewById(R.id.info_img);
					info_img.execute(response.imageUrl, 0);
					AsyncImageView logo = (AsyncImageView) findViewById(R.id.logo);
					logo.execute(response.adLogoUrl, 0);
				}
			}

			@Override
			public void onFail(int errorCode, String errorInfo, Throwable t) {

			}
		});

		Button inter_btn = (Button) findViewById(R.id.inter_btn);

		interstitialAd = new GrowInterstitialAd(this);
		interstitialAd.loadAd();

		inter_btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (interstitialAd.isAdReady()) {
					interstitialAd.showAd(GrowAdsActivity.this);
				} else {
					interstitialAd.loadAd();
				}
			}
		});


		Button inter_splash = (Button) findViewById(R.id.inter_splash);
		inter_splash.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				splashAd = new GrowSplashAd(GrowAdsActivity.this, splashfl, new GrowSplashAdListener() {
					@Override
					public void onAdPresent() {
						System.out.println(">>>>>>onAdPresent>>> ");
					}

					@Override
					public void onAdDismissed() {
						splashfl.removeAllViews();
					}

					@Override
					public void onAdFailed(String error) {
						Toast.makeText(GrowAdsActivity.this, "加载失败：" + error, Toast.LENGTH_SHORT).show();
						System.out.println(">>>>>>onAdFailed>>> ");
					}

					@Override
					public void onAdClick() {

					}
				}, true);
			}
		});
	}

	@Override
	protected void onDestroy() {
		if (null != bannerAdView) {
			bannerAdView.destroy();
		}
		if (null != interstitialAd) {
			interstitialAd.destroy();
		}
		if (null != splashAd) {
			splashAd.destroy();
		}
		super.onDestroy();
	}
}
