package cn.qisee.cheesemilk.entity;

import javax.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name ="course")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "semester")
    private String semester;

    @Column(name = "week_num")
    private String weekNumber;

    @Column(name = "class_name")
    private String className;

    @Column(name = "class_address")
    private String classAddress;

    @Column(name = "teacher_name")
    private String teacherName;

    @Column(name = "start")
    private int start;

    @Column(name = "length")
    private int length;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

}
