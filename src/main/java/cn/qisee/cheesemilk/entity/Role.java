package cn.qisee.cheesemilk.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tbl_role")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Role extends BasicEntity{

    private String name;

    private String desc;

    private int permissions;

}
