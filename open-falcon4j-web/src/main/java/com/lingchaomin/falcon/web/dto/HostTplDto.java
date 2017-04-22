package com.lingchaomin.falcon.web.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/10 下午11:41
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HostTplDto {

    private Long hostId;

    private Long tplId;
}
