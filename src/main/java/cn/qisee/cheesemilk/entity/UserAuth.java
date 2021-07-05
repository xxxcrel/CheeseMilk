package cn.qisee.cheesemilk.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_user_aut")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserAuth extends BasicEntity{

    @OneToOne
    private User user;

    @Convert(converter = IdentityTypeConverter.class)
    @Column(name = "identity_type")
    private IdentityType identityType;

    @Column(name = "identifier")
    private String identifier;

    @Column(name = "credential")
    private String credential;

    @Column(name = "bind_flag")
    private boolean bindFlag;

    public enum IdentityType{
        INNER("inner"),
        WECHAT("wechat"),
        QQ("qq"),
        GITHUB("github");

        private final String typeName;
        IdentityType(String typeName){
            this.typeName = typeName;
        }
        public static IdentityType from(String value){
            switch (value){
                case "inner":
                    return INNER;
                case "wechat":
                    return WECHAT;
                case "qq":
                    return QQ;
                case "github":
                    return GITHUB;
                default:
                    throw new IllegalArgumentException("No such IdentityType value: " + value);
            }
        }
    }
    static class IdentityTypeConverter implements AttributeConverter<IdentityType, String>{
        @Override
        public String convertToDatabaseColumn(IdentityType attribute) {
            return attribute.typeName;
        }

        @Override
        public IdentityType convertToEntityAttribute(String dbData) {
            return IdentityType.from(dbData);
        }
    }
}
