package vd.mall.response;

public enum GlobalResponseCode implements IErrorCode {
    SUCCESS(200, "操作成功！"),

    //账户相关
    USERNAME_OR_PASSWORD_ERROR(501, "输入的密码不正确！"),
    ACCOUNT_LOCKED_ERROR(502, "账户被锁定！"),
    CREDENTIALS_EXPIRED_ERROR(503, "密码过期！"),
    ACCOUNT_EXPIRED_ERROR(504, "账户过期！"),
    ACCOUNT_DISABLED_ERROR(505, "账户被禁用！"),
    VALIDATE_CODE_NOT_MATCHED_ERROR(506, "验证码不匹配！"),
    LOGIN_FAILED_ERROR(507, "登录失败！"),
    USERNAME_NOT_FOUND_ERROR(508, "用户名不存在！"),
    //权限
    ACCESS_FORBIDDEN_ERROR(403, "无访问权限");


    private int code;
    private String message;

    GlobalResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
