package lv.rtu.dip701.gifsercher

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class GiphyRepository {

    private val apiService: GiphyApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.giphy.com/v1/gifs/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(GiphyApiService::class.java)
    }

    suspend fun searchGifs(query: String, page: Int): List<Gif> {
        val limit = 20 // Number of items to fetch per page
        val offset = page * limit // Calculate the offset based on the page number

        return try {
            val response = apiService.searchGifs(
                apiKey = "CDScw3SxneC2xkyXSd7dYZJQ6kyfWqPY",
                query = query,
                limit = limit,
                offset = offset
            )
            response.body()?.data ?: emptyList()
        } catch (e: Exception) {
            // Handle the exception, e.g., logging the error
            emptyList()
        }
    }

    interface GiphyApiService {
        @GET("search")
        suspend fun searchGifs(
            @Query("api_key") apiKey: String,
            @Query("q") query: String,
            @Query("limit") limit: Int,
            @Query("offset") offset: Int
        ): retrofit2.Response<GiphyResponse>
    }
}
