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
 * @date 2017/4/10 下午9:38
 * @description hostName
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Host {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * host名称
     */
    private String hostName;
}
