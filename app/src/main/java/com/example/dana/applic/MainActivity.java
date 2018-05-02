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
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener {

    private boolean stop = false; // arrete la connexion et la detection des points si il est à vrai

    public Form form;

    private GridView gridView;

    private  BackGround backGround = new BackGround();

    private Point p1 = new Point(1,2),
            p2 = new Point(3,4),
            p3 = new Point(5,6),
            p4 = new Point(7,8);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // creation de l'activite
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main); // recupere la bonne vue

        this.gridView = this.findViewById(R.id.myView);

        this.gridView.setBackgroundColor(Color.WHITE); //la couleur de fond est mise a blanc

        this.stop = false;

        this.gridView.setOnTouchListener(this); //application du listener sur le gridView

    }
    @Override
    /**
     * à l'arrer temporaire de l'activité on relance tout
     */
    protected void onPause() {

        super.onPause();

        MainActivity m = new MainActivity();

        backGround.cancel(true);

        m.stop = false;

    }
    /**
     * à l'arrer  de l'activité on relance tout
     */
    protected void onStop() {

        super.onStop();

        MainActivity m = new MainActivity();

        backGround.cancel(true);

        m.stop = false;

    }

    /**
     *
     * methode du listener
     *
     * teste le nombre de points decte sur l ecran
     *
     * si les points correspondents a une forme connue
     *
     * une connexion vers le servereur est lance
     */
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {

        stop = false;

        //init la forme et les points
        p1 = new Point(1,2);
        p2 = new Point(3,4);
        p3 = new Point(5,6);
        p4 = new Point(7,8);

        form = new Form(p1,p2,p3,p4);

        //si le nombre de points est <2 on arrete tout
        if(motionEvent.getPointerCount() < 2) {

            stop =true;
            return true;
        }

        if(motionEvent.getPointerCount() == 3 && stop == false) { //test le nombre de points detecte

            //init les points
            p1.x = (int) (motionEvent.getX(0));
            p1.y = (int) (motionEvent.getY(0));

            p2.x = (int) (motionEvent.getX(1));
            p2.y = (int) (motionEvent.getY(1));

            p3.x = (int) (motionEvent.getX(2));
            p3.y = (int) (motionEvent.getY(2));

            //init la forme
            form = new Form(p1, p2, p3, p4);

            if(form.isVerticalLines3Points() || form.isHorizantalLines3points()) { //test si la forme existe
                backGround = new BackGround();
                backGround.execute(form.getFormType());
                return true;
            }

        }

        //meme logique que precedemment
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

            if(form.isRectangle() ||form.isSquare() ) {

                backGround = new BackGround();
                backGround.execute(form.getFormType());

                return true;

            }
        }
    /*
        if(motionEvent.getPointerCount() == 2 && stop==false) {

            p1.x = (int) (motionEvent.getX(0));
            p1.y = (int) (motionEvent.getY(0));

            p2.x = (int) (motionEvent.getX(1));
            p2.y = (int) (motionEvent.getY(1));


            form = new Form(p1, p2, p3, p4);
            if(form.isHorizantalLines2points() ||form.isVerticalLines2Points() ) {
                backGround = new BackGround();

                backGround.execute(form.getFormType());
                return true;

            }
        }*/
        return false;

    }

    /**
     * cette classe interne lance une connexion au serveur
     * envoie la forme detecter au serveur
     */
    class BackGround extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... arg0) {

            try{

                /**
                 * on init les points et la forme pour eviter les erreurs
                 */
                p1 = new Point(1,2);
                p2 = new Point(3,4);
                p3 = new Point(5,6);
                p4 = new Point(7,8);

                form = new Form(p1,p2,p3,p4);

                String dote_value = (String)arg0[0]; // recupere la forme detectee

                String link="https://mrstruct.000webhostapp.com/log.php";

                String data  = URLEncoder.encode("dot_value", "UTF-8") + "=" +

                        URLEncoder.encode(dote_value, "UTF-8");

                URL url = new URL(link);

                //lance la connection
                URLConnection conn = url.openConnection();

                conn.setDoOutput(true);

                OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                wr.write( data ); // envoie la donnee au serveur

                wr.flush();

                //recupere la reponse du serveur
                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder sb = new StringBuilder();

                String line = null;

                // lit la reponse du serveur
                while((line = reader.readLine()) != null) { //tant que le serveur envoie une reponse la lire

                    sb.append(line); // la reponse est stocker dans un string puis retourner

                    break;
                }
                conn.setConnectTimeout(1000); //la connection fini aprer 1 second

                return sb.toString();

            } catch(Exception e) {

                return "Exception: " + e.getMessage();

            }
        }

        /**
         *
         * @param res le message envoyé par le serveur
         */
        @Override
        protected void onPostExecute(final String res) {


            if (res != "<br />" && stop == false) {  // test si le message n'est pas vide
                super.onPostExecute(res);

                // fait vibrer le telephone pendant 1 seconde
                Vibrator vs = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                    vs.vibrate(VibrationEffect.createOneShot(1000, VibrationEffect.DEFAULT_AMPLITUDE));

                } else {

                    //desapprouver dans l'api 26
                    vs.vibrate(1000);

                    backGround.cancel(true); //interom la connexion

                    //affiche l'information dans le navigateur
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(res));

                    startActivity(browserIntent);
                }
            }
            return ;
        }
    }
}
