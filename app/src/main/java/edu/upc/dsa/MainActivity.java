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

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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
            Call<List<Track>> tracks = service.listTracks();
            tracks.enqueue(new Callback<List<Track>>() {

                @Override
                public void onResponse(Call<List<Track>> call, Response<List<Track>> response) {
                    List<Track> tracks = response.body();
                    List<String> input = new ArrayList<>();
                    for(Track r:tracks){
                        input.add(r.title+","+r.singer+"/"+r.id);
                    }
                    mAdapter = new MyAdapter(input);
                    recyclerView.setAdapter(mAdapter);
                }

                @Override
                public void onFailure(Call<List<Track>> call, Throwable t) { }
            });
            Button bt = (Button) findViewById(R.id.button);

            bt.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    Intent intent = new Intent (MainActivity.this, AddTracksAct.class);
                    startActivity(intent);
                }
            });
    }
    }


