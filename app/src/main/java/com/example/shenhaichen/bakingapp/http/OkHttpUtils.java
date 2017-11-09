package com.example.shenhaichen.bakingapp.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 *  虽然可以直接读取下载好的json数据，但是为了让它更像一个随时更新的app ：）
 *  所以还是直接去下载比较好
 *  这里只是简单封装okhtttp
 * Created by shenhaichen on 09/11/2017.
 */

public class OkHttpUtils {
    public static final String TAG = "OKHttpUtils";
    private static OkHttpUtils mInstance;
    private OkHttpClient mClient;
    private Gson mGson;
    private Handler mHandler;

    static {
        mInstance = new OkHttpUtils();
    }

    private OkHttpUtils(){
        mClient = new OkHttpClient();
        mClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mClient.setReadTimeout(10, TimeUnit.SECONDS);
        mClient.setWriteTimeout(30, TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static OkHttpUtils getInstance(){
        return mInstance;
    }

    /**
     * 由于本次项目没有提交个人隐私信息
     * 所以我只封装一个get方法用以得到相关的电影信息
     */
    public void get(String url, BaseCallBack callBack){

        Request.Builder builder = new Request.Builder()
                .url(url).get();

        Request request = builder.build();

        getResponse(request, callBack);

    }

    /**
     *  获取烘焙信息，并得到具体的烘焙信息
     * @param request
     * @return
     */
    private void getResponse (final Request request, final BaseCallBack callBack) {

        mClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                callBack.onFailure(request, e);
            }

            @Override
            public void onResponse(Response response) throws IOException {

                if (response.isSuccessful()) {
                    String resultStr = response.body().string();

//                    System.out.println("getMessage"+resultStr);
                    //如果返回的只是字符串，就获得字符串
                    Log.d(TAG,resultStr);
                        //如果是json数据，就要进行解析
                        try {
                            Object object = mGson.fromJson(resultStr, callBack.mType);
                            callbackSuccess(callBack, response, object);

                        } catch (JsonParseException e) {
                            callBack.onError(response, response.code(), e);
                        }
                    }
                }
        });
    }


    /**
     * 线程运行问题，所以包裹在handler中进行
     * @param callBack
     * @param response
     */
    private void callbackSuccess(final BaseCallBack callBack, final Response response, final Object object){

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callBack.onSuccess(response, object);
            }
        });
    }


}
