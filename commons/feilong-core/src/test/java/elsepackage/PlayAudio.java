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
package elsepackage;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * <p>
 * Title: Applet播放声音文件
 * </p>
 * <p>
 * Description: 使用AudioClip类加载声音文件，并当鼠标点击后开始播放
 * </p>
 * <p>
 * Copyright: Copyright (c) 2003
 * </p>
 * <p>
 * Filename: PlayAudio.java
 * </p>
 * 
 * @version 1.0
 */
public class PlayAudio extends Applet implements ActionListener{

	AudioClip	audio;

	Button		btExit, btOpen, btPlay, btLoop, btStop;

	/**
	 *<br>
	 * 方法说明：初始化Applet <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void init(){
		//加载声音文件
		audio = getAudioClip(getDocumentBase(), "img/1.mid");
		//构造按钮
		setLayout(new FlowLayout()); //使用布局管理器
		btPlay = new Button("Play"); //定义Play按钮                   
		btPlay.addActionListener(this); //给Play按钮添加一个监听事件
		btLoop = new Button("Loop"); //定义Play按钮
		btLoop.addActionListener(this); //给Play按钮添加一个监听事件
		btStop = new Button("Stop"); //定义Play按钮
		btStop.addActionListener(this); //给Play按钮添加一个监听事件
		//将按钮添加到Applet中
		add(btPlay);
		add(btLoop);
		add(btStop);
	}

	public void actionPerformed(ActionEvent e){
		//如果点击的是Play按钮
		if (e.getSource() == btPlay){
			play();
		}
		//如果点击的是loop按钮
		if (e.getSource() == btLoop){
			loop();
		}
		//如果点击的是stop按钮
		if (e.getSource() == btStop){
			stop();
		}
	}

	/**
	 *<br>
	 * 方法说明：播放声音 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void play(){
		if (audio != null)
			stop();
		audio.play();
	}

	/**
	 *<br>
	 * 方法说明：循环播放声音 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void loop(){
		if (audio != null)
			audio.loop();
	}

	/**
	 *<br>
	 * 方法说明：停止播放声音 <br>
	 * 输入参数： <br>
	 * 返回类型：
	 */
	public void stop(){
		if (audio != null)
			audio.stop();
	}
	
	
}