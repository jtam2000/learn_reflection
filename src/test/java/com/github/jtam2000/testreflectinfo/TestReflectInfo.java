package com.github.jtam2000.testreflectinfo;

import com.github.jtam2000.classdata.Employee;
import com.github.jtam2000.classdata.Person;
import com.github.jtam2000.reflectinfo.ReflectInfo;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestReflectInfo {


    @Test
    public void test_GetFieldsFromClass() {

        List<Field> fields = ReflectInfo.getFields(Person.class);
        System.out.println("Person Class - fields :");
        printFieldsDetails(fields);

    }

    @Test
    public void test_GetDeclaredFieldsFromClass() {

        List<Field> fields = ReflectInfo.getDeclaredFields(Person.class);
        System.out.println("Person Class - declared fields :");
        printFieldsDetails(fields);

    }

    private void printFieldsDetails(List<Field> fields) {
        fields.forEach(f -> System.out.println(
                "\t" +
                        Modifier.toString(f.getModifiers()) + " " +
                        f.getType().getSimpleName() + " " +
                        f.getName())
        );
    }


    @Test
    public void test_GetDeclaredMethods() {

        //return all Public, Protected, Private and Package scoped methods in this class.
        //NOT the super hierarchy
        List<Method> methods = ReflectInfo.getDeclaredMethods(Employee.class);
        methods.forEach(System.out::println);

    }

    @Test
    public void test_GetMethods() {

        // return PUBLIC  methods in this and its object hierarchy
        // NOtes: does not include: Static method in super interfaces

        List<Method> methods = ReflectInfo.getMethods(Employee.class);
        methods.forEach(System.out::println);
    }

    @Test
    public void test_getMethodParameters() {

        final List<String> params = new LinkedList<>();
        List<Method> methods = ReflectInfo.getMethods(Person.class);


        methods.stream()
                .filter(m -> m.getName().equals("methodWithParameters")).limit(1)
                .forEach(m -> params.addAll(ReflectInfo.getMethodParameters(m)));

        List<String> expected = List.of("int myInt", "String myString", "Instant instant");
        assertEquals(expected, params);

    }

    @Test
    public void test_getMethodReturnType() {

        List<Method> method = ReflectInfo.getMethodsByName(Person.class, "methodReturnsDouble");

        String returnType = method.get(0).getReturnType().getSimpleName();

        System.out.println("return type => " + returnType);

        assertEquals("double", returnType);

    }

    @Test
    public void test_getMethodDetails() {

        ReflectInfo.printMethodDetails(Person.class, "methodWithParameters");
        ReflectInfo.printMethodDetails(Person.class, "staticMethodReturnVoid");
        ReflectInfo.printMethodDetails(Person.class, "methodReturnsDouble");


    }


    @Test
    public void test_getConstructors() {
        System.out.println("Listing of Person Constructors");
        List<Constructor<?>> constructors = ReflectInfo.getConstructors(Person.class);
        assert constructors != null;
        constructors.forEach(System.out::println);

        System.out.println("Person Constructor Details");
        constructors.forEach(ReflectInfo::printMethodDetails);

    }
}
