package com.lanvige.example.util;

import lombok.Data;

/**
 * ToolCommandClientsBO
 * <p>
 *
 * @author uniscrm.net
 * @since 2021/09/29
 */
@Data
public class ToolCommandClientsBO {

    // 用户名
    private String username;

    // 企业微信账号
    private String accountId;

    private String userId;

    private String corpId;
}
