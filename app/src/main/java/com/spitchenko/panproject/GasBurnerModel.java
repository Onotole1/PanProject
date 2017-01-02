package com.spitchenko.panproject;

import java.io.Serializable;
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
class GasBurnerModel implements BurnerModel, BurnerSubject, Serializable{
    private float mBurn;

    private ArrayList<BurnerObserver> mObservers = new ArrayList<>();


    void setBurn(float size) {
            this.mBurn = size;
            notifyObservers();
    }

    @Override
    public void registerObserver(BurnerObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void removeObserver(BurnerObserver observer) {
        int i = mObservers.indexOf(observer);
        if (i >= 0)
            mObservers.remove(mObservers.indexOf(observer));
    }

    @Override
    public void notifyObservers() {
        for (BurnerObserver observer:mObservers) {
            observer.update(this.mBurn);
        }
    }


}
