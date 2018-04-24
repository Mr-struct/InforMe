package com.example.salon.touchedots;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.PointF;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.os.Vibrator;
import java.util.ArrayList;
public class MultitouchView extends View {
    private int width = 0;
    private int height = 0;
    private  Context mContext;
    private SparseArray<PointF> mActivePointers;
    private ArrayList<Dot> dots = new ArrayList<Dot>();
    private DataExmple exmple1,exmple2;
    private Paint mPaint;


    public MultitouchView(Context context, AttributeSet attrs) {

        super(context, attrs);

        this.mContext = context;

        this.setBackgroundColor(Color.BLACK);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        Display display = wm.getDefaultDisplay();

        Point size = new Point();

        display.getSize(size);

        width = size.x;

        height = size.y;

        for(int i = 1 ; i < 6; i++){

            for(int j = 1; j < 12; j++){

                dots.add(new Dot((i * width/6),

                        (j * height/12),

                        width/20,

                        Color.YELLOW));

            }
        }

        exmple1 = new DataExmple(dots.size(),"https://mrstruct.000webhostapp.com/");

        exmple2 = new DataExmple(dots.size(),"https://www.instagram.com/danapistol/");

        exmple1.setDotsExmple(0,true);

        exmple1.setDotsExmple(54,true);

        exmple2.setDotsExmple(1,true);

        exmple2.setDotsExmple(53,true);

        initView();

    }

    private void initView() {
        mActivePointers = new SparseArray<PointF>();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setColor(Color.BLUE);

        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

    }

    public boolean  isOK(DataExmple d){

      for(int i = 0; i < dots.size(); i++) {

          if(dots.get(i).statut != d.exmpleDotsGetStatut(i)) {

              System.out.println("NE FAIS RIEN");

              return false;
          }

      }

        System.out.println("OK ICI");

        return true;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        // get pointer index from the event object
        int pointerIndex = event.getActionIndex();

        // get pointer ID
        int pointerId = event.getPointerId(pointerIndex);

        // get masked (not specific to a pointer) action
        int maskedAction = event.getActionMasked();

        switch (maskedAction) {

            case MotionEvent.ACTION_DOWN:

            case MotionEvent.ACTION_POINTER_DOWN: {
                // We have a new pointer. Lets add it to the list of pointers

                PointF f = new PointF();

                f.x = event.getX(pointerIndex);

                f.y = event.getY(pointerIndex);

                mActivePointers.put(pointerId, f);

                break;
            }
            case MotionEvent.ACTION_MOVE: { // a pointer was moved

                for (int size = event.getPointerCount(), i = 0; i < size; i++) {

                    PointF point = mActivePointers.get(event.getPointerId(i));

                    if (point != null) {

                        point.x = event.getX(i);

                        point.y = event.getY(i);

                    }

                    invalidate();
                }
                break;
            }
            case MotionEvent.ACTION_UP:

            case MotionEvent.ACTION_POINTER_UP: {
                for(Dot d: dots){
                    d.color = Color.YELLOW;
                    d.statut = false;
                }
                invalidate();
           }
            case MotionEvent.ACTION_CANCEL: {
                mActivePointers.remove(pointerId);
                for(Dot d: dots){
                    d.color = Color.YELLOW;
                    d.statut = false;
                }
                invalidate();
                break;
            }
        }
        if(isOK(exmple1)){
            Vibrator v = (Vibrator) mContext.getSystemService(Context.VIBRATOR_SERVICE);
            // Vibrate for 500 milliseconds
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
            }else{
                //deprecated in API 26
                v.vibrate(500);

                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(exmple1.webPage));
                mContext.startActivity(browserIntent);
            }
        }
        invalidate();

        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float oldX = 0.0f;
        float oldY = 0.0f;
        // draw all pointers
        int k = 0;
        for (final Dot c : dots) {
            mPaint.setColor(c.color);
            canvas.drawCircle(c.getX(), c.getY(), c.getSize(), mPaint);
            mPaint.setColor(Color.BLACK);
        }
        for (int size = mActivePointers.size(), i = 0; i < size; i++) {

            PointF point = mActivePointers.valueAt(i);

            for (final Dot c : dots) {

                if (point != null &&
                        (point.x < c.getX() + c.getSize() && point.x > c.getX() - c.getSize()) &&
                        (point.y < c.getY() + c.getSize() && point.y > c.getY() - c.getSize())) {
                    c.statut = true;
                    c.color = Color.WHITE;
                    mPaint.setColor(Color.GREEN);
                    canvas.drawCircle(point.x, point.y, width/10 , mPaint);
                }
            }


        }
    }

}