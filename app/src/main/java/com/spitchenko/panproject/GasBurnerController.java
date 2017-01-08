package com.spitchenko.panproject;

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
	public void setBurn(float size) {
		mGasBurnerModel.setBurn(size);
	}
}
