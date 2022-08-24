package tik.prometheus.mobile

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

object Configs {
    var TAG = Configs::class.java.toString()
    val CONFIG_UIR: String = "https://prometheus-config.herokuapp.com"
    var configs: String? = ""
    var loaded: Boolean = false

    fun initConfig() {
        val builder = Retrofit.Builder()
            .baseUrl(CONFIG_UIR)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        val retrofit = builder.build()
        val configService = retrofit.create(ConfigService::class.java)
        val result = configService.getConfigs("prometheus-server", "default").execute()
        configs = result.body()
        loaded = true

    }

//    fun hi(): String {
//        val builder = Retrofit.Builder()
//            .baseUrl(CONFIG_UIR)
//            .addConverterFactory(
//                GsonConverterFactory.create()
//            ).addCallAdapterFactory(
//                RxJava2CallAdapterFactory.create()
//            )
//        val retrofit = builder.build()
//        val configService = retrofit.create(ConfigService::class.java)
//        GlobalScope.launch {
//            val result = configService.getConfigs("prometheus-server", "default");
//            Log.d(TAG, result.body().toString())
//        }
//        return ""
//    }

    internal interface ConfigService {
        @GET("/{name}-{profile}.properties")
        fun getConfigs(@Path("name") name: String?, @Path("profile") profile: String?): Call<String>
    }
}



