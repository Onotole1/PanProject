package com.spitchenko.panproject;

import android.os.Message;

import com.spitchenko.panproject.MVC.PanView;
import com.spitchenko.panproject.MyObserver.BurnerObserver;


import static com.spitchenko.panproject.MainActivity.h1;

/**
 * Date: 26.12.16
 * Time: 23:28
 *
 * @author anatoliy
 */
public class GasBurnerView implements PanView, BurnerObserver {
    private float temperatureView;

    @Override
    public void update(float temperatureView) {
        this.temperatureView = temperatureView;
        System.out.println("Ручка повёрнута на " + this.temperatureView + " процентов");
        int drawable = 0;
        if (temperatureView == 0)
            drawable = R.drawable.burner_0;
        if (temperatureView > 0 && temperatureView <= 25)
            drawable = R.drawable.burner_25;
        if (temperatureView > 25 && temperatureView <= 50)
            drawable = R.drawable.burner_50;
        if (temperatureView > 50 && temperatureView <= 75)
            drawable = R.drawable.burner_75;
        if (temperatureView > 75 && temperatureView <= 100)
            drawable = R.drawable.burner_100;
        Message msg = new Message();
        msg.arg1 = drawable;
        h1.sendMessage(msg);
    }
}
