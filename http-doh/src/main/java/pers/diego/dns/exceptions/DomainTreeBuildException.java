package pers.diego.dns.exceptions;

/**
 * @author kang.zhang
 * @date 2021/11/25 22:55
 */
public class DomainTreeBuildException extends RuntimeException {
    private int errorCode;
    public DomainTreeBuildException(ErrorType errorType){
        super(errorType.getErrorMessage());
        this.errorCode = errorType.getErrorCode();
    }

    public DomainTreeBuildException(String message, Integer errorCode){
        super(message);
        this.errorCode = errorCode;
    }
}
