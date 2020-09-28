package beer.cheese.model.dto;

public class CourseDTO {

    private String semester;

    private String weekNumber;

    private String className;

    private String classAddress;

    private String teacherName;

    private int start;

    private int length;

    public CourseDTO(){}

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
}
