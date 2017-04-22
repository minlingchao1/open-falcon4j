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
 * @description 邮件
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@List(key = "mail")
public class MailDto  implements Serializable{

    /**
     * 发送邮箱
     */
    @ListColumn
    private String tos;

    /**
     *
     */
    @ListColumn
    private String subject;

    /**
     * 内容
     */
    @ListColumn
    private String content;
}
