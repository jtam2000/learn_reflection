package com.github.jtam2000.classdata;

import com.github.jtam2000.annotation.DefaultPersonValues;
import com.github.jtam2000.reflectinfo.ReflectInfo;

import java.lang.reflect.Method;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.atomic.AtomicInteger;

@SuppressWarnings({"FieldMayBeFinal", "unused", "FieldCanBeLocal"})
public class Person {

    private AtomicInteger idGenerator = new AtomicInteger(0);
    private int primaryKey;

    private String name;
    private int age;

    private final int DEFAULT_AGE = 1;

    private final String DEFAULT_NAME = "<anonymous>";

    public String publicTag = "UNTAGGED";
    public Person(String name, int age) {
        primaryKey = idGenerator.incrementAndGet();
        this.name = name;
        this.age = age;
    }

    public Person(String name) {
        primaryKey = idGenerator.incrementAndGet();
        this.name = name;
        this.age = DEFAULT_AGE;
    }

    public Person() {
        primaryKey = idGenerator.incrementAndGet();
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

    public void methodWithParameters(int myInt, String myString, Instant instant) throws IllegalArgumentException {

        System.out.printf("parameter values => %d : %s : %t %n" + myInt + myString + instant);
        if (myInt < 0)
            throw new IllegalArgumentException("You can not have negative input values");
    }

    @SuppressWarnings("UnusedReturnValue")
    @DefaultPersonValues(name = "Inserted Value from Annotation")
    public double methodReturnsDouble(int myInt, String myString, Instant instant) {

        System.out.printf("parameter values => %d : %s : %s %n",myInt, myString, instant);

        Method method=ReflectInfo.getFirstMethodByName(this.getClass(),"methodReturnsDouble");

        DefaultPersonValues annotation = method.getAnnotation(DefaultPersonValues.class);

        if(annotation !=null){
            Person person = new Person(annotation.name(), annotation.age());
            System.out.println("Person from Constructed by Annotation:" + person);
        }
        return 12D;
    }

    private double privateMethodReturnDouble(String myString) {
        System.out.println("input: " + myString);
        return 12D;
    }

    @Override
    public String toString() {
        return String.format("Person:%n\tname: %s%n\tage: %d%n\tid: %d%n",
                name,age, primaryKey);

    }

    protected double protectedMethod(String input) {
        System.out.println("input:  " + input);
        return 12D;
    }


}
