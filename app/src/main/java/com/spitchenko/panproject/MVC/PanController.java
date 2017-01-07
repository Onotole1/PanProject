package com.spitchenko.panproject.MVC;

import com.spitchenko.panproject.MyObserver.BurnerObserver;

/**
 * Date: 29.12.16
 * Time: 14:20
 *
 * @author anatoliy
 */
public interface PanController extends BurnerObserver {
	void setPanConcreteModel(PanModel panModel);
}
