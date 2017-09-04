package cn.share.jack.cygwidget.sidebar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import cn.share.jack.cygwidget.R;


/**
 * Created by jack on 2017/7/6
 */

public class MySideBar extends View {

    // 26个字母
    private String[] b = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private int textColorNormal;
    private int textColorPressed;

    private Paint paint;

    private int selectLetter = -1;     //选中字母的下标

    private OnTouchLetterChangeListener onTouchLetterChangeListener;


    public MySideBar(Context context) {
        this(context, null);
    }

    public MySideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MySideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray array = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MySideBar, defStyleAttr, 0);
        textColorNormal = array.getColor(R.styleable.MySideBar_text_color_normal, Color.BLACK);
        textColorPressed = array.getColor(R.styleable.MySideBar_text_color_pressed, Color.WHITE);

        array.recycle();
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float singleHeight = (getHeight() - 20) / b.length;    //每一个字母的高度
        for (int i = 0; i < b.length; i++) {
            paint.setColor(textColorNormal);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setAntiAlias(true);
            paint.setTextSize(30);
            //选中
            if (i == selectLetter) {
                paint.setColor(textColorPressed);
                paint.setFakeBoldText(true);
            }
            //paint.measureText()方法是测量字符串的宽度
            float letterX = getWidth() / 2 - paint.measureText(b[i]) / 2;
            float letterY = singleHeight * i + singleHeight;
            canvas.drawText(b[i], letterX, letterY, paint);
            paint.reset();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        float y = event.getY();
        int oldSelectLetter = selectLetter;    //上一次选中的
        int index = (int) (y / getHeight() * b.length);

        switch (event.getAction()) {
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                setBackgroundColor(0x00000000);
                selectLetter = -1;
                if (null != onTouchLetterChangeListener) {
                    onTouchLetterChangeListener.unSelected();
                }
                invalidate();
                break;
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                Log.d("TAG", "dispatchTouchEvent: " + index);
                setBackgroundColor(0x80000000);
                if (oldSelectLetter != index) {      //这里当选中没有变化时就不往下进行了（不多做不必要的操作）
                    if (index >= 0 && index < b.length) {
                        if (null != onTouchLetterChangeListener) {
                            onTouchLetterChangeListener.selected(b[index]);
                        }
                        selectLetter = index;
                        invalidate();
                    }
                }
                break;
        }
        return true;
    }

    public void setOnTouchLetterChangeListener(OnTouchLetterChangeListener onTouchLetterChangeListener) {
        this.onTouchLetterChangeListener = onTouchLetterChangeListener;
    }

    public interface OnTouchLetterChangeListener {
        void selected(String s);

        void unSelected();
    }
}