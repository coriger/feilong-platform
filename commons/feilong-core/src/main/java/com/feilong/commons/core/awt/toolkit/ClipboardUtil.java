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
package com.feilong.commons.core.awt.toolkit;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *剪切板操作
 * 
 * @author <a href="mailto:venusdrogon@163.com">金鑫</a>
 * @version 1.0 2011-5-23 下午02:38:55
 * @since 1.0
 */
public class ClipboardUtil{

	private final static Logger	log	= LoggerFactory.getLogger(ClipboardUtil.class);

	/**
	 * 获取系统 Clipboard 的一个实例，该 Clipboard 可作为本机平台提供的剪贴板工具的接口。<br>
	 * 该剪贴板使数据能够在 Java 应用程序和使用本机剪贴板工具的本机应用程序之间传输。
	 * 
	 * @return Clipboard
	 */
	public static Clipboard getSystemClipboard(){
		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		return clipboard;
	}

	/**
	 * 设置剪贴板数据
	 */
	public static void setClipboardContent(String data){
		Clipboard clipboard = getSystemClipboard();
		Transferable transferable = new StringSelection(data);
		clipboard.setContents(transferable, null);
	}

	/**
	 * 从剪贴板中取数据
	 * 
	 * @return
	 */
	public static String getClipboardContent(){
		String returnValue = null;
		Clipboard clipboard = getSystemClipboard();
		// 取得系统剪贴板里可传输的数据构造的Java对象
		Transferable transferable = clipboard.getContents(null);
		// 因为原系的剪贴板里有多种信息, 如文字, 图片, 文件等
		// 先判断开始取得的可传输的数据是不是文字, 如果是, 取得这些文字
		DataFlavor dataFlavor = DataFlavor.stringFlavor;
		if (transferable != null && transferable.isDataFlavorSupported(dataFlavor)){
			try{
				// 同样, 因为Transferable中的DataFlavor是多种类型的,
				// 所以传入DataFlavor这个参数, 指定要取得哪种类型的Data.
				returnValue = (String) transferable.getTransferData(dataFlavor);
			}catch (UnsupportedFlavorException e){
				log.debug(e.getMessage());
			}catch (IOException e){
				log.debug(e.getMessage());
			}
		}
		return returnValue;
	}
}
