package com.lonelyxpc.mvp.api;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface APIService {
    @GET
    Observable<ResponseBody> webGet(@Url String url);

    @POST
    Observable<ResponseBody> webPOST(@Url String url, @Body RequestBody requestBody);

    @Streaming //添加这个注解用来下载大文件
    @POST
    Observable<ResponseBody> downLoadFile(@Url String url,@Body RequestBody requestBody);

    @Multipart
    @POST
    Observable<ResponseBody> upLoadFile(@Url String url, @PartMap Map<String, RequestBody> params, @Part List<MultipartBody.Part> parts);

}
