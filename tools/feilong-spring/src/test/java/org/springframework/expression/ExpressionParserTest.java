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
package org.springframework.expression;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Test;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import com.feilong.entity.user.User;
import com.feilong.spring.expression.SpelUtil;

public class ExpressionParserTest{

	@Test
	public void getValue(){
		String ex = "'Hello,World'";
		System.out.println(SpelUtil.getValue(ex));
		System.out.println(SpelUtil.getValue(ex + ".length()"));
		System.out.println(SpelUtil.getValue(ex + ".concat('!')"));
		System.out.println(SpelUtil.getValue(ex + ".class"));
		System.out.println(SpelUtil.getValue(ex + ".bytes.length"));
		System.out.println(SpelUtil.getValue("new String(" + ex + ").toUpperCase()"));
	}

	@Test
	public void test(){
		// Create and set a calendar 
		GregorianCalendar c = new GregorianCalendar();
		c.set(1856, 7, 9);
		Date date = c.getTime();
		//  The constructor arguments are name, birthday, and nationality.
		User user = new User(1, "jinxin", date);
		ExpressionParser parser = new SpelExpressionParser();
		Expression exp = parser.parseExpression("name.equals('jinxin') and (4+3)==(7-0)");
		Object name = null;
		//		EvaluationContext context = new StandardEvaluationContext(user);
		//		  name = exp.getValue(context);
		//System.out.println(name);
		//exp = parser.parseExpression("name");
		name = exp.getValue(user, Boolean.class);
		System.out.println(name);
	}
}
