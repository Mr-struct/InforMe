package com.example.dana.applic;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity {

    private boolean stop = false;

    public Form form;

    private GridView gridView;

    private  BackGround backGround = new BackGround();

   private  Point p1 = new Point(0,0),
            p2 = new Point(0,0),
            p3 = new Point(0,0),
            p4 = new Point(0,0);

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        this.gridView = this.findViewById(R.id.myView);

        this.gridView.setBackgroundColor(Color.WHITE);

        this.stop = false;

        this.gridView.setOnTouchListener(new Contoler());
    }
    @Override
    protected void onStop() {

        super.onStop();

        MainActivity m = new MainActivity();
        m.stop = false;

    }

    class Contoler implements View.OnTouchListener {

        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            stop = false;


            if(motionEvent.getPointerCount() < 2) {
                stop =true;
                return true;
            }
            if(motionEvent.getPointerCount() == 2 && stop==false) {
                p1.x = (int) (motionEvent.getX(0));
                p1.y = (int) (motionEvent.getY(0));

                p2.x = (int) (motionEvent.getX(1));
                p2.y = (int) (motionEvent.getY(1));

                form = new Form(p1, p2, p3, p4);

                if(form.isVerticalLines2Points()) {
                    Log.d("https://www.google.fr/",form.getFormType());

                    backGround = new BackGround();

                    backGround.execute(form.getFormType());

                    return true;

                } else if(form.isHorizantalLines2points()) {
                    Log.d("https://www.google.fr/",form.getFormType());

                    backGround = new BackGround();

                    backGround.execute(form.getFormType());

                    return true;

                }
            }
            if(motionEvent.getPointerCount() == 3 && stop==false) {
                p1.x = (int) (motionEvent.getX(0));
                p1.y = (int) (motionEvent.getY(0));

                p2.x = (int) (motionEvent.getX(1));
                p2.y = (int) (motionEvent.getY(1));

                p3.x = (int) (motionEvent.getX(2));
                p3.y = (int) (motionEvent.getY(2));

                form = new Form(p1, p2, p3, p4);
                if(form.isVerticalLines3Points()) {

                    backGround = new BackGround();

                    backGround.execute(form.getFormType());
                    return true;


                } else if(form.isHorizantalLines3points()) {

                    backGround = new BackGround();

                    backGround.execute(form.getFormType());

                    return true;

                }

            }
            if(motionEvent.getPointerCount() == 4 && stop==false) {

                p1.x = (int) (motionEvent.getX(0));
                p1.y = (int) (motionEvent.getY(0));

                p2.x = (int) (motionEvent.getX(1));
                p2.y = (int) (motionEvent.getY(1));

                p3.x = (int) (motionEvent.getX(2));
                p3.y = (int) (motionEvent.getY(2));

                p4.x = (int) (motionEvent.getX(3));
                p4.y = (int) (motionEvent.getY(3));

                form = new Form(p1, p2, p3, p4);
                if(form.isRectangle()) {

                    backGround = new BackGround();

                    backGround.execute(form.getFormType());
                    return true;

                }
                else if(form.isSquare()) {

                    backGround = new BackGround();

                    backGround.execute(form.getFormType());
                    return true;

                }
            }
            return false;
        }
    }
    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {

            try{

                String dote_value = (String)arg0[0];

                String link="https://mrstruct.000webhostapp.com/log.php";

                String data  = URLEncoder.encode("dot_value", "UTF-8") + "=" +

                        URLEncoder.encode(dote_value, "UTF-8");

                URL url = new URL(link);

                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data );

                wr.flush();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();

                String line = null;

                // Read Server Response

                while((line = reader.readLine()) != null) {

                    sb.append(line);

                    break;

                }

                return sb.toString();

            } catch(Exception e) {

                return "Exception: " + e.getMessage();

            }
        }

        @Override
        protected void onPostExecute(final String res) {

            if (res != "<br />" && stop == false) {

                Vibrator vs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                // Vibrate for 500 milliseconds

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    vs.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));
                   // stop = true;
                } else {

                    //deprecated in API 26
                    vs.vibrate(1000);

                    Log.d("https://www.cestBon.com", res);
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(res));
                    startActivity(browserIntent);
                    //stop = true;
                }
            }
            stop = true;
            return ;
        }
    }
}
