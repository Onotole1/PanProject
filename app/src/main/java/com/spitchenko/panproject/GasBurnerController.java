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
class GasBurnerController implements BurnerController, SeekBar.OnSeekBarChangeListener {
    private GasBurnerModel burnerModel;
	private boolean isPan;
	private PanController panController;
	private PanConcrete panConcrete;
	private PanSoupView panSoupView;
	private Thread thread;
	private Button capButton;
	private Context context;

    GasBurnerController(Context context, Button buttonPan, Button buttonCap) {
	    burnerModel = new GasBurnerModel();
	    GasBurnerView gasBurnerView = new GasBurnerView();
	    burnerModel.registerObserver(gasBurnerView);
		this.capButton = buttonCap;
		isPan = false;
		this.context = context;
	    View.OnClickListener oclBtnOk = new ViewOnClickListener();

	    buttonPan.setOnClickListener(oclBtnOk);

    }


	private class ViewOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (!isPan) {

				panConcrete = new PanConcrete();
				panController = new PanController(capButton, panConcrete);
				panSoupView = new PanSoupView(context);
				panController = new PanController(capButton, panConcrete);
				thread = new Thread(panConcrete);
				burnerModel.registerObserver(panController);
				panConcrete.registerObserver(panSoupView);
				thread.start();
				burnerModel.notifyObservers();
				isPan = true;
			}
			else {
				panConcrete.setRunThread(false);
				panConcrete.removeObserver(panSoupView);
				thread.interrupt();
				burnerModel.removeObserver(panController);
				isPan = false;
			}
		}
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
