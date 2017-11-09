package com.example.shenhaichen.bakingapp.http;

import com.google.gson.internal.$Gson$Types;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 用于回调的一个抽象类，可以借此传递数据
 * Created by shenhaichen on 25/09/2017.
 */

public abstract class BaseCallBack<T> {

    public Type mType;
    //这是为了把泛型转为需要的类
    static Type getSuperclassTypeParameter(Class<?> subclass)
    {
        Type superclass = subclass.getGenericSuperclass();
        if (superclass instanceof Class)
        {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return $Gson$Types.canonicalize(parameterized.getActualTypeArguments()[0]);
    }


    public BaseCallBack()
    {
        mType = getSuperclassTypeParameter(getClass());
    }

    /**
     * 网络请求出错时，调用此方法
     * @param request
     * @param e
     */
    public abstract  void onFailure(Request request, Exception e) ;

    /**
     *请求成功时调用此方法
     * @param response
     */
    public abstract  void onResponse(Response response);

    /**
     *
     * 状态码大于200，小于300 时调用此方法
     * @param response
     * @param t
     */
    public abstract void onSuccess(Response response, T t) ;

    /**
     * 状态码400，404，403，500等时调用此方法
     * @param response
     * @param code
     * @param e
     */
    public abstract void onError(Response response, int code,Exception e) ;
}

