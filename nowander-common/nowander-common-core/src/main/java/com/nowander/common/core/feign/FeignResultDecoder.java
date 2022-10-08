package com.nowander.common.core.feign;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.nowander.common.core.exception.service.ServiceFiegnException;
import com.nowander.common.core.pojo.Msg;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @author wtk
 * @date 2022-09-02
 */
@Slf4j
public class FeignResultDecoder implements Decoder {

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.body() == null) {
            throw new DecodeException(response.status(), "没有返回有效的数据", response.request());
        }
        //对结果进行转换
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        Msg<?> result;
        try {
            result = JSONUtil.toBean(bodyStr, Msg.class);
        } catch (Exception e) {
            throw new DecodeException(response.status(), "没有返回预期的数据，响应体无法转为 Msg.class。相应数据：" + bodyStr, response.request());
        }
        //如果返回错误，且为内部错误，则直接抛出异常
        if (!result.isSuccess()) {
            throw new ServiceFiegnException(result, response.status(), response.request());
        }
        // result.getData() 是 JSONObject 类型，直接返回会出现强转异常
        try {
            return JSONUtil.toBean((JSONObject) result.getData(), Class.forName(type.getTypeName()));
        } catch (ClassNotFoundException e) {
            throw new DecodeException(response.status(), "无法获取接口返回值类型的Class对象：" + type.getTypeName(), response.request());
        }
    }
}
