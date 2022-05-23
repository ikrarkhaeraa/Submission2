package com.example.mysubmissiontwo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysubmissiontwo.databinding.ItemRowProfileBinding


class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.ListViewHolder>() {

    private val listUser = ArrayList<FollowingResponseItem>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowProfileBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FollowingAdapter.ListViewHolder, position: Int) {
        val data = listUser[position]
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .circleCrop()
            .into(holder.binding.rvImage)
        holder.binding.tvNamauser.text = data.login
        holder.binding.tvItemDescription.text = data.subscriptionsUrl
    }

    override fun getItemCount() = listUser.size

    class ListViewHolder(var binding: ItemRowProfileBinding) : RecyclerView.ViewHolder(binding.root)

    fun setData(users: List<FollowingResponseItem>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }
}
