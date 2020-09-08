package com.github.jtam2000.classdata;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;

@SuppressWarnings({"FieldMayBeFinal", "unused", "FieldCanBeLocal"})
public class Person {

    private  int primaryKey;
    private  String name;
    private  int age;

    private final int DEFAULT_AGE = 1;

    private final String DEFAULT_NAME = "<anonymous>";

    public String publicTag="UNTAGGED";

    public Person(int id, String name, int age) {
        primaryKey= id;
        this.name = name;
        this.age = age;
    }

    public Person(int id, String name) {
        primaryKey= id;
        this.name = name;
        this.age = DEFAULT_AGE;
    }

    public Person(int id) {
        primaryKey= id;
        this.name = DEFAULT_NAME;
        this.age = DEFAULT_AGE;
    }

    public static void staticMethodReturnVoid(String[] args) {

        System.out.printf("%nHello World - Second Application in Same Project %nApp Date => %s%n",
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));
    }

    public static LocalDateTime staticMethodReturnLocalDateTime(String[] args) {

        System.out.printf("%nHello World - Second Application in Same Project %nApp Date => %s%n",
                LocalDateTime.now().format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)));

        return LocalDateTime.now();
    }

    public void methodWithParameters(int myInt, String myString, Instant instant) throws IllegalArgumentException{

        System.out.printf("parameter values => %d : %s : %t %n" + myInt + myString + instant);
        if (myInt<0)
            throw new IllegalArgumentException("You can not have negative input values");
    }

    public double methodReturnsDouble(int myInt, String myString, Instant instant) {

        System.out.printf("parameter values => %d : %s : %t %n" + myInt + myString + instant);
        return 12D;
    }

    private double privateMethodReturnDouble(String myString) {
        System.out.println("input: " + myString);
        return 12D;
    }

    protected double protectedMethod(String input) {
        System.out.println("input:  " + input);
        return 12D;
    }



}
