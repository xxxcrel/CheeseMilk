package beer.cheese.entity;

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

    private String semester;

    private String weekNumber;

    private String className;

    private String classAddress;

    private String teacherName;

    private int start;

    private int length;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

}
