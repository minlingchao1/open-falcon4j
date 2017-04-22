package com.lingchaomin.falcon.web.dto;


import com.lingchaomin.falcon.web.entity.Template;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/13 下午4:06
 * @description
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TplDto {

    private Long id;

    private Long parentId;

    private Long actionId;

    private String tplName;

    private String parentName;

    private String actionName;


    public static TplDto convertTpl2Dto(Template template){
        TplDto tplDto=TplDto.builder()
                .tplName(template.getTplName())
                .actionId(template.getActionId())
                .parentId(template.getParentId())
                .build();

        return tplDto;
    }

}
