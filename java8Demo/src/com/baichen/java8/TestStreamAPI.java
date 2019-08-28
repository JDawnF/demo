package com.baichen.java8;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

/*
 * 一、Stream API 的操作步骤：
 * 1. 创建 Stream
 * 2. 中间操作
 * 3. 终止操作(终端操作)
 */
public class TestStreamAPI {

    //1. 创建 Stream，有4种方式
    @Test
    public void test1() {
        //1. Collection 提供了两个方法  stream() 与 parallelStream()[并行流]
        List<String> list = new ArrayList<>();
        Stream<String> stream = list.stream(); //获取一个顺序流
        Stream<String> parallelStream = list.parallelStream(); //获取一个并行流

        //2. 通过 Arrays 中的 stream() 获取一个数组流,流类型跟数组类型一样
        Integer[] nums = new Integer[10];
        Stream<Integer> stream1 = Arrays.stream(nums);

        //3. 通过 Stream 类中静态方法 of()
        Stream<Integer> stream2 = Stream.of(1, 2, 3, 4, 5, 6);

        //4. 创建无限流
        //迭代，seed是起始值
        Stream<Integer> stream3 = Stream.iterate(0, (x) -> x + 2).limit(10);
        stream3.forEach(System.out::println);

        //生成
        Stream<Double> stream4 = Stream.generate(Math::random).limit(2);
        stream4.forEach(System.out::println);

    }

    //2. 中间操作
    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66),
            new Employee(101, "张三", 18, 9999.99),
            new Employee(103, "王五", 28, 3333.33),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(104, "赵六", 8, 7777.77),
            new Employee(105, "田七", 38, 5555.55)
    );
	/*
	  筛选与切片
		filter——接收 Lambda ， 从流中排除某些元素。
		limit——截断流，使其元素不超过给定数量。
		skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
		distinct——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素，需要重写这两个方法
	 */

    //内部迭代(遍历数据)：迭代操作 Stream API 内部完成
    @Test
    public void test2() {
        //中间操作，所有的中间操作不会做任何的处理
        Stream<Employee> stream = emps.stream()
                .filter((e) -> {// 过滤操作
                    System.out.println("测试中间操作");
                    return e.getAge() <= 35;
                });
        //终止操作，只有当做终止操作时，所有的中间操作会一次性的全部执行，称为“惰性求值”
        stream.forEach(System.out::println);
    }

    //外部迭代
    @Test
    public void test3() {
        Iterator<Employee> it = emps.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }

    //    截断流，使其元素不超过给定数量
    @Test
    public void test4() {
        emps.stream()
                .filter((e) -> {
                    System.out.println("短路！"); // &&  ||
                    return e.getSalary() >= 5000;
                }).limit(3)// 只迭代3次
                .forEach(System.out::println);// 终止操作
    }

    // 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
    @Test
    public void test5() {
        emps.parallelStream()
                .filter((e) -> e.getSalary() >= 5000)
                .skip(2)
                .forEach(System.out::println);
    }

    @Test
    public void test6() {
        emps.stream()
                .distinct()// 去重
                .forEach(System.out::println);
    }

    /**
     * 映射
     * map -- 接收Lambda，将元素转换成其他形式或提取信息。接受一个函数作为参数，
     * 该函数会被应用到每个元素上，并将其映射成一个新的元素
     * flatMap -- 接受一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流
     * 这两个类似于List中的add和addAll，前者是添加了一个list，后者是将一个list中的元素一个个取出添加到list
     */
    @Test
    public void test7() {
        List<String> list = Arrays.asList("aaa", "bbb", "ccc");
        list.stream()
                .map((str) -> str.toUpperCase())    // 转为大写
                .forEach(System.out::println);

        System.out.println("-------------------------------");
//        emps.stream().map(Employee::getName).forEach(System.out::println);// 获取员工姓名
        // 嵌套流，最里层是字符
//        Stream<Stream<Character>> stream = list.stream().map(TestStreamAPI::filterCharacter);
//        stream.forEach((sm) -> sm.forEach(System.out::println));//遍历流和字符串

        //将所有流转为一个流
        Stream<Character> stream1 = list.stream().flatMap(TestStreamAPI::filterCharacter);
        stream1.forEach(System.out::println);
    }

    // 将字符串转为字符
    public static Stream<Character> filterCharacter(String str) {
        List<Character> list = new ArrayList<>();

        for (Character ch : str.toCharArray()) {
            list.add(ch);
        }
        return list.stream();
    }

    /*
		sorted()——自然排序(Comparable)
		sorted(Comparator com)——定制排序(Comparator,即自定义排序)
	 */
    @Test
    public void test8() {
        emps.stream()
                .map(Employee::getName)
                .sorted()
                .forEach(System.out::println);

        System.out.println("------------------------------------");

        emps.stream()
                .sorted((x, y) -> {
                    if (x.getAge() == y.getAge()) {//先按年龄排，然后再按姓名排
                        return x.getName().compareTo(y.getName());
                    } else {
                        return Integer.compare(x.getAge(), y.getAge());
                    }
                }).forEach(System.out::println);
    }
}
