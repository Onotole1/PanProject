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
public class PanController implements BurnerObserver, com.spitchenko.panproject.MVC.PanController {
	PanModel mPanModel;
	private Button capButton;

	public PanController(Button capButton, PanModel panModel) {
		this.capButton = capButton;
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
	//private SeekBar mSeekBarWater;
	//final PipedOutputStream output = new PipedOutputStream();

	@Override
	public void update(float temperature) {
		mPanModel.setTemperatureBurner(temperature);
	}

	/*@Override
	public void onClick(View view) {
		switch (view.getId()) {
			case R.id.activity_main_pan_button:

		}
	}*/

}
