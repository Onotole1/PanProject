package com.spitchenko.panproject;

import java.util.ArrayList;

import android.os.AsyncTask;
import android.support.annotation.UiThread;
import android.support.annotation.WorkerThread;
import android.util.Log;

import com.spitchenko.panproject.MVC.PanModel;
import com.spitchenko.panproject.MyObserver.PanObserver;

/**
 * Date: 02.01.17
 * Time: 21:27
 *
 * @author anatoliy
 */
class PanConcreteModel extends AsyncTask<Void, Integer, Void> implements PanModel {
    //по умолчанию кастрюля полностью наполнена водой
    private volatile float mSizeWater = 100f;

    private volatile float mTemperatureBurner;

    //по умолчанию вода комнатной температуры
    private volatile float mTemperatureWater = 20f;

    //по умолчанию кастрюля накрыта крышкой
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

	        //Если конфорка находится в выключенном состоянии, то вода остывает
	        if (mTemperatureBurner == 0) {
		        if (mTemperatureWater > 21) {
			        mTemperatureWater -= 1;
			        notifyObservers();
			        asyncInterrupt(1500, "mTemperatureBurner == 0");
		        }
	        }
	        //Если конфорка находится во включенном состоянии, то вода наревается. С крышкой этот процесс быстрее
	        else if (mTemperatureBurner != 0f && mCap && mSizeWater > 0f) {
		        if (mTemperatureWater < 100f) {
			        Log.d("PanConcreteModel", "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater < 100f");
			        mTemperatureWater += mTemperatureBurner / mSizeWater;
			        if (mTemperatureWater > 100)
				        mTemperatureWater = 100;
			        notifyObservers();
			        asyncInterrupt(500, "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater < 100f");
		        }
		        //Если вода закипела, то она превращается в пар и её объём (жидкости) уменьшается
		        else if (mTemperatureWater == 100f) {
			        Log.d("PanConcreteModel", "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater == 100f");
			        mSizeWater -= 5f;
			        notifyObservers();
			        asyncInterrupt(500, "mTemperatureBurner != 0f && mCap && mSizeWater > 0f, mTemperatureWater == 100f");
		        }
	        }
	        //Если конфорка находится во включенном состоянии, то вода наревается. Без крышки этот процесс медленнее
	        else if (mTemperatureBurner != 0f && !mCap && mSizeWater > 0f) {
		        if (mTemperatureWater < 100f) {
			        mTemperatureWater += mTemperatureBurner / mSizeWater;
			        if (mTemperatureWater > 100)
				        mTemperatureWater = 100;
			        Log.d("PanConcreteModel", "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater < 100f");
			        notifyObservers();
			        asyncInterrupt(1500, "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater < 100f");
		        }
		        //Если вода закипела, то она превращается в пар и её объём (жидкости) уменьшается
		        else if (mTemperatureWater == 100f) {
			        mSizeWater -= 5f;
			        Log.d("PanConcreteModel", "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater == 100f");
			        notifyObservers();
			        asyncInterrupt(500, "mTemperatureBurner != 0f && !mCap && mSizeWater > 0f, mTemperatureWater == 100f");
		        }
	        }
	        //Если вся вода выкипела, то ничего кроме оповещения не происходит
	        else if (mSizeWater == 0f) {
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

    float getSizeWater() {
        return mSizeWater;
    }

    float getTemperatureWater() {
        return mTemperatureWater;
    }

    void setSizeWater(float sizeWater) {
        mSizeWater = sizeWater;
    }

    void setTemperatureWater(float temperatureWater) {
        mTemperatureWater = temperatureWater;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        for (PanObserver p:observers) {
            p.finished();
            removeObserver(p);
        }
    }

	ArrayList<PanObserver> getObservers() {
		return observers;
	}

	/**
	 * Метод имитирует длительность процессов
     * @param sleep - время, на которое засыпает поток
     * @param description - описание ситуации
     */
    private void asyncInterrupt(long sleep, String description) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException e) {
            Log.d("Interrupt", description);
        }
    }
}