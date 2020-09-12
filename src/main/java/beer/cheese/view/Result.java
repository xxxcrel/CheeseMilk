package beer.cheese.view;

import org.springframework.http.HttpStatus;

public class Result<T> {

    private int status;

    private T data;

    public Result(T data){
        this.data = data;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> ok(T data){
        Result<T> result = new Result<>(data);
        result.setStatus(HttpStatus.OK.value());
        return result;
    }

    public static <T> Result<T> notFound(T data){
        Result<T> result = new Result<>(data);
        result.setStatus(HttpStatus.NOT_FOUND.value());
        return result;
    }
}
