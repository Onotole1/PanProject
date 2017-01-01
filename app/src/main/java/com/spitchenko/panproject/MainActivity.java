package com.spitchenko.panproject;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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

	@BindView(R.id.activity_main_burn_seekBar)
	SeekBar mSeekBar;

	@BindView(R.id.activity_main_text_view_temperature_water)
	TextView mTemperatureWaterTextView;

	public static Handler h;
	public static Handler h1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//GifImageView mImageViewPan = (GifImageView)findViewById(R.id.activity_main_pan_image_view_pan);
		ButterKnife.bind(this);

		h = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(android.os.Message msg) {
				//tvInfo.setText("Закачано файлов: " + msg.what);
				//if (msg.arg1 >= 0 && msg.what <= 100)
				mTemperatureWaterTextView.setText("Температуры воды " + msg.arg1 + " V воды " + msg.arg2);
				mImageViewPan.setImageDrawable((GifDrawable)msg.obj);
				return true;
			}
		});

		h1 = new Handler(new Handler.Callback() {
			@Override
			public boolean handleMessage(android.os.Message msg) {
				//tvInfo.setText("Закачано файлов: " + msg.what);
				//if (msg.arg1 >= 0 && msg.what <= 100)
				mImageViewBurner.setImageResource(msg.arg1);
				return true;
			}
		});

		//GasBurnerModel gasBurnerModel = new GasBurnerModel();

		GasBurnerController gasBurnerController = new GasBurnerController(this, mPanButton, mCapButton);
		//gasBurnerController.onProgressChanged(mSeekBar);
		mSeekBar.setOnSeekBarChangeListener(gasBurnerController);
		//GasBurnerView gasBurnerView = new GasBurnerView();
		//gasBurnerModel.registerObserver(gasBurnerView);


		//PanSoup panSoup = new PanSoup();
		//PanController panController = new PanController(mCapButton, panSoup);
		//gasBurnerModel.registerObserver(panController);
		//PanSoupView panSoupView = new PanSoupView(this);
		//panSoup.registerObserver(panSoupView);




	}


}
