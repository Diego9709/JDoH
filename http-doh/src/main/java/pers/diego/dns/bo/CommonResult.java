package pers.diego.dns.bo;

/**
 * @author kang.zhang
 * @date 10/23/2022 11:09 AM
 */
public class CommonResult {
    private long code;
    private String message;

    protected CommonResult() {
    }

    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    protected CommonResult(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonResult success(String message) {
        return new CommonResult(200, message);
    }


    public static  CommonResult failed(String message) {
        return new CommonResult(100, message);
    }
}
