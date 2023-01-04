package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.Toast;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddTracksAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_tracks);
        Button ok = (Button) findViewById(R.id.okBtn);
        EditText title = (EditText) findViewById(R.id.textTitle);
        EditText singer = (EditText) findViewById(R.id.textSinger);
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TracksService service = retrofit.create(TracksService.class);
        ok.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((singer.getText()==null)||(title.getText()==null))
                    Toast.makeText(getApplicationContext(), "Error.Campos vacíos.", Toast.LENGTH_LONG).show();
                else {
                    Track t = new Track();
                    t.singer = singer.getText().toString();
                    t.title = title.getText().toString();
                    service.AddTrack(t).enqueue(new Callback<Track>() {

                        @Override
                        public void onResponse(Call<Track> call, Response<Track> response) {
                            if (response.isSuccessful())
                                Toast.makeText(getApplicationContext(), "Enviado", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onFailure(Call<Track> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "Fallo al enviar", Toast.LENGTH_LONG).show();
                        }
                    });
                }
                Intent intent = new Intent (AddTracksAct.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
