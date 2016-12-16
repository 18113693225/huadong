package com.demo.app.util;

import java.io.File;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import android.os.AsyncTask;
import android.util.Log;

import com.demo.app.activity.index.PowerRunXSKActivity;
import com.demo.app.network.NetworkResponceFace;

public class HttpPostUtil {

    public static String data = "";
    static File uploadFile;


    public static void imageAdd(NetworkResponceFace face, final String uploaded_picture[], final String XMid, final String LBid, final String id) {
//        dateBeanAdd(face, list, InspectionCardBean.class);
        http_file(face, uploaded_picture, PowerRunXSKActivity.xiangmu, PowerRunXSKActivity.leibie, id);
    }


    public static void http_file(final NetworkResponceFace face, final String uploaded_picture[], final String XMid, final String LBid, final String id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpClient httpClient = new DefaultHttpClient();
                String urlStr = "http://192.168.1.164:8080/electricproperty/api/inspectionImg/upload";
                HttpPost httpPost = new HttpPost(urlStr);
                try {
                    httpClient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                    MultipartEntity mpEntity = new MultipartEntity(); // 文件传输
                    if (uploaded_picture.length > 0) {
                        for (int i = 0; i < uploaded_picture.length; i++) {
                            uploadFile = new File(uploaded_picture[i]);
                            ContentBody cbFile = new FileBody(uploadFile);
                            mpEntity.addPart("files", cbFile);
                        }
                    }
//                    mpEntity.addPart("files", new ContentBody());
                    mpEntity.addPart("XMid", new StringBody(XMid));
                    mpEntity.addPart("LBid", new StringBody(LBid));
                    mpEntity.addPart("userId", new StringBody(id));

                    httpPost.setEntity(mpEntity);
                    // httpClient执行httpPost提交
                    HttpResponse response = httpClient.execute(httpPost);
                    // HttpResponse response =
                    // filterExecutePost(httpClient,httpPost);
                    // 得到服务器响应实体对象
                    HttpEntity responseEntity = response.getEntity();
                    if (responseEntity != null) {
                        /** 服务器返回 */
                        data = EntityUtils.toString(responseEntity, "utf-8");
                    }
                    if (responseEntity == null) {
                        Log.e("------->", "值还没加载出来");
                    }
                } catch (Exception e) {

                } finally {
                    // 释放资源
                    httpClient.getConnectionManager().shutdown();
                    face.callback(data);
                }
            }
        }).start();

    }
}