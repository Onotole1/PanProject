package com.spitchenko.panproject;

import java.util.ArrayList;

import com.spitchenko.panproject.MVC.PanModel;
import com.spitchenko.panproject.MyObserver.PanObserver;
import com.spitchenko.panproject.MyObserver.PanSubject;


/**
 * Created by anatoliy on 25.12.16.
 */
public class PanConcrete implements PanModel, Runnable, PanSubject {
    private volatile float sizeWater = 100f;
    //private float sizePan = 2f;

    private volatile boolean runThread;

    private volatile float temperatureWater = 95f;
    private boolean cap = true;

    public ArrayList<PanObserver> getObservers() {
        return observers;
    }

    public void setObservers(ArrayList<PanObserver> observers) {
        this.observers = observers;
    }

    public float getTemperatureBurner() {
        return temperatureBurner;
    }

    public void setTemperatureBurner(float temperatureBurner) {
        this.temperatureBurner = temperatureBurner;
    }

    public boolean isCap() {
        return cap;
    }

    public void setCap(boolean cap) {
        this.cap = cap;
    }

    public float getTemperatureWater() {
        return temperatureWater;
    }

    public void setTemperatureWater(float temperatureWater) {
        this.temperatureWater = temperatureWater;
    }

    public float getSizeWater() {
        return sizeWater;
    }

    public void setSizeWater(float sizeWater) {
        this.sizeWater = sizeWater;
    }

    private float temperatureBurner;

    private ArrayList<PanObserver> observers = new ArrayList<>();



    @Override
    public void run() {
        runThread = true;
        while (runThread) {
            if (temperatureBurner != 0f && cap && sizeWater > 0f) {
                try {
                    if (temperatureWater < 100f) {
                        temperatureWater += temperatureBurner / sizeWater;
                        notifyObservers();
                        Thread.sleep(500);
                    }
                    else if (temperatureWater == 100f) {
                        sizeWater -= 5f;
                        notifyObservers();
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (temperatureBurner != 0f && !cap && sizeWater > 0f) {
                try {
                    if (temperatureWater < 100f) {
                        temperatureWater += temperatureBurner / sizeWater;
                        notifyObservers();
                        Thread.sleep(1500);
                    }
                    else if (temperatureWater == 100f) {
                        sizeWater -= 5f;
                        notifyObservers();
                        Thread.sleep(500);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else if (sizeWater == 0f) {
                temperatureWater = 0f;
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
            observer.update(this.temperatureWater, this.sizeWater, this.cap);
        }
    }

    public void setRunThread(boolean runThread) {
        this.runThread = runThread;
    }
}
