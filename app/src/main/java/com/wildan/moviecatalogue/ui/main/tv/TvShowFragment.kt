package com.wildan.moviecatalogue.ui.main.tv

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.wildan.moviecatalogue.R
import com.wildan.moviecatalogue.adapter.TvShowAdapter
import kotlin.properties.Delegates

class TvShowFragment : Fragment() {

    private lateinit var viewModel: TvShowViewModel
    private lateinit var rvTvShowList: RecyclerView
    private var adapter by Delegates.notNull<TvShowAdapter>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvTvShowList = view.findViewById(R.id.rv_movie)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (activity != null) {
            viewModel = ViewModelProviders.of(this).get(TvShowViewModel::class.java)

            adapter = TvShowAdapter()
            viewModel.getTvShowList()?.let { adapter.setData(it) }
            rvTvShowList.layoutManager = LinearLayoutManager(context)
            rvTvShowList.setHasFixedSize(true)
            rvTvShowList.adapter = adapter
        }
    }
}