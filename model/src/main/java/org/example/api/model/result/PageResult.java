package org.example.api.model.result;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    /**
     * 信息
     */
    private  String msg;
    /**
     * 0 成功 1失败
     */
    private  int code;

    /**
     * 数据
     */
    private T data;
    /**
     * 总大小
     */
    private  int count;

    protected static <T> PageResult<T> build(T data) {
        PageResult<T> result = new PageResult<T>();
        if (data != null)
            result.setData( data);
        return result;
    }
    public static <T> PageResult<T> build(T body, int count) {
        PageResult<T> result = build(body);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMessage());
        result.setCount(count);
        return result;
    }





}
