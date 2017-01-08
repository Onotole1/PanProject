package com.spitchenko.panproject;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
	public static Handler sHandler2;

	private PanConcreteModel mPanConcreteModel;
	private GasBurnerModel mGasBurnerModel;
	private PanConcreteController mPanConcreteController;
	private GasBurnerController mGasBurnerController;
	private Context mContext = this;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);

		//Первый хандлер ставит в очередь главного потока сообщения от представления кастрюли
		sHandler = new Handler(newHandlerPanViewCallback());

		//Второй хандлер обрабатывает сообщения представления конфорки
		sHandler1 = new Handler(newHandlerBurnerViewCallback());

		//Третий хандлер обрабатывает нажатия на кнопку создания кастрюли контроллера кастрюли
		sHandler2 = new Handler(newHandlerPanControllerCallback());

		initGasBurner();

		initPan(savedInstanceState);

		initCircleSeekBar(savedInstanceState);
	}

	private Handler.Callback newHandlerPanViewCallback() {
		return new Handler.Callback() {
		@Override
		public boolean handleMessage(android.os.Message msg) {
			mImageViewPan.setImageDrawable((GifDrawable) msg.obj);
			if (msg.arg1 == -1) {
				mTemperatureWaterTextView.setText(getResources().getString(R.string.text_view_temperature));
				return true;
			}
			mTemperatureWaterTextView.setText("Температура воды = " + msg.arg1 + "°C V воды = " + msg.arg2 + "%");
			return true;
		}
		};
	}
	private Handler.Callback newHandlerBurnerViewCallback() {
		return new Handler.Callback() {
			@Override
			public boolean handleMessage(android.os.Message msg) {
				mImageViewBurner.setImageResource(msg.arg1);
				return true;
			}
		};
	}

	private Handler.Callback newHandlerPanControllerCallback() {
		return new Handler.Callback() {
			@Override
			public boolean handleMessage(android.os.Message msg) {
				mPanConcreteModel = new PanConcreteModel();
				mPanConcreteModel.registerObserver(new PanConcreteView(mContext));
				mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
				mGasBurnerModel.registerObserver(mPanConcreteController);
				mPanConcreteModel.execute();
				mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
				return true;
			}
		};
	}

	/**
	 * Инициализация конфорки согласно паттерну MVC
	 */
	private void initGasBurner() {
		mGasBurnerModel = new GasBurnerModel();
		mGasBurnerController = new GasBurnerController(mGasBurnerModel);
		GasBurnerView gasBurnerView = new GasBurnerView();
		mGasBurnerModel.registerObserver(gasBurnerView);
	}

	/**
	 * Инициализация кастрюли согласно паттерну MVC с загрузкой предыдущего состояния
	 */
	private void initPan(Bundle savedInstanceState) {
		mPanConcreteController = new PanConcreteController(mPanButton, mCapButton, this);
		if (savedInstanceState != null) {
			if (savedInstanceState.getBoolean("mPanConcreteController")) {
				mPanConcreteModel = (PanConcreteModel) getLastCustomNonConfigurationInstance();
				if (mPanConcreteModel != null) {
					mPanConcreteModel.notifyObservers();
					Log.d("mPanConcreteLast", mPanConcreteModel.toString());
				}
				mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
				mGasBurnerModel.registerObserver(mPanConcreteController);
				mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
			}
		}
	}

	/**
	 * Инициализация кругового сикбара
	 */
	private void initCircleSeekBar(Bundle savedInstanceState) {
		try {
			mProgressCircle.setOnSeekBarChangeListener(mGasBurnerController);
			mProgressCircle.setProgress(savedInstanceState.getInt("mMySeekBarProgress"));
			mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
		} catch (NullPointerException e) {
			mProgressCircle.setOnSeekBarChangeListener(mGasBurnerController);
		}
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return mPanConcreteModel;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		mPanConcreteModel.cancel(false);
		mPanConcreteModel = null;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int mProgress = mProgressCircle.getProgress();
		outState.putInt("mMySeekBarProgress", mProgress);
		outState.putBoolean("mPanConcreteController", mPanConcreteController.isPan());
	}
}
