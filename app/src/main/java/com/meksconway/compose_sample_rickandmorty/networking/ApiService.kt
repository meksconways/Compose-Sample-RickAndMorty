package com.meksconway.compose_sample_rickandmorty.networking

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Mutation
import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.api.Query
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.coroutines.toDeferred
import javax.inject.Inject

interface ApiService {

    companion object {
        const val BASE_URL = "https://rickandmortyapi.com/graphql"
    }

    suspend fun <D : Operation.Data, T, V : Operation.Variables>
            request(mutation: Mutation<D, T, V>): Response<T>

    suspend fun <D : Operation.Data, T, V : Operation.Variables>
            request(query: Query<D, T, V>): Response<T>

}

class ApiServiceImpl @Inject constructor(
    private val apolloClient: ApolloClient
) : ApiService {

    override suspend fun <D : Operation.Data, T, V : Operation.Variables>
            request(mutation: Mutation<D, T, V>): Response<T> {
        return apolloClient.mutate(mutation).toDeferred().await()
    }

    override suspend fun <D : Operation.Data, T, V : Operation.Variables>
            request(query: Query<D, T, V>): Response<T> {
        return apolloClient.query(query).toDeferred().await()
    }
}