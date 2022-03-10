package com.example.okhttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetURL extends AppCompatActivity {


    Button btnCheckUrl;
    EditText edtUrl;
    TextView txtUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_url);


        AnhXa();

        btnCheckUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new GetUrl().execute("https://" + edtUrl.getText().toString().trim());
            }
        });
    }

    private void AnhXa() {
        btnCheckUrl = (Button) findViewById(R.id.buttoncheck);
        edtUrl = (EditText) findViewById(R.id.edittexturl);
        txtUrl = (TextView) findViewById(R.id.textviewurl);
    }

    class GetUrl extends AsyncTask<String,String,String> {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().
                connectTimeout(15, TimeUnit.SECONDS).
                writeTimeout(10,TimeUnit.SECONDS).
                readTimeout(15,TimeUnit.SECONDS).retryOnConnectionFailure(true).
                build();

        @Override
        protected String doInBackground(String... strings) {
//            String url = "https://jsonplaceholder.typicode.com/photos";

            Request.Builder builder = new Request.Builder();
            builder.url(strings[0]);

            Request request = builder.build();

            try {
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (!s.equals("")){
                txtUrl.append(s);
            } else {
                Toast.makeText(GetURL.this, "đường dẫn bị lỗi", Toast.LENGTH_SHORT).show();
            }
            super.onPostExecute(s);
        }
    }
}

