package com.spitchenko.panproject.MyObserver;

/**
 * Date: 26.12.16
 * Time: 11:02
 *
 * @author anatoliy
 */
public interface PanObserver {
    void update(float temperature, float sizeWater, boolean pan);
    void finished();
}
