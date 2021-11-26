package pers.diego.dns.exceptions;

/**
 * @author kang.zhang
 * @date 2021/11/26 20:33
 */
public class ConnectionErrorException extends RuntimeException {
    private int errorCode;
    private String url;
    public ConnectionErrorException(ErrorType errorType,String errorUrl){
        super(errorType.getErrorMessage());
        this.errorCode = errorType.getErrorCode();
        this.url = errorUrl;
    }

    public ConnectionErrorException(String message, Integer errorCode,String errorUrl){
        super(message);
        this.errorCode = errorCode;
        this.url = errorUrl;
    }
}
