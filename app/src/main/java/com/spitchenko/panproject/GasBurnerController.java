package com.spitchenko.panproject;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.spitchenko.panproject.MVC.BurnerController;

/**
 * Date: 26.12.16
 * Time: 11:51
 *
 * @author anatoliy
 */
public class GasBurnerController implements BurnerController, SeekBar.OnSeekBarChangeListener {
    private GasBurnerModel burnerModel;
	private boolean isPan;
	private PanController panController;
	private PanSoup panSoup;
	private PanSoupView panSoupView;
	private Thread thread;

    public GasBurnerController(final Context context, Button buttonPan, Button buttonCap) {
        this.burnerModel = new GasBurnerModel();
	    panSoup = new PanSoup();
	    this.panController = new PanController(buttonCap, panSoup);
	    GasBurnerView gasBurnerView = new GasBurnerView();
	    burnerModel.registerObserver(gasBurnerView);

	    panSoupView = new PanSoupView(context);
	    panController = new PanController(buttonCap, panSoup);
	    thread = new Thread(panSoup);

		isPan = true;
	    View.OnClickListener oclBtnOk = new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {

			    if (!isPan) {
				    burnerModel.registerObserver(panController);
				    panSoup.registerObserver(panSoupView);
				    thread.start();
				    isPan = true;
			    }
			    else {
				    panSoup.removeObserver(panSoupView);
					burnerModel.removeObserver(panController);
				    //thread.interrupt();
				    isPan = false;
			    }
		    }
	    };

	    buttonPan.setOnClickListener(oclBtnOk);
	    //mContext = context;
	    //mSeekBar = seekBar;
    }



    @Override
    public void changeTemperature(float size) {
		burnerModel.setBurn(burnerModel.getBurn() + size);
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
		burnerModel.setBurn((float)i * 25);
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {

	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {

	}


}
