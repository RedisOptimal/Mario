package com.renren.infra.xweb.entity;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import com.renren.infra.xweb.util.excel.annotation.ExcelField;

public class Test {

    private Long id;

    @ExcelField(title = "消息", align = 0, sort = 0, value = "msg")
    @NotBlank(message = "别乱写，不支持三个字以下的")
    private String msg;

    @Length(min = 3, max = 10)
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

}
