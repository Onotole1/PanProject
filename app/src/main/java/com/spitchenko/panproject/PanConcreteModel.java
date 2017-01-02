package com.spitchenko.panproject;

import java.io.Serializable;
import java.util.ArrayList;

import com.spitchenko.panproject.MVC.PanModel;
import com.spitchenko.panproject.MyObserver.PanObserver;
import com.spitchenko.panproject.MyObserver.PanSubject;


/**
 * Date: 25.12.16
 * Time: 13:06
 *
 * @author anatoliy
 */
class PanConcreteModel implements PanModel, Runnable, PanSubject, Serializable {
    private volatile float mSizeWater = 100f;

    private volatile float mTemperatureBurner;

    private volatile boolean mRunThread;

    private volatile float mTemperatureWater = 95f;
    private boolean mCap = true;

    public void setTemperatureBurner(float temperatureBurner) {
        this.mTemperatureBurner = temperatureBurner;
        notifyObservers();
    }

    public boolean isCap() {
        return mCap;
    }

    public void setCap(boolean cap) {
        this.mCap = cap;
        notifyObservers();
    }

    private ArrayList<PanObserver> observers = new ArrayList<>();

    @Override
    public void run() {
	    mRunThread = true;
        while (mRunThread) {
            System.out.println(mTemperatureWater + " " + mSizeWater);
            if (mTemperatureBurner != 0f && mCap && mSizeWater > 0f) {
                try {
                    if (mTemperatureWater < 100f) {
                        System.out.println("1");
	                    mTemperatureWater += mTemperatureBurner / mSizeWater;
                        if (mTemperatureWater > 100)
	                        mTemperatureWater = 100;
                        notifyObservers();
                        Thread.sleep(500);
                    }
                    else if (mTemperatureWater == 100f) {
                        System.out.println("2");
	                    mSizeWater -= 5f;
                        notifyObservers();
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (mTemperatureBurner != 0f && !mCap && mSizeWater > 0f) {
                try {
                    if (mTemperatureWater < 100f) {
	                    mTemperatureWater += mTemperatureBurner / mSizeWater;
                        if (mTemperatureWater > 100)
	                        mTemperatureWater = 100;
                        System.out.println("3");
                        notifyObservers();
                        Thread.sleep(1500);
                    }
                    else if (mTemperatureWater == 100f) {
	                    mSizeWater -= 5f;
                        System.out.println("4");
                        notifyObservers();
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (mSizeWater == 0f) {
	            mTemperatureWater = 0f;
                notifyObservers();
            }

        }
    }

    @Override
    public void registerObserver(PanObserver observer) {
        observers.add(observer);
        notifyObservers();
    }

    @Override
    public void removeObserver(PanObserver observer) {
        int i = observers.indexOf(observer);
        if (i >= 0) {
            observers.get(observers.indexOf(observer)).finished();
            observers.remove(observers.indexOf(observer));
        }
    }

    @Override
    public void notifyObservers() {
        for (PanObserver observer:observers) {
            observer.update(this.mTemperatureWater, this.mSizeWater, this.mCap);
        }
    }

    void setRunThread(boolean runThread) {
        this.mRunThread = runThread;
    }
}
