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

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.entity.JoinStringEntity;
import com.feilong.test.User;

/**
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-12 上午11:29:02
 * @since 1.0
 */
public class ArrayUtilTest{

	private static final Logger	log	= LoggerFactory.getLogger(ArrayUtilTest.class);

	@Test
	public final void test(){
		String[] aStrings = new String[2];
		aStrings[0] = "a";
		aStrings[1] = "b";
		System.out.println(Arrays.toString(aStrings));
		System.out.println(ArrayUtils.toString(aStrings));
		String aString = "FACTORY_ID,SHOE_NAME,CHANNEL,PRODUCT_CODE,COLOR_CODE,PAYTYPE,FACTORY_CODE,TRACKING_NO_UPS,MH_ID,DEVICE,SUB_TOTAL_PRICE,TAX_PRICE,DELIVERY_PRICE,TOTAL_PRICE,PAY_DATE,REVENUE_DATE,RETURN_DATE,CANCEL_DATE,SHOP_NAME,CALCEL_CODE";
		System.out.println(ListUtil.toString(Arrays.asList(aString.split(",")), true));
	}

	/**
	 * 数组和list 转换
	 */
	@Test
	public final void arrayAndList(){

		String aString = "FACTORY_ID,SHOE_NAME,CHANNEL,PRODUCT_CODE,COLOR_CODE,PAYTYPE,FACTORY_CODE,TRACKING_NO_UPS,MH_ID,DEVICE,SUB_TOTAL_PRICE,TAX_PRICE,DELIVERY_PRICE,TOTAL_PRICE,PAY_DATE,REVENUE_DATE,RETURN_DATE,CANCEL_DATE,SHOP_NAME,CALCEL_CODE";
		List<String> asList = Arrays.asList(aString.split(","));
		System.out.println(ListUtil.toString(asList, true));

		String[] array = asList.toArray(new String[0]);

		for (String string : array){
			log.info(string);
		}

		User[] users = { new User(5L), new User(6L) };
		List<User> list = Arrays.asList(users);
		for (User user : list){
			log.info(user.getId() + "");
		}

		User[] usersarray = list.toArray(new User[0]);

		for (User user : usersarray){
			log.info(user.getId() + "");
		}
	}

	@Test
	public final void testisContainValue(){
		Assert.assertEquals(true, ArrayUtil.isContain(new Integer[] { 1, 223 }, 1));
		Assert.assertEquals(true, ArrayUtil.isContain(new Long[] { 1L, 223L }, 1L));
		String[] array = new String[] { "1", "223" };
		Assert.assertEquals(true, ArrayUtil.isContain(array, "1"));
		//
		ArrayUtils.add(array, "");
	}

	@Test
	public final void convertListToStringReplaceBrackets(){
		String[] array = new String[] { "1", "223" };
		log.info(array.toString());
		log.info(Arrays.toString(array));
		log.info(StringUtils.join(array, ","));
	}

	@Test
	public final void toIterator(){
		String[] array = { "1", "223" };
		int[] arrays = { 1, 2 };
		Iterator<String> iterator = ArrayUtil.toIterator(array);

		while (iterator.hasNext()){
			Object object = (Object) iterator.next();
			log.debug("{}", object);
		}
	}

	@Test
	public final void toIteratorUser(){
		User user1 = new User();
		user1.setId(1L);
		User user2 = new User();
		user2.setId(2L);

		User[] users = { user1, user2 };

		Iterator<User> iterator = ArrayUtil.toIterator(users);

		while (iterator.hasNext()){
			User user = (User) iterator.next();
			log.debug("{}", user.getId());
		}
	}

	@Test
	public final void toLinkedList(){
		User user1 = new User();
		user1.setId(1L);
		User user2 = new User();
		user2.setId(2L);

		User[] users = { user1, user2 };
		LinkedList<User> list = ArrayUtil.toLinkedList(users);
		log.info(JsonFormatUtil.format(list));
	}

	@Test
	public void toList(){
		User user1 = new User();
		user1.setId(1L);
		User user2 = new User();
		user2.setId(2L);

		User[] users = { user1, user2 };

		List<User> list = ArrayUtil.toList(users);
		log.info(JsonFormatUtil.format(list));

	}

	@Test
	public void toString1(){
		Object[] arrays = { "222", "1111" };
		JoinStringEntity joinStringEntity = new JoinStringEntity(",");
		log.info(ArrayUtil.toString(arrays, joinStringEntity));

		Integer[] array1 = { 2, 1 };
		log.info(ArrayUtil.toString(array1, joinStringEntity));

	}
}
