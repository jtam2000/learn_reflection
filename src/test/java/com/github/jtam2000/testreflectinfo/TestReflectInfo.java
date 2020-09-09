package com.github.jtam2000.testreflectinfo;

import com.github.jtam2000.annotation.DefaultPersonValues;
import com.github.jtam2000.classdata.Employee;
import com.github.jtam2000.classdata.Person;
import com.github.jtam2000.reflectinfo.ReflectInfo;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.github.jtam2000.reflectinfo.ReflectInfo.MethodDetail.*;
import static org.junit.Assert.assertEquals;

public class TestReflectInfo {

    final static String addTab = "\t";

    @Test
    public void test_GetFieldsFromClass() {

        List<Field> fields = ReflectInfo.getFields(Person.class);
        System.out.println("Person Class - fields :");
        List<String> actual = getFieldDetails(fields);
        System.out.println(actual);
        assertEquals("public String publicTag", actual.get(0).strip());

    }

    @Test
    public void test_GetDeclaredFieldsFromClass() {

        List<Field> fields = ReflectInfo.getDeclaredFields(Person.class);
        System.out.println("Person Class - declared fields :");
        getFieldDetails(fields).forEach(System.out::println);

        List<String> actual = ReflectInfo.getFieldDetails(fields);
        List<String> expected = List.of(
                "private AtomicInteger idGenerator",
                "private int primaryKey",
                "private String name",
                "private int age",
                "private final int DEFAULT_AGE",
                "private final String DEFAULT_NAME",
                "public String publicTag"
        );
        assertEquals(expected, actual);
    }

    private List<String> getFieldDetails(List<Field> fields) {

        return ReflectInfo.getFieldDetails(fields).stream()
                .map(f -> addTab + f)
                .collect(Collectors.toList());
    }


    @Test
    public void test_GetDeclaredMethods() {

        //return all Public, Protected, Private and Package scoped methods in this class.
        //NOT the super hierarchy
        List<Method> methods = ReflectInfo.getDeclaredMethods(Employee.class);
        List<String> expected = List.of(
                "public double com.github.jtam2000.classdata.Employee.companyName()",
                "public java.lang.String com.github.jtam2000.classdata.Employee.getCompanyName()",
                "private int com.github.jtam2000.classdata.Employee.privateCompanyMethod(int)"
        );
        methods.forEach(System.out::println);
        List<String> actual = methods.stream().map(Method::toString).collect(Collectors.toList());
        assertEquals(expected, actual);

        List<String> expectedSimpleName = List.of("companyName", "getCompanyName", "privateCompanyMethod");
        List<String> actualMethodName = methods.stream().map(Method::getName).collect(Collectors.toList());
        assertEquals(expectedSimpleName, actualMethodName);
        actualMethodName.forEach(System.out::println);

    }

    @Test
    public void test_GetMethods() {

        // return PUBLIC  methods in this and its object hierarchy
        // NOtes: does not include: Static method in super interfaces

        List<Method> methods = ReflectInfo.getMethods(Employee.class);
        methods.forEach(System.out::println);
        List<String> actual = methods.stream().map(Method::toString).collect(Collectors.toList());

        List<String> expected = List.of(
                "public double com.github.jtam2000.classdata.Employee.companyName()",
                "public java.lang.String com.github.jtam2000.classdata.Employee.getCompanyName()",
                "public static java.time.LocalDateTime com.github.jtam2000.classdata.Person.staticMethodReturnLocalDateTime(java.lang.String[])",
                "public double com.github.jtam2000.classdata.Person.methodReturnsDouble(int,java.lang.String,java.time.Instant)",
                "public void com.github.jtam2000.classdata.Person.methodWithParameters(int,java.lang.String,java.time.Instant) throws java.lang.IllegalArgumentException",
                "public static void com.github.jtam2000.classdata.Person.staticMethodReturnVoid(java.lang.String[])",
                "public java.lang.String com.github.jtam2000.classdata.Person.toString()",
                "public final native void java.lang.Object.wait(long) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait(long,int) throws java.lang.InterruptedException",
                "public final void java.lang.Object.wait() throws java.lang.InterruptedException",
                "public boolean java.lang.Object.equals(java.lang.Object)",
                "public native int java.lang.Object.hashCode()",
                "public final native java.lang.Class java.lang.Object.getClass()",
                "public final native void java.lang.Object.notify()",
                "public final native void java.lang.Object.notifyAll()"
        );

        IntStream.range(0, actual.size())
                .forEach(i -> assertEquals(expected.get(i), actual.get(i)));

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

        Map<ReflectInfo.MethodDetail, String> expected = Map.of(
                returns, "void",
                accessor, "public",
                class_package, "package com.github.jtam2000.classdata",
                javadoc_name, "public void methodWithParameters (int myInt, String myString, Instant instant)",
                parameters, "[int myInt, String myString, Instant instant]",
                method_name, "methodWithParameters",
                class_name, "Person",
                exceptions, "IllegalArgumentException"
        );

        assertMethodDetails("methodWithParameters", expected);


        expected = Map.of(
                returns, "void",
                accessor, "public static",
                class_package, "package com.github.jtam2000.classdata",
                javadoc_name, "public static void staticMethodReturnVoid (String[] args)",
                parameters, "[String[] args]",
                method_name, "staticMethodReturnVoid",
                class_name, "Person",
                exceptions, ""
        );
        assertMethodDetails("staticMethodReturnVoid", expected);


        expected = Map.of(
                returns, "double",
                accessor, "private",
                class_package, "package com.github.jtam2000.classdata",
                javadoc_name, "private double privateMethodReturnDouble (String myString)",
                parameters, "[String myString]",
                method_name, "privateMethodReturnDouble",
                class_name, "Person",
                exceptions, ""
        );
        assertMethodDetails("privateMethodReturnDouble", expected);
    }

