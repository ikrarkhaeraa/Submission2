package com.example.mysubmissiontwo

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiontwo.databinding.FragmentFollowersBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersFragment() : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private val adapter = FollowersAdapter()

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
        binding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvFollowers.layoutManager = LinearLayoutManager(context)
        binding.rvFollowers.adapter = adapter
        arguments?. let {
            findFollowersUser(it.getString("username")?: "")
            Log.d("tesUsername", "hasil: ${it.getString("username")}")
        }
    }

    private fun findFollowersUser(username:String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getFollowers(username)
        client.enqueue(object: Callback<List<FollowersResponseItem>> {
            override fun onResponse(
                call: Call<List<FollowersResponseItem>>,
                response: Response <List<FollowersResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        setListFollowers(responseBody)
                        if (responseBody.isEmpty()) Toast.makeText(context, "Tidak ada Followers", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("tesFollowers", "onResponse: ${response}")
                }
            }
            override fun onFailure(call: Call<List<FollowersResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun setListFollowers(listFollowersResponse: List<FollowersResponseItem>) {
        adapter.setData(listFollowersResponse)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

}


