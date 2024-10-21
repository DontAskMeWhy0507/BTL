package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Solution {
    public double tuso;
    public double mauso;

    public double giatriPhanSo() {
        if(mauso == 0) {
            throw new IllegalArgumentException("Mau so khong the bang 0");
        }
         else {
             return tuso / mauso;
        }
    }
    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.tuso = 10;
        solution.mauso = 0;
        try {
            System.out.println(solution.giatriPhanSo());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}