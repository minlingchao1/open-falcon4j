package com.lingchaomin.falcon.web.entity;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午9:37
 * @description 模版和houstgroup关联
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class HostGroupTplRef {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * host group id
     */
    private Long grpId;

    /**
     * 模版id
     */
    private Long tplId;
}
