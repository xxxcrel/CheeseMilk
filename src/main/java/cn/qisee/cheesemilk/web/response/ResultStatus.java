package cn.qisee.cheesemilk.web.response;

public enum ResultStatus implements IResultStatus{
    SUCCESS(200, "success"),
    ERROR(400, "error");
    private final int code;
    private final String message;
    ResultStatus(int code, String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public java.lang.String getMessage() {
        return message;
    }
}
