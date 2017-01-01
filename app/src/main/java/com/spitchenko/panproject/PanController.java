package com.spitchenko.panproject;

import android.view.View;
import android.widget.Button;

import com.spitchenko.panproject.MVC.PanModel;
import com.spitchenko.panproject.MyObserver.BurnerObserver;

/**
 * Date: 29.12.16
 * Time: 14:20
 *
 * @author anatoliy
 */
class PanController implements BurnerObserver, com.spitchenko.panproject.MVC.PanController {
	private PanModel mPanModel;

	PanController(Button capButton, PanModel panModel) {
		mPanModel = panModel;
		capButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (mPanModel.isCap())
					mPanModel.setCap(false);
				else
					mPanModel.setCap(true);
			}
		});
	}

	@Override
	public void update(float temperature) {
		mPanModel.setTemperatureBurner(temperature);
	}

}
