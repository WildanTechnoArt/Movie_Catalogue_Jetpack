package com.wildan.moviecatalogue.repository;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PagedList;

import com.wildan.moviecatalogue.data.source.local.LocalRepository;
import com.wildan.moviecatalogue.data.source.local.entity.DetailMovieEntity;
import com.wildan.moviecatalogue.data.source.local.entity.DetailTvShowEntity;
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteMovieEntity;
import com.wildan.moviecatalogue.data.source.local.entity.FavoriteTvShowEntity;
import com.wildan.moviecatalogue.data.source.local.entity.MovieEntity;
import com.wildan.moviecatalogue.data.source.local.entity.TvShowEntity;
import com.wildan.moviecatalogue.data.source.remote.RemoteRepository;
import com.wildan.moviecatalogue.utils.FakeDataDummy;
import com.wildan.moviecatalogue.utils.InstantAppExecutors;
import com.wildan.moviecatalogue.utils.LiveDataTestUtil;
import com.wildan.moviecatalogue.utils.PagedListUtil;
import com.wildan.moviecatalogue.view.DetailMovieView;
import com.wildan.moviecatalogue.view.DetailTvShowView;
import com.wildan.moviecatalogue.view.MovieView;
import com.wildan.moviecatalogue.view.TvShowView;
import com.wildan.moviecatalogue.vo.Resource;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CatalogueRepositoryTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private final RemoteRepository remote = mock(RemoteRepository.class);
    private final LocalRepository local = mock(LocalRepository.class);
    private final InstantAppExecutors executors = mock(InstantAppExecutors.class);
    private final FakeDataRepository repository = new FakeDataRepository(local, remote, executors);

    private final List<MovieEntity> movieList = FakeDataDummy.INSTANCE.getMovieList();
    private final List<FavoriteMovieEntity> favoriteMovieList = FakeDataDummy.INSTANCE.getFavMovieList();
    private final List<TvShowEntity> tvShowList = FakeDataDummy.INSTANCE.getTvShowList();
    private final List<FavoriteTvShowEntity> favoriteTvShowList = FakeDataDummy.INSTANCE.getFavTvShowList();

    @Mock
    private MovieView.View view;

    @Mock
    private TvShowView.View view2;

    @Mock
    private DetailMovieView.View view3;

    @Mock
    private DetailTvShowView.View view4;

    @Mock
    private DataSource.Factory<Integer, FavoriteMovieEntity> dataSourceFactory;

    @Mock
    private DataSource.Factory<Integer, FavoriteTvShowEntity> dataSourceFactory2;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getAllMovie() {
        MutableLiveData<List<MovieEntity>> dummyMovie = new MutableLiveData<>();
        dummyMovie.setValue(FakeDataDummy.INSTANCE.getMovieList());

        when(local.getMovies()).thenReturn(dummyMovie);

        Resource<List<MovieEntity>> result = LiveDataTestUtil.getValue(repository.getAllMovie(view));

        verify(local).getMovies();
        assertNotNull(result.getData());

        assertNotNull(result);
        assertEquals(movieList.size(), result.getData().size());
    }

    @Test
    public void getAllTvShow() {
        MutableLiveData<List<TvShowEntity>> dummyTvShow = new MutableLiveData<>();
        dummyTvShow.setValue(FakeDataDummy.INSTANCE.getTvShowList());

        when(local.getTvShows()).thenReturn(dummyTvShow);

        Resource<List<TvShowEntity>> result = LiveDataTestUtil.getValue(repository.getAllTvShow(view2));

        verify(local).getTvShows();
        assertNotNull(result.getData());

        assertNotNull(result);
        assertEquals(movieList.size(), result.getData().size());
    }

    @Test
    public void getDetailMovie() {
        String movieId = "429617";

        MutableLiveData<DetailMovieEntity> dummyMovie = new MutableLiveData<>();
        dummyMovie.setValue(FakeDataDummy.INSTANCE.getDetailMovie());

        when(local.getMovieById(movieId)).thenReturn(dummyMovie);

        Resource<DetailMovieEntity> result = LiveDataTestUtil.getValue(repository.getDetailMovie(movieId, view3));

        verify(local).getMovieById(movieId);
        assertNotNull(result.getData());

        assertNotNull(result);
        assertEquals(movieList.get(0).getMovieId(), result.getData().getMovieId());
        assertEquals(movieList.get(0).getMovieBackdrop(), result.getData().getMovieBackdrop());
        assertEquals(movieList.get(0).getMovieOverview(), result.getData().getMovieOverview());
        assertEquals(movieList.get(0).getMovieName(), result.getData().getMovieName());
        assertEquals(movieList.get(0).getMoviePopularity(), result.getData().getMoviePopularity());
        assertEquals(movieList.get(0).getMoviePoster(), result.getData().getMoviePoster());
        assertEquals(movieList.get(0).getMovieDate(), result.getData().getMovieDate());
        assertEquals(movieList.get(0).getMovieRating(), result.getData().getMovieRating());
    }

    @Test
    public void getDetailTvShow() {
        String tvId = "1412";

        MutableLiveData<DetailTvShowEntity> dummyTvShow = new MutableLiveData<>();
        dummyTvShow.setValue(FakeDataDummy.INSTANCE.getDetailTvShow());

        when(local.getTvShowById(tvId)).thenReturn(dummyTvShow);

        Resource<DetailTvShowEntity> result = LiveDataTestUtil.getValue(repository.getDetailTvShow(tvId, view4));

        verify(local).getTvShowById(tvId);
        assertNotNull(result.getData());

        assertNotNull(result);
        assertEquals(tvShowList.get(0).getTvId(), result.getData().getTvId());
        assertEquals(tvShowList.get(0).getTvBackdrop(), result.getData().getTvBackdrop());
        assertEquals(tvShowList.get(0).getTvOverview(), result.getData().getTvOverview());
        assertEquals(tvShowList.get(0).getTvName(), result.getData().getTvName());
        assertEquals(tvShowList.get(0).getTvPopularity(), result.getData().getTvPopularity());
        assertEquals(tvShowList.get(0).getTvPoster(), result.getData().getTvPoster());
        assertEquals(tvShowList.get(0).getTvDate(), result.getData().getTvDate());
        assertEquals(tvShowList.get(0).getTvRating(), result.getData().getTvRating());
    }

    @Test
    public void getMovieAsPaged() {
        when(local.getMovieAsPaged()).thenReturn(dataSourceFactory);
        repository.getMovieAsPaged();
        Resource<PagedList<FavoriteMovieEntity>> result = Resource.Companion.success(
                PagedListUtil.mockPagedList(favoriteMovieList));

        verify(local).getMovieAsPaged();
        assertNotNull(result.getData());
        assertEquals(favoriteMovieList.size(), result.getData().size());
    }

    @Test
    public void getTvShowAsPaged() {
        when(local.getTvShowAsPaged()).thenReturn(dataSourceFactory2);
        repository.getTvShowAsPaged();
        Resource<PagedList<FavoriteTvShowEntity>> result = Resource.Companion.success(
                PagedListUtil.mockPagedList(favoriteTvShowList));

        verify(local).getTvShowAsPaged();
        assertNotNull(result.getData());
        assertEquals(favoriteTvShowList.size(), result.getData().size());
    }
}