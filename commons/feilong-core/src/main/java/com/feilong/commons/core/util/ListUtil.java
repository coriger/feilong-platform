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
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * list工具类
 * 
 * @author 金鑫 2010-3-2 下午03:20:12
 * @since 1.0
 */
public final class ListUtil{

	private static final Logger	log	= LoggerFactory.getLogger(ListUtil.class);

	/** Don't let anyone instantiate this class. */
	private ListUtil(){}

	/**
	 * iterator是否包含某个值
	 * 
	 * @param iterator
	 *            iterator
	 * @param value
	 *            value
	 * @return iterator是否包含某个值,如果iterator为null/empty,则返回false
	 */
	public static boolean isContain(Iterator<?> iterator,Object value){
		boolean flag = false;
		if (Validator.isNotNullOrEmpty(iterator)){
			Object object = null;
			while (iterator.hasNext()){
				object = iterator.next();
				if (object.toString().equals(value.toString())){
					flag = true;
					break;
				}
			}
		}else{
			log.debug("iterator is null/empty");
		}
		return flag;
	}

	/**
	 * 用于 自定义标签/ 自定义el
	 * 
	 * @param collection
	 *            一个集合,将会被转成Iterator,可以为逗号隔开的字符串,会被分隔成Iterator.
	 * @param value
	 *            任意类型的值,最终toString 判断比较.
	 * @return
	 */
	public static boolean _isContainTag(Object collection,Object value){
		@SuppressWarnings("rawtypes")
		Iterator iterator = ObjectUtil.toIterator(collection);
		return ListUtil.isContain(iterator, value);
	}

	/**
	 * 集合转成数组
	 * 
	 * @param list
	 *            list
	 * @return 数组
	 */
	@SuppressWarnings("unchecked")
	public static <T> T[] toArray(List<T> list){
		if (Validator.isNullOrEmpty(list)){
			return null;
		}
		Class<? extends Object> firstClass = list.get(0).getClass();
		Class<? extends Object> compontType = firstClass;
		// // 所有类型是不是同一个
		// boolean isAllSameClass = true;
		// for (T t : list){
		// if (t.getClass() != firstClass){
		// isAllSameClass = false;
		// break;
		// }
		// }
		//
		// // 不是
		// if (!isAllSameClass){
		// compontType = Object.class;
		// }

		T[] array = (T[]) Array.newInstance(compontType, list.size());
		return (T[]) list.toArray(array);
	}

	/**
	 * 获得 list第一个元素
	 * 
	 * @param list
	 *            list
	 * @return 第一个元素,<br>
	 *         空list返回null
	 */
	public static <T> T getFirstItem(List<T> list){
		if (Validator.isNotNullOrEmpty(list)){
			return list.get(0);
		}
		return null;
	}

	// ***********************************************************************************
	/**
	 * list集合转换成字符串,仅将[]中括号符号 换成()小括号,其余不动<br>
	 * 如:
	 * 
	 * <pre>
	 * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
	 * testList.add(&quot;xinge&quot;);
	 * testList.add(&quot;feilong&quot;);
	 * 
	 * toStringReplaceBrackets(testList)----->(xinge, feilong)
	 * </pre>
	 * 
	 * @param list
	 *            list集合
	 * @return list集合转换成字符串,仅将[]中括号符号 换成()小括号,其余不动
	 */
	public static String toStringReplaceBrackets(List<String> list){
		return list.toString().replace('[', '(').replace(']', ')');
	}

	/**
	 * list集合转换成字符串,移除[]中括号符号,并去除空格 <br>
	 * 如:
	 * 
	 * <pre>
	 * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
	 * testList.add(&quot;xinge&quot;);
	 * testList.add(&quot;feilong&quot;);
	 * 
	 * toStringRemoveBrackets(testList)----->xinge,feilong
	 * </pre>
	 * 
	 * @param list
	 *            list集合
	 * @return list集合转换成字符串,移除[]中括号符号,并去除空格
	 */
	public static String toStringRemoveBrackets(List<String> list){
		String s = list.toString();
		return s.substring(1, s.length() - 1).replaceAll(" ", "");
	}

	/**
	 * list集合转换成字符串 如:
	 * 
	 * <pre>
	 * List&lt;String&gt; testList = new ArrayList&lt;String&gt;();
	 * testList.add(&quot;xinge&quot;);
	 * testList.add(&quot;feilong&quot;);
	 * 
	 * toString(testList,true)----->'xinge','feilong'
	 * toString(testList,false)----->xinge,feilong
	 * </pre>
	 * 
	 * @param list
	 *            list集合
	 * @param isAddQuotation
	 *            是否增加单引号
	 * @return list集合转换成字符串
	 */
	public static String toString(List<String> list,boolean isAddQuotation){
		String returnValue = toStringRemoveBrackets(list);
		if (isAddQuotation){
			returnValue = "'" + returnValue.replaceAll(",", "','") + "'";
		}
		return returnValue;
	}

	/**
	 * 解析对象集合,取到对象特殊字段值,拼成集合
	 * 
	 * @param objectList
	 *            对象集合
	 * @param field
	 *            指定字段
	 * @return 如果 objectList是null/empty 返回null
	 */
	@SuppressWarnings("unchecked")
	public static <T, O> List<T> getFieldValueList(List<O> objectList,String field){
		if (Validator.isNotNullOrEmpty(objectList)){
			List<T> _list = new LinkedList<T>();
			for (O object : objectList){
				try{
					T property = (T) PropertyUtils.getProperty(object, field);
					_list.add(property);
				}catch (IllegalAccessException e){
					e.printStackTrace();
				}catch (InvocationTargetException e){
					e.printStackTrace();
				}catch (NoSuchMethodException e){
					e.printStackTrace();
				}
			}
			return _list;
		}
		return null;
	}
}