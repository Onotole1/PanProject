package com.spitchenko.panproject.MyObserver;

/**
 * Date: 26.12.16
 * Time: 11:02
 *
 * @author anatoliy
 */
public interface PanSubject {

    void registerObserver(PanObserver observer);
    void removeObserver(PanObserver observer);
    void notifyObservers();
}
