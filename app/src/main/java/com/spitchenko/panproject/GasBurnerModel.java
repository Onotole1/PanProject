package com.spitchenko.panproject;

import java.util.ArrayList;

import com.spitchenko.panproject.MVC.BurnerModel;
import com.spitchenko.panproject.MyObserver.BurnerObserver;

/**
 * Date: 26.12.16
 * Time: 0:52
 *
 * @author anatoliy
 */
class GasBurnerModel implements BurnerModel {
    //Мощность конфорки
    private float mBurn;

    private ArrayList<BurnerObserver> mObservers = new ArrayList<>();


    @Override
    public void setBurn(float size) {
            this.mBurn = size;
            notifyObservers();
    }

    @Override
    public void registerObserver(BurnerObserver observer) {
        mObservers.add(observer);
    }

    @Override
    public void notifyObservers() {
        for (BurnerObserver observer:mObservers) {
            observer.update(this.mBurn);
        }
    }


}
