package com.task.githubprofile.ui.dashaboard.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.task.githubprofile.R
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.databinding.LayoutItemRepoListViewBinding
import com.task.githubprofile.utils.base.interfaces.OnItemClickListener
import com.task.githubprofile.utils.extensions.loadImage

class RepoListItemViewHolder(private val binding: ViewBinding) :
    RecyclerView.ViewHolder(binding.root) {
    @SuppressLint("SetTextI18n")
    fun onBind(
        data: Profile,
        position: Int,
        onItemClickListener: OnItemClickListener?
    ) {
        when (binding) {
            is LayoutItemRepoListViewBinding -> {
                binding.ivProfile.loadImage(data.avatarUrl)
                binding.tvUserName.text = data.login
                binding.tvScore.text =
                    binding.root.context.getString(R.string.screen_dashboard_display_text_score) + " "+ data.score
            }
        }
    }
}