package com.spitchenko.panproject.MVC;

import com.spitchenko.panproject.MyObserver.PanSubject;

/**
 * Date: 25.12.16
 * Time: 0:16
 *
 * @author anatoliy
 */
public interface PanModel extends PanSubject {
	/**
	 * Метод задаёт мощность конфорки
	 * @param temperatureBurner - мощность конфорки
	 */
	void setTemperatureBurner(float temperatureBurner);

	/**
	 * Метод задаёт наличие крышки у модели кастрюли
	 * @param cap - наличие крышки у модели кастрюли
	 */
	void setCap(boolean cap);

	/**
	 * Метод, предоставляющий информацию о наличии крышки
	 * @return - наличие крышки
	 */
	boolean isCap();
}