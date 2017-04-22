package com.lingchaomin.falcon.web.entity;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:14
 * @description 项目组
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostGroup {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * host组名
     */
    private String grpName;

    private String note;

    private String tplName;

    private List<String> hostNames;


}
