/**
 * Copyright (C) 2008 feilong (venusdrogon@163.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * This product currently only contains code developed by authors
 * of specific components, as identified by the source code files.
 *
 * Since product implements StAX API, it has dependencies to StAX API
 * classes.
 *
 * For additional credits (generally to people who reported problems)
 * see CREDITS file.
 */
package com.feilong.commons.core.lang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数学工具类.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Oct 11, 2013 12:30:10 AM
 */
public class MathUtil{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(MathUtil.class);

	/**
	 * 弧度转成度 <br>
	 * .
	 * 
	 * @param radians
	 *            the radians
	 * @return the double
	 */
	public static double radian2Degree(double radians){
		double degree = radians * 180 / Math.PI;
		return degree;
	}

	/**
	 * 度转成弧度.
	 * 
	 * @param degree
	 *            the degree
	 * @return the double
	 */
	public static double degree2Radian(double degree){
		double radians = degree * Math.PI / 180;
		return radians;
	}

}
