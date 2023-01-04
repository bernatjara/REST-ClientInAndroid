package edu.upc.dsa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class delete_modify_tracks extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_delete_modify_tracks);
        Button editbtn = (Button)findViewById(R.id.EditBtn);
        Button deletebtn = (Button)findViewById(R.id.DeleteBtn);
        EditText editsinger = (EditText)findViewById(R.id.SingerEdit);
        EditText edittitle = (EditText)findViewById(R.id.TitleEdit);

        Track trck= new Track();

        trck.title=getIntent().getExtras().getString("title");
        trck.singer=getIntent().getExtras().getString("singer");
        trck.id=getIntent().getExtras().getString("id");
        editsinger.setText(trck.singer);
        edittitle.setText(trck.title);

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

        editbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((editsinger.getText()==null)&&(edittitle.getText()==null))
                    Toast.makeText(getApplicationContext(), "Error.Campos vacíos.", Toast.LENGTH_LONG).show();
                else if ((editsinger.getText()==null)&&(edittitle.getText()!=null))
                    trck.title=edittitle.getText().toString();
                else if ((editsinger.getText()!=null)&&(edittitle.getText()==null))
                    trck.singer=editsinger.getText().toString();
                else {
                    trck.title=edittitle.getText().toString();
                    trck.singer=editsinger.getText().toString();
                }

                service.UpdateTrack(trck).enqueue(new Callback<Track>() {

                    @Override
                    public void onResponse(Call<Track> call, Response<Track> response) {
                        if(response.isSuccessful())
                            Toast.makeText(getApplicationContext(),"Actualizada",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Track> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Fallo al actualizar", Toast.LENGTH_LONG).show();
                    }
                });
                Intent intent = new Intent(delete_modify_tracks.this, MainActivity.class);
                startActivity(intent);
            }
        });

        deletebtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                service.DeleteTrack(trck.id).enqueue(new Callback<Track>() {

                    @Override
                    public void onResponse(Call<Track> call, Response<Track> response) {
                            Toast.makeText(getApplicationContext(),"Eliminada",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onFailure(Call<Track> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),"Fallo al eliminar",Toast.LENGTH_LONG).show();
                    }
                });
                Intent in = new Intent(delete_modify_tracks.this,MainActivity.class);
                startActivity(in);
            }
        });
    }
}
