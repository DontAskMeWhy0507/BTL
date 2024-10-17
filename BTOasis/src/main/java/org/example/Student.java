package org.example;

public class Student extends Person {
    private String program;
    private int year;
    private double fee;

    /**
     * Constructor voi 5 tham so.
     * @param name ten.
     * @param address dia chi.
     * @param program   chuong trinh hoc.
     * @param year nam hoc.
     * @param fee hoc phi.
     */

    public Student(String name, String address, String program, int year, double fee) {
        super(name, address);
        this.program = program;
        this.year = year;
        this.fee = fee;
    }

    public String getProgram() {
        return program;
    }

    public void setProgram(String program) {
        this.program = program;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getFee() {
        return fee;
    }

    public void setFee(double fee) {
        this.fee = fee;
    }

    /**
     * Chuyen doi doi tuong Student thanh chuoi.
     * @return chuoi chua thong tin cua sinh vien.
     */
    public String toString() {
        return "Student[" + super.toString() + ",program=" + program + ",year=" + year + ",fee=" + fee + "]";
    }
}
