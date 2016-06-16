package com.example.vishnu.tasktwo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {


    public static final int REQUEST_CODE = 1;
    Paint paint;
    Canvas canvas;

    private Timer timer;
    private TimerTask timerTask;

    float vX = 3;
    float vY = 5;

    private float c, r;

    int screenWidth, screenHeight;

    float X, Y;

    float xA;

    float xB;

    float yAB;
    float bottom;

    float xD;
    float xE;
    float yDE;

    float centreX, centreY;

    float a;

    float moveX;
    float moveY;

    float bHeight;

    Bitmap b;
    ImageView imageView;

    RectF rectF;
    RectF oval;

    private int state = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        screenWidth =size.x;
        screenHeight = size.y;

        moveX = screenWidth/10;
        moveY = screenHeight/10;

        b = Bitmap.createBitmap(screenWidth, screenHeight-15, Bitmap.Config.ARGB_8888);
        b.eraseColor(Color.RED);
        //b.eraseColor(Color.RED);

        c=(float) (screenWidth/10);
        r= (float) (0.625*c);

        xA = (float) (c - r * Math.cos(Math.PI / 12));
        xB = (float) (c + r * Math.cos(Math.PI / 12));
        yAB = (float) (c - r * Math.sin(Math.PI / 12));
        bottom = c + r;

        xD = (float) (0.625*c);
        xE = (float) (1.375*c);
        yDE = (float) (0.625*c);

        centreX = screenWidth/2 - c;
        centreY = screenHeight/2 -c;


        a = (float) (c*0.125);


        imageView = (ImageView) findViewById(R.id.imageView);
        imageView.setImageBitmap(b);

        canvas = new Canvas(b);
        canvas.translate(centreX, centreY);

        X = centreX;
        Y = centreY;

        paint = new Paint();

        paint.setAntiAlias(true);


        drawFace();
        setNormal();

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);

        Button button = (Button) findViewById(R.id.button);
        bHeight = button.getHeight();

    }

    public void drawFace(){
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.YELLOW);
        canvas.drawCircle(c, c, c, paint);

        drawEyes();
    }

    public void drawEyes(){

        paint.setColor(Color.BLACK);

        canvas.drawCircle(xD, yDE, a, paint);
        canvas.drawCircle(xE, yDE, a, paint);

    }

    public void setNormal(){

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10F);

        oval = new RectF(xA, yAB-r+a, xB, yAB+r+a);
        paint.setColor(Color.BLACK);
        Path smile = new Path();
        smile.arcTo(oval, 15,150,true);
        canvas.drawPath(smile, paint);
    }

    public void setHappy(){

        rectF = new RectF(xA, yAB, xB, bottom);

        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        canvas.drawArc(rectF, 0F, 180F, true, paint);

    }

    public void setSad(){

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10F);

        oval = new RectF(xA,(float) (yAB-r+1.5*r), xB, (float) (yAB+r+1.5*r));
        paint.setColor(Color.BLACK);
        Path smile = new Path();
        smile.arcTo(oval, 195,150,true);
        canvas.drawPath(smile, paint);

    }

    public void listen(View view) {


        paint.setAntiAlias(false);

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "I'm listening");

        startActivityForResult(intent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode==RESULT_OK && requestCode==REQUEST_CODE){
            ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            String result = results.get(0);

            Toast.makeText(MainActivity.this, result, Toast.LENGTH_SHORT).show();

            if(result.length()==0){
                Toast.makeText(MainActivity.this, "Sorry! Didn't catch you!", Toast.LENGTH_SHORT).show();
            }



            else if (result.contains("happy") || result.contains("laugh")) {

                drawFace();
                setHappy();
                state = 2;
                imageView.invalidate();
            }
            else if (result.contains("normal") || result.contains("smile") || result.contains("default") ||
                    result.contains("hey") || result.contains("what's up") || result.contains("WhatsApp")) {

                drawFace();
                setNormal();
                state = 1;
                imageView.invalidate();
            }
            else if (result.contains("sad") || result.contains("*")) {

                drawFace();
                setSad();
                state = 3;
                imageView.invalidate();
            }





            else if(result.contains("right") || result.equals("East") || result.equals("east")){

                if(X+2*r+moveX>screenWidth)
                {
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(moveX, 0F);
                    X+=moveX;
                    refreshSmiley();
                }

            }

            else if(result.contains("left") || result.equals("West") || result.equals("west")){

                if(X-moveX<0)
                {
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(-moveX, 0F);
                    X-=moveX;
                    refreshSmiley();
                }
            }

            else if(result.contains("up")||result.equals("North") || result.equals("north")){

                if(Y-moveY<0){
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(0F, -moveY);
                    Y-=moveY;
                    refreshSmiley();
                }
            }

            else if(result.contains("down")||result.equals("South")||result.equals("south")){
                if(Y+moveY+2*r+bHeight>screenHeight){
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(0F, moveY);
                    Y+=moveY;
                    refreshSmiley();
                }
            }

            else if(result.contains("North East")){
                if(Y-moveY<0 || X+moveX+2*r>screenWidth)
                {
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(moveX, -moveY);
                    X+=moveX;
                    Y-=moveY;
                    refreshSmiley();
                }
            }

            else if(result.contains("southwest")){

                if(Y+2*r+moveY+bHeight>screenHeight || X-moveX<0)
                {
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(-moveX, moveY);
                    X-=moveX;
                    Y+=moveY;
                    refreshSmiley();
                }
            }

            else if(result.contains("Northwest")){

                if(Y-moveY<0 || X-moveX<0)
                {
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(-moveX, -moveY);
                    X-=moveX;
                    Y-=moveY;
                    refreshSmiley();
                }
            }

            else if(result.contains("Southeast") || result.contains("south east")){

                if(Y+2*r+moveY+bHeight>screenHeight || X+2*r+moveX>screenWidth)
                {
                    Toast.makeText(MainActivity.this, "Can't move! Not enough space", Toast.LENGTH_SHORT).show();
                }

                else {
                    canvas.translate(moveX, moveY);
                    X+=moveX;
                    Y+=moveY;
                    refreshSmiley();
                }
            }

            else if(result.contains("bounce")){


                timer = new Timer();

                timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        update();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshSmiley();
                            }
                        });
                    }
                };


                timer.schedule(timerTask, 0, 10);


            }

            else if(result.contains("stop")){
                timerTask.cancel();
                timer.cancel();
                timer.purge();
            }


            else
                Toast.makeText(MainActivity.this, "No such command ;P", Toast.LENGTH_SHORT).show();



        }

    }


    public void update(){

        if(vX>0) {

            if (X + 2 * r + vX + 60> screenWidth) {
                vX = -vX;
            }
        }

        else
        {
            if(X+vX<0){
                vX = -vX;
            }
        }

        if(vY>0){
            if(Y+2*r+vY+bHeight>screenHeight){
                vY = -vY;
            }
        }


        else{
            if(Y+vY<0){
                vY=-vY;
            }
        }

        X+=vX;
        Y+=vY;
        canvas.translate(vX, vY);


    }


    private void refreshSmiley() {
        b.eraseColor(Color.RED);

        drawFace();

        switch (state){
            case 1: setNormal();
                break;
            case 2: setHappy();
                break;
            case 3: setSad();
                break;

            default: setNormal();
                break;
        }

        imageView.invalidate();
    }

}
