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
package jdk.java.util;

import java.io.Serializable;

/**
 * 浏览历史.
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2012-4-6 下午2:18:01
 */
public class BrowsingHistoryCommand implements Serializable,Comparable<BrowsingHistoryCommand>{

	/** The Constant serialVersionUID. */
	private static final long	serialVersionUID	= 288232184048495608L;

	/** 商品信息. */
	private String				simpleSkuCommand;

	/** 浏览时间 (毫秒)为了减少 json 长度,声明成long 类型. */
	private long				date;

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj){
		if (null == this.simpleSkuCommand && null == obj){
			return true;
		}
		if (getClass() != obj.getClass()){
			return false;
		}
		BrowsingHistoryCommand browsingHistoryCommand = (BrowsingHistoryCommand) obj;
		// 只要 simpleSkuCommand equals 就认为 equals
		if (this.simpleSkuCommand.equals(browsingHistoryCommand.getSimpleSkuCommand())){
			return true;
		}
		return false;
	}

	/**
	 * @param simpleSkuCommand
	 * @param date
	 */
	public BrowsingHistoryCommand(String simpleSkuCommand, long date){
		this.simpleSkuCommand = simpleSkuCommand;
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(BrowsingHistoryCommand browsingHistoryCommand){
		if (this.equals(browsingHistoryCommand)){
			return 0;
		}
		// 最近访问的排前面
		if (this.date > browsingHistoryCommand.getDate()){
			return -1;
		}else if (this.date < browsingHistoryCommand.getDate()){
			return 1;
		}
		return 0;
	}

	/**
	 * @return the simpleSkuCommand
	 */
	public String getSimpleSkuCommand(){
		return simpleSkuCommand;
	}

	/**
	 * @param simpleSkuCommand
	 *            the simpleSkuCommand to set
	 */
	public void setSimpleSkuCommand(String simpleSkuCommand){
		this.simpleSkuCommand = simpleSkuCommand;
	}

	/**
	 * @return the date
	 */
	public long getDate(){
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(long date){
		this.date = date;
	}
}
