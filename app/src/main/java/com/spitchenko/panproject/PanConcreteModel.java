package com.spitchenko.panproject;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.spitchenko.panproject.MVC.PanModel;
import com.spitchenko.panproject.MyObserver.PanObserver;
import com.spitchenko.panproject.MyObserver.PanSubject;

/**
 * Date: 02.01.17
 * Time: 21:27
 *
 * @author anatoliy
 */
class PanConcreteModel extends AsyncTask<Void, Integer, Void> implements PanSubject, PanModel {
    private volatile float mSizeWater = 100f;

    private volatile float mTemperatureBurner;

    private volatile float mTemperatureWater = 20f;

    private volatile boolean mCap = true;

    private volatile ArrayList<PanObserver> observers = new ArrayList<>();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @WorkerThread
    @Override
    protected Void doInBackground(Void... voids) {
        while (!isCancelled()) {

                Log.d("PanConcreteModel", mTemperatureWater + " " + mSizeWater + " " + mCap + " " + mTemperatureBurner + " " + this.toString());
                if (mTemperatureBurner != 0f && mCap && mSizeWater > 0f) {
                        if (mTemperatureWater < 100f) {
                            Log.d("PanConcreteModel", "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater < 100f");
                            mTemperatureWater += mTemperatureBurner / mSizeWater;
                            if (mTemperatureWater > 100)
                                mTemperatureWater = 100;
                            notifyObservers();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Log.d("Interrupt", "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater < 100f");
                            }
                        }
                        else if (mTemperatureWater == 100f) {
                            Log.d("PanConcreteModel", "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater == 100f");
                            mSizeWater -= 5f;
                            notifyObservers();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Log.d("Interrupt", "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater == 100f");
                            }
                        }
                } else if (mTemperatureBurner != 0f && !mCap && mSizeWater > 0f) {

                        if (mTemperatureWater < 100f) {
                            mTemperatureWater += mTemperatureBurner / mSizeWater;
                            if (mTemperatureWater > 100)
                                mTemperatureWater = 100;
                            Log.d("PanConcreteModel", "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater < 100f");
                            notifyObservers();
                            try {
                                Thread.sleep(1500);
                            } catch (InterruptedException e) {
                                Log.d("Interrupt", "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater < 100f");
                            }
                        } else if (mTemperatureWater == 100f) {
                            mSizeWater -= 5f;
                            Log.d("PanConcreteModel", "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater == 100f");
                            notifyObservers();
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                Log.d("Interrupt", "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater == 100f");
                            }
                        }
                } else if (mSizeWater == 0f) {
                    mTemperatureWater = 0f;
                    notifyObservers();
                }

            }

        return null;
    }

    @UiThread
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @UiThread
    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
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

    @Override
    public void setTemperatureBurner(float temperatureBurner) {
        this.mTemperatureBurner = temperatureBurner;
    }

    @Override
    public void setCap(boolean cap) {
        this.mCap = cap;
	    notifyObservers();
    }

    @Override
    public boolean isCap() {
        return this.mCap;
    }

	ArrayList<PanObserver> getObservers() {
		return observers;
	}

    @Override
    protected void onCancelled() {
        super.onCancelled();
        for (PanObserver p:observers) {
            p.finished();
            removeObserver(p);
        }
    }
}