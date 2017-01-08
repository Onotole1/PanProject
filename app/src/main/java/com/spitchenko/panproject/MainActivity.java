package com.spitchenko.panproject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.spitchenko.panproject.MyObserver.PanObserver;

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
	private PanConcreteView mPanConcreteView;
	private GasBurnerView mGasBurnerView;
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

		initPan();

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
				mPanConcreteView = new PanConcreteView(mPanButton, mCapButton, mContext);
				mPanConcreteView.setPanController(mPanConcreteController);
				mPanConcreteModel.registerObserver(mPanConcreteView);
				mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
				mGasBurnerModel.registerObserver(mPanConcreteController);
				mPanConcreteModel.execute();
				mGasBurnerView.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
				return true;
			}
		};
	}

	/**
	 * Инициализация конфорки согласно паттерну MVC
	 */
	private void initGasBurner() {
		mGasBurnerModel = new GasBurnerModel();
		GasBurnerController gasBurnerController = new GasBurnerController(mGasBurnerModel);
		mGasBurnerView = new GasBurnerView();
		mGasBurnerView.setGasBurnerController(gasBurnerController);
		mGasBurnerModel.registerObserver(mGasBurnerView);
	}

	/**
	 * Инициализация кастрюли согласно паттерну MVC с загрузкой предыдущего состояния
	 */
	private void initPan() {
		mPanConcreteController = new PanConcreteController(this);
		mPanConcreteView = new PanConcreteView(mPanButton, mCapButton, this);
		mPanConcreteView.setPanController(mPanConcreteController);
		mPanConcreteModel = (PanConcreteModel) getLastCustomNonConfigurationInstance();
		if (mPanConcreteModel != null && mPanConcreteModel.getStatus() != AsyncTask.Status.FINISHED) {
			for (PanObserver panObserver:mPanConcreteModel.getObservers()) {
				mPanConcreteModel.removeObserver(panObserver);
			}
			Log.d("mPanConcreteLast", mPanConcreteModel.toString());
			mPanConcreteModel.registerObserver(mPanConcreteView);
			mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
			mGasBurnerModel.registerObserver(mPanConcreteController);
			mGasBurnerView.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
			mPanConcreteModel.notifyObservers();
		}
	}

	/**
	 * Инициализация кругового сикбара
	 */
	private void initCircleSeekBar(Bundle savedInstanceState) {
		try {
			mProgressCircle.setOnSeekBarChangeListener(mGasBurnerView);
			mProgressCircle.setProgress(savedInstanceState.getInt("mMySeekBarProgress"));
			mGasBurnerView.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
		} catch (NullPointerException e) {
			mProgressCircle.setOnSeekBarChangeListener(mGasBurnerView);
		}
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return mPanConcreteModel;
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		int mProgress = mProgressCircle.getProgress();
		outState.putInt("mMySeekBarProgress", mProgress);
	}
}
