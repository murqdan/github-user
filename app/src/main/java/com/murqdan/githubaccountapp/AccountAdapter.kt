package com.murqdan.githubaccountapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AccountAdapter(private val listAccount: List<ItemsItem>) :
    RecyclerView.Adapter<AccountAdapter.ViewHolder>() {

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setOnItemClickCallback(callback: OnItemClickCallback) {
        onItemClickCallback = callback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(viewGroup.context).inflate(R.layout.item_account, viewGroup, false))

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val account = listAccount[position]
        viewHolder.tvUsernameHome.text = account.login
        viewHolder.tvAccountLinkHome.text = account.htmlUrl
        Glide.with(viewHolder.ivAvatarHome.context)
            .load(account.avatarUrl)
            .into(viewHolder.ivAvatarHome)

        viewHolder.itemView.setOnClickListener {
            onItemClickCallback?.onItemClicked(account)
        }
    }

    override fun getItemCount() = listAccount.size
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ivAvatarHome: ImageView = view.findViewById(R.id.ivAvatarHome)
        val tvUsernameHome: TextView = view.findViewById(R.id.tvUsernameHome)
        val tvAccountLinkHome: TextView = view.findViewById(R.id.tvAccountLinkHome)
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemsItem)
    }
}