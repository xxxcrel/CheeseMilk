package beer.cheese.model.entity;

import javax.persistence.*;

@Entity
@Table(name ="course")
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public String getWeekNumber() {
        return weekNumber;
    }

    public void setWeekNumber(String weekNumber) {
        this.weekNumber = weekNumber;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassAddress() {
        return classAddress;
    }

    public void setClassAddress(String classAddress) {
        this.classAddress = classAddress;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
