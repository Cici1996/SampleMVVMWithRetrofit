package com.example.samplemvvmwithretrofit.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.samplemvvmwithretrofit.R
import com.example.samplemvvmwithretrofit.databinding.FragmentFormUserBinding
import com.example.samplemvvmwithretrofit.model.DataUserRequest
import com.example.samplemvvmwithretrofit.repository.AppRepository
import com.example.samplemvvmwithretrofit.utils.Resource
import com.example.samplemvvmwithretrofit.viewmodel.MovieViewModel
import com.example.samplemvvmwithretrofit.viewmodel.UserViewModel
import com.example.samplemvvmwithretrofit.viewmodel.ViewModelProviderFactory
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_form_user.*

class FormUserFragment : Fragment() {

    private lateinit var binding: FragmentFormUserBinding
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_form_user, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            setupViewModel()

            btnSave.setOnClickListener{

                val name = txtName.text.toString()
                val job = txtJob.text.toString()

                val body = DataUserRequest(
                    name,
                    job
                )

                viewModel.loginUser(body)
                viewModel.loginResponse.observe(this, Observer { event ->
                    event.getContentIfNotHandled()?.let { response ->
                        when (response) {
                            is Resource.Success -> {
                                hideProgressBar()
                                response.data?.let { loginResponse ->
                                    Log.i("ResponseData",loginResponse.name)
                                    Toast.makeText(activity,loginResponse.name,Toast.LENGTH_LONG).show()
                                }
                            }

                            is Resource.Error -> {
                                response.message?.let { message ->
                                    hideProgressBar()
                                    //progress.errorSnack(message, Snackbar.LENGTH_LONG)
                                }
                            }

                            is Resource.Loading -> {
                                showProgressBar()
                            }
                        }
                    }
                })

            }
        }
    }

    private fun setupViewModel() {
        val repository = AppRepository()
        val factory = ViewModelProviderFactory(activity!!.application, repository)
        viewModel = ViewModelProvider(this, factory).get(UserViewModel::class.java)
    }

    private fun hideProgressBar() {
        progress.visibility = View.GONE
    }

    private fun showProgressBar() {
        progress.visibility = View.VISIBLE
    }
}