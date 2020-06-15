package net.city.qrapplecation.data

import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @FormUrlEncoded
    @Headers("Content-Type: application/x-www-form-urlencoded")
    @POST("api2.php/")
    fun scanUser(@Field("text") text:String):Call<AnswerData>
}

data class AnswerData(
    val answer: String
)