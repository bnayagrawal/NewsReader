package xyz.bnayagrawal.android.newsreader.data.remote;

import android.arch.lifecycle.LiveData;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.QueryMap;
import xyz.bnayagrawal.android.newsreader.data.model.Everything;
import xyz.bnayagrawal.android.newsreader.data.model.Sources;
import xyz.bnayagrawal.android.newsreader.data.model.TopHeadlines;

public interface NewsService {

    @GET(NewsApiServer.ENDPOINT_TOP_HEADLINES)
    LiveData<ApiResponse<TopHeadlines>> getTopHeadlines(@Header("X-Api-Key") String apiKey, @QueryMap Map<String, String> options);

    @GET(NewsApiServer.ENDPOINT_EVERYTHING)
    LiveData<ApiResponse<Everything>> getEverything(@Header("X-Api-Key") String apiKey, @QueryMap Map<String, String> options);

    @GET(NewsApiServer.ENDPOINT_SOURCES)
    Call<Sources> getSources(@Header("X-Api-Key") String apiKey, @QueryMap Map<String, String> options);
}
