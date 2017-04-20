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
 * @date 2017/4/11 上午10:43
 * @description 用户组
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserGroup {

     /**
     * id
     */
    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * 用户组名
     */
    private String grpName;

    /**
     * 备注
     */
    private String note;
}
