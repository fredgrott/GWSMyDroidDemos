/*
 * Copyright 2015 Prolific Interactive
 * Modifications Copyright (C) 2015 Fred Grott(GrottWorkShop)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.grottworkshop.gwsmaterialcalendarview;

import android.animation.Animator;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.TextView;


import com.grottworkshop.gwsmaterialcalendarview.format.TitleFormatter;

/**
 * TitleChanger class
 * Created by fgrott on 9/16/2015.
 */
@SuppressWarnings("unused")
class TitleChanger {

    public static final int DEFAULT_ANIMATION_DELAY = 400;
    public static final int DEFAULT_Y_TRANSLATION_DP = 20;

    private final TextView title;
    private TitleFormatter titleFormatter;

    private final int animDelay;
    private final int animDuration;
    private final int yTranslate;
    private final Interpolator interpolator = new DecelerateInterpolator(2f);

    private long lastAnimTime = 0;
    private CalendarDay previousMonth = null;

    public TitleChanger(TextView title) {
        this.title = title;

        Resources res = title.getResources();

        animDelay = DEFAULT_ANIMATION_DELAY;

        animDuration = res.getInteger(android.R.integer.config_shortAnimTime) / 2;

        yTranslate = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, DEFAULT_Y_TRANSLATION_DP, res.getDisplayMetrics()
        );
    }

    public void change(final CalendarDay currentMonth) {
        long currentTime = System.currentTimeMillis();

        if(currentMonth == null) {
            return;
        }

        if(TextUtils.isEmpty(title.getText()) || (currentTime-lastAnimTime) < animDelay) {
            doChange(currentTime, currentMonth, false);
        }

        if (currentMonth.equals(previousMonth)) {
            return;
        }

        doChange(currentTime, currentMonth, true);
    }

    private void doChange(final long now, final CalendarDay currentMonth, boolean animate) {

        title.animate().cancel();
        title.setTranslationY(0);
        title.setAlpha(1);
        lastAnimTime = now;

        final CharSequence newTitle = titleFormatter.format(currentMonth);

        if(!animate) {
            title.setText(newTitle);
        }
        else {
            final int yTranslation = yTranslate * (previousMonth.isBefore(currentMonth) ? 1 : -1);

            title.animate()
                    .translationY(yTranslation * -1)
                    .alpha(0)
                    .setDuration(animDuration)
                    .setInterpolator(interpolator)
                    .setListener(new AnimatorListener() {

                        @Override
                        public void onAnimationCancel(Animator animator) {
                            title.setTranslationY(0);
                            title.setAlpha(1);
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            title.setText(newTitle);
                            title.setTranslationY(yTranslation);
                            title.animate()
                                    .translationY(0)
                                    .alpha(1)
                                    .setDuration(animDuration)
                                    .setInterpolator(interpolator)
                                    .setListener(new AnimatorListener())
                                    .start();
                        }
                    }).start();
        }

        previousMonth = currentMonth;
    }

    public TitleFormatter getTitleFormatter() {
        return titleFormatter;
    }

    public void setTitleFormatter(TitleFormatter titleFormatter) {
        this.titleFormatter = titleFormatter;
    }

    public void setPreviousMonth(CalendarDay previousMonth) {
        this.previousMonth = previousMonth;
    }
}
