/**
 * Copyright (c) 2008-2014 FeiLong, Inc. All Rights Reserved.
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
package com.feilong.application.sina;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import weibo4j.Timeline;
import weibo4j.http.ImageItem;
import weibo4j.model.Status;
import weibo4j.model.WeiboException;

import com.feilong.commons.core.enumeration.CharsetType;
import com.feilong.commons.core.io.IOUtil;
import com.feilong.commons.core.net.URIUtil;
import com.feilong.commons.core.util.Validator;
import com.feilong.tools.json.JsonUtil;

/**
 * 微博核心工具类
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Jan 11, 2013 12:20:52 AM
 */
public final class WeiboUtil{

	private static final Logger	log	= LoggerFactory.getLogger(WeiboUtil.class);

	// source false string 采用OAuth授权方式不需要此参数，其他授权方式为必填参数，数值为应用的AppKey。
	// access_token false string 采用OAuth授权方式为必填参数，其他授权方式不需要此参数，OAuth授权后获得。
	// status true string 要发布的微博文本内容，必须做URLencode，内容不超过140个汉字。
	// visible false int 微博的可见性，0：所有人能看，1：仅自己可见，2：密友可见，3：指定分组可见，默认为0。
	// list_id false string 微博的保护投递指定分组ID，只有当visible参数为3时生效且必选。
	// lat false float 纬度，有效范围：-90.0到+90.0，+表示北纬，默认为0.0。
	// long false float 经度，有效范围：-180.0到+180.0，+表示东经，默认为0.0。
	// annotations false string 元数据，主要是为了方便第三方应用记录一些适合于自己使用的信息，每条微博可以包含一个或者多个元数据，必须以json字串的形式提交，字串长度不超过512个字符，具体内容可以自定。

	/**
	 * 发送微博
	 * 
	 * @param access_token
	 *            access_token
	 * @param content
	 *            内容
	 * @throws WeiboException
	 */
	public static void sendWeibo(String access_token,String content) throws WeiboException{
		sendWeibo(access_token, content, null);
	}

	/**
	 * 发送微博(带图片)
	 * 
	 * @param access_token
	 * @param content
	 *            微博内容
	 * @param imagePath
	 *            图片路径
	 * @throws WeiboException
	 */
	public static void sendWeibo(String access_token,String content,String imagePath) throws WeiboException{
		int length = content.length();
		boolean isLessThanMaxLength = length < 140;

		if (log.isDebugEnabled()){

			Map<String, Object> map = new HashMap<String, Object>();

			map.put("content", content);
			map.put("isLessThanMaxLength", isLessThanMaxLength);
			map.put("length", length);

			log.debug(JsonUtil.format(map));
		}

		if (isLessThanMaxLength){
			Status status = null;

			Timeline timeline = new Timeline();
			timeline.setToken(access_token);

			// 有图片
			if (Validator.isNotNullOrEmpty(imagePath)){
				File file = new File(imagePath);
				byte[] bytes = IOUtil.convertFileToByteArray(file);
				ImageItem item = new ImageItem(bytes);

				// 下面的 没有图片的 发送底层会设置 param.setContentCharset("UTF-8");
				// 此处需要自己 独立设置
				content = URIUtil.encode(content, CharsetType.UTF8);
				status = timeline.UploadStatus(content, item);
			}
			// 没有图片
			else{
				String[] _annotations = { "飞龙", "小蜜" };
				// 北纬40度，西纬75度
				//40.684804,-73.962708
				String annotations = JsonUtil.format(_annotations);
				status = timeline.UpdateStatus(content, 40.68f, -73.96f, annotations);
			}
			// status.
			if (log.isDebugEnabled()){
				// log.debug("status:{}", JsonUtil.format(status));
			}
		}else{
			log.error("You can't thanMaxLength[140]");
		}
	}
}