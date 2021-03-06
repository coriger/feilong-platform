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
package elsepackage.temple.awt.swing.JComboBox;

import java.awt.Container;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BorderFactory;
import javax.swing.ComboBoxEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;

public class 可自行输入的JComboBox{

	String[]	fontsize		= { "12", "14", "16", "18", "20", "22", "24", "26", "28" };

	String		defaultMessage	= "请选择或直接输入文字大小！";

	public 可自行输入的JComboBox(){
		JFrame f = new JFrame("JComboBox");
		Container contentPane = f.getContentPane();
		JComboBox combo = new JComboBox(fontsize);
		combo.setBorder(BorderFactory.createTitledBorder("请选择你要的文字大小"));
		combo.setEditable(true);//将JComboBox设成是可编辑的.
		ComboBoxEditor editor = combo.getEditor();//getEditor()方法返回ComboBoxEditor对象,如果你查看手册，你就会发
		//现ComboBoxEditor是个接口(interface),因此你可以自行实作这个接口，制作自己想要的ComboBoxEditor组件。但通常
		//我们不需要这么做，因为默认的ComboBoxEditor是使用JTextField,这已经足够应付大部份的情况了。
		//configureEditor()方法会初始化JComboBox的显示项目。例如例子中一开始就出现:"请选择或直接输入文字大小！"这个
		//字符串。
		combo.configureEditor(editor, defaultMessage);
		contentPane.add(combo);
		f.pack();
		f.show();
		f.addWindowListener(new WindowAdapter(){

			public void windowClosing(WindowEvent e){
				System.exit(0);
			}
		});
	}

	public static void main(String args[]){
		new 可自行输入的JComboBox();
	}
}
