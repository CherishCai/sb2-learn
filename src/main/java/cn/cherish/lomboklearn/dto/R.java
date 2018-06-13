package cn.cherish.lomboklearn.dto;

import lombok.Data;

/**
 * @author Cherish
 * @version 1.0
 * @date 2018/6/13 10:38
 */
@Data
public class R<T> {

    private Integer code;

    private String bCode;

    private String msg;

    private T data;


    public R(){
        code = 200;
        bCode = BCode.SUCCESS;
    }

    public R(T data) {
        this();
        this.data = data;
    }

    public R(String msg, T data) {
        this();
        this.msg = msg;
        this.data = data;
    }

}
