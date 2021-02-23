package beer.cheese.exception;

import org.springframework.util.ObjectUtils;

import beer.cheese.view.IResultStatus;
import lombok.Getter;

@Getter
public class BaseException extends RuntimeException {
    Object data;
    IResultStatus status;

    public BaseException(IResultStatus status) {
        super(status.getMessage());
        this.status = status;
    }

    public BaseException(IResultStatus status, Object... data) {
        super(status.getMessage());
        this.status = status;
        if (!ObjectUtils.isEmpty(data)) {
            this.data = data;
        }
    }

    public BaseException(IResultStatus status, Throwable cause, Object... data) {
        super(status.getMessage(), cause);
        this.status = status;
        if (!ObjectUtils.isEmpty(data)) {
            this.data = data;
        }
    }
}
