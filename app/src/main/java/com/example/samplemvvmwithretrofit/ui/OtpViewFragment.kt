package com.example.samplemvvmwithretrofit.ui

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.samplemvvmwithretrofit.R
import kotlinx.android.synthetic.main.fragment_otp_view.*


class OtpViewFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var timer: CountDownTimer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.let {
            timer = object: CountDownTimer(300000, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val durasi = (millisUntilFinished / 1000)
                    val formatTime = String.format("%02d:%02d",(durasi % 3600) / 60, (durasi % 60));

                    countdown.text = formatTime
                }

                override fun onFinish() {
                    countdown.text = "Finished"
                }
            }
            timer?.start()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_otp_view, container, false)
    }
}