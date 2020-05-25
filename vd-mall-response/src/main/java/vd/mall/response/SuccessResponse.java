package vd.mall.response;

/**
 * Created by pengq on 2019/9/7 12:29.
 */
public class SuccessResponse extends GenericResponse {
    private static final GlobalResponseCode success = GlobalResponseCode.SUCCESS;

    public SuccessResponse() {
        super(success.getCode(), success.getMessage());
    }

    public SuccessResponse(int code, String message) {
        super(code, message);
    }
}
