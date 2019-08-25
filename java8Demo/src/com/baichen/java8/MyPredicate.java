package com.baichen.java8;

// 定义泛型方法,函数式接口
@FunctionalInterface
public interface MyPredicate<T> {

	public boolean test(T t);
	
}
