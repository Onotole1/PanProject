package com.spitchenko.panproject.MyObserver;

/**
 * Date: 29.12.16
 * Time: 11:01
 *
 * @author anatoliy
 */
public interface BurnerSubject {
	/**
	 * Добавление объекта в список наблюдателей
	 * @param observer - объект наблюдатель
	 */
	void registerObserver(BurnerObserver observer);

	/**
	 * Метод оповещения наблюдателей
	 */
	void notifyObservers();
}
