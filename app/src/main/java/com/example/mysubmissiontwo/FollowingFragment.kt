package com.example.mysubmissiontwo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiontwo.databinding.FragmentFollowingBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingFragment() : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private val adapter = FollowingAdapter()

    companion object {
        private val TAG = "FollowersFragment"
        fun create(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val args = Bundle()
            args.putString("username", username)
            Log.d("tesUsername", "hasil: $username")
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowing.layoutManager = LinearLayoutManager(context)
        binding.rvFollowing.adapter = adapter
        arguments?. let {
            findFollowingUser(it.getString("username")?: "")
            Log.d("tesUsername", "hasil: ${it.getString("username")}")
        }
    }

    private fun findFollowingUser(username:String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object: Callback<List<FollowingResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowingResponseItem>>,
                response: Response <List<FollowingResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setListFollowing(responseBody)
                        if (responseBody.isEmpty()) Toast.makeText(context, "Tidak ada Following", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("tesFollowers", "onResponse: ${response}")
                }
            }
            override fun onFailure(call: Call<List<FollowingResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setListFollowing(listFollowingResponse: List<FollowingResponseItem>) {
            adapter.setData(listFollowingResponse)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

}

