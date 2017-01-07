package com.spitchenko.panproject.MyObserver;

/**
 * Date: 29.12.16
 * Time: 11:01
 *
 * @author anatoliy
 */
public interface BurnerObserver {
	/**
	 * Метод, обновляющий объект представления конфорки
	 * @param temperature - температура конфорки
	 */
	void update(float temperature);
}
