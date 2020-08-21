package com.test.revolut.data

import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//object RetrofitClient {
//
//    val instance: Retrofit by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://hiring.revolut.codes/")
//            .client(HttpsClient.client)
//            .addConverterFactory(
//                GsonConverterFactory.create(
//                    GsonBuilder()
//                        .serializeNulls()
//                        .create()
//                )
//            )
//            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
//            .build()
//    }
//}

//object HttpsClient {
//    val client : OkHttpClient by lazy {
//        OkHttpClient.Builder()
//            .build()
//    }
//}