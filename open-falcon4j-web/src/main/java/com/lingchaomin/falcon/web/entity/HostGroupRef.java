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
 * @date 2017/4/10 下午9:23
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostGroupRef {

    private Long id;

    private Date gmtCreate;

    private Date gmtModified;

    /**
     * host groupId
     */
    private Long grpId;

    /**
     * hostId
     */
    private Long hostId;

}
