package com.majazeh.emall.data.api

import com.majazeh.emall.data.api.response.*
import retrofit2.Response
import retrofit2.http.*


interface DataApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("username") number: String,
        @Field("password") password: String
    ): Response<Login>

    @GET("explode")
    suspend fun explode(@Header("Authorization") Authorization: String): Response<Explode>

    @GET("products")
    suspend fun products(
        @Header("Authorization") Authorization: String,
        @Query("page") page: Int,
        @Query("q") q: String,
        @Query("category") category: Int,
        @Query("brand") brand: String
    ): Response<ProductData>

    @GET("logout")
    suspend fun logOut(@Header("Authorization") Authorization: String): Response<ResponseApi>

    @FormUrlEncoded
    @POST("me")
    suspend fun editProfile(
        @Header("Authorization") Authorization: String,
        @Field("name") name: String,
        @Field("number") number: String,
        @Field("email") email: String,
        @Field("address") address: String,
        @Field("password") password: String
    ): Response<EditMe>

    @FormUrlEncoded
    @POST("register")
    suspend fun signUp(
        @Field("name") name: String,
        @Field("number") number: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<Login>

    @FormUrlEncoded
    @POST("cart/details")
    suspend fun addCart(
        @Header("Authorization") Authorization: String,
        @Field("product_id") product_id: String,
        @Field("count") count: String
    ): Response<ResponseApi>

    @DELETE("cart/details/{id}")
    suspend fun deleteCart(
        @Header("Authorization") Authorization: String,
        @Path("id") id: String
    ): Response<ResponseApi>

    @FormUrlEncoded
    @POST("cart/close")
    suspend fun closeCart(
        @Header("Authorization") Authorization: String,
        @Field("location") location: String
    ): Response<ResponseApi>

    @GET("cart")
    suspend fun cart(@Header("Authorization") Authorization: String): Response<ShoppingCartData>

    @GET("invoices")
    suspend fun invoices(
        @Header("Authorization") Authorization: String,
        @Query("page") page: Int
    ): Response<InvoiceData>

    @GET("invoices/{invoice_id}")
    suspend fun invoiceDetailShow(
        @Header("Authorization") Authorization: String,
        @Path("invoice_id") invoice_id: String
    ): Response<ShoppingCartData>


    @FormUrlEncoded
    @POST("requests")
    suspend fun requests(
        @Header("Authorization") Authorization: String,
        @Field("title") title: String,
        @Field("description") description: String
    ): Response<ResponseApi>

}