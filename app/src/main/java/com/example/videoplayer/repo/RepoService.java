package com.example.videoplayer.repo;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;

public interface RepoService {
    
    @GET
    @Streaming
    Observable<Response> getVideo();
    
}
