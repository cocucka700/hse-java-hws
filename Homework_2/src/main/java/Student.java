public class Student {
    private final String name;
    private final String surname;
    private byte mark = 0;
    private boolean attendance = false;
    private boolean markIsChanged = false;

    public Student(String name, String surname) {
        this.name = name;
        this.surname = surname;
        this.mark = 0;
        this.attendance = false;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public byte getMark() {
        return mark;
    }

    public void setMark(byte mark) {
        if (mark > 10 || mark < 0) {
            throw new IllegalArgumentException("Некорректная оценка! Проверьте, что она входит в диапазон [0; 10].");
        }
        this.mark = mark;
    }

    public void setAttendance(boolean attendance) {
        this.attendance = attendance;
    }

    public boolean getAttendance() {
        return attendance;
    }

    public void setMarkIsChanged(boolean markIsChanged) {
        this.markIsChanged = markIsChanged;
    }

    public boolean getMarkIsChanged() {
        return markIsChanged;
    }
}
