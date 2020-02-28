package com.example.videoplayer.repo;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

public class RepoRepository {

    private final RepoService repoService;

    @Inject
    public RepoRepository(RepoService repoService) {
        this.repoService = repoService;
    }
    
    public Observable<Response> getVideoRepo(){
        return repoService.getVideo();
    }

//    public Single<List<Repo>> getRepositories() {
//        return repoService.getRepositories();
//    }
//
//    public Single<Repo> getRepo(String owner, String name) {
//        return repoService.getRepo(owner, name);
//    }
}
