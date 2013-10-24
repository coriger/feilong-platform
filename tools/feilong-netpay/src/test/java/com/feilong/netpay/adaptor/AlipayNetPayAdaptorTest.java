/**
 * Copyright (c) 2008-2013 FeiLong, Inc. All Rights Reserved.
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
package com.feilong.netpay.adaptor;

import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;
import com.feilong.commons.core.util.JsonFormatUtil;
import com.feilong.netpay.PaymentAdaptor;
import com.feilong.netpay.PaymentFormEntity;

/**
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Jan 16, 2013 8:27:39 PM
 */
@ContextConfiguration(locations = { "classpath*:spring/spring-payment.xml" })
public class AlipayNetPayAdaptorTest extends AbstractJUnit4SpringContextTests{

	private static final Logger	log	= LoggerFactory.getLogger(AlipayNetPayAdaptorTest.class);

	@Autowired
	@Qualifier("alipayNetPayAdaptor")
	private PaymentAdaptor		paymentAdaptor;

	/**
	 * Test method for
	 * {@link com.jumbo.brandstore.payment.adaptor.AlipayPayAdaptor#doBeginPayment(java.lang.String, java.math.BigDecimal, java.lang.String, java.lang.String)}.
	 */
	@Test
	public void testDoBeginPayment(){

		String code = DateUtil.date2String(new Date(), DatePattern.timestamp);
		BigDecimal total_fee = new BigDecimal(0.01f);
		String return_url = "/patment1url";
		String notify_url = "/patment2url";
		PaymentFormEntity paymentFormEntity = paymentAdaptor.doBeginPayment(code, total_fee, return_url, notify_url, null);
	 
		log.info(JsonFormatUtil.format(paymentFormEntity));
		System.out.println(paymentFormEntity.getFullRequestURL());
		System.out.println();
		System.out.println();
		System.out.println();
	}

	/**
	 * Test method for {@link com.jumbo.brandstore.payment.adaptor.AlipayPayAdaptor#doNotifyVerify(javax.servlet.http.HttpServletRequest)}.
	 */
	@Test
	public void testDoNotifyVerify(){
		fail("Not yet implemented");
	}

	/**
	 * Test method for {@link com.jumbo.brandstore.payment.adaptor.AlipayPayAdaptor#doGetFeedbackSoCode(javax.servlet.http.HttpServletRequest)}.
	 */
	@Test
	public void testDoGetFeedbackSoCode(){
		fail("Not yet implemented");
	}
}
