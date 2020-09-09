package com.github.jtam2000.classdata;

@SuppressWarnings("unused")
public class Employee extends Person{

    public String getCompanyName() {
        return companyName;
    }

    private final String companyName;

    Employee(String name, int age, String companyName) {

        super(name, age);
                this.companyName = companyName;
    }

    public double companyName() {
        System.out.println("Company Name => " + companyName);
        return 12D;
    }

    private int privateCompanyMethod(int input) {

        input++;
        return input;
    }

}
