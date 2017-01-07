package com.spitchenko.panproject.MVC;

import com.devadvance.circularseekbar.CircularSeekBar;

/**
 * Date: 26.12.16
 * Time: 0:16
 *
 * @author anatoliy
 */
public interface BurnerController extends CircularSeekBar.OnCircularSeekBarChangeListener {
	/**
	 * Метод, обрабатывающий изменения величины кругового прогресс-бара
	 * @param circularSeekBar - круговой прогресс-бар
	 * @param progress - прогресс
	 * @param fromUser - true, если прогресс задается пользователем
	 */
	void onProgressChanged(CircularSeekBar circularSeekBar, int progress, boolean fromUser);
}
