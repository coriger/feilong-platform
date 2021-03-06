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
package com.feilong.tools.om.nginx;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.enumeration.HttpMethodType;
import com.feilong.commons.core.util.Validator;
import com.feilong.tools.net.httpclient.HttpClientUtil;
import com.feilong.tools.net.httpclient.HttpClientUtilException;
import com.feilong.tools.om.nginx.command.StubStatusCommand;

/**
 * NginxStubStatus 工具类
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Dec 23, 2013 3:42:00 AM
 */
public final class StubStatusUtil{

	private static final Logger	log	= LoggerFactory.getLogger(StubStatusUtil.class);

	/**
	 * 解析nginx stub status 成 NginxStubStatusCommand
	 * 
	 * @param uri
	 *            uri
	 * @param userName
	 *            用户名
	 * @param password
	 *            密码
	 */
	public static StubStatusCommand getStubStatusCommand(String uri,String userName,String password){
		Date now = new Date();

		Integer activeConnections = 0;
		Long serverAccepts = 0L;
		Long serverHandled = 0L;
		Long serverRequests = 0L;
		Integer reading = 0;
		Integer writing = 0;
		Integer waiting = 0;

		// **************************************************************************
		StubStatusCommand stubStatusCommand = new StubStatusCommand();

		try{
			String responseBodyAsString = HttpClientUtil.getHttpMethodResponseBodyAsString(uri, HttpMethodType.GET, userName, password);

			if (Validator.isNotNullOrEmpty(responseBodyAsString)){

				log.debug("responseBodyAsString:\n{}", responseBodyAsString);

				Reader reader = new StringReader(responseBodyAsString);
				LineNumberReader lineNumberReader = new LineNumberReader(reader);

				String line = null;

				try{
					while ((line = lineNumberReader.readLine()) != null){
						int lineNumber = lineNumberReader.getLineNumber();
						log.debug("the param lineNumber:{}", lineNumber);
						if (1 == lineNumber){
							String[] split = line.split(":");
							activeConnections = Integer.parseInt(split[1].trim());
						}else if (2 == lineNumber){
							// nothing to do,only text "server accepts handled requests"
						}else if (3 == lineNumber){
							String[] split = line.trim().split(" ");

							serverAccepts = Long.parseLong(split[0].trim());
							serverHandled = Long.parseLong(split[1].trim());
							serverRequests = Long.parseLong(split[2].trim());

						}else if (4 == lineNumber){
							String[] split = line.trim().split(" ");

							reading = Integer.parseInt(split[1].trim());
							writing = Integer.parseInt(split[3].trim());
							waiting = Integer.parseInt(split[5].trim());
						}else{
							break;
						}
					}
				}catch (NumberFormatException e){
					e.printStackTrace();
				}catch (IOException e){
					e.printStackTrace();
				}
			}
		}catch (HttpClientUtilException e1){
			e1.printStackTrace();
		}

		// **************有可能异常情况, 设置为默认值*****************************************
		stubStatusCommand.setActiveConnections(activeConnections);
		stubStatusCommand.setReading(reading);
		stubStatusCommand.setServerAccepts(serverAccepts);
		stubStatusCommand.setServerHandled(serverHandled);
		stubStatusCommand.setServerRequests(serverRequests);
		stubStatusCommand.setWaiting(waiting);
		stubStatusCommand.setWriting(writing);
		stubStatusCommand.setCrawlDate(now);

		return stubStatusCommand;
	}
}
