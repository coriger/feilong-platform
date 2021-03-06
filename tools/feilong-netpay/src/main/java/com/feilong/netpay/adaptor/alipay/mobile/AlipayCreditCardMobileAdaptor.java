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
package com.feilong.netpay.adaptor.alipay.mobile;

import java.util.Map;

import com.feilong.commons.core.util.ArrayUtil;
import com.feilong.commons.core.util.Validator;

/**
 * 手机版alipay支付(信用卡)
 * 
 * @author 冯明雷
 * @time 2013-6-4 下午2:05:50
 */
public class AlipayCreditCardMobileAdaptor extends AlipayPayMobileAdaptor {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.jumbo.brandstore.payment.adaptor.AlipayPayAdaptor#validatorSpecialSignMap
	 * (java.util.Map)
	 */
	@Override
	protected boolean validatorSpecialSignMap(Map<String, String> specialSignMap) {
		if (Validator.isNullOrEmpty(specialSignMap)) {
			throw new NullPointerException("the specialSignMap is null or empty!");
		}

		String bankCode = specialSignMap.get("defaultbank");
		if (Validator.isNullOrEmpty(bankCode)) {
			throw new NullPointerException("the defaultbank param is null or empty!");
		}

		if (!isSupportBank(bankCode)) {
			throw new IllegalArgumentException("bankCode:" + bankCode + " don't support,please see document");
		}

		return super.validatorSpecialSignMap(specialSignMap);
	}

	/**
	 * 判断传入的 银行code 是否 支持
	 * 
	 * @param defaultbank
	 * @return
	 */
	private boolean isSupportBank(String defaultbank) {
		return ArrayUtil.isContain(supportBank, defaultbank);
	}

	/**
	 * 支持的银行code
	 */
	private String[] supportBank = {
			// ******************************信用卡
			"CREDITCARD_CMB",// 招行信用卡
			"CREDITCARD_CCB",// 建行信用卡
			"CREDITCARD_ICBC",// 工行信用卡
			"CREDITCARD_GDB",// 广发信用卡
			"CREDITCARD_BOC",// 中行信用卡
			"CREDITCARD",// 信用卡默认code，跳转到支付宝会有选择界面
	};
}
