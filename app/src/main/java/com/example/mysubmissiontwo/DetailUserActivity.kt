package com.example.mysubmissiontwo

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmissiontwo.databinding.ActivityDetailUserBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_followers,
            R.string.tab_following
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Detail User"

        val data = intent.getParcelableExtra<ItemsItem>("DATA")
        var username = data?.login

        if (username != null) {
            findDetailData(username)
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(data?.login ?: "", this)
        Log.d("tesData", "message : ${data?.login}")
        //sectionsPagerAdapter.username = tvName.toString()
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun findDetailData(username:String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getDetail(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Glide.with(this@DetailUserActivity)
                         .load(responseBody.avatarUrl)
                         .circleCrop()
                         .into(binding.rvImage)
                        binding.tvName.text = responseBody.name
                        binding.tvUsername.text = responseBody.login
                        binding.tvCompany.text = responseBody.company
                        binding.tvRepo.text = responseBody.publicRepos.toString()
                        binding.tvFollowers.text = responseBody.followers.toString()
                        binding.tvFollowing.text = responseBody.following.toString()
                        binding.tvLocation.text = responseBody.location
                        binding.FollowersTitle.text = "Followers"
                        binding.FollowingTitle.text = " Following"
                        binding.RepoTitle.text = "Repository"
                        binding.LocationTitle.text = "Location"

                        }
                } else {
                    Log.e("tes", "onResponse: ${response}")
                }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t:  Throwable) {
                showLoading(false)
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

}