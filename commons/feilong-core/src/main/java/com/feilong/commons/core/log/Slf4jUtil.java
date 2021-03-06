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
package com.feilong.commons.core.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MessageFormatter;

/**
 * slf4j util.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Dec 30, 2013 2:24:01 AM
 */
public final class Slf4jUtil{

	/** The Constant log. */
	private static final Logger	log	= LoggerFactory.getLogger(Slf4jUtil.class);

	/**
	 * 格式化字符串,此方法就是抽取slf4j的核心方法<br>
	 * 在java中,常会拼接字符串生成新的字符串值,在字符串拼接过程中 容易写错或者位置写错<br>
	 * slf4j的log支持格式化输出log ,比如<br>
	 * <ul>
	 * <li>log.error("{}","feilong");</li>
	 * <li>log.info("{},{}","feilong","hello");</li>
	 * </ul>
	 * 这些写法非常简洁且有效,不易出错 <br>
	 * <br>
	 * 因此,你可以在代码中出现这样的写法:
	 * 
	 * <pre>
	 * 比如
	 * throw new IllegalArgumentException(Slf4jUtil.formatMessage(
	 * 	"callbackUrl:[{}] ,length:[{}] can't >{}",
	 * 	callbackUrl,
	 * 	callbackUrlLength,
	 * 	callbackUrlMaxLength)
	 * 
	 * 又或者
	 * return Slf4jUtil.formatMessage(" {} [{}]", encode, encode.length());
	 * </pre>
	 * 
	 * @param messagePattern
	 *            the message pattern
	 * @param args
	 *            the args
	 * @return the string
	 */
	public static String formatMessage(String messagePattern,Object...args){
		// FormattingTuple formattingTuple = MessageFormatter.format(messagePattern, args);
		FormattingTuple formattingTuple = MessageFormatter.arrayFormat(messagePattern, args);
		String formatMessage = formattingTuple.getMessage();
		return formatMessage;
	}
}
