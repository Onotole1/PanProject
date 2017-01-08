package com.spitchenko.panproject;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Message;

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
	//Контекст необходим для извлечения звуковых ресурсов
	private Context mContext;

	PanConcreteController(Context context) {
		mContext = context;

	}

	@Override
	public void update(float temperature) {
		if (mPanConcreteModel != null)
			mPanConcreteModel.setTemperatureBurner(temperature);
	}

	@Override
	public void setPanConcreteModel(PanModel panConcreteModel) {
		mPanConcreteModel = (PanConcreteModel) panConcreteModel;
		if (mPanConcreteModel.getStatus() != AsyncTask.Status.FINISHED) {
			mPanConcreteModel = (PanConcreteModel) panConcreteModel;
		} else {
			mPanConcreteModel = null;
		}
	}

	void setPan() {
		Message msg = new Message();
		if (mPanConcreteModel == null) {
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
		}
	}

	void setCap() {
		if (mPanConcreteModel != null) {
			if (mPanConcreteModel.isCap()) {
				MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.cap_up);
				mediaPlayer.start();
				mPanConcreteModel.setCap(false);
			}
			else {
				MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.cap_down);
				mediaPlayer.start();
				mPanConcreteModel.setCap(true);
			}
		}
	}
}
