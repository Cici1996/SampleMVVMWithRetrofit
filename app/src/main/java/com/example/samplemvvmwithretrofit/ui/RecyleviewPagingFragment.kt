package com.example.samplemvvmwithretrofit.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.adapter.MovieWithPagingAdapter
import com.example.samplemvvmwithretrofit.databinding.FragmentGosoBinding
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Resource
import com.example.samplemvvmwithretrofit.viewmodel.MoviePagingViewModel
import com.example.samplemvvmwithretrofit.viewmodel.ViewModelProviderFactory
import kotlinx.android.synthetic.main.fragment_recyleview_paging.*

class RecyleviewPagingFragment : Fragment() {

    private lateinit var viewModel: MoviePagingViewModel
    lateinit var movieAdapter: MovieWithPagingAdapter
    private lateinit var binding: FragmentGosoBinding
    lateinit var progress : LinearLayout

    private var isLoading: Boolean = false
    private var isLastPage: Boolean = false
    private var currentPage: Int = 1
    private var isScrolling: Boolean = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val rootView =  inflater.inflate(R.layout.fragment_goso, container, false)
        binding = FragmentGosoBinding.inflate(layoutInflater)
        progress = rootView.findViewById(R.id.progress) as LinearLayout
        return  rootView;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            init()
        }
    }

    private fun init() {
        setupViewModel()
        rvMovies.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount

                val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
                val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
                val isNotAtBeginning = firstVisibleItemPosition >= 0
                val isTotalMoreThanVisible = totalItemCount >= 20
                val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                        isTotalMoreThanVisible && isScrolling
                if(shouldPaginate) {
                    if (visibleItemCount + firstVisibleItemPosition >= totalItemCount && firstVisibleItemPosition >= 0) {
                        currentPage += 1
                        getMovies(currentPage)
                    }

                    isScrolling = false
                } else {
                    recyclerView.setPadding(0, 0, 0, 0)
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                    isScrolling = true
                }
            }
        })
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(activity!!.application, repository)
        viewModel = ViewModelProvider(this, factory).get(MoviePagingViewModel::class.java)
        getMovies(currentPage)
        setupRecyclerView();
    }

    private fun getMovies(page:Int) {
        viewModel.getData(page);
        viewModel.data.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { response ->
                        val datas = response.results
                        movieAdapter.differ.submitList(datas)
                        rvMovies.post{
                            movieAdapter.notifyDataSetChanged()
                        }
                    }
                }

                is Resource.Error -> {
                    response.message?.let { message ->

                    }
                }

                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun hideProgressBar() {
        progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        progress.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        movieAdapter = MovieWithPagingAdapter()
        rvMovies.apply {
            adapter  = movieAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }
}