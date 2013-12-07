/**
 * Copyright (c) 2008-2013 FeiLong, Inc. All Rights Reserved.
 * <p>
 * 	This software is the confidential and proprietary information of FeiLong Network Technology, Inc. ("Confidential Information").  <br>
 * 	You shall not disclose such Confidential Information and shall use it 
 *  only in accordance with the terms of the license agreement you entered into with FeiLong.
 * </p>
 * <p>
 * 	FeiLong MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE SOFTWARE, EITHER EXPRESS OR IMPLIED, 
 * 	INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * 	PURPOSE, OR NON-INFRINGEMENT. <br> 
 * 	FeiLong SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * 	THIS SOFTWARE OR ITS DERIVATIVES.
 * </p>
 */
package com.feilong.commons.core.util;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import com.feilong.commons.core.entity.JoinStringEntity;

/**
 * 数组工具类
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2010-4-16 下午01:00:27
 * @since 1.0
 */
public final class ArrayUtil{

	/** Don't let anyone instantiate this class. */
	private ArrayUtil(){}

	/**
	 * 将数组转成转成Iterator<br>
	 * 如果我们幸运的话，它是一个对象数组,我们可以遍历并with no copying<br>
	 * 否则,异常 ClassCastException 中 ,Rats -- 它是一个基本类型数组,循环放入arrayList 转成arrayList.iterator()
	 * 
	 * @param arrays
	 *            数组,可以是 对象数组,或者是 基本类型数组
	 * @return Iterator
	 */
	@SuppressWarnings("unchecked")
	public static <T> Iterator<T> toIterator(Object arrays){
		Iterator<T> iterator = null;
		try{
			// 如果我们幸运的话，它是一个对象数组,我们可以遍历并with no copying
			iterator = (Iterator<T>) Arrays.asList((Object[]) arrays).iterator();
		}catch (ClassCastException e){
			// Rats -- 它是一个基本类型数组
			int length = Array.getLength(arrays);
			ArrayList<Object> arrayList = new ArrayList<Object>(length);
			for (int i = 0; i < length; ++i){
				arrayList.add(Array.get(arrays, i));
			}
			iterator = (Iterator<T>) arrayList.iterator();
		}
		return iterator;
	}

	/**
	 * 数组转成LinkedList
	 * 
	 * @param arrays
	 *            字符串数组
	 * @return 数组转成LinkedList<br>
	 *         如果 arrays isNotNullOrEmpty,返回null
	 */
	public static <T> LinkedList<T> toLinkedList(T[] arrays){
		if (Validator.isNotNullOrEmpty(arrays)){
			LinkedList<T> list = new LinkedList<T>();
			for (int i = 0, j = arrays.length; i < j; ++i){
				list.add(arrays[i]);
			}
			return list;
		}
		return null;
	}

	/**
	 * 数组转成 List(ArrayList)
	 * 
	 * @param arrays
	 *            T数组
	 * @return 数组转成 List(ArrayList)<br>
	 *         如果 Validator.isNullOrEmpty(arrays) return null
	 */
	public static <T> List<T> toList(T[] arrays){
		if (Validator.isNullOrEmpty(arrays)){
			return null;
		}
		return Arrays.asList(arrays);
	}

	/**
	 * 任意的数组转成Integer 数组
	 * 
	 * @param objects
	 *            objects
	 * @return Validator.isNotNullOrEmpty(objects)则返回null<br>
	 *         一旦其中有值转换不了integer,则出现参数异常
	 */
	public static Integer[] toIntegers(Object[] objects){
		if (Validator.isNotNullOrEmpty(objects)){
			int length = objects.length;
			Integer[] integers = new Integer[length];
			for (int i = 0; i < length; i++){
				integers[i] = ObjectUtil.toInteger(objects[i]);
			}
			return integers;
		}
		return null;
	}

	/**
	 * 判断 一个数组中,是否包含某个特定的值<br>
	 * 使用equals 来比较,所以如果是 对象类型 需要自己实现equals方法
	 * 
	 * @param arrays
	 *            数组
	 * @param value
	 *            特定值
	 * @return 如果 Validator.isNotNullOrEmpty(array) 返回false <br>
	 *         如果包含,则返回true,否则返回false<br>
	 */
	public static <T> boolean isContain(T[] arrays,T value){
		if (Validator.isNotNullOrEmpty(arrays)){
			for (T arr : arrays){
				if (arr.equals(value)){
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 将数组 通过 joinStringEntity 拼接成 字符串
	 * 
	 * @param arrays
	 *            请使用包装类型,比如 Integer []arrays,而不是 int []arrays
	 * @param joinStringEntity
	 * @return <ul>
	 *         <li>如果 arrays 是null 或者Empty ,返回null</li>
	 *         <li>否则循环,拼接joinStringEntity.getConnector()</li>
	 *         </ul>
	 */
	public static <T> String toString(T[] arrays,JoinStringEntity joinStringEntity){
		if (Validator.isNotNullOrEmpty(arrays)){

			StringBuilder sb = new StringBuilder();
			for (int i = 0, j = arrays.length; i < j; ++i){
				T t = arrays[i];

				sb.append(t);
				// 不是最后一个 拼接
				if (i != j - 1){
					sb.append(joinStringEntity.getConnector());
				}
			}
			return sb.toString();
		}

		return null;

	}
}