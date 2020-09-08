package com.github.jtam2000.reflectinfo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.jtam2000.reflectinfo.ReflectInfo.MethodDetail.*;

public class ReflectInfo {

    public static void main(String[] args) {

        System.out.printf("%nHello World, it is now => %s%n", LocalDateTime.now().
                format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM)));
    }

    public static List<Field> getFields(Class<?> inputClass) {

        return Arrays.stream(inputClass.getFields())
                .collect(Collectors.toList());
    }


    public static List<Field> getDeclaredFields(Class<?> inputClass) {

        return Arrays.stream(inputClass.getDeclaredFields())
                .collect(Collectors.toList());

    }

    public static List<Method> getDeclaredMethods(Class<?> inputClass) {

        //returns public, protected, package, private methods in this class
        //excludes inherited methods

        return Arrays.stream(inputClass.getDeclaredMethods())
                .collect(Collectors.toList());
    }

    public static Method getFirstMethodByName(Class<?> inputClass, String methodName) {

        List<Method> methods = getMethodsByName(inputClass, methodName);

        return methods.stream()
                .filter(m -> m.getName().equals(methodName))
                .limit(1)
                .collect(Collectors.toList()).get(0);

    }

    public static List<Method> getMethods(Class<?> inputClass) {

        //return PUBLIC methods in this and its object hierarchy

        //LEARNING:  Class.getMethods are PUBLIC methods in the class's inheritance/interface hierarchy
        // including those declared by the class or interface and those inherited from superclasses and superinterfaces.
        // Static method in super interfaces are not included, read the java doc

        return Arrays.stream(inputClass.getMethods()).collect(Collectors.toList());
    }

    public static List<Method> getMethodsByName(Class<?> inputClass, String methodName) {

        return Arrays.stream(inputClass.getMethods())
                .filter(m -> m.getName().equals(methodName))
                .collect(Collectors.toList());
    }

    public static List<String> getMethodParameters(Method method) {

        Parameter[] params = method.getParameters();


        return Arrays.stream(params)
                .map(p -> p.getType().getSimpleName() + " " + p.getName())
                .collect(Collectors.toList());
    }

    public static String getModifier(int modifier) {
        return Modifier.toString(modifier);
    }

    enum MethodDetail {
        javadoc_name,
        method_name,
        accessor,
        parameters,
        returns,
        exceptions,
        class_package,
        class_name

    }

    public static Map<MethodDetail, String> getMethodDetails(Method method) {

        /* sample output

        => 	public void methodWithParameters (int myInt, String myString, Instant instant)
		package:		package com.github.jtam2000.classdata
		class:		Person
		accessor:		public
		method name:		methodWithParameters
		parameters:		[int myInt, String myString, Instant instant]
		returns:		void
		exceptions:		[]

         */


        Map<MethodDetail, String> methodDetails = new HashMap<>();

        methodDetails.put(accessor, getModifier(method.getModifiers()));
        methodDetails.put(returns, method.getReturnType().getSimpleName());
        methodDetails.put(method_name, method.getName());
        methodDetails.put(parameters, ReflectInfo.getMethodParameters(method).toString());
        methodDetails.put(class_package, method.getDeclaringClass().getPackage().toString());
        methodDetails.put(class_name, method.getDeclaringClass().getSimpleName());

        String methodName = Modifier.toString(method.getModifiers()) + " " +
                method.getReturnType().getSimpleName() + " " +
                method.getName() + " " +
                "(" + String.join(", ", ReflectInfo.getMethodParameters(method)) + ")";
        methodDetails.put(javadoc_name, methodName);

        String exceptionsString = Arrays.stream(method.getExceptionTypes()).map(Class::getSimpleName)
                .collect(Collectors.joining(","));
        methodDetails.put(exceptions, exceptionsString);

        return methodDetails;
    }

    public static void printMethodDetails(Method method) {

        Map<MethodDetail, String> details = getMethodDetails(method);
        String tabs = "\t\t";

        System.out.println("=> " + details.get(javadoc_name));
        System.out.println(tabs + "package:" + tabs + details.get(class_package));
        System.out.println(tabs + "class:" + tabs + details.get(class_name));
        System.out.println(tabs + "accessor:" + tabs + details.get(accessor));
        System.out.println(tabs + "returns:" + tabs + details.get(returns));
        System.out.println(tabs + "method name:" + tabs + details.get(method_name));
        System.out.println(tabs + "parameters:" + tabs + details.get(parameters));
        System.out.println(tabs + "exceptions:" + tabs + details.get(exceptions));

    }

    public static void printMethodDetails(Class<?> inputClass, String method) {

        printMethodDetails(getFirstMethodByName(inputClass, method));
    }

    public static List<Constructor<?>> getConstructors(Class<?> inputClass) {

        try {
            return Arrays.stream(inputClass.getConstructors()).collect(Collectors.toList());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        return null;
    }

}