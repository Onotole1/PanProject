package com.spitchenko.panproject;

import android.os.Message;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.spitchenko.panproject.MVC.BurnerView;


import static com.spitchenko.panproject.MainActivity.sHandler1;

/**
 * Date: 26.12.16
 * Time: 23:28
 *
 * @author anatoliy
 */
class GasBurnerView implements BurnerView {
    private GasBurnerController mGasBurnerController;

    @Override
    public void update(float temperatureView) {
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
        sHandler1.sendMessage(msg);
    }

    @Override
    public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
        mGasBurnerController.setBurn(progress);
    }

    @Override
    public void onStopTrackingTouch(CircularSeekBar seekBar) {

    }

    @Override
    public void onStartTrackingTouch(CircularSeekBar seekBar) {

    }

    void setGasBurnerController(GasBurnerController gasBurnerController) {
        mGasBurnerController = gasBurnerController;
    }
}
