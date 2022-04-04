package com.task.githubprofile.ui.dashaboard

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.task.githubprofile.R
import com.task.githubprofile.data.dtos.responsedtos.Profile
import com.task.githubprofile.databinding.ActivityDashboardBinding
import com.task.githubprofile.utils.base.BaseActivity
import com.task.githubprofile.utils.base.interfaces.OnItemClickListener
import com.task.githubprofile.utils.base.sealed.UIEvent
import com.task.githubprofile.utils.extensions.gone
import com.task.githubprofile.utils.extensions.observe
import com.task.githubprofile.utils.extensions.visible
import com.task.githubprofile.utils.uikit.searchview.SearchView
import com.task.githubprofile.utils.uikit.toolbarview.ToolBarView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DashboardActivity : BaseActivity<ActivityDashboardBinding, IDashboardVM>(),
    SearchView.OnSearchViewClickListener, ToolBarView.OnToolBarViewClickListener {
    override val viewModel: IDashboardVM by viewModels<DashboardVM>()
    override fun getViewBinding(): ActivityDashboardBinding =
        ActivityDashboardBinding.inflate(layoutInflater)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModelObservers()
        setListener()
        setUpRecyclerView()
        mViewBinding.searchView.textWatcher = viewModel.watcher
        viewModel.getTrendyGithubRepos(query = viewModel.query, false)
    }

    private fun setUpRecyclerView() {
        viewModel.adaptor.onItemClickListener = rvItemClickListener
        mViewBinding.recyclerView.adapter = viewModel.adaptor
    }

    private val rvItemClickListener = object : OnItemClickListener {
        override fun onItemClick(view: View, data: Any, pos: Int) {
            when (data) {
                is Profile -> {
                    // handle later
                }
            }
        }
    }

    private fun handleRepoList(list: List<Profile>) {
        if (!list.isNullOrEmpty()) {
            viewModel.adaptor.setList(list)
        } else setErrorView()
    }

    private fun setListener() {
        mViewBinding.btnRetry.setOnClickListener(this)
        mViewBinding.tbView.setOnToolBarViewClickListener(this)
        mViewBinding.searchView.setOnToolBarViewClickListener(this)
        mViewBinding.swRefresh.setOnRefreshListener {
            onCancelClicked()
            mViewBinding.swRefresh.isRefreshing = false
            viewModel.fetchFreshData()
        }
    }

    override fun onEndIconClicked() {
        mViewBinding.searchView.openSearch()
        mViewBinding.tbView.visibility = View.INVISIBLE
    }

    override fun onCancelClicked() {
        mViewBinding.searchView.closeSearch()
        mViewBinding.tbView.visibility = View.VISIBLE
    }

    override fun onClick(id: Int) {
        when (id) {
            R.id.btnRetry -> viewModel.fetchFreshData()
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
        mViewBinding.errorView.visible()
        mViewBinding.recyclerView.gone()
        hideShimmerLoading()
    }

    private fun setSuccessView() {
        mViewBinding.recyclerView.visible()
        mViewBinding.errorView.gone()
        hideShimmerLoading()
    }

    private fun setLoadingView() {
        mViewBinding.errorView.gone()
        mViewBinding.recyclerView.gone()
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

    private fun viewModelObservers() {
        observe(viewModel.repoLists, ::handleRepoList)
        observe(viewModel.uiState, ::handleUiState)
    }
}
