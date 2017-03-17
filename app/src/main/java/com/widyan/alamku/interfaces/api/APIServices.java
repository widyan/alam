package com.widyan.alamku.interfaces.api;

import com.widyan.alamku.models.Alam;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.models.User;
import com.widyan.alamku.models.UserData;
import com.widyan.alamku.utils.Constants;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Created by widyan on 3/15/2017.
 */

public interface APIServices {
    @POST(Constants.Apps.LOGIN)
    @FormUrlEncoded
    Call<User> login(@Field("username") String username, @Field("password") String password);

    @POST(Constants.Apps.REGISTER)
    @FormUrlEncoded
    Call<UserData> register(@Field("first_name") String first_name, @Field("last_name") String last_name,
                            @Field("username") String username, @Field("password") String password,
                            @Field("bdate") String bdate, @Field("gender") String gender, @Field("phone") String phone);

    @POST(Constants.Apps.ADD_TEMPAT_WISATA)
    @Multipart
    Call<AlamData> addDataAlam(@Part("judul") RequestBody judul, @Part("location") RequestBody location,
                           @Part("kategori") RequestBody kategori, @Part("deskripsi") RequestBody deskripsi,
                           @Part("id_user") RequestBody id_user, @Part MultipartBody.Part image);

    @GET(Constants.Apps.LIST_TEMPAT_WISATA)
    Call<Alam> getListAlam();

    @GET(Constants.Apps.BANNER)
    @FormUrlEncoded
    Call<Alam> getBanner(@Field("kategori") String kategori);

    @GET(Constants.Apps.FILTER_TEMPAT_WISATA)
    Call<Alam> getFilterAlamDao(@Query("kategori") int kategori);

    @GET(Constants.Apps.GET_DETAIL_DATA)
    Call<Alam> getDetailAlam(@Query("itemid") int itemid);


}
