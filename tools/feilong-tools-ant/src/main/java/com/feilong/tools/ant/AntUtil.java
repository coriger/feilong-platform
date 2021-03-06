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
package com.feilong.tools.ant;

import java.io.File;

import org.apache.tools.ant.DefaultLogger;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;

/**
 * ant 工具类
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-11-26 下午01:41:31
 */
public final class AntUtil{

	/**
	 * 执行某个任务
	 * 
	 * @param antFilePath
	 *            ant文件路径
	 * @param targetName
	 *            targetName
	 * @param messageOutputLevel
	 *            日志级别 *
	 *            <P>
	 *            Constants for the message levels are in the {@link Project Project} class. The order of the levels, from least to most verbose, is
	 *            <code>MSG_ERR</code>, <code>MSG_WARN</code>, <code>MSG_INFO</code>, <code>MSG_VERBOSE</code>, <code>MSG_DEBUG</code>.
	 *            <P>
	 *            The default message level for DefaultLogger is Project.MSG_ERR.
	 */
	public static void executeTarget(String antFilePath,String targetName,int messageOutputLevel){
		Project project = new Project();
		// 添加日志输出
		DefaultLogger consoleLogger = new DefaultLogger();
		consoleLogger.setErrorPrintStream(System.err);
		consoleLogger.setOutputPrintStream(System.out);
		// 输出信息级别
		consoleLogger.setMessageOutputLevel(messageOutputLevel);
		project.addBuildListener(consoleLogger);

		project.init();

		ProjectHelper projectHelper = ProjectHelper.getProjectHelper();
		File buildFile = new File(antFilePath);

		// see http://yxhcquedu.iteye.com/blog/861110 但是我的层次太多了
		// ProjectHelper.configureProject(project, new File("E:/DataCommon/java/Taglib/Apache Ant/config/build-config-common-nested.xml"));
		// ProjectHelper.configureProject(project, new File("E:/DataCommon/java/Taglib/Apache Ant/config/build-feilong-nested.xml"));
		projectHelper.parse(project, buildFile);
		project.executeTarget(targetName);
	}
}
