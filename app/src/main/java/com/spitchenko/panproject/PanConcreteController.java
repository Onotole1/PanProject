package com.spitchenko.panproject;

import java.io.Serializable;

import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.spitchenko.panproject.MyObserver.BurnerObserver;


import static com.spitchenko.panproject.MainActivity.sHandler2;

/**
 * Date: 29.12.16
 * Time: 14:20
 *
 * @author anatoliy
 */
class PanConcreteController implements BurnerObserver, com.spitchenko.panproject.MVC.PanController, Serializable, View.OnClickListener {
	private PanConcreteModel mPanConcreteModel;
	private boolean mIsPan = false;

	PanConcreteController(Button panButton, Button capButton) {
		panButton.setOnClickListener(new ViewOnClickListenerPan());
		capButton.setOnClickListener(new ViewOnClickListenerCap());
	}

	@Override
	public void update(float temperature) {
		mPanConcreteModel.setTemperatureBurner(temperature);
	}

	@Override
	public void onClick(View view) {
		if (mPanConcreteModel.isCap())
			mPanConcreteModel.setCap(false);
		else
			mPanConcreteModel.setCap(true);
	}

	private class ViewOnClickListenerPan implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			Message msg = new Message();
			if (!mIsPan) {
				mIsPan = true;
				msg.obj = "true";
				sHandler2.sendMessage(msg);
			}
			else {
				synchronized (this) {
					mPanConcreteModel.cancel(false);
					mIsPan = false;
					msg.obj = "false";
					sHandler2.sendMessage(msg);
				}
			}
		}
	}

	private class ViewOnClickListenerCap implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (mPanConcreteModel.isCap())
				mPanConcreteModel.setCap(false);
			else {
				mPanConcreteModel.setCap(true);
			}
		}
	}

	PanConcreteModel getPanConcreteModel() {
		return mPanConcreteModel;
	}

	void setPanConcreteModel(PanConcreteModel panConcreteModel) {
		mPanConcreteModel = panConcreteModel;
		mIsPan = true;
	}
}
