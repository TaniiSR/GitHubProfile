package com.task.githubprofile.ui.detail

import android.os.Bundle
import androidx.activity.viewModels
import com.task.githubprofile.data.dtos.responsedtos.User
import com.task.githubprofile.databinding.ActivityDetailBinding
import com.task.githubprofile.utils.base.BaseActivity
import com.task.githubprofile.utils.base.sealed.UIEvent
import com.task.githubprofile.utils.extensions.gone
import com.task.githubprofile.utils.extensions.loadImage
import com.task.githubprofile.utils.extensions.observe
import com.task.githubprofile.utils.extensions.visible
import com.task.githubprofile.utils.uikit.toolbarview.ToolBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity : BaseActivity<ActivityDetailBinding, IDetailVM>(),
    ToolBarView.OnToolBarViewClickListener {
    override val viewModel: IDetailVM by viewModels<DetailVM>()
    override fun getViewBinding(): ActivityDetailBinding =
        ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
        setListener()
        viewModel.userName = intent.getStringExtra(DetailActivity::class.java.name) ?: ""
        viewModel.getGithubUser(
            userName = viewModel.userName, false
        )
    }

    override fun onStartIconClicked() {
        onBackPressed()
    }

    private fun setListener() {
        mViewBinding.errorView.btnRetry.setOnClickListener(this)
        mViewBinding.tbView.setOnToolBarViewClickListener(this)
        mViewBinding.swRefresh.setOnRefreshListener {
            mViewBinding.swRefresh.isRefreshing = false
            viewModel.fetchFreshData()
        }
    }

    private fun handleUiState(uiEvent: UIEvent) {
        when (uiEvent) {
            is UIEvent.Loading -> setLoadingView()
            is UIEvent.Success -> setSuccessView()
            is UIEvent.Error -> setErrorView()
            is UIEvent.Message -> showToast(uiEvent.message)
        }
    }

    private fun setErrorView() {
        mViewBinding.errorView.errorView.visible()
        mViewBinding.clMain.gone()
        hideShimmerLoading()
    }

    private fun setSuccessView() {
        mViewBinding.errorView.errorView.gone()
        mViewBinding.clMain.visible()
        hideShimmerLoading()
    }

    private fun setLoadingView() {
        mViewBinding.clMain.gone()
        mViewBinding.errorView.errorView.gone()
        showShimmerLoading()
    }

    private fun showShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.visible()
        mViewBinding.shimmerLayout.shimmerFrameLayout.startShimmer()

    }

    private fun hideShimmerLoading() {
        mViewBinding.shimmerLayout.shimmerFrameLayout.gone()
        mViewBinding.shimmerLayout.shimmerFrameLayout.stopShimmer()
    }

    private fun handleUser(user: User?) {
        user?.let {
            mViewBinding.ivImage.loadImage(user.avatarUrl)
            mViewBinding.tvDescription.text = user.bio
            mViewBinding.tvEmail.text = user.email
            mViewBinding.tvName.text = user.name
        }
    }

    private fun viewModelObservers() {
        observe(viewModel.user, ::handleUser)
        observe(viewModel.uiState, ::handleUiState)
    }
}