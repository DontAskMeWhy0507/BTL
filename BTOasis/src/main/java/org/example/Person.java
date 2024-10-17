package org.example;
public class Person {
    private String name;
    private String address;

    /**
     * Constructor 2 tham so.
     * @param name ten.
     * @param address dia chi.
     */
    public Person(String name, String address) {
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * Phuong thuc tra ve thong tin cua doi tuong.
     * @return thong tin doi tuong.
     */
    public String toString() {
        return "Person[name=" + name + ",address=" + address + "]";
    }
}