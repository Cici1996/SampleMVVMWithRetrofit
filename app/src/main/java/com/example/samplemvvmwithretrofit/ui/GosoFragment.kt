package com.example.samplemvvmwithretrofit.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.adapter.MovieAdapter
import com.example.samplemvvmwithretrofit.databinding.FragmentGosoBinding
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Resource
import com.example.samplemvvmwithretrofit.viewmodel.MovieViewModel
import com.example.samplemvvmwithretrofit.viewmodel.ViewModelProviderFactory


class GosoFragment : Fragment() {

    private lateinit var viewModel: MovieViewModel
    lateinit var adapter: MovieAdapter
    private lateinit var binding: FragmentGosoBinding

    lateinit var recyclerView : RecyclerView
    lateinit var progress : LinearLayout


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
        recyclerView = rootView.findViewById(R.id.rvMovies) as RecyclerView
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
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = MovieAdapter()
        setupViewModel()
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(activity!!.application, repository)
        viewModel = ViewModelProvider(this, factory).get(MovieViewModel::class.java)
        getNews()
    }

    private fun getNews() {
        viewModel.data.observe(this, Observer { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { response ->
                        val datas = response.results
                        adapter.differ.submitList(datas)
                        recyclerView.adapter = adapter
                        adapter.notifyDataSetChanged()
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
}