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
package com.feilong.commons.core.text;

import java.text.Format;
import java.text.MessageFormat;

/**
 * MessageFormat 工具包,常用于国际化<br>
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-3-27 上午1:19:22
 * @see MessageFormat
 * @see Format
 */
public final class MessageFormatUtil{

	/**
	 * 格式化 <br>
	 * 用法:
	 * 
	 * <pre>
	 * MessageFormatUtil.format(&quot;name=张三{0}a{1}&quot;, &quot;jin&quot;, &quot;xin&quot;)
	 * 
	 * 返回: name=张三jinaxin
	 * </pre>
	 * 
	 * @param pattern
	 *            占位符有三种方式书写方式：
	 *            <ul>
	 *            <li>{argumentIndex}: 0-9 之间的数字，表示要格式化对象数据在参数数组中的索引号</li>
	 *            <li>{argumentIndex,formatType}: 参数的格式化类型</li>
	 *            <li>{argumentIndex,formatType,FormatStyle}: 格式化的样式，它的值必须是与格式化类型相匹配的合法模式、或表示合法模式的字符串。</li>
	 *            </ul>
	 * @param arguments
	 *            动态参数
	 * @return
	 */
	public static String format(String pattern,Object...arguments){
		return MessageFormat.format(pattern, arguments);
	}
}
