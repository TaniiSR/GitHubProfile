package com.task.githubprofile.data.dtos.responsedtos


import com.google.gson.annotations.SerializedName
import com.task.githubprofile.data.remote.baseclient.BaseApiResponse


data class GitUser(
    @SerializedName("incomplete_results")
    var incompleteResults: Boolean? = null,
    @SerializedName("items")
    var repos: List<Profile>? = null,
    @SerializedName("total_count")
    var totalCount: Int? = null
) : BaseApiResponse()