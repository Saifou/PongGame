package com.startup.monpong.monpong;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import static android.content.Context.VIBRATOR_SERVICE;
import static android.graphics.Color.rgb;

// SmsManager.getDefault().sendTextMessage(number, null, messageToSend, null,null);

/**
 * Created by Boss on 10/04/2018.
 */

public class PongView extends View implements View.OnTouchListener {

    boolean sansFin = true;
    String sScore = "";
    int counter = 2;
    int iScore = 0;
    int ballY;
    int ballX;
    int speedY;
    int speedX;
    float radius;
    Paint mypaint;
    Paint scoreMyPaint;
    float rectLeft;
    float rectTop;
    float rectRight;
    float rectBottom;
    float axeXPaddle;
    String messageToSend = "this is a message";
    String number = "0661874147";
    Vibrator vb;




    public PongView(Context context) {
        super(context);


        this.setOnTouchListener(this);
        ballY = 200;
        ballX = 200;
        speedY = 15;
        speedX = 10;
        radius = 30f;
        mypaint = new Paint(50);
        scoreMyPaint = new Paint(rgb(0, 0, 0));
        rectLeft = 50f;
        rectTop = 2250f;
        rectRight = 500f;
        rectBottom = 2300f;
        vb = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        Drawable image = (Drawable) getResources().getDrawable(R.drawable.ic_launcher_background);
        setBackground(image);


    }

    @Override
    protected void onDraw(Canvas canvas) {            // Creation du canvas
        super.onDraw(canvas);
        //canvas.drawPaint(mypaint);
        mypaint.setStyle(Paint.Style.FILL);
        mypaint.setColor(Color.BLACK);
        scoreMyPaint.setTextSize(80);
        sScore = String.valueOf(iScore);
        canvas.drawText(sScore, 10, 2500, scoreMyPaint);
        canvas.drawCircle(ballX, ballY, radius, mypaint);
        canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, mypaint);
        updateCanvas();

    }

    public void updateCanvas() {             // update ball & rect
        this.ballX += speedX;
        this.ballY += speedY;


        scopeCanvas();
        racketLeft();
        racketRight();
        racketBottom();
        racketTop();


        invalidate();
    }



    public void scopeCanvas() {

        if (this.ballX < 0 || this.ballX > 1400) {
            this.speedX *= -1;
        }
        if (this.ballY < 0 || this.ballY > 2600) {
            this.speedY *= -1;
        }
    }


    public void racketBottom() {
        if ((this.ballY > rectTop && this.ballY < rectBottom) && (this.ballX > rectLeft && this.ballX < rectRight)) {
            vb.vibrate(100l);
            this.speedY *= -1;
            this.iScore++;

        }
    }


    public void racketTop() {
        if ((this.ballX > rectLeft && this.ballX < rectRight) && (this.ballY < rectBottom && this.ballY > rectTop)) {
            vb.vibrate(100l);
            this.speedY *= +1;
            this.iScore++;
        }
    }

    public void racketLeft() {
        if ((this.ballX == rectRight && this.ballX > rectLeft) && (this.ballY > rectTop && this.ballY < rectBottom)) {

            vb.vibrate(100l);
            this.speedX *= -1;
            this.iScore++;
        }
    }



   public void racketRight(){
        if((this.ballX == rectLeft && this.ballX < rectRight) && (this.ballY > rectTop && this.ballY < rectBottom)){
            vb.vibrate(100l);
            this.speedX *= -1;
            this.iScore++;
        }
    }







    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float positionY = motionEvent.getY();
        float positionX = motionEvent.getX();

        if(positionY > 2100){ // gestion du passage en bas
            rectTop = 2250f;
            rectBottom = 2300f;
            rectLeft = motionEvent.getX()-250;
            rectRight = motionEvent.getX()+200;
        }
        if(positionX < 250) { // Gestion du passage à droite
            rectLeft = 50f;
            rectRight = 100f;
            rectTop = motionEvent.getY() - 250;
            rectBottom = motionEvent.getY() + 200;
        }
        if(positionX > 1336) { // Gestion du passage en haut
            rectLeft = 1250f;
            rectRight = 1300f;
            rectTop = motionEvent.getY() - 250;
            rectBottom = motionEvent.getY() + 200;
        }
        if(positionY < 86){ // Gestion du passage à gauche
            rectTop = 130f;
            rectBottom = 180f;
            rectLeft = motionEvent.getX()-250;
            rectRight = motionEvent.getX()+200;
        }

        return true;
    }
}
