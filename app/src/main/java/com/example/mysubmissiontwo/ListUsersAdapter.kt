package com.example.mysubmissiontwo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mysubmissiontwo.databinding.ItemRowProfileBinding

class ListUsersAdapter : RecyclerView.Adapter<ListUsersAdapter.ListViewHolder>() {

    private val listUser = ArrayList<ItemsItem>()

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ListViewHolder {
        val binding = ItemRowProfileBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listUser[position])
        holder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(listUser[holder.adapterPosition])
        }
    }

    override fun getItemCount() = listUser.size


    inner class ListViewHolder(var binding: ItemRowProfileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(userData:ItemsItem){
            binding.apply{
                Glide.with(itemView)
                    .load(userData.avatarUrl)
                    .circleCrop()
                    .into(binding.rvImage)
                binding.tvNamauser.text = userData.login
                binding. tvItemDescription.text = userData.subscriptionsUrl
            }
        }
    }

    fun setData(users: List<ItemsItem>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    interface OnItemClickCallback {
        fun onItemClicked(userData: ItemsItem)
    }
}