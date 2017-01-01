package com.spitchenko.panproject.MyObserver;

/**
 * Date: 29.12.16
 * Time: 11:01
 *
 * @author anatoliy
 */
public interface BurnerSubject {
	void registerObserver(BurnerObserver observer);
	void removeObserver(BurnerObserver observer);
	void notifyObservers();
}
