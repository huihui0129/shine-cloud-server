package com.shine.constant;

import com.shine.common.constant.BaseConstant;

/**
 * @author huihui
 * @date 2024/11/14 18:16
 * @description GatewayConstant
 */
public interface GatewayConstant extends BaseConstant {

    String CLIENT_KEY = "client";

    int FILTER_ORDER_REQUEST_LOG = 3;

    int REMOVE_HEADER_FILTER = 4;

    int FILTER_ORDER_CLIENT_AUTHENTICATION = 5;

    int FILTER_ORDER_SECURITY_AUTHENTICATION = 6;

    int FILTER_ORDER_SERVICE_AUTHENTICATION = 7;

}
