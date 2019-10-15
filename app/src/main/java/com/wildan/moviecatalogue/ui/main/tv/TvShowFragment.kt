package com.wildan.moviecatalogue.ui.main.tv

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.androidnetworking.AndroidNetworking
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.adapter.ListTvAdapter
import com.wildan.moviecatalogue.adapter.TvShowAdapterListener
import com.wildan.moviecatalogue.data.source.remote.response.tv.TvShowResponse
import com.wildan.moviecatalogue.network.ConnectivityStatus
import com.wildan.moviecatalogue.network.NetworkError
import com.wildan.moviecatalogue.ui.main.detail.tv.DetailTvShowActivity
import com.wildan.moviecatalogue.utils.UtilsConstant.MOVIE_EXTRA
import com.wildan.moviecatalogue.view.TvShowView
import com.wildan.moviecatalogue.viewmodel.ViewModelFactory
import com.wildan.moviecatalogue.vo.Status
import retrofit2.HttpException
import java.net.SocketTimeoutException
import kotlin.properties.Delegates

class TvShowFragment : Fragment(), TvShowView.View, TvShowAdapterListener {

    private lateinit var rvTvShow: RecyclerView
    private var tvViewModel: TvShowViewModel? = null
    private var adapter by Delegates.notNull<ListTvAdapter>()
    private var totalPage: Int? = null
    private var isLoading = false
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

        AndroidNetworking.initialize(context)

        swipeRefresh.setOnRefreshListener {
            loadData()
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

    @RequiresApi(Build.VERSION_CODES.M)
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
        val intent = Intent(context, DetailTvShowActivity::class.java)
        intent.putExtra(MOVIE_EXTRA, tvId)
        startActivity(intent)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            tvViewModel = obtainViewModel(activity)
            adapter = ListTvAdapter(this)
            tvViewModel?.setUsername("Movie Catalogue")

            loadData()

            rvTvShow.setHasFixedSize(true)
            rvTvShow.layoutManager = LinearLayoutManager(context)
            rvTvShow.adapter = adapter
        }
    }

    private fun loadData() {
        tvViewModel?.getTvShow(view = this)?.observe(this, Observer {
            if (it != null) {
                when (it.status) {
                    Status.LOADING -> {
                        isLoading = true
                        swipeRefresh.isRefreshing = true
                    }
                    Status.SUCCESS -> {
                        isLoading = false
                        swipeRefresh.isRefreshing = false
                        it.data?.let { it1 -> adapter.setData(it1) }
                    }
                    Status.ERROR -> {
                        isLoading = false
                        swipeRefresh.isRefreshing = false
                    }
                }
            }
        })
    }

    companion object {
        fun obtainViewModel(activity: FragmentActivity?): TvShowViewModel? {
            val factory = activity?.application?.let { ViewModelFactory.getInstance(it) }
            return activity?.let {
                ViewModelProviders.of(it, factory).get(TvShowViewModel::class.java)
            }
        }
    }
}