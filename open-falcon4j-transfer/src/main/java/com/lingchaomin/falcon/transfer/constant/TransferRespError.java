package com.lingchaomin.falcon.transfer.constant;

/**
 * @author minlingchao
 * @version 1.0
 * @date 2017/4/20 下午11:34
 * @description
 */
public enum  TransferRespError {

    SUCCESS("success","成功"),

    INVALID_METRIC_OR_ENDPOINT("invalid_metric_or_endpoint","缺少指标或机器名"),

    VALUE_IS_NULL("value is null","指标值为空"),

    METRIC_TAG_LENGTH_OUT_OF_RANGE("metric_tag_length_out_of_range","指标和标签长度超出限制"),

    STEP_INVAILD("step_invalid","步长不合法")
    ;

    TransferRespError(String valueEn, String valueZn) {
        this.valueEn = valueEn;
        this.valueZn = valueZn;
    }

    private String valueEn;

    private String valueZn;

    public String getValueEn() {
        return valueEn;
    }

    public void setValueEn(String valueEn) {
        this.valueEn = valueEn;
    }

    public String getValueZn() {
        return valueZn;
    }

    public void setValueZn(String valueZn) {
        this.valueZn = valueZn;
    }
}
