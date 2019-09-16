package com.wildan.moviecatalogue.ui.main.tv

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.adapter.ListTvAdapter
import com.wildan.moviecatalogue.adapter.TvShowAdapterListener
import com.wildan.moviecatalogue.model.tv.TvShowResponse
import com.wildan.moviecatalogue.model.tv.TvShowResult
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.ui.main.detail.DetailMovieActivity
import com.wildan.moviecatalogue.utils.UtilsConstant.API_KEY
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.utils.UtilsConstant.STATE_SAVED
import com.wildan.moviecatalogue.view.TvShowView
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.properties.Delegates

class TvShowFragment : Fragment(), TvShowView.View, TvShowAdapterListener {

    private lateinit var rvTvShow: RecyclerView

    private lateinit var tvViewModel: TvShowViewModel
    private var adapter by Delegates.notNull<ListTvAdapter>()
    private var page: Int = 1
    private var totalPage: Int? = null
    private var isLoading by Delegates.notNull<Boolean>()
    private lateinit var swipeRefresh: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvTvShow = view.findViewById(R.id.rv_movie)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)

        prepare()
        scrollListener()

        if (savedInstanceState == null) {
            showListTvShow()
        }

        swipeRefresh.setOnRefreshListener {
            showListTvShow()
        }

    }

    override fun getTvShowData(tv: TvShowResponse) {
        totalPage = tv.totalPages
    }

    override fun showProgressBar() {
        isLoading = true
        swipeRefresh.isRefreshing = true
    }

    override fun hideProgressBar() {
        isLoading = false
        swipeRefresh.isRefreshing = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(STATE_SAVED, "saved")
    }

    override fun handleError(t: Throwable?) {
        if (ConnectivityStatus.isConnected(context)) {
            when (t) {
                is HttpException -> // non 200 error codes
                    NetworkError.handleError(t, context)
                is SocketTimeoutException -> // connection errors
                    Toast.makeText(
                        context,
                        resources.getString(R.string.timeout),
                        Toast.LENGTH_SHORT
                    ).show()
            }
        } else {
            Toast.makeText(context, resources.getString(R.string.no_internet), Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onTvClickListener(tvId: String) {
        val intent = Intent(context, DetailMovieActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, tvId)
        startActivity(intent)
    }

    private fun prepare() {
        AndroidNetworking.initialize(context)

        tvViewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)
        tvViewModel.getTvShows().observe(this, getTvShow)

        adapter = ListTvAdapter(this)
        adapter.notifyDataSetChanged()

        rvTvShow.setHasFixedSize(true)
        rvTvShow.layoutManager = LinearLayoutManager(context)

        rvTvShow.adapter = adapter
    }

    private fun scrollListener() {
        rvTvShow.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
                val countItem = linearLayoutManager.itemCount
                val lastVisiblePosition =
                    linearLayoutManager.findLastCompletelyVisibleItemPosition()
                val isLastPosition = countItem.minus(1) == lastVisiblePosition
                if (!isLoading && isLastPosition && page < totalPage ?: 0) {
                    page = page.plus(1)
                    showListTvShow()
                }
            }
        })
    }

    private fun showListTvShow() {
        tvViewModel.setTvShow(API_KEY, page = page, view = this)
    }

    private val getTvShow = Observer<ArrayList<TvShowResult>> { movieItems ->
        if (movieItems != null) {
            if (page == 1) {
                adapter.setData(movieItems)
            } else {
                adapter.refreshAdapter(movieItems)
            }
        }
    }
}