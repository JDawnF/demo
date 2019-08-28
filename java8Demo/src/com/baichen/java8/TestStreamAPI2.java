package com.baichen.java8;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import com.baichen.java8.Employee.Status;

/*
 * 一、 Stream 的操作步骤
 *
 * 1. 创建 Stream
 *
 * 2. 中间操作
 *
 * 3. 终止操作
 */
public class TestStreamAPI2 {

    List<Employee> emps = Arrays.asList(
            new Employee(102, "李四", 59, 6666.66, Status.BUSY),
            new Employee(101, "张三", 18, 9999.99, Status.FREE),
            new Employee(103, "王五", 28, 3333.33, Status.VOCATION),
            new Employee(104, "赵六", 8, 7777.77, Status.BUSY),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE),
            new Employee(104, "赵六", 8, 7777.77, Status.FREE)
    );

    //3. 终止操作
	/*
		allMatch——检查是否匹配所有元素
		anyMatch——检查是否至少匹配一个元素
		noneMatch——检查是否没有匹配的元素
		findFirst——返回第一个元素
		findAny——返回当前流中的任意元素
		count——返回流中元素的总个数
		max——返回流中最大值
		min——返回流中最小值
	 */
    @Test
    public void test1() {
        boolean bl = emps.stream()
                .allMatch((e) -> e.getStatus().equals(Status.BUSY));

        System.out.println("是否匹配所有元素：" + bl);
        boolean bl1 = emps.stream()
                .anyMatch((e) -> e.getStatus().equals(Status.BUSY));
        System.out.println("是否至少匹配一个元素：" + bl1);
        boolean bl2 = emps.stream()
                .noneMatch((e) -> e.getStatus().equals(Status.BUSY));
        System.out.println("是否没有匹配的元素：" + bl2);
    }

    @Test
    public void test2() {
        Optional<Employee> op = emps.stream()//Optional可以避免空指针,空的值可以存到Optional中
                .sorted((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()))// 按工资排序
                .findFirst();
        System.out.println(op.get());
        System.out.println("--------------------------------");
        Optional<Employee> op2 = emps.parallelStream()
                .filter((e) -> e.getStatus().equals(Status.FREE))
                .findAny();// 返回任意空闲的员工
        System.out.println(op2.get());
    }

    @Test
    public void test3() {
        long count = emps.stream()
                .filter((e) -> e.getStatus().equals(Status.FREE))
                .count();
        System.out.println("空闲的员工数量：" + count);
        Optional<Double> op = emps.stream()
                .map(Employee::getSalary)
                .max(Double::compare);// 工资最高的，先用map提取工资

        System.out.println(op.get());

        Optional<Employee> op2 = emps.stream()
                .min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));// 工资最低

        System.out.println(op2.get());
    }

    //注意：流进行了终止操作后，不能再次使用
    @Test
    public void test4() {
        Stream<Employee> stream = emps.stream()
                .filter((e) -> e.getStatus().equals(Status.FREE));

        long count = stream.count();

        stream.map(Employee::getSalary)
                .max(Double::compare);
    }

    //3. 终止操作
	/*
	归约
	reduce(T identity, BinaryOperator) / reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。
	 */
    @Test
    public void test5() {// 求综合
        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);
        System.out.println("----------------------------------------");
        Optional<Double> op = emps.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op.get());
    }

    //需求：搜索名字中 “六” 出现的次数
    @Test
    public void test6() {
        Optional<Integer> sum = emps.stream()
                .map(Employee::getName)
                .flatMap(TestStreamAPI::filterCharacter)
                .map((ch) -> {
                    if (ch.equals('六'))
                        return 1;
                    else
                        return 0;
                }).reduce(Integer::sum);

        System.out.println(sum.get());
    }

    //收集：collect——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
    @Test
    public void test7() {
        List<String> list = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toList());// 收集名字
        list.forEach(System.out::println);
        System.out.println("----------------------------------");
        Set<String> set = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());//去重收集
        set.forEach(System.out::println);
        System.out.println("----------------------------------");
        HashSet<String> hs = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.toCollection(HashSet::new));
        hs.forEach(System.out::println);
    }

    @Test
    public void test8() {
        Optional<Double> max = emps.stream().map(Employee::getSalary)
                .collect(Collectors.maxBy(Double::compare));
        System.out.println(max.get());
        Optional<Employee> op = emps.stream()
                .collect(Collectors.minBy((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary())));
        System.out.println("最小值:" + op.get());
        Double sum = emps.stream()  //总数
                .collect(Collectors.summingDouble(Employee::getSalary));
        System.out.println("总和是：" + sum);
        Double avg = emps.stream()  //平均值
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println("平均值是：" + avg);
        Long count = emps.stream()
                .collect(Collectors.counting());
        System.out.println("元素个数是：" + count);
        System.out.println("--------------------------------------------");
        DoubleSummaryStatistics dss = emps.stream()//计算元素的各种结果，这里是计算最大值
                .collect(Collectors.summarizingDouble(Employee::getSalary));
        System.out.println("最大值：" + dss.getMax());
    }

    //分组
    @Test
    public void test9() {
        Map<Status, List<Employee>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));//根据状态分组
        System.out.println(map);
    }

    //多级分组,先按状态分，再按年龄分
    @Test
    public void test10() {
        Map<Status, Map<String, List<Employee>>> map = emps.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy((e) -> {
                    if (e.getAge() >= 60)
                        return "老年";
                    else if (e.getAge() >= 35)
                        return "中年";
                    else
                        return "成年";
                })));
        System.out.println(map);
    }

    //分区，满足条件的为一个区
    @Test
    public void test11() {
        Map<Boolean, List<Employee>> map = emps.stream()
                .collect(Collectors.partitioningBy((e) -> e.getSalary() >= 5000));
        System.out.println(map);
    }

    //连接
    @Test
    public void test12() {
        String str = emps.stream()
                .map(Employee::getName)
                .collect(Collectors.joining(",", "----", "----"));
        System.out.println(str);
    }

    @Test
    public void test13() {
        Optional<Double> sum = emps.stream()
                .map(Employee::getSalary)
                .collect(Collectors.reducing(Double::sum));
        System.out.println(sum.get());
    }
}
