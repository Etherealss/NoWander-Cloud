package com.nowander.common.core.feign;

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
        String bodyStr = Util.toString(response.body().asReader(Util.UTF_8));
        //对结果进行转换
        Msg<?> result = JSONUtil.toBean(bodyStr, Msg.class);
        //如果返回错误，且为内部错误，则直接抛出异常
        if (!result.isSuccess()) {
            throw new ServiceFiegnException(result, response.status(), response.request());
        }
        return result.getData();
    }
}
