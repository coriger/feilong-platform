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

package com.feilong.tools.memcached;

import java.util.Date;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.danga.MemCached.MemCachedClient;
import com.danga.MemCached.SockIOPool;
import com.feilong.commons.core.configure.ResourceBundleUtil;

/**
 * memcached util<br>
 * Reference from dianchao
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-11-11 下午02:57:05
 * @deprecated
 */
public class MemCachedUtil{

	private final static Logger		log				= LoggerFactory.getLogger(MemCachedUtil.class);

	private static ResourceBundle	config			= ResourceBundle.getBundle("memcached");

	private final String			poolname		= config.getString("memcached.poolname");

	private final Integer			EXPIRE_TIME		= ResourceBundleUtil.getValue(config, "memcached.expiretime", Integer.class);

	private final String[]			serverlist		= ResourceBundleUtil.getArray(config, "memcached.serverlist", ",");

	/**
	 * 权重
	 */
	private final Integer[]			weight			= ResourceBundleUtil.getArray(config, "memcached.serverweight", ",", Integer.class);

	private final Integer			INIT_CONNECTION	= ResourceBundleUtil.getValue(config, "memcached.initconnection", Integer.class);

	private final Integer			MIN_CONNECTION	= ResourceBundleUtil.getValue(config, "memcached.minconnection", Integer.class);

	private final Integer			MAX_CONNECTION	= ResourceBundleUtil.getValue(config, "memcached.maxconnection", Integer.class);

	/**
	 * 设置主线程睡眠时间，每30秒苏醒一次，维持连接池大小
	 */
	private final Integer			maintSleep		= ResourceBundleUtil.getValue(config, "memcached.maintSleep", Integer.class);

	/**
	 * 关闭套接字缓存
	 */
	private final Boolean			NAGLE			= ResourceBundleUtil.getValue(config, "memcached.nagle", Boolean.class);

	/**
	 * 连接建立后的超时时间
	 */
	private final Integer			SOCKET_TO		= ResourceBundleUtil.getValue(config, "memcached.socketto", Integer.class);

	private final Boolean			ALIVE_CHECK		= ResourceBundleUtil.getValue(config, "memcached.alivecheck", Boolean.class);

	/**************************************************************************************/
	private static MemCachedUtil	memCachedUtil	= null;

	private SockIOPool				sockIOPool		= null;

	/****************************************************************************/
	public static synchronized MemCachedUtil getInstance(){
		if (memCachedUtil == null){
			memCachedUtil = new MemCachedUtil();
		}
		return memCachedUtil;
	}

	public MemCachedUtil(){
		initialize();
	}

	/**
	 * 初始化
	 */
	private void initialize(){
		// ******************************************************************
		// 创建一个Socked连接池实例
		sockIOPool = SockIOPool.getInstance(poolname);
		// ************************************************************
		sockIOPool.setServers(serverlist);
		sockIOPool.setWeights(weight);
		// ********************************************
		sockIOPool.setAliveCheck(ALIVE_CHECK);
		// sockIOPool.setBufferSize(paramInt);
		// sockIOPool.setFailback(paramBoolean);
		sockIOPool.setFailover(true);
		// sockIOPool.setHashingAlg(paramInt);
		// 设置初始连接数、最小和最大连接数以及最大处理时间
		sockIOPool.setInitConn(INIT_CONNECTION);
		/**
		 * set the sleep for the maint thread,设置主线程的睡眠时间 <br>
		 * it will wake up every x seconds and maintain the pool size
		 */
		sockIOPool.setMaintSleep(maintSleep);
		// sockIOPool.setMaxBusyTime(paramLong);
		sockIOPool.setMaxConn(MAX_CONNECTION);
		// sockIOPool.setMaxIdle(paramLong);
		sockIOPool.setMinConn(MIN_CONNECTION);
		/**
		 * Tcp的规则就是在发送一个包之前，本地机器会等待远程主机对上一次发送的包的确认信息到来； <br>
		 * 这个方法就可以关闭套接字的缓存，以至这个包准备好了就发
		 */
		sockIOPool.setNagle(NAGLE);
		// sockIOPool.setServers(paramArrayOfString);
		/**
		 * 连接建立时对超时的控制
		 */
		// sockIOPool.setSocketConnectTO(0);
		/**
		 * 连接建立后对超时的控制
		 */
		sockIOPool.setSocketTO(SOCKET_TO);
		// ************************************************************
		/**
		 * initialize the connection pool ,初始化连接池<br>
		 * 初始化一些值并与MemcachedServer段建立连接
		 */
		sockIOPool.initialize();
		log.debug("memcached Initialized ok");
		// lets set some compression on for the client
		// compress anything larger than 64k
		// // 压缩设置，超过指定大小（单位为K）的数据都会被压缩
		// mcc.setCompressEnable(true);
		// mcc.setCompressThreshold(64 * 1024);
	}

	/*************************************************************************************/
	public boolean exists(String id){
		MemCachedClient memCachedClient = this.getMemCachedClient();
		return memCachedClient.keyExists(id);
	}

	public Object get(String id){
		MemCachedClient memCachedClient = this.getMemCachedClient();
		Object data = memCachedClient.get(id);
		return data;
	}

	public boolean add(String id,Object data){
		return add(id, data, null);
	}

	/**
	 * 添加
	 * 
	 * <pre>
	 * 
	 * if (memCachedClient.keyExists(id)){
	 * 	flag = memCachedClient.replace(id, data, expiryDate);
	 * }else{
	 * 	flag = memCachedClient.add(id, data, expiryDate);
	 * }
	 * </pre>
	 * 
	 * @param id
	 * @param data
	 * @param expiryDate
	 *            过期时间
	 * @return
	 */
	public boolean add(String id,Object data,Date expiryDate){
		MemCachedClient memCachedClient = this.getMemCachedClient();
		boolean flag = false;
		if (memCachedClient.keyExists(id)){
			flag = memCachedClient.replace(id, data, expiryDate);
		}else{
			flag = memCachedClient.add(id, data, expiryDate);
		}
		return flag;
	}

	public void delete(String id){
		MemCachedClient memCachedClient = this.getMemCachedClient();
		memCachedClient.delete(id);
	}

	/**
	 * MemCachedClient
	 * 
	 * @return
	 */
	private MemCachedClient getMemCachedClient(){
		MemCachedClient memCachedClient = new MemCachedClient(poolname);
		memCachedClient.setPrimitiveAsString(true);
		return memCachedClient;
	}

	/**
	 * 让缓存内的所有当前条目无效（或到期失效）
	 * 
	 * @return
	 */
	public boolean flushAll(){
		MemCachedClient memCachedClient = this.getMemCachedClient();
		return memCachedClient.flushAll();
	}

	protected void shutDown(){
		if (null != this.sockIOPool){
			this.sockIOPool.shutDown();
		}
	}
}
