package com.roninhub.security.application.service;

import com.roninhub.security.application.constant.ResponseCode;
import com.roninhub.security.application.dto.response.Meta;
import com.roninhub.security.application.dto.response.ResponseDto;
import com.roninhub.security.infrastructure.constant.FieldName;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;


@Service
@RequiredArgsConstructor
public class ResponseFactory {

    @Value("${spring.application.name}")
    String applicationName;

    final private Environment env;

    private Meta buildMeta(String requestId,
                           ResponseCode responseCode,
                           String paramName,
                           Object paramValue) {
        Meta meta = Meta.builder()
                .requestId(requestId)
                .code(responseCode.getCode())
                .message(env.getProperty(String.valueOf(responseCode.getCode())))
                .serviceId(applicationName)
                .build();

        if (paramName != null && paramValue != null) {
            Map<String, Object> extraMeta = new HashMap<>();
            extraMeta.put(paramName, paramValue);
            meta.setExtraMeta(extraMeta);
        }

        return meta;
    }

    public ResponseDto response(String requestId,
                                Object data,
                                ResponseCode responseCode,
                                String paramName,
                                Object paramValue) {
        Meta meta = buildMeta(requestId, responseCode, paramName, paramValue);
        return new ResponseDto(meta, data);
    }

    public ResponseDto response(String requestId,
                                ResponseCode responseCode,
                                String param,
                                Object data) {
        return response(requestId, data, responseCode, FieldName.PARAM, param);
    }

    public ResponseDto response(Object data,
                                String paramName,
                                Object paramValue) {
        return response(null, data, ResponseCode.SUCCESS, paramName, paramValue);
    }

    public ResponseDto response(ResponseCode responseCode) {
        return response(null, responseCode, null, null);
    }

    public ResponseDto response(Object data) {
        return response(null, ResponseCode.SUCCESS, null, data);
    }

    public ResponseDto response(String requestId, Object data) {
        return response(requestId, ResponseCode.SUCCESS, null, data);
    }

    public ResponseDto responseBabRequest(String message) {
        ResponseDto resp = response(null, ResponseCode.INVALID_REQUEST, null, null);
        if (StringUtils.hasText(message)) {
            resp.getMeta().setMessage(message);
        }

        return resp;
    }
}
