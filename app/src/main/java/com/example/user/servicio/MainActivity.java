package com.example.user.servicio;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class    MainActivity extends AppCompatActivity {
    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boton = (Button) findViewById(R.id.boton);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new consumir().execute();
            }
        });

    }

    public class consumir extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.
            Log.e("Fx", "FerX");

            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("http://192.168.1.18/bme/configuracion/");
                urlConnection = (HttpURLConnection) url.openConnection();

                urlConnection.setDoOutput(true);
                urlConnection.setChunkedStreamingMode(0);

                OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
                writeStream(out);

                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                String s = readStream(in);
                Log.e("Fx", s);


            } catch (Exception e) {
                Log.e("Fx", String.valueOf(e));
            } finally {
                urlConnection.disconnect();
            }

            try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {

            if (success) {
                Intent intent = new Intent(getApplicationContext(), NuevaActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();
            }
        }

        @Override
        protected void onCancelled() {

        }

        private String readStream(InputStream in) {
            try {
                ByteArrayOutputStream bo = new ByteArrayOutputStream();
                int i = in.read();
                while (i != -1) {
                    bo.write(i);
                    i = in.read();
                }
                return bo.toString();
            } catch (IOException e) {
                return "";
            }
        }

        private void writeStream(OutputStream out) throws IOException {
            String output = "Hello world";

            out.write(output.getBytes());
            out.flush();
        }
    }
}
