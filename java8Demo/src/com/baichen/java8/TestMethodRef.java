package com.baichen.java8;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

/*
 * 一、方法引用：
 * 若 Lambda 体中的功能，已经有方法提供了实现，可以使用方法引用（可以将方法引用理解为 Lambda 表达式的另外一种表现形式）
 * 主要有三种语法格式：
 * 1. 对象的引用 :: 实例方法名
 * 2. 类名 :: 静态方法名
 * 3. 类名 :: 实例方法名
 *
 * 注意：
 * 	 ①方法引用所引用的方法的参数列表与返回值类型，需要与函数式接口中抽象方法的参数列表和返回值类型保持一致！
 * 	 ②若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，格式： ClassName::MethodName
 * 二、构造器引用 :构造器的参数列表，需要与函数式接口中参数列表保持一致！
 * 1. 类名 :: new
 * 三、数组引用
 * 	类型[] :: new;
 */
public class TestMethodRef {
    //数组引用
    @Test
    public void test8() {
        Function<Integer, String[]> fun = (args) -> new String[args];
        String[] strs = fun.apply(10);
        System.out.println(strs.length);
        System.out.println("--------------------------");
        Function<Integer, Employee[]> fun2 = Employee[]::new;
        Employee[] emps = fun2.apply(20);
        System.out.println(emps.length);
    }

    //构造器引用
    @Test
    public void test7() {
//      构造器的参数列表，需要与函数式接口中参数列表保持一致
        Function<String, Employee> fun = Employee::new;// 供给型接口
        BiFunction<String, Integer, Employee> fun2 = Employee::new;
        //有参数的构造器引用
        Function<String, Employee> fun3 = Employee::new;
        Employee employee = fun3.apply("小明");// 调用了只包含了name属性的构造器,根据参数类型判断
        System.out.println(employee);
    }

    //没有参数的构造器引用
    @Test
    public void test6() {
        Supplier<Employee> sup = () -> new Employee();
        System.out.println(sup.get());
        System.out.println("------------------------------------");
        Supplier<Employee> sup2 = Employee::new;
        System.out.println(sup2.get());
    }

    //类名 :: 实例方法名
    @Test
    public void test5() {
        BiPredicate<String, String> bp = (x, y) -> x.equals(y);
        System.out.println(bp.test("abcde", "abcde"));
        System.out.println("-----------------------------------------");
// 若Lambda 的参数列表的第一个参数，是实例方法的调用者，第二个参数(或无参)是实例方法的参数时，
// 格式： ClassName::MethodName(即这里相当于是abc.equals(abc))
        BiPredicate<String, String> bp2 = String::equals;
        System.out.println(bp2.test("abc", "abc"));
        System.out.println("-----------------------------------------");
        Function<Employee, String> fun = (e) -> e.show();
        System.out.println(fun.apply(new Employee()));
        System.out.println("-----------------------------------------");
        Function<Employee, String> fun2 = Employee::show;
        System.out.println(fun2.apply(new Employee()));

    }

    //类名 :: 静态方法名
    @Test
    public void test4() {
        Comparator<Integer> com = (x, y) -> Integer.compare(x, y);
        System.out.println("-------------------------------------");
        Comparator<Integer> com2 = Integer::compare;
    }

    @Test
    public void test3() {
        BiFunction<Double, Double, Double> fun = (x, y) -> Math.max(x, y);
        System.out.println(fun.apply(1.5, 22.2));
        System.out.println("--------------------------------------------------");
        BiFunction<Double, Double, Double> fun2 = Math::max;
        System.out.println(fun2.apply(1.2, 1.5));
    }

    //对象的引用 :: 实例方法名
    @Test
    public void test2() {
        Employee emp = new Employee(101, "张三", 18, 9999.99);
        Supplier<String> sup = () -> emp.getName();
        System.out.println(sup.get());
        System.out.println("----------------------------------");
        Supplier<Integer> sup2 = emp::getAge;// 参数类型和返回值类型要一致
        System.out.println(sup2.get());
    }

    @Test
    public void test1() {
        PrintStream ps = System.out;
        Consumer<String> con = (str) -> ps.println(str);
        con.accept("Hello World！");
        System.out.println("--------------------------------");
        // 方法引用，参数类型和返回体类型要一致(这里是accept方法)println是实例方法
        Consumer<String> con2 = ps::println;
        con2.accept("Hello Java8！");
        Consumer<String> con3 = System.out::println;
        con3.accept("这也是方法引用");
    }
}
