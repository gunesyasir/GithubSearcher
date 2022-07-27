package com.example.githubsearcher.Model

import com.google.gson.annotations.SerializedName

data class RepoResponse(
    @SerializedName("total_count"         ) var totalCount             : Int?  = null,
    @SerializedName("incomplete_results"  ) var incompleteResults      : Boolean?     = null,
    @SerializedName("items"               ) var items                  : List<RepoModel>?  = listOf()
) : CommonResponse()
