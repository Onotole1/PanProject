package com.spitchenko.panproject;

import android.content.Context;
import android.content.SharedPreferences;
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
	private SharedPreferences mSharedPrefs;
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

		initCircleSeekBar();
	}

	@Override
	protected void onStop() {
		super.onStop();
		mSharedPrefs = getPreferences(MODE_PRIVATE);
		SharedPreferences.Editor mEditor = mSharedPrefs.edit();
		int mProgress = mProgressCircle.getProgress();
		mEditor.putInt("mMySeekBarProgress", mProgress);
		/*if (mPanConcreteModel != null) {
			mEditor.putFloat("mPanConcreteModelTemperatureWater", mPanConcreteModel.getTemperatureWater());
			mEditor.putFloat("mPanConcreteModelSizeWater", mPanConcreteModel.getSizeWater());
			mEditor.putBoolean("mPanConcreteModelCap", mPanConcreteModel.isCap());
			for (PanObserver p:mPanConcreteModel.getObservers()) {
				mPanConcreteModel.removeObserver(p);
			}
			mPanConcreteModel.cancel(false);
		}*/
		mEditor.apply();
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
				mSharedPrefs = getPreferences(MODE_PRIVATE);
				SharedPreferences.Editor mEditor = mSharedPrefs.edit();
				if (msg.obj.equals("true")) {
					mPanConcreteModel = new PanConcreteModel();
					mPanConcreteModel.registerObserver(new PanConcreteView(mContext));
					mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
					mGasBurnerModel.registerObserver(mPanConcreteController);
					mPanConcreteModel.execute();
					mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
					mEditor.putString("mPanControllerState", "true");
					mEditor.apply();
				} else {
					mEditor.putString("mPanControllerState", "false");
					mEditor.apply();
				}
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
	private void initPan() {
		mPanConcreteController = new PanConcreteController(mPanButton, mCapButton, this);
		/*try {
			mSharedPrefs = getPreferences(MODE_PRIVATE);
			String mPanControllerState = mSharedPrefs.getString("mPanControllerState", "");
			if (mPanControllerState.equals("true")) {
				float mPanConcreteModelTemperatureWater = mSharedPrefs.getFloat("mPanConcreteModelTemperatureWater", -1);
				float mPanConcreteModelSizeWater = mSharedPrefs.getFloat("mPanConcreteModelSizeWater", -1);
				boolean mPanConcreteModelCap = mSharedPrefs.getBoolean("mPanConcreteModelCap", false);

				mPanConcreteModel = new PanConcreteModel();
				mPanConcreteModel.setCap(mPanConcreteModelCap);
				mPanConcreteModel.setTemperatureWater(mPanConcreteModelTemperatureWater);
				mPanConcreteModel.setSizeWater(mPanConcreteModelSizeWater);
				mPanConcreteModel.execute();
				Log.d("mPanConcreteModelId", mPanConcreteModel.toString());
				SharedPreferences.Editor editor = mSharedPrefs.edit();
				editor.remove("mPanConcreteModelTemperatureWater");
				editor.remove("mPanConcreteModelSizeWater");
				editor.remove("mPanConcreteModelCap");
				editor.apply();

				mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
				mGasBurnerModel.registerObserver(mPanConcreteController);
				mPanConcreteModel = mPanConcreteController.getPanConcreteModel();
				PanConcreteView panConcreteView = new PanConcreteView(this);
				mPanConcreteModel.registerObserver(panConcreteView);
				mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
			}
		} catch (NullPointerException e) {
			Log.d("mSharedPrefs", " = null");
		}*/
		mPanConcreteModel = (PanConcreteModel) getLastCustomNonConfigurationInstance();
		if (mPanConcreteModel != null) {
			Log.d("mPanConcreteLast", mPanConcreteModel.toString());
		}
		else {
			mPanConcreteModel = new PanConcreteModel();
			mPanConcreteModel.execute();
			Log.d("mPanConcreteModelId", mPanConcreteModel.toString());


			PanConcreteView panConcreteView = new PanConcreteView(this);
			mPanConcreteModel.registerObserver(panConcreteView);
			mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
		}
		mPanConcreteController.setPanConcreteModel(mPanConcreteModel);
		mGasBurnerModel.registerObserver(mPanConcreteController);
		mGasBurnerController.onProgressChanged(mProgressCircle, mProgressCircle.getProgress(), true);
	}

	/**
	 * Инициализация кругового сикбара
	 */
	private void initCircleSeekBar() {
		mProgressCircle.setOnSeekBarChangeListener(mGasBurnerController);
		try {
			mSharedPrefs = getPreferences(MODE_PRIVATE);
			int mProgress = mSharedPrefs.getInt("mMySeekBarProgress", 0);
			Log.d("mSharedPrefs", " = " + mProgress);
			mProgressCircle.setProgress(mProgress);
			mGasBurnerController.onProgressChanged(mProgressCircle, mProgress, true);
		} catch (NullPointerException e) {
			Log.d("mSharedPrefs", " = null");
		}
	}

	@Override
	public Object onRetainCustomNonConfigurationInstance() {
		return mPanConcreteModel;
	}
}
