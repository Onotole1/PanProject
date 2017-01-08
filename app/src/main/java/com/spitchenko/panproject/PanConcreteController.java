package com.spitchenko.panproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.spitchenko.panproject.MVC.PanModel;


import static com.spitchenko.panproject.MainActivity.sHandler2;

/**
 * Date: 29.12.16
 * Time: 14:20
 *
 * @author anatoliy
 */
class PanConcreteController implements com.spitchenko.panproject.MVC.PanController {
	//Контроллер кастрюли хранит ссылку на объект модели кастрюли
	private PanConcreteModel mPanConcreteModel;
	//По умолчанию состояние переменной наличия кастрюли отрицательно
	private boolean mIsPan = false;
	//Контекст необходим для извлечения звуковых ресурсов
	private Context mContext;
	//Ссылка на кнопку управления крышкой нужна для того, чтобы при отсутствии кастрюли сделать её неактивной
	private Button mCapButton;

	PanConcreteController(Button panButton, Button capButton, Context context) {
		panButton.setOnClickListener(new ViewOnClickListenerPan());
		capButton.setOnClickListener(new ViewOnClickListenerCap());
		mContext = context;
		mCapButton = capButton;
		mCapButton.setEnabled(false);

	}

	@Override
	public void update(float temperature) {
		if (mPanConcreteModel != null)
			mPanConcreteModel.setTemperatureBurner(temperature);
		if (mPanConcreteModel != null)
			mCapButton.setEnabled(true);
	}

	/**
	 * Поведение слушателя кнопки, создающей кастрюлю
	 */
	private class ViewOnClickListenerPan implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			Message msg = new Message();
			if (!mIsPan) {
				mIsPan = true;
				mCapButton.setEnabled(true);
				MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.pan_down);
				mediaPlayer.start();
				msg.obj = "true";
				sHandler2.sendMessage(msg);
			}
			else {
				mPanConcreteModel.cancel(false);
				mPanConcreteModel = null;
				MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.pan_up);
				mediaPlayer.start();
				mCapButton.setEnabled(false);
				mIsPan = false;
			}
		}
	}

	/**
	 * Поведение слушателя кнопки, устанавливающей наличие крышки
	 */
	private class ViewOnClickListenerCap implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			if (mPanConcreteModel != null) {
				if (mPanConcreteModel.isCap()) {
					mPanConcreteModel.setCap(false);
					MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.cap_up);
					mediaPlayer.start();
				} else if (!mPanConcreteModel.isCap()) {
					mPanConcreteModel.setCap(true);
					MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.cap_down);
					mediaPlayer.start();
				}
			}
		}
	}

	@Override
	public void setPanConcreteModel(PanModel panConcreteModel) {
		mPanConcreteModel = (PanConcreteModel) panConcreteModel;
		if (mPanConcreteModel.getStatus() != AsyncTask.Status.FINISHED) {
			mIsPan = true;
			mCapButton.setEnabled(true);
		} else {
			mPanConcreteModel = null;
			mCapButton.setEnabled(false);
		}
	}

	boolean isPan() {
		return mIsPan;
	}
}
