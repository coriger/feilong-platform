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
package com.feilong.commons.core;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 *
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a> 
 * @version 1.0 Aug 12, 2013 1:22:08 AM  
 */
public class FeiLongVersionTest{

	private static final Logger	log	= LoggerFactory.getLogger(FeiLongVersionTest.class);

	/**
	 * Test method for {@link com.feilong.commons.core.FeiLongVersion#getVersion()}.
	 */
	@Test
	public final void testGetVersion(){
		log.info(FeiLongVersion.getVersion());
	}
}
