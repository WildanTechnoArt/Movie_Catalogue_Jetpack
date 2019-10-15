package com.wildan.moviecatalogue.data.source;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.wildan.moviecatalogue.data.source.local.LocalRepository;
import com.wildan.moviecatalogue.data.source.local.entity.DetailMovieEntity;
import com.wildan.moviecatalogue.data.source.local.entity.DetailTvShowEntity;
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity;
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity;
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity;
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.wildan.moviecatalogue.data.source.remote.ApiResponse;
import com.wildan.moviecatalogue.data.source.remote.NetworkBoundResource;
import com.wildan.moviecatalogue.data.source.remote.RemoteRepository;
import com.wildan.moviecatalogue.data.source.remote.response.Genre;
import com.wildan.moviecatalogue.data.source.remote.response.movie.DetailMovieResponse;
import com.wildan.moviecatalogue.data.source.remote.response.movie.MovieResult;
import com.wildan.moviecatalogue.data.source.remote.response.tv.DetailTvShowResponse;
import com.wildan.moviecatalogue.data.source.remote.response.tv.TvShowResult;
import com.wildan.moviecatalogue.utils.AppExecutors;
import com.wildan.moviecatalogue.view.DetailMovieView;
import com.wildan.moviecatalogue.view.DetailTvShowView;
import com.wildan.moviecatalogue.view.MovieView;
import com.wildan.moviecatalogue.view.TvShowView;
import com.wildan.moviecatalogue.vo.Resource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CatalogueRepository implements CatalogueDataSource {

    private volatile static CatalogueRepository INSTANCE = null;

    private final RemoteRepository remoteRepository;
    private final LocalRepository localRepository;
    private final AppExecutors appExecutors;

    private CatalogueRepository(@NonNull LocalRepository localRepository,
                                @NonNull RemoteRepository remoteRepository, AppExecutors appExecutors) {
        this.localRepository = localRepository;
        this.remoteRepository = remoteRepository;
        this.appExecutors = appExecutors;
    }

    public static CatalogueRepository getInstance(LocalRepository localRepository,
                                                  RemoteRepository remoteData, AppExecutors appExecutors) {
        if (INSTANCE == null) {
            synchronized (CatalogueRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CatalogueRepository(localRepository, remoteData, appExecutors);
                }
            }
        }
        return INSTANCE;
    }

    @NotNull
    @Override
    public LiveData<Resource<List<MovieEntity>>> getAllMovie(@NotNull MovieView.View view) {
        return new NetworkBoundResource<List<MovieEntity>, List<MovieResult>>(appExecutors) {

            @Override
            protected void saveCallResult(List<MovieResult> data) {
                List<MovieEntity> movieEntities = new ArrayList<>();

                for (MovieResult movies : data) {
                    movieEntities.add(
                            new MovieEntity(
                                    Objects.requireNonNull(movies.getId()),
                                    movies.getTitle(),
                                    movies.getReleaseDate(),
                                    movies.getVoteAverage(),
                                    movies.getPosterPath(),
                                    movies.getPopularity(),
                                    movies.getOverview(),
                                    movies.getBackdropPath()
                            )
                    );
                }
                localRepository.insertMovies(movieEntities);
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<List<MovieResult>>> createCall() {
                return remoteRepository.getAllMovie(view);
            }

            @NotNull
            @Override
            protected Boolean shouldFetch(List<MovieEntity> data) {
                return (data == null) || (data.isEmpty());
            }

            @NotNull
            @Override
            protected LiveData<List<MovieEntity>> loadDataFromDB() {
                return localRepository.getMovies();
            }
        }.asLiveData();
    }

    @NotNull
    @Override
    public LiveData<Resource<List<TvShowEntity>>> getAllTvShow(@NotNull TvShowView.View view) {
        return new NetworkBoundResource<List<TvShowEntity>, List<TvShowResult>>(appExecutors) {

            @Override
            protected void saveCallResult(List<TvShowResult> data) {
                List<TvShowEntity> tvShowEntities = new ArrayList<>();

                for (TvShowResult tvShowResult : data) {
                    tvShowEntities.add(
                            new TvShowEntity(
                                    Objects.requireNonNull(tvShowResult.getId()),
                                    tvShowResult.getName(),
                                    tvShowResult.getFirstAirDate(),
                                    tvShowResult.getVoteAverage(),
                                    tvShowResult.getPosterPath(),
                                    tvShowResult.getPopularity(),
                                    tvShowResult.getOverview(),
                                    tvShowResult.getBackdropPath()
                            )
                    );
                }
                localRepository.insertTvShows(tvShowEntities);
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<List<TvShowResult>>> createCall() {
                return remoteRepository.getAllTvShow(view);
            }

            @NotNull
            @Override
            protected Boolean shouldFetch(List<TvShowEntity> data) {
                return (data == null) || (data.isEmpty());
            }

            @NotNull
            @Override
            protected LiveData<List<TvShowEntity>> loadDataFromDB() {
                return localRepository.getTvShows();
            }
        }.asLiveData();
    }

    @NotNull
    @Override
    public LiveData<Resource<DetailMovieEntity>> getDetailMovie(@NotNull String movieId, @NotNull DetailMovieView.View view) {
        return new NetworkBoundResource<DetailMovieEntity, DetailMovieResponse>(appExecutors) {

            @Override
            protected void saveCallResult(DetailMovieResponse data) {

                String genres = "";
                for (Genre genre : data.getGenreList()) {
                    genres += genre.getName() + ", ";
                }

                String genreResult = genres.substring(0, genres.length()-2);

                DetailMovieEntity detailMovieEntity;
                detailMovieEntity = new DetailMovieEntity(
                        Objects.requireNonNull(data.getId()),
                        data.getTitle(),
                        data.getReleaseDate(),
                        data.getVoteAverage(),
                        data.getPosterPath(),
                        data.getPopularity(),
                        data.getOverview(),
                        data.getBackdropPath(),
                        genreResult,
                        data.getRuntime()
                        );
                localRepository.insertDetailMovie(detailMovieEntity);
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<DetailMovieResponse>> createCall() {
                return remoteRepository.getDetailMovie(movieId, view);
            }

            @NotNull
            @Override
            protected Boolean shouldFetch(DetailMovieEntity data) {
                return (data == null);
            }

            @NotNull
            @Override
            protected LiveData<DetailMovieEntity> loadDataFromDB() {
                return localRepository.getMovieById(movieId);
            }
        }.asLiveData();
    }

    @NotNull
    @Override
    public LiveData<Resource<DetailTvShowEntity>> getDetailTvShow(@NotNull String tvId, @NotNull DetailTvShowView.View view) {
        return new NetworkBoundResource<DetailTvShowEntity, DetailTvShowResponse>(appExecutors) {

            @Override
            protected void saveCallResult(DetailTvShowResponse data) {

                String genres = "";
                for (Genre genre : data.getGenreList()) {
                    genres += genre.getName() + ", ";
                }

                String genreResult = genres.substring(0, genres.length()-2);

                DetailTvShowEntity detailTvShowEntity;
                detailTvShowEntity = new DetailTvShowEntity(
                        Objects.requireNonNull(data.getId()),
                        data.getName(),
                        data.getFirstAirDate(),
                        data.getVoteAverage(),
                        data.getPosterPath(),
                        data.getPopularity(),
                        data.getOverview(),
                        data.getBackdropPath(),
                        genreResult
                );
                localRepository.insertDetailTv(detailTvShowEntity);
            }

            @NotNull
            @Override
            protected LiveData<ApiResponse<DetailTvShowResponse>> createCall() {
                return remoteRepository.getDetailTvShow(tvId, view);
            }

            @NotNull
            @Override
            protected Boolean shouldFetch(DetailTvShowEntity data) {
                return (data == null);
            }

            @NotNull
            @Override
            protected LiveData<DetailTvShowEntity> loadDataFromDB() {
                return localRepository.getTvShowById(tvId);
            }
        }.asLiveData();
    }

    @Override
    public void onDestroy() {
        remoteRepository.onDestroy();
    }

    @NotNull
    @Override
    public LiveData<Resource<PagedList<FavoriteMovieEntity>>> getMovieAsPaged() {
        return new NetworkBoundResource<PagedList<FavoriteMovieEntity>, List<MovieResult>>(appExecutors){

            @Override
            protected void saveCallResult(List<MovieResult> data) {

            }

            @Nullable
            @Override
            protected LiveData<ApiResponse<List<MovieResult>>> createCall() {
                return null;
            }

            @NotNull
            @Override
            protected Boolean shouldFetch(PagedList<FavoriteMovieEntity> data) {
                return false;
            }

            @NotNull
            @Override
            protected LiveData<PagedList<FavoriteMovieEntity>> loadDataFromDB() {
                return new LivePagedListBuilder<>(localRepository.getMovieAsPaged()
                        , /* page size */ 10).build();
            }
        }.asLiveData();
    }

    @NotNull
    @Override
    public LiveData<Resource<PagedList<FavoriteTvShowEntity>>> getTvShowAsPaged() {
        return new NetworkBoundResource<PagedList<FavoriteTvShowEntity>, List<TvShowResult>>(appExecutors){

            @Override
            protected void saveCallResult(List<TvShowResult> data) {

            }

            @Nullable
            @Override
            protected LiveData<ApiResponse<List<TvShowResult>>> createCall() {
                return null;
            }

            @NotNull
            @Override
            protected Boolean shouldFetch(PagedList<FavoriteTvShowEntity> data) {
                return false;
            }

            @NotNull
            @Override
            protected LiveData<PagedList<FavoriteTvShowEntity>> loadDataFromDB() {
                return new LivePagedListBuilder<>(localRepository.getTvShowAsPaged()
                        , /* page size */ 10).build();
            }
        }.asLiveData();
    }
}