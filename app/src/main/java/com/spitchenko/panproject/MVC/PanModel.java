package com.spitchenko.panproject.MVC;

/**
 * Created by anatoliy on 25.12.16.
 */
public interface PanModel {
	void setTemperatureBurner(float temperatureBurner);
	void setSizeWater(float sizeWater);
	void setCap(boolean cap);
	boolean isCap();
}
/*
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

	public float getSizePan() {
		return sizePan;
	}

	public void setSizePan(float sizePan) {
		this.sizePan = sizePan;
	}

	public float getSizeWater() {
		return sizeWater;
	}

	public void setSizeWater(float sizeWater) {
		this.sizeWater = sizeWater;
	}
*/