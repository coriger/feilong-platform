/*
 * Copyright (c) 2010 Jumbomart All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Jumbomart.
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Jumbo.
 *
 * JUMBOMART MAKES NO REPRESENTATIONS OR WARRANTIES ABOUT THE SUITABILITY OF THE
 * SOFTWARE, EITHER EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE, OR NON-INFRINGEMENT. JUMBOMART SHALL NOT BE LIABLE FOR ANY DAMAGES
 * SUFFERED BY LICENSEE AS A RESULT OF USING, MODIFYING OR DISTRIBUTING
 * THIS SOFTWARE OR ITS DERIVATIVES.
 *
 */
package com.feilong.netpay.adaptor.bca.klikpay;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.feilong.commons.core.date.DateUtil;
import com.feilong.commons.core.security.oneway.MD5Util;
import com.feilong.commons.core.util.NumberUtil;
import com.feilong.commons.core.util.StringUtil;
import com.feilong.commons.core.util.Validator;
import com.feilong.netpay.adaptor.AbstractPaymentAdaptor;
import com.feilong.netpay.command.PayRequest;
import com.feilong.netpay.command.PaymentFormEntity;
import com.feilong.netpay.command.TradeRole;
import com.feilong.tools.net.httpclient.HttpClientUtilException;

/**
 * alipay 纯网关接口<br>
 * <ul>
 * <li>此接口只支持 https 请求</li>
 * <li>参数 body（商品描述）、subject（商品名称）、extra_common_param（公用 回传参数）不能包含特殊字符（如：#、%、&、+）、敏感词汇，<br>
 * 也不能使用外 国文字（旺旺不支持的外文，如：韩文、泰语、藏文、蒙古文、阿拉伯语）；</li>
 * <li>此接口支持重复调用，前提是交易基本信息（买家、卖家、交易金额、超时时间等）在多次调用中保持一致，且交易尚未完成支付。</li>
 * </ul>
 * .
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Jan 15, 2013 8:41:39 PM
 */
public class KlikPayAdaptor extends AbstractPaymentAdaptor{

	/** The Constant log. */
	private static final Logger	log								= LoggerFactory.getLogger(KlikPayAdaptor.class);

	/** 表单提交地址. */
	private String				gateway;

	/** The method. */
	private String				method;

	/**
	 * What is the "klikPayCode"? <br>
	 * [AD] klikPayCode is unique ID for MetraPlasa
	 */
	private String				klikPayCode;

	/**
	 * clearKey given by BCA
	 */
	private String				clearkey;

	// - payType (5) field may consists only one of these values:
	// ▪ 01 = Full Transaction
	// ▪ 02 = Installment Transaction
	// ▪ 03 = Combination of both transactions above
	/** 01 = Full Transaction. */
	private String				payType_FullTransaction			= "01";

	/** 02 = Installment Transaction. */
	private String				payType_InstallmentTransaction	= "02";

	/** 03 = Combination of both transactions above. */
	private String				payType_CombinationTransaction	= "03";

	/**
	 * currency只能是 IDR - currency (4) field may consists only one of these values: ▪ IDR
	 */
	private String				currencyDefault;

	/** The price pattern. */
	private String				pricePattern					= "############.00";

	/** 最小支付金额. */
	private BigDecimal			minPriceForPay;

