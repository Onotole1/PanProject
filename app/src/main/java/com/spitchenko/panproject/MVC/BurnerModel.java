package com.spitchenko.panproject.MVC;

import com.spitchenko.panproject.MyObserver.BurnerSubject;

/**
 * Date: 26.12.16
 * Time: 9:43
 *
 * @author anatoliy
 */
public interface BurnerModel extends BurnerSubject {
	/**
	 * Метод, задающий мощность конфорки в процентах
	 * @param size - мощность конфорки в процентах
	 */
	void setBurn(float size);
}
