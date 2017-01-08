package com.spitchenko.panproject;

import java.io.IOException;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.spitchenko.panproject.MVC.PanView;

import pl.droidsonroids.gif.GifDrawable;


import static com.spitchenko.panproject.MainActivity.sHandler;

/**
 * Date: 26.12.16
 * Time: 22:47
 *
 * @author anatoliy
 */
class PanConcreteView implements PanView {
    //ссылка на контекст необходима для извлечения ресурсов
    private Context mContext;
    private PanConcreteController mPanController;
    private Button mCapButton;

    PanConcreteView(Button panButton, Button capButton, Context context) {
        mContext = context;
        mCapButton = capButton;
        capButton.setOnClickListener(new ViewOnClickListenerCap());
        panButton.setOnClickListener(new ViewOnClickListenerPan());
        mCapButton.setEnabled(false);
    }

    @Override
    @SuppressWarnings("all")
    public void update(float temperature, float sizeWater, boolean cap) {
        Message msg = new Message();
        msg.arg1 = (int)temperature;
        msg.arg2 = (int)sizeWater;

        GifDrawable gifFromResource = null;
        try {
            if (sizeWater > 0f && sizeWater <= 25f && cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_25);
            } else if (sizeWater > 25f && sizeWater <= 50f && cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_50);
            } else if (sizeWater > 50f && sizeWater <= 75f && cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_75);
            } else if (sizeWater > 75f && sizeWater <= 100f && cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_100);
            } else if (sizeWater == 0f && cap) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_0);
            } else if (sizeWater > 0f && sizeWater <= 25f && !cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_25);
            } else if (sizeWater > 25f && sizeWater <= 50f && !cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_50);
            } else if (sizeWater > 50f && sizeWater <= 75f && !cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_75);
            } else if (sizeWater > 75f && sizeWater <= 100f && !cap && temperature < 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_100);
            } else if (sizeWater == 0f && !cap) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_0);
            } else if (sizeWater > 0f && sizeWater <= 25f && cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_25_boiling);
                panBoilBul();
            } else if (sizeWater > 25f && sizeWater <= 50f && cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_50_boiling);
                panBoilBul();
            } else if (sizeWater > 50f && sizeWater <= 75f && cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_75_boiling);
                panBoilBul();
            } else if (sizeWater > 75f && sizeWater <= 100f && cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_cap_100_boiling);
                panBoilBul();
            } else if (sizeWater > 0f && sizeWater <= 25f && !cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_25_boiling);
                panBoilBul();
            } else if (sizeWater > 25f && sizeWater <= 50f && !cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_50_boiling);
                panBoilBul();
            } else if (sizeWater > 50f && sizeWater <= 75f && !cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_75_boiling);
                panBoilBul();
            } else if (sizeWater > 75f && sizeWater <= 100f && !cap && temperature >= 100) {
                gifFromResource = new GifDrawable(mContext.getResources(), R.drawable.pan_100_boiling);
                panBoilBul();
            }

            msg.obj = gifFromResource;
            sHandler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Поведение слушателя кнопки, создающей кастрюлю
     */
    private class ViewOnClickListenerPan implements View.OnClickListener {

        @Override
        public void onClick(View v) {
	        if (mCapButton.isEnabled())
		        mCapButton.setEnabled(false);
	        else
		        mCapButton.setEnabled(true);
            mPanController.setPan();
        }
    }

    /**
     * Поведение слушателя кнопки, устанавливающей наличие крышки
     */
    private class ViewOnClickListenerCap implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            mPanController.setCap();
        }
    }

    @Override
    public void finished() {
        Message msg = new Message();
        try {
            msg.obj = new GifDrawable(mContext.getResources(), R.drawable.pan_null);
            msg.arg1 = -1;
            sHandler.sendMessage(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

	/**
	 * Метод воспроизведения звука кипящей воды
	 */
    private void panBoilBul() {
	    MediaPlayer mediaPlayer = MediaPlayer.create(mContext, R.raw.bul);
        mediaPlayer.start();
    }

    void setPanController(PanConcreteController panController) {
        mPanController = panController;
    }
}
