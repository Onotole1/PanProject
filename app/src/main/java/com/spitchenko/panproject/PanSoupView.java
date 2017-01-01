package com.spitchenko.panproject;

import java.io.IOException;

import android.content.Context;
import android.os.Message;

import com.spitchenko.panproject.MVC.PanView;
import com.spitchenko.panproject.MyObserver.PanObserver;

import pl.droidsonroids.gif.GifDrawable;


import static com.spitchenko.panproject.MainActivity.h;

/**
 * Date: 26.12.16
 * Time: 22:47
 *
 * @author anatoliy
 */
class PanSoupView implements PanView, PanObserver {
    private Context mContext;

    PanSoupView(Context context) {
        mContext = context;
    }

    @Override
    @SuppressWarnings("all")
    public void update(float temperature, float sizeWater, boolean pan) {
        Message msg = new Message();
        msg.arg1 = (int)temperature;
        msg.arg2 = (int)sizeWater;

        GifDrawable gifFromResource = null;
        try {
            if (sizeWater > 0f && sizeWater <= 25f && pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_25);
            } else if (sizeWater > 25f && sizeWater <= 50f && pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_50);
            } else if (sizeWater > 50f && sizeWater <= 75f && pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_75);
            } else if (sizeWater > 75f && sizeWater <= 100f && pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_100);
            } else if (sizeWater == 0f && pan) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_0);
            } else if (sizeWater > 0f && sizeWater <= 25f && !pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_25);
            } else if (sizeWater > 25f && sizeWater <= 50f && !pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_50);
            } else if (sizeWater > 50f && sizeWater <= 75f && !pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_75);
            } else if (sizeWater > 75f && sizeWater <= 100f && !pan && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_100);
            } else if (sizeWater == 0f && !pan) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_0);
            } else if (sizeWater > 0f && sizeWater <= 25f && pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_25_boiling);
            } else if (sizeWater > 25f && sizeWater <= 50f && pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_50_boiling);
            } else if (sizeWater > 50f && sizeWater <= 75f && pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_75_boiling);
            } else if (sizeWater > 75f && sizeWater <= 100f && pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_100_boiling);
            } else if (sizeWater > 0f && sizeWater <= 25f && !pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_25_boiling);
            } else if (sizeWater > 25f && sizeWater <= 50f && !pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_50_boiling);
            } else if (sizeWater > 50f && sizeWater <= 75f && !pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_75_boiling);
            } else if (sizeWater > 75f && sizeWater <= 100f && !pan && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_100_boiling);
            }


            msg.obj = gifFromResource;
            h.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void finished() {
        Message msg = new Message();
        try {
            msg.obj = new GifDrawable(mContext.getResources(), R.drawable.pan_null);
            h.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
