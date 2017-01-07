package com.spitchenko.panproject.MyObserver;

/**
 * Date: 26.12.16
 * Time: 11:02
 *
 * @author anatoliy
 */
public interface PanObserver {
	/**
     * Метод, обновляющий объект представления кастрюли
     * @param temperature - температура
     * @param sizeWater - объём воды в процентах
     * @param pan - наличие у кастрюли крышки
     */
    void update(float temperature, float sizeWater, boolean pan);

	/**
	 * Метод очистки экрана от представления
	 */
    void finished();
}
