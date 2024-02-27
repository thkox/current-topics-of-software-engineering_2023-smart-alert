package eu.tkacas.smartalert.interfacesAPI

import eu.tkacas.smartalert.models.PlacesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
interface PlacesAPI {
    @GET("place/autocomplete/json")
    fun getPlacesAutocomplete(
        @Query("input") input: String,
        @Query("key") apiKey: String,
        @Query("components") components: String = "country:gr"
    ): Call<PlacesResponse>
}