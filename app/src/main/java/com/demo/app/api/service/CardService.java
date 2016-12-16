package com.demo.app.api.service;


import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by LXG on 2016/12/9.
 */

public interface CardService {
    /**
     * 圖片上傳
     */
    @Multipart
    @POST("inspectionImg/upload")
    Call<String> addImage(@PartMap
                                  Map<String, RequestBody> imgs,
                          @Part("XMid") int XMid, @Part("LBid") int LBid, @Part("userId") int userId);


    /**
     * 获取大图
     */
    @FormUrlEncoded
    @POST("getInspectionImgList")
    Call<String> allImage(@Field("spection_card_id") int id);

}
