package edu.upc.dsa;
import com.google.gson.annotations.JsonAdapter;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TracksService {

    @GET("tracks/")
    Call<List<Track>> listTracks();

    @POST("tracks/")
    Call<Track> AddTrack(@Body Track track);

    @PUT("tracks/")
    Call<Track>UpdateTrack(@Body Track track);

    @DELETE("tracks/{id}")
    Call<Track>DeleteTrack(@Path("id") String id);
}
