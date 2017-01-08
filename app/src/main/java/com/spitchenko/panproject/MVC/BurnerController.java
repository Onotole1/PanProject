package com.spitchenko.panproject.MVC;

/**
 * Date: 26.12.16
 * Time: 0:16
 *
 * @author anatoliy
 */
public interface BurnerController  {
	/**
	 * Метод, обрабатывающий изменения величины кругового прогресс-бара
	 * @param size - мощность конфорки
	 */
	void setBurn(float size);
}
