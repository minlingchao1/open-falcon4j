package com.lingchaomin.falcon.web.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午3:57
 * @description 模版
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template {

    /**
     * id
     */
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 模版名称
     */
    private String tplName;

    /**
     * 父id
     */
    private Long parentId;

    /**
     * ACTIONID
     */
    private Long actionId;


}
