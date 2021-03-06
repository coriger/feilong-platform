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
package com.feilong.tools.application.taobao;

import java.util.Date;

import org.junit.Test;

import com.feilong.application.taobao.FeiLongTaoBaoUtil;
import com.feilong.application.taobao.entity.TaoBaoOAuthLoginForCodeEntity;
import com.feilong.application.taobao.entity.TaoBaoOAuthLoginForTokenEntity;
import com.feilong.application.taobao.entity.TaoBaoStandardLoginEntity;
import com.feilong.application.taobao.entity.TaoBaoStandardLoginOutEntity;
import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;
import com.feilong.commons.core.net.URIUtil;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.domain.User;
import com.taobao.api.request.TopatsResultGetRequest;
import com.taobao.api.request.TopatsTradesFullinfoGetRequest;
import com.taobao.api.request.TradesSoldGetRequest;
import com.taobao.api.response.TopatsResultGetResponse;
import com.taobao.api.response.TopatsTradesFullinfoGetResponse;
import com.taobao.api.response.TradesSoldGetResponse;

/**
 * 飞龙淘宝包
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-10-17 下午01:31:02
 */
public class FeiLongTaoBaoUtilTest{

	public final static String	APP_KEY		= "12398690";

	public final static String	APP_SERCET	= "91d2fc6d34b01f954f44a6751fa2c114";

	TaobaoClient				client		= new DefaultTaobaoClient(
													"http://gw.api.taobao.com/router/rest",
													APP_KEY,
													APP_SERCET);

	@Test
	public void getCode(){
		String url = "http://my.open.taobao.com/auth/authorize.htm?appkey=" + APP_KEY;
		System.out.println(url);
	}

	@Test
	public void getUrl(){
		String code = "TOP-10ff7fc2f1e78b2989828a72ac7efc5e5ctTzMHr3MezjxHpRUwvreaUnd8zTMlT-END";
		String url = "http://container.open.taobao.com/container?authcode=" + code;
		System.out.println(url);
		// top_session
	}

	/**
	 * taobao.topats.trades.fullinfo.get 异步批量获取交易订单详情api
	 */
	@Test
	public void name(){
		TopatsTradesFullinfoGetRequest req = new TopatsTradesFullinfoGetRequest();
		req.setFields("tid,alipay_no,commission_fee,received_payment,buyer_alipay_no");
		req.setTids("118920997107310");
		String sessionKey = "4122435f86fb3e8017b2ebda2eb495c20662e547552cbCMX872773211";
		try{
			TopatsTradesFullinfoGetResponse response = client.execute(req, sessionKey);
			System.out.println(response.getBody());
			// {"topats_trades_fullinfo_get_response":{"task":{"created":"2011-12-24 17:06:55","task_id":4219310}}}
		}catch (ApiException e){
			e.printStackTrace();
		}
	}

	/**
	 * taobao.topats.result.get 获取异步任务结果
	 */
	@Test
	public void asdasd(){
		TopatsResultGetRequest req = new TopatsResultGetRequest();
		req.setTaskId(4219310L);
		try{
			TopatsResultGetResponse response = client.execute(req);
			System.out.println(response.getBody());
		}catch (ApiException e){
			e.printStackTrace();
		}
	}

	/**
	 * taobao.trades.sold.get 搜索当前会话用户作为卖家已卖出的交易数据
	 */
	@Test
	public void sadasd(){
		String sessionKey = "4122435f86fb3e8017b2ebda2eb495c20662e547552cbCMX872773211";
		TradesSoldGetRequest req = new TradesSoldGetRequest();
		req.setFields("total_fee");
		Date endCreated = new Date();
		Date beiginDate = DateUtil.addMonth(endCreated, -2);
		req.setStartCreated(beiginDate);
		req.setEndCreated(endCreated);
		req.setStatus("ALL_WAIT_PAY");
		req.setBuyerNick("zhangsan");
		req.setType("game_equipment");
		req.setRateStatus("RATE_UNBUYER");
		req.setTag("time_card");
		req.setPageNo(1L);
		req.setPageSize(40L);
		// req.setUseHasNext(true);
		try{
			TradesSoldGetResponse response = client.execute(req, sessionKey);
			System.out.println(response.getBody());
		}catch (ApiException e){
			e.printStackTrace();
		}
	}

	/**
	 * 标准登录
	 */
	@Test
	public void getTaoBaoStandardLoginUrlTest(){
		TaoBaoStandardLoginEntity taoBaoStandardLoginEntity = new TaoBaoStandardLoginEntity();
		taoBaoStandardLoginEntity.setRequestUrl("http://container.api.taobao.com/container/identify");
		taoBaoStandardLoginEntity.setApp_key(APP_KEY);
		taoBaoStandardLoginEntity.setApp_secret(APP_SERCET);
		taoBaoStandardLoginEntity.setSign_method("md5");
		taoBaoStandardLoginEntity.setTimestamp(DateUtil.date2String(
				new Date(),
				DatePattern.commonWithTime));
		System.out.println(FeiLongTaoBaoUtil.getTaoBaoStandardLoginUrl(taoBaoStandardLoginEntity));
	}

