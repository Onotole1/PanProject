package com.spitchenko.panproject;

import java.io.Serializable;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.spitchenko.panproject.MVC.BurnerController;

/**
 * Date: 26.12.16
 * Time: 11:51
 *
 * @author anatoliy
 */
class GasBurnerController implements BurnerController, CircularSeekBar.OnCircularSeekBarChangeListener, Serializable {
    private GasBurnerModel mGasBurnerModel;

	private boolean mIsPan;
	private PanConcreteController mPanConcreteController;
	private PanConcreteModel mPanConcreteModel;
	private PanConcreteView mPanConcreteView;
	private transient Thread mThread;
	private Button mCapButton;
	private transient Context mContext;

    GasBurnerController(Context context, Button buttonPan, Button buttonCap) {
	    mGasBurnerModel = new GasBurnerModel();
	    GasBurnerView gasBurnerView = new GasBurnerView();
	    mGasBurnerModel.registerObserver(gasBurnerView);
		this.mCapButton = buttonCap;
	    mIsPan = false;
		this.mContext = context;
	    View.OnClickListener oclBtnOk = new ViewOnClickListener();

	    buttonPan.setOnClickListener(oclBtnOk);

    }

	@Override
	public void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser) {
		mGasBurnerModel.setBurn(progress);
	}

	@Override
	public void onStopTrackingTouch(CircularSeekBar seekBar) {

	}

	@Override
	public void onStartTrackingTouch(CircularSeekBar seekBar) {

	}

	private class ViewOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {

			if (!mIsPan) {

				mPanConcreteModel = new PanConcreteModel();
				mPanConcreteController = new PanConcreteController(mCapButton, mPanConcreteModel);
				mPanConcreteView = new PanConcreteView(mContext);
				threadStart();
				mIsPan = true;
			}
			else {
				unSubscribe();
			}
		}
	}

	private void threadStart() {
		this.mIsPan = true;
		mGasBurnerModel.registerObserver(mPanConcreteController);
		mPanConcreteModel.registerObserver(mPanConcreteView);
		mGasBurnerModel.notifyObservers();
		mPanConcreteModel.setRunThread(false);
		mThread = new Thread(mPanConcreteModel);
		mThread.start();
	}

	private void unSubscribe() {
		mPanConcreteModel.setRunThread(false);
		mPanConcreteModel.removeObserver(mPanConcreteView);
		mThread.interrupt();
		mGasBurnerModel.removeObserver(mPanConcreteController);
		mIsPan = false;
	}
}
