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

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-4-27 上午11:55:46
 * @since 1.0
 */
public class IOUtilTest{

	private static final Logger	log			= LoggerFactory.getLogger(IOUtilTest.class);

	private String				fileName1	= "F:/pie2.png";

	private String				fileName	= "E:\\Data\\Java\\Taglib\\Apache Commons 非常有用的工具包\\commons-net\\ftp";

	private String				fString		= "/home/webuser/nike_int/johnData/${date}/nikeid_pix_${typeName}.csv";

	@Test
	public void down(){
		String url = "http://www.kenwen.com/egbk/31/31186/4395342.txt";
		String directoryName = SpecialFolder.getDesktop();
		IOUtil.down(url, directoryName);
	}

	@Test
	public void getContentLength(){
		try{
			URL url = new URL("http://www.jinbaowang.cn/images//20110722/096718c3d1c9b4a1.jpg");
			URLConnection urlConnection = url.openConnection();
			int contentLength = urlConnection.getContentLength();
			System.out.println(FileUtil.formatFileSize(contentLength));
		}catch (IOException e){
			e.printStackTrace();
		}
		try{
			URL url = new URL("http://localhost:8080/TestHttpURLConnectionPro/index.jsp");
			URLConnection rulConnection = url.openConnection();

			HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
		}catch (MalformedURLException e){
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}
	}

	@Test
	@Ignore
	public void testGetFullName(){
		Class class1 = IOUtil.class;
		System.out.println(class1.getName());
	}

	@Test
	// @Ignore
	public void testGetP(){
		File file = new File(fString);
		log.info(file.getAbsolutePath());
		System.out.println(file.getParent());
	}

}
