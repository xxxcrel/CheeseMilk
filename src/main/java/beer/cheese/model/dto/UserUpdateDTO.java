package beer.cheese.model.dto;

import beer.cheese.model.entity.User;
import beer.cheese.validation.ValidEntityField;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserUpdateDTO {

    @NotEmpty
    @ValidEntityField(entityClass = User.class)
    private String updatedField;

    @NotNull
    private Object updatedValue;

    public String getUpdatedField() {
        return updatedField;
    }

    public void setUpdatedField(String updatedField) {
        this.updatedField = updatedField;
    }

    public Object getUpdatedValue() {
        return updatedValue;
    }

    public void setUpdatedValue(Object updateValue) {
        this.updatedValue = updateValue;
    }
}

