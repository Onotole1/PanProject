package com.spitchenko.panproject;

import java.util.ArrayList;

import com.spitchenko.panproject.MVC.BurnerModel;
import com.spitchenko.panproject.MyObserver.BurnerObserver;
import com.spitchenko.panproject.MyObserver.BurnerSubject;

/**
 * Date: 26.12.16
 * Time: 0:52
 *
 * @author anatoliy
 */
class GasBurnerModel implements BurnerModel, BurnerSubject {
    private float minBurn = 0f;
    private float burn = minBurn;

    private ArrayList<BurnerObserver> observers = new ArrayList<>();


    void setBurn(float size) {
        float maxBurn = 100f;
        if (size > maxBurn) {
            this.burn = maxBurn;
            notifyObservers();
        }
        else if (size < minBurn) {
            this.burn = minBurn;
            notifyObservers();
        }
        else {
            this.burn = size;
            notifyObservers();
        }
    }

    float getBurn() {
        return this.burn;
    }

    @Override
    public void registerObserver(BurnerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(BurnerObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0)
            observers.remove(observers.indexOf(observer));
    }

    @Override
    public void notifyObservers() {
        for (BurnerObserver observer:observers) {
            observer.update(this.burn);
        }
    }


}
