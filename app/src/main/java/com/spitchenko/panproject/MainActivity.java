package com.spitchenko.panproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devadvance.circularseekbar.CircularSeekBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
	@BindView(R.id.activity_main_cap_button)
	Button mCapButton;

	@BindView(R.id.activity_main_pan_button)
	Button mPanButton;

	@BindView(R.id.activity_main_pan_image_view_burner)
	ImageView mImageViewBurner;

	@BindView(R.id.activity_main_pan_image_view_pan)
	GifImageView mImageViewPan;

	@BindView(R.id.activity_main_text_view_temperature_water)
	TextView mTemperatureWaterTextView;

	@BindView(R.id.circularSeekBar1)
	CircularSeekBar mProgressCircle;

	public static Handler sHandler;
	public static Handler sHandler1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		sHandler = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(android.os.Message msg) {
				mTemperatureWaterTextView.setText("Температура воды = " + msg.arg1 + " V воды = " + msg.arg2 + "%");
				mImageViewPan.setImageDrawable((GifDrawable)msg.obj);
				return true;
			}
		});

		sHandler1 = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(android.os.Message msg) {
				mImageViewBurner.setImageResource(msg.arg1);
				return true;
			}
		});

		GasBurnerController gasBurnerController = new GasBurnerController(this, mPanButton, mCapButton);
		mProgressCircle.setOnSeekBarChangeListener(gasBurnerController);
	}
}
