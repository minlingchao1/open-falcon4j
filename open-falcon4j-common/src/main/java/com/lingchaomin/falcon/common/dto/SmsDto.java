package com.lingchaomin.falcon.common.dto;


import com.lingchaomin.falcon.common.redis.annotation.List;
import com.lingchaomin.falcon.common.redis.annotation.ListColumn;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/11 下午1:27
 * @description 短信
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@List(key = "sms")
public class SmsDto implements Serializable {

    /**
     * 发送手机号
     */
    @ListColumn
    private String to;

    /**
     * 内容
     */
    @ListColumn
    private String content;
}
