package com.feilong.office.excel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;

import com.feilong.commons.core.date.DateUtil;
import com.feilong.tools.office.excel.ExcelConfigEntity;
import com.feilong.tools.office.excel.ExcelCreateUtil;

/**
 * 生成 excel 测试
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 Feb 10, 2014 2:33:52 AM
 */
public class ExcelCreateTest{

	@Test
	public void createExcel() throws IOException{
		String[] columnsTitle = new String[30];
		columnsTitle[0] = "姓名";
		columnsTitle[1] = "地址";
		columnsTitle[2] = "email";
		for (int i = 3; i < 30; i++){
			columnsTitle[i] = "呵呵" + i;
		}
		List<Object> list = new ArrayList<Object>();
		String[] temp = null;
		for (int i = 0; i < 200; i++){
			temp = new String[30];
			temp[0] = "1";
			temp[1] = "GABC22222";
			temp[2] = "xy2venus@163.com";
			for (int j = 3; j < 30; j++){
				temp[j] = "呵呵" + j;
			}
			list.add(temp);
		}

		// *********************************************************************
		ExcelConfigEntity excelEntity = new ExcelConfigEntity();
		excelEntity.setSpecialString("G");
		excelEntity.setIsHasSpecialStringToAddStyle(true);
		excelEntity.setIsRowChangeColor(true);
		String fileName = "D://test" + DateUtil.date2String(new Date(), "yyyyMMddHHmmss") + ".xls";

		ExcelCreateUtil excel = new ExcelCreateUtil();
		excel.createExcel(columnsTitle, list, fileName, excelEntity);
	}
}
