package com.feilong.tools.jfreechart;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.jfree.data.gantt.Task;
import org.junit.Test;

import com.feilong.commons.core.date.DatePattern;
import com.feilong.commons.core.date.DateUtil;
import com.feilong.tools.jfreechart.category.gantt.GanttChartEntity;
import com.feilong.tools.jfreechart.category.gantt.GanttChartUtil;

public class GanttChartUtilTest{

	public static Task getTask(String description,String start,String end,String pattern){
		Date startDate = DateUtil.string2Date(start, pattern);
		Date endDate = DateUtil.string2Date(end, pattern);
		return new Task(description, startDate, endDate);
	}

	@Test
	public void createGanttChart() throws IOException{
		Map<String, List<Task>> seriesAndDataMap = new LinkedHashMap<String, List<Task>>();
		String pattern = DatePattern.onlyDate;
		List<Task> taskList = null;
		/**************************************************************************/
		taskList = new ArrayList<Task>();
		Task task = getTask("4.数据", "2012-1-16", "2012-1-20", pattern);
		task.setPercentComplete(1);
		taskList.add(task);
		taskList.add(getTask("3.日常维护", "2012-1-16", "2012-1-20", pattern));
		seriesAndDataMap.put("金鑫", taskList);
		taskList = new ArrayList<Task>();
		taskList.add(getTask("5.订单详细页面", "2012-1-16", "2012-1-20", pattern));
		seriesAndDataMap.put("王伟", taskList);
		taskList = new ArrayList<Task>();
		taskList.add(getTask("6.商品详细页面", "2012-1-16", "2012-1-20", pattern));
		seriesAndDataMap.put("易小青", taskList);
		taskList = new ArrayList<Task>();
		taskList.add(getTask("1.龙年限量款活动", "2012-1-16", "2012-1-18", pattern));
		taskList.add(getTask("2.新团购fff", "2012-1-18", "2012-1-20", pattern));
		seriesAndDataMap.put("张星星", taskList);
		taskList = new ArrayList<Task>();
		Task task2 = getTask("1.龙年限量款活动", "2012-1-16", "2012-1-17", pattern);
		//task2.addSubtask(task);
		taskList.add(task2);
		seriesAndDataMap.put("陈俊杰", taskList);
		taskList = new ArrayList<Task>();
		taskList.add(task2);
		taskList.add(getTask("3.日常维护", "2012-1-16", "2012-1-20", pattern));
		seriesAndDataMap.put("乔一民", taskList);
		/**************************************************************************/
		GanttChartEntity ganttChartEntity = new GanttChartEntity();
		ganttChartEntity.setChartTitle("IT工作安排(甘特图)");
		ganttChartEntity.setCategoryAxisLabel("任务工作");
		ganttChartEntity.setDateAxisLabel("时间");
		ganttChartEntity.setSeriesAndDataMap(seriesAndDataMap);
		//		ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
		//		
		//		info.se
		ChartInfoEntity chartInfoEntity = new ChartInfoEntity();
		chartInfoEntity.setImageNameOrOutputStream(ganttChartEntity.getChartTitle() + ".png");
		chartInfoEntity.setWidth(1200);
		chartInfoEntity.setHeight(1200);
		GanttChartUtil ganttChartUtil = new GanttChartUtil(ganttChartEntity);
		ganttChartUtil.getDateAxis().setDateFormatOverride(new SimpleDateFormat(DatePattern.monthAndDayWithWeek));
		//ganttChartUtil.getTextTitle().setBorder(1, 1, 1, 1);
		//		ganttChartUtil.getjFreeChart().setBackgroundPaint(Color.black);
		//		ganttChartUtil.getTextTitle().setPaint(Color.white);
		ganttChartUtil.createImage(chartInfoEntity);
	}
}
