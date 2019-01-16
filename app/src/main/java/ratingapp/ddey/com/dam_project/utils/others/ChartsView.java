package ratingapp.ddey.com.dam_project.utils.others;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;


public class ChartsView extends View {
    private Map<String, Integer> mValues;
    private Paint mPaint;


    public ChartsView(Context context, Map<String, Integer> mValues) {
        super(context);
        this.mValues = mValues;
        this.mPaint = new Paint(Paint.DEV_KERN_TEXT_FLAG);
        this.mPaint.setColor(Color.BLACK);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (mValues != null && mValues.size() > 0) {
            List<String> barLabels = new ArrayList<>(mValues.keySet());
            int nr = barLabels.size();

            float paddingWidth = (float) (canvas.getWidth() * 0.05);
            float paddingHeight = (float) (canvas.getHeight() * 0.05);
            float availableWidth = canvas.getWidth() - 2 * paddingWidth;
            float availableHeight = canvas.getHeight() - 2 * paddingHeight;
            double sourceMaxValue = getMaximumValueFromHashMap(mValues);
            float barSpaceWidth = (float) (canvas.getWidth() * 0.02);
            float barWidth = availableWidth / mValues.size() - barSpaceWidth;
            mPaint.setStrokeWidth((float) (availableWidth * 0.005));

            canvas.drawLine(paddingWidth, paddingHeight, paddingWidth, paddingHeight + availableHeight, mPaint);
            canvas.drawLine(paddingWidth, paddingHeight + availableHeight, paddingWidth + availableWidth, paddingHeight + availableHeight, mPaint);



            Random rnd = new Random();
            for (int i = 0; i < barLabels.size(); i++) {
                mPaint.setARGB(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                float x1;
                if (i == 0) {
                    x1 = paddingWidth + i * barWidth + barSpaceWidth;
                } else {
                    x1 = paddingWidth + i * (barWidth + 2 * barSpaceWidth);
                }
                float y1 = (float) (paddingHeight + (1 - mValues
                        .get(barLabels.get(i)) / sourceMaxValue)
                        * availableHeight);
                float x2 = x1 + barWidth;
                float y2 = paddingHeight + availableHeight;

                canvas.drawRect(x1, y1, x2, y2, mPaint);

                float x = x1 + barWidth / 2 + barSpaceWidth;
                float y = paddingHeight / 2 + availableHeight;
                mPaint.setColor(Color.BLACK);
                mPaint.setTextSize((float) (availableHeight * 0.05));
                canvas.rotate(270, x, y);
                canvas.drawText(barLabels.get(i) + " -> "
                                + mValues.get(barLabels.get(i)),
                        x, y, mPaint);
                canvas.rotate(-270, x, y);
            }

        }
    }

    private double getMaximumValueFromHashMap(Map<String, Integer> map) {
        double max = 0;

        for (Integer i : map.values()) {
            if (i > max) {
                max = i;
            }
        }

        return max;
    }
}
