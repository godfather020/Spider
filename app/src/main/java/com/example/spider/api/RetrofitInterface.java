package com.example.spider.api;

import com.example.spider.model.Ifsc_Bank_Detail;
import com.example.spider.model.Response;
import com.example.spider.utils.Constant;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitInterface {

    @POST("service.php")
    Call<Response> getLogin(@Body RequestBody params);

    @POST("service.php")
    Call<Response> logout(@Body RequestBody params);

    @POST("service.php")
    Call<Response> check_mobile_no(@Body RequestBody params);

    @POST("service.php")
    Call<Response> signUp(@Body RequestBody params);

    @POST("service.php")
    Call<Response> changePassword(@Body RequestBody params);

    @POST("service.php")
    Call<Response> getWebsiteList(@Body RequestBody params);

    @POST("service.php")
    Call<Response> createId(@Body RequestBody params);

    @POST("service.php")
    Call<Response> deposite(@Body RequestBody params);

    @GET
    Call<Ifsc_Bank_Detail> getBankDetail(@Url  String url);

    @POST("service.php")
    Call<Response> addBankDetail(@Body RequestBody params);

    @POST("service.php")
    Call<Response> getBannerList(@Body RequestBody params);

    @POST("service.php")
    Call<Response> getMyIdList(@Body RequestBody params);

    @POST("service.php")
    Call<Response> getWalletList(@Body RequestBody params);


}
