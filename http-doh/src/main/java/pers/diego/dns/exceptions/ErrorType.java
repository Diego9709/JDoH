package pers.diego.dns.exceptions;

import lombok.Getter;
import lombok.Setter;

/**
 * @author kang.zhang
 * @date 2021/11/25 23:46
 */


@Getter
public enum ErrorType {
    GFW_FILE_NOT_FOUND("gfw.list文件未找到",4004),
    DOH_URL_CONNECTION_ERROR("DoH连接失败",4005),
    FILE_READ_ERROR("读取文件错误",5002);



    private String errorMessage;

    private Integer errorCode;

    ErrorType(String errorMessage, Integer errorCode){
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
}
