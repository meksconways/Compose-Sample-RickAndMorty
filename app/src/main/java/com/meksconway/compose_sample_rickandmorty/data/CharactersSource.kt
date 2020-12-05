package com.meksconway.compose_sample_rickandmorty.data

import androidx.paging.PagingSource
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.exception.ApolloException
import com.meksconway.compose_sample_rickandmorty.CharactersQuery
import com.meksconway.compose_sample_rickandmorty.networking.ApiService
import javax.inject.Inject

class CharactersSource @Inject constructor(
    private val apiService: ApiService
): PagingSource<Int, CharactersQuery.Result>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CharactersQuery.Result> {
        try {
            val currentLoadingPageKey = params.key ?: 1
            val response =
                apiService.request(
                    CharactersQuery(
                        Input.optional(currentLoadingPageKey)
                    )
                )
            val keyData = response.data?.characters
            if (response.hasErrors() || keyData == null) {
                return LoadResult.Error(Exception(response.errors?.first()?.message))
            }

            return LoadResult.Page(
                data = keyData.results?.mapNotNull { it } ?: emptyList(),
                prevKey = keyData.info?.prev,
                nextKey = keyData.info?.next
            )

        } catch (e: ApolloException) {
            return LoadResult.Error(e)
        }
    }


}