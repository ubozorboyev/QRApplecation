package net.city.qrapplecation

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult
import kotlinx.android.synthetic.main.activity_main.*
import net.city.qrapplecation.data.AnswerData
import net.city.qrapplecation.data.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {


    val retrofit=Retrofit.Builder()
        .baseUrl("https://c.citynet.uz/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(ApiInterface::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {

            kotlin.run {
                IntentIntegrator(this@MainActivity).initiateScan()
            }

        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val result: IntentResult? = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null){
            if (result.contents != null) {
                getHtmltext(result.contents.toString())
                Toast.makeText(this,result.contents,Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun getHtmltext(data:String) {


        retrofit.scanUser(data).enqueue(object :Callback<AnswerData>{
            override fun onFailure(call: Call<AnswerData>, t: Throwable) { }

            override fun onResponse(call: Call<AnswerData>, response: Response<AnswerData>) {
                if (response.isSuccessful) {

                 //   val d = Html.fromHtml(response.body()?.answer)

                    textQR.settings.javaScriptEnabled = true
                    textQR.loadData(response.body()?.answer,"text/html",null)
                }
            }
        })
    }
}