	/** 最大支付金额. */
	private BigDecimal			maxPriceForPay;

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.adaptor.PaymentAdaptor#getPaymentFormEntity(com.feilong.netpay.command.PayRequest, java.util.Map)
	 */
	public PaymentFormEntity getPaymentFormEntity(PayRequest payRequest,Map<String, String> specialSignMap){
		String tradeNo = payRequest.getTradeNo();
		BigDecimal totalFee = payRequest.getTotalFee();
		String return_url = payRequest.getReturnUrl();

		// validate
		if (Validator.isNullOrEmpty(tradeNo)){
			throw new IllegalArgumentException("code can't be null/empty!");
		}
		if (Validator.isNullOrEmpty(totalFee)){
			throw new IllegalArgumentException("total_fee can't be null/empty!");
		}

		// 交易总额 单位为 RMB-Yuan 取值范围为[0.01， 100000000.00]
		// 精确到小数点 后两位
		BigDecimal minPay = new BigDecimal(0.01f);
		BigDecimal maxPay = new BigDecimal(100000000);
		if (totalFee.compareTo(minPay) == -1 || totalFee.compareTo(maxPay) == 1){
			throw new IllegalArgumentException("total_fee:" + totalFee + " can't < " + minPay + " or > " + maxPay);
		}

		if (Validator.isNullOrEmpty(return_url)){
			throw new IllegalArgumentException("return_url can't be null/empty!");
		}

		// **************************************************************************

		Map<String, String> map = new HashMap<String, String>();

		// klikPayCode String 10 (AN) TRUE
		map.put("klikPayCode", klikPayCode);

		// transactionNo String 18 (AN) TRUE
		String transactionNo = payRequest.getTradeNo();
		map.put("transactionNo", transactionNo);

		// totalAmount String 12 7500000.00 TRUE
		// - totalAmount和miscFee 最后两个数字必须是00,注意舍入单位是每1货币
		String totalAmount = NumberUtil.toString(payRequest.getTotalFee(), pricePattern);
		map.put("totalAmount", totalAmount);

		// currency String 5 (AN) TRUE
		map.put("currency", currencyDefault);

		// payType String 2 99 TRUE
		// - payType (5) field may consists only one of these values:
		// ▪ 01 = Full Transaction
		// ▪ 02 = Installment Transaction
		// ▪ 03 = Combination of both transactions above
		map.put("payType", payType_FullTransaction);

		// callback String 100 (AN) TRUE
		// callback (6) is the URL on your site that user will be directed when they have finished the transaction on BCA KlikPay.
		map.put("callback", return_url);

		// transactionDate String 19 DD/MM/YYYY hh:mm:ss TRUE
		String datePattern = "dd/MM/yyyy HH:mm:ss";

		Date transactionDate = new Date();
		map.put("transactionDate", DateUtil.date2String(transactionDate, datePattern));

		// descp String 60 (AN) FALSE
		map.put("descp", "");

		// miscFee String 12 7500000.00 FALSE
		map.put("miscFee", "0.00");

		// signature String 10 (N) TRUE
		// - signature (10) is validation that will be parsed by BCA KlikPay login page to decide whether data sent is valid or not.
		String keyId = getKeyId(clearkey);
		String signature = getSignature(klikPayCode, transactionDate, transactionNo, totalAmount, currencyDefault, keyId);
		map.put("signature", signature);

		// *************************************************************************************************
		// 需要被签名的 参数map

		// 特殊 传入
		if (Validator.isNotNullOrEmpty(specialSignMap)){
			map.putAll(specialSignMap);
		}

		return getPaymentFormEntity(gateway, method, map);
		// throw new IllegalArgumentException("specialSignMap has IllegalArgument key");
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doNotifyVerify(javax.servlet.http.HttpServletRequest)
	 */
	public boolean doNotifyVerify(HttpServletRequest request){
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doGetFeedbackSoCode(javax.servlet.http.HttpServletRequest)
	 */
	public String doGetFeedbackTradeNo(HttpServletRequest request){
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doGetFeedbackTotalFee(javax.servlet.http.HttpServletRequest)
	 */
	public String doGetFeedbackTotalFee(HttpServletRequest request){
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#doCloseTrade(java.lang.String, com.feilong.netpay.command.TradeRole)
	 */
	public boolean doCloseTrade(String orderNo,TradeRole tradeRole) throws HttpClientUtilException{
		return false;
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.PaymentAdaptor#isSupportCloseTrade()
	 */
	public boolean isSupportCloseTrade(){
		return false;
	}

	// 详见 svn "API Doc. for Payment KlikPay BCA-Sprint v1.8.pdf"
	/**
	 * Gets the sign.
	 * 
	 * @param klikPayCode
	 *            the klik pay code
	 * @param transactionDate
	 *            the transaction date
	 * @param transactionNo
	 *            the transaction no
	 * @param totalAmount
	 *            the total amount
	 * @param currency
	 *            the currency
	 * @param keyId
	 *            the key id
	 * @return the sign
	 */
	public String getSignature(String klikPayCode,Date transactionDate,String transactionNo,String totalAmount,String currency,String keyId){

		String firstValue = klikPayCode + transactionNo + currency + keyId;
		log.debug("the firstValue:{}", firstValue);

		String datePattern = "ddMMyyyy";
		String formatTransactionDate = DateUtil.date2String(transactionDate, datePattern);

		// Total amount from redirect forward : 1500500.00
		// Total amount that will be used : 1500500
		// - totalAmount和miscFee 最后两个数字必须是00,注意舍入单位是每1货币。
		String formatTotalAmount = StringUtil.substringWithoutLast(totalAmount, 3);

		Integer secondValue = Integer.parseInt(formatTransactionDate) + Integer.parseInt(formatTotalAmount);
		log.debug("the secondValue:{}", secondValue);

		String firstValueHash = hash(firstValue);
		String secondValueHash = hash(secondValue + "");

		String result = Math.abs((Integer.parseInt(firstValueHash) + Integer.parseInt(secondValueHash))) + "";
		return result;
	}

	/*
	 * (non-Javadoc)
	 * @see com.feilong.netpay.adaptor.PaymentAdaptor#doRedirectVerify(javax.servlet.http.HttpServletRequest)
	 */
	public boolean doRedirectVerify(HttpServletRequest request){
		return true;
	}

	// A = klikPayCode
	// B = transactionNo C = currency
	// D = transactionDate
	// K = keyId (current key used for transaction)
	// e
	// k = encrypt by keyId
	// h = hash function
	/**
	 * Gets the auth key.
	 * 
	 * @param klikPayCode
	 *            the klik pay code
	 * @param transactionDate
	 *            the transaction date
	 * @param transactionNo
	 *            the transaction no
	 * @param currency
	 *            the currency
	 * @param keyId
	 *            the key id
	 * @return the auth key
	 */
	public String getAuthKey(String klikPayCode,Date transactionDate,String transactionNo,String currency,String keyId){

		// klikPay Code : 123 1230000000
		// transactionNo : 456 456AAAAAAAAAAAAAAA
		// currency : IDR IDR11
		// transactionDate : 20/01/2010 01:01:01  20/01/2010 01:01:01
		// KeyId : 12345678901234561234567890123456 
		// 12345678901234561234567890123456

		String padKlikPayCode = "";
		String padTransactionDate = "";
		String padTransactionNo = "";
		String padCurrency = "";
		String padKeyId = "";

		String secondValue = padKlikPayCode + padTransactionNo + padCurrency + padTransactionDate + padKeyId;

		String md5SecondValue = MD5Util.encode(secondValue).toUpperCase();

		String authKeyString;
		// Field# Parameter Pad Position Length Pad Data
		// 1. klikPayCode Right 10 0
		// 2. transactionNo Right 18 A
		// 3. currency Right 5 1
		// 4. transactionDate Left 19 C
		// 5. keyId Right 32 E

		String result = "";
		return result;
	}

	/**
	 * Hash.
	 * 
	 * @param value
	 *            the value
	 * @return the string
	 */
	@SuppressWarnings("cast")
	// TODO 检查这个算法的漏洞
	public String hash(String value){
		Integer hash = 0;
		for (int i = 0; i < value.length(); i++){
			// (int)chars[i]); ascii( value[i] );
			hash = (hash * 31) + (int) (value.charAt(i));
			while (hash > Integer.MAX_VALUE){
				hash = hash + Integer.MIN_VALUE - Integer.MAX_VALUE - 1;
			}
			while (hash < Integer.MIN_VALUE){
				hash = hash + Integer.MAX_VALUE - Integer.MIN_VALUE + 1;
			}
		}
		log.debug("the value:{}---> hash:{}", value, hash);
		return hash + "";
	}

	/**
	 * Uppercase [to String [Hexa[clearKey]]]
	 * 
	 * @param clearKey
	 * @return
	 */
	public static String getKeyId(String clearKey){
		char[] hexArray = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		byte[] bytes = clearKey.getBytes();
		String keyId = "";
		for (int cntr = 0; cntr < clearKey.length(); cntr++){
			keyId = keyId + hexArray[(bytes[cntr] & 0xFF) / 16] + hexArray[(bytes[cntr] & 0xFF) % 16];
		}

		log.debug("clearKey:{}, keyId:{}", clearKey, keyId);
		return keyId;
	}

	/**
	 * @param gateway
	 *            the gateway to set
	 */
	public void setGateway(String gateway){
		this.gateway = gateway;
	}

	/**
	 * @param method
	 *            the method to set
	 */
	public void setMethod(String method){
		this.method = method;
	}

	/**
	 * @param klikPayCode
	 *            the klikPayCode to set
	 */
	public void setKlikPayCode(String klikPayCode){
		this.klikPayCode = klikPayCode;
	}

	/**
	 * @param currencyDefault
	 *            the currencyDefault to set
	 */
	public void setCurrencyDefault(String currencyDefault){
		this.currencyDefault = currencyDefault;
	}

	/**
	 * @param clearkey
	 *            the clearkey to set
	 */
	public void setClearkey(String clearkey){
		this.clearkey = clearkey;
	}
}