	/**
	 * 标准退出
	 */
	@Test
	public void getTaoBaoStandardLoginOutUrlTest(){
		TaoBaoStandardLoginOutEntity taoBaoStandardLoginOutEntity = new TaoBaoStandardLoginOutEntity();
		taoBaoStandardLoginOutEntity.setRequestUrl("http://container.api.taobao.com/container/logoff");
		taoBaoStandardLoginOutEntity.setApp_key(APP_KEY);
		taoBaoStandardLoginOutEntity.setApp_secret(APP_SERCET);
		taoBaoStandardLoginOutEntity.setSign_method("md5");
		taoBaoStandardLoginOutEntity.setTimestamp(DateUtil.date2String(
				new Date(),
				DatePattern.commonWithTime));
		System.out.println(FeiLongTaoBaoUtil.getTaoBaoStandardLoginOutUrl(taoBaoStandardLoginOutEntity));
	}

	/**
	 * OAuth2.0:获取授权码Code
	 */
	@Test
	public void getTaoBaoOAuthLoginUrlForGetCodeTest(){
		TaoBaoOAuthLoginForCodeEntity taoBaoOAuthLoginForCodeEntity = new TaoBaoOAuthLoginForCodeEntity();
		taoBaoOAuthLoginForCodeEntity.setRequestUrl("https://oauth.taobao.com/authorize");
		taoBaoOAuthLoginForCodeEntity.setRedirect_uri("http://www.feilong.com/taobao");
		taoBaoOAuthLoginForCodeEntity.setClient_id(APP_KEY);
		taoBaoOAuthLoginForCodeEntity.setResponse_type("code");
		taoBaoOAuthLoginForCodeEntity.setState("11111");
		System.out.println(FeiLongTaoBaoUtil.getTaoBaoOAuthLoginUrlForGetCode(taoBaoOAuthLoginForCodeEntity));
		// 成功返回
		// http://www.feilong.com/taobao?code=Agx2k1jZZ8PSFLg3Z49NVdIl792&state=11111
		// http://www.feilong.com/taobao?error=invalid_client&error_description=The%20Application%20already%20Bind%20the%20user:%DE)T%08&state=11111
	}

	@Test
	public final void getTaoBaoOAuthLoginOutUrl(){
		String client_id = APP_KEY;
		String redirect_uri = "http://www.feilong.com/usercenter";
		System.out.println(FeiLongTaoBaoUtil.getTaoBaoOAuthLoginOutUrl(client_id, redirect_uri));
	}

	@Test
	public final void testBrowseTaoBaoLoginOut1(){
		String code = "uOZLczuR7xDJAUV4blD46Vra1219";
		TaoBaoOAuthLoginForTokenEntity taoBaoOAuthLoginForTokenEntity = new TaoBaoOAuthLoginForTokenEntity();
		taoBaoOAuthLoginForTokenEntity.setRequestUrl("https://oauth.taobao.com/token");
		taoBaoOAuthLoginForTokenEntity.setCode(code);
		taoBaoOAuthLoginForTokenEntity.setRedirect_uri("http://www.feilong.com/taobao");
		taoBaoOAuthLoginForTokenEntity.setGrant_type("authorization_code");
		taoBaoOAuthLoginForTokenEntity.setClient_id(APP_KEY);
		taoBaoOAuthLoginForTokenEntity.setClient_secret(APP_SERCET);
		// access_token就相当于sessionkey，后续调用其他接口可以直接使用
		String access_token = FeiLongTaoBaoUtil.getTaoBaoOAuthAccessToken(taoBaoOAuthLoginForTokenEntity);
		String serverUrl = "http://gw.api.taobao.com/router/rest";
		String[] fields = {
				"user_id",
				"uid",
				"nick",
				"sex",
				"buyer_credit",
				"seller_credit",
				"location",
				"created",
				"last_visit",
				"birthday",
				"type",
				"has_more_pic",
				"item_img_num",
				"item_img_size",
				"prop_img_num",
				"prop_img_size",
				"auto_repost",
				"promoted_type",
				"status",
				"alipay_bind",
				"consumer_protection",
				"alipay_account",
				"alipay_no",
				"avatar",
				"liangpin",
				"sign_food_seller_promise",
				"has_shop",
				"is_lightning_consignment",
				"vip_info",
				"email",
				"magazine_subscribe",
				"vertical_market",
				"online_gaming" };
		User user = FeiLongTaoBaoUtil.getUserByAccessToken(access_token, serverUrl, APP_KEY, APP_SERCET, fields);
		// 获取当前登录用户nick等数据，
		// 获取nick
		System.out.println(user.getNick());
	}

	public static void main(String[] args){
		String str = "%E9%A3%9E%E5%A4%A9%E5%A5%94%E6%9C%88";
		System.out.println(URIUtil.decode(str, "utf-8"));
	}
}
