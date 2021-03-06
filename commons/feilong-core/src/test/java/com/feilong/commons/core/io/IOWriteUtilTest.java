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
package com.feilong.commons.core.io;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.Constants;
import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;

/**
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Dec 23, 2013 10:29:11 PM
 */
public class IOWriteUtilTest{

	private static final Logger	log	= LoggerFactory.getLogger(IOWriteUtilTest.class);

	@Test
	public void write(){
		String url = "F:\\test.txt";
		String directoryName = SpecialFolder.getDesktop();
		IOWriteUtil.write(url, directoryName);
	}

	@Test
	public void testWrite(){
		String path = "/home/webuser/nike_int/expressdelivery/${yearMonth}/${expressDeliveryType}/vipQuery_${fileName}.log";
		Date date = new Date();
		path = path.replace("${yearMonth}", DateUtil.date2String(date, DatePattern.yearAndMonth));
		path = path.replace("${expressDeliveryType}", "sf");
		path = path.replace("${fileName}", DateUtil.date2String(date, DatePattern.timestamp));
		// **************************************************************
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("****************************************************" + Constants.lineSeparator);
		stringBuilder.append("2011-05-13 22:24:37调用,系统顺丰在途订单597件" + Constants.lineSeparator);
		stringBuilder.append("耗时:429020" + Constants.lineSeparator);
		stringBuilder.append("****************************************************" + Constants.lineSeparator);
		stringBuilder.append("派送成功订单495条" + Constants.lineSeparator);
		for (int i = 0; i < 1000; i++){
			stringBuilder.append("订单号:20850010运单号:102085592089\t寄件时间:2011-05-09 19:00:00\t签收人:张寄件时间:2011-05-10 14:49:00\t回单类型:1\n");
		}
		IOWriteUtil.write(path, stringBuilder.toString());
	}
}