    private void assertMethodDetails(String methodName,
                                     Map<ReflectInfo.MethodDetail, String> expected) {

        ReflectInfo.printMethodDetails(Person.class, methodName);

        Map<ReflectInfo.MethodDetail, String> actual = ReflectInfo.getMethodDetails(
                ReflectInfo.getFirstMethodByName(Person.class, methodName));

        assertEquals(expected, actual);
        expected.forEach((k, v) -> assertEquals(actual.get(k), v));
    }


    @Test
    public void test_getConstructors() {

        System.out.println("\nListing of Person Constructors");
        List<Constructor<?>> constructors = ReflectInfo.getConstructors(Person.class);
        assert constructors != null;
        constructors.forEach(System.out::println);

        System.out.println("\n\nPerson Constructor Details:");
        constructors.forEach(ReflectInfo::printMethodDetails);


        Map<ReflectInfo.MethodDetail, String> expected = Map.of(
                accessor, "public",
                class_package, "package com.github.jtam2000.classdata",
                javadoc_name, "public Person (String name, int age)",
                parameters, "[String name, int age]",
                class_name, "Person",
                exceptions, ""
        );
        int i = 0;
        assertConstructorDetails(ReflectInfo.getMethodDetails(constructors.get(i)), expected);


        expected = Map.of(
                accessor, "public",
                class_package, "package com.github.jtam2000.classdata",
                javadoc_name, "public Person (String name)",
                parameters, "[String name]",
                class_name, "Person",
                exceptions, ""
        );
        i++;
        assertConstructorDetails(ReflectInfo.getMethodDetails(constructors.get(i)), expected);


        expected = Map.of(
                accessor, "public",
                class_package, "package com.github.jtam2000.classdata",
                javadoc_name, "public Person ()",
                parameters, "[]",
                class_name, "Person",
                exceptions, ""
        );
        i++;
        assertConstructorDetails(ReflectInfo.getMethodDetails(constructors.get(i)), expected);

    }

    private void assertConstructorDetails(Map<ReflectInfo.MethodDetail, String> actual,
                                          Map<ReflectInfo.MethodDetail, String> expected) {

        expected.forEach((k, v) -> assertEquals(v, actual.get(k)));
        assertEquals(expected, actual);
    }

    @Test
    @DefaultPersonValues
    //LEARNING: it is ok to have extra space between Annotation and the annotation target

    //Note: extra line spacing here in intentional to show Annotation can have spaces between annotation and target
    public void test_Annotation() {

        Person person = new Person();
        person.methodReturnsDouble(1, "hello", Instant.now());//this method uses the annotation

        //test the annotation on this test method
        Method method = ReflectInfo.getFirstMethodByName(this.getClass(), "test_Annotation");
        DefaultPersonValues annotation = method.getAnnotation(DefaultPersonValues.class);
        assertEquals("<Anonymous>", annotation.name());
        assertEquals(0, annotation.age());


    }
}
