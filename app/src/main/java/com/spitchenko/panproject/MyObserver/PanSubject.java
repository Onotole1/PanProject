package com.spitchenko.panproject.MyObserver;

/**
 * Date: 26.12.16
 * Time: 11:02
 *
 * @author anatoliy
 */
public interface PanSubject {
	/**
     * Добавление объекта в список наблюдателей за моделью кастрюли
     * @param observer - объект наблюдатель
     */
    void registerObserver(PanObserver observer);

	/**
     * Удаление объекта из списка наблюдателей
     * @param observer - объект наблюдатель
     */
    void removeObserver(PanObserver observer);

	/**
     * Метод оповещения наблюдателей
     */
    void notifyObservers();
}
