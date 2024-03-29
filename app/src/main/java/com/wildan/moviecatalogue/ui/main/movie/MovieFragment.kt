package com.wildan.moviecatalogue.ui.main.movie

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.adapter.MovieAdapter
import kotlin.properties.Delegates

class MovieFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    private lateinit var rvMovieList: RecyclerView
    private var adapter by Delegates.notNull<MovieAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMovieList = view.findViewById(R.id.rv_movie)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)

            adapter = MovieAdapter()
            viewModel.getMovieList()?.let { adapter.setData(it) }
            rvMovieList.layoutManager = LinearLayoutManager(context)
            rvMovieList.setHasFixedSize(true)
            rvMovieList.adapter = adapter
        }
    }
}