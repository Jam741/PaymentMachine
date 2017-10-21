package com.bolink.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bolink.R;

/**
 * Created by xulu on 2017/9/9.
 */

public class CircleProgressBar extends View {
    private Paint circlePaint,ringPaint,textPaint;
    private int circleColor,ringColor,textColor;
    private float circleRadius,ringRadius,ringStrokeWidth;
    private int centerX,centerY;
    private float textWidth,textHeight;
    private int totalProgress = 100;
    private int mProgress;
    public CircleProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CircleProgressBar,0,0);
        circleRadius = typedArray.getDimension(R.styleable.CircleProgressBar_cicleRadius,80);
        ringStrokeWidth = typedArray.getDimension(R.styleable.CircleProgressBar_ringStokeWidth,10);
        ringRadius = circleRadius+ringStrokeWidth/2;
        circleColor = typedArray.getColor(R.styleable.CircleProgressBar_circleColor,0xfffff);
        ringColor = typedArray.getColor(R.styleable.CircleProgressBar_ringColor,0xfffff);
        textColor = typedArray.getColor(R.styleable.CircleProgressBar_textColor,0xfffff);

        init();
    }



    public CircleProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
    private void init(){
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(circleColor);
        circlePaint.setStyle(Paint.Style.FILL);

        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setColor(ringColor);
        ringPaint.setStyle(Paint.Style.STROKE);
        ringPaint.setStrokeWidth(ringStrokeWidth);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setColor(textColor);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextSize(circleRadius/2);

        Paint.FontMetrics fm = textPaint.getFontMetrics();
        textHeight = ((int) Math.ceil(fm.descent - fm.ascent));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        centerX = getWidth()/2;
        centerY = getHeight()/2;
        canvas.drawCircle(centerX,centerY,circleRadius,circlePaint);

        if(mProgress>0){
            RectF rectF = new RectF();
            rectF.left = centerX-ringRadius;
            rectF.top = centerY -ringRadius;
            rectF.right = centerX +ringRadius;
            rectF.bottom = centerY+ringRadius;
            //这里要注意一点，先把mProgress强转成float，避免int类型除法得到0
            canvas.drawArc(rectF,-90,((float) mProgress/totalProgress)*360,false,ringPaint);

            String text = mProgress+"%";
            textWidth = textPaint.measureText(text,0,text.length());
            canvas.drawText(text,centerX-textWidth/2,centerY+textHeight/4,textPaint);

        }
    }
    public void setProgress(int progress){
        mProgress = progress;
        postInvalidate();
    }
}
