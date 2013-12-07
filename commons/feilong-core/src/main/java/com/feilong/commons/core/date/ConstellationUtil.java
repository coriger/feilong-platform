/**
 * Copyright (c) 2008-2013 FeiLong, Inc. All Rights Reserved.
 * <p/>
 * This software is the confidential and proprietary information of FeiLong
 * Network Technology, Inc. ("Confidential Information").  You shall not
 * disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with FeiLong.
 * <p/>
 * FeiLong MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. FeiLong SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 * <p/>
 */
package com.feilong.commons.core.date;

import java.util.Date;

/**
 * 飞龙星座
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-1-5 上午11:28:07
 * @since 1.0
 */
public final class ConstellationUtil{

	/**
	 * 飞龙星座枚举
	 */
	private static final ConstellationType[]	constellationTypes	= {
																	// 水瓶座 1月20日-2月18日
			ConstellationType.Aquarius,
			// 双鱼座 2月19日-3月20日
			ConstellationType.Pisces,
			// 白羊座 3月21日-4月19日
			ConstellationType.Aries,
			// 金牛座 4月20日-5月20日
			ConstellationType.Taurus,
			// 双子座 5月21日-6月21日
			ConstellationType.Gemini,
			// 巨蟹座 6月22日-7月22日
			ConstellationType.Cancer,
			// 狮子座 7月23日-8月22日
			ConstellationType.Leo,
			// 处女座 8月23日-9月22日
			ConstellationType.Virgo,
			// 天秤座 9月23日-10月23日
			ConstellationType.Libra,
			// 天蝎座 10月24日-11月22日
			ConstellationType.Scorpio,
			// 射手座 11月23日-12月21日
			ConstellationType.Sagittarius,
			// 摩羯座 12月22日-1月19日
			ConstellationType.Capricorn							};

	/**
	 * 星座边界日期
	 */
	private static final int[]					edgeDays			= { 20, 19, 21, 20, 21, 22, 23, 23, 23, 24, 23, 22 };

	// *************************************************************************************************
	/**
	 * 通过生日 获得星座信息
	 * 
	 * @param birthday
	 *            出生日期
	 * @param pattern
	 *            生日字符串的格式
	 * @return 通过生日 获得星座信息
	 */
	public static ConstellationType getConstellationType(String birthday,String pattern){
		Date date = DateUtil.string2Date(birthday, pattern);
		// 月
		int month = DateUtil.getMonth(date);
		// 日
		int day = DateUtil.getDayOfMonth(date);
		// **********************************************************************
		/**
		 * 索引 默认月份-1
		 */
		int index = month - 1;
		/**
		 * 当前月的边界日期
		 */
		int edgeDay = edgeDays[month - 1];
		if (day < edgeDay){
			index = index - 1;
			// 如果 是1月20-的 那么是摩羯座....
			if (index == -1){
				index = 11;
			}
		}
		return constellationTypes[index];
	}

	/**
	 * 通过生日 获得星座信息
	 * 
	 * <pre>
	 * 默认选择FeiLongDate.pattern_onlyDate 日期模式(只有时间 年月日 yyyy-MM-dd)
	 * </pre>
	 * 
	 * @param birthday
	 *            生日
	 * @return ConstellationType
	 */
	public static ConstellationType getConstellationType(String birthday){
		return getConstellationType(birthday, DatePattern.onlyDate);
	}
}