package com.spitchenko.panproject;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.spitchenko.panproject.MVC.BurnerController;

/**
 * Date: 26.12.16
 * Time: 11:51
 *
 * @author anatoliy
 */
class GasBurnerController implements BurnerController {
	//Контроллер конфорки хранит ссылку на модель конфорки
    private GasBurnerModel mGasBurnerModel;

    GasBurnerController(GasBurnerModel gasBurnerModel) {
	    mGasBurnerModel = gasBurnerModel;
    }

	@Override
	public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
		mGasBurnerModel.setBurn(progress);
	}

	@Override
	public void onStopTrackingTouch(CircularSeekBar seekBar) {

	}

	@Override
	public void onStartTrackingTouch(CircularSeekBar seekBar) {

	}
}
