package com.task.githubprofile.ui.dashaboard.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.task.githubprofile.R
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.databinding.LayoutItemRepoListViewBinding
import com.task.githubprofile.utils.base.BaseRecyclerAdapter

class RepoListAdapter(
    private val list: MutableList<Profile>,
) : BaseRecyclerAdapter<Profile, RepoListItemViewHolder>(list) {
    override fun onCreateViewHolder(binding: ViewBinding): RepoListItemViewHolder {
        return RepoListItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoListItemViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.onBind(list[position], position, onItemClickListener)
    }

    override fun getItemViewType(position: Int): Int {
        return R.layout.layout_item_repo_list_view
    }

    override fun getItemCount(): Int = list.size

    override fun getViewBindingByViewType(
        layoutInflater: LayoutInflater,
        viewGroup: ViewGroup,
        viewType: Int
    ): ViewBinding {
        return LayoutItemRepoListViewBinding.inflate(layoutInflater, viewGroup, false)
    }
}