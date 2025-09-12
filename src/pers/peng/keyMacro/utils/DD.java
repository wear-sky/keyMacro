package pers.peng.keyMacro.utils;

import com.sun.jna.Library;
import com.sun.jna.Native;

import pers.peng.keyMacro.utils.Constants.DdCode;

/**
 * 连接DD驱动链接库
 * 
 * @author peng
 *
 */
public interface DD extends Library {

	/**
	 * DD驱动实例
	 */
	DD INSTANCE = (DD) Native.load("./dll/dd", DD.class);

	/**
	 * 鼠标绝对移动
	 * 
	 * @param x 以屏幕左上角为原点，以像素为单位
	 * @param y 以屏幕左上角为原点，以像素为单位
	 * @return
	 */
	int DD_mov(int x, int y);

	/**
	 * 模拟鼠标相对移动
	 * 
	 * @param dx 以当前坐标为原点，以像素为单位
	 * @param dy 以当前坐标为原点，以像素为单位
	 * @return
	 */
	int DD_movR(int dx, int dy);

	/**
	 * 鼠标点击
	 * 
	 * @param btn 鼠标按键码
	 * @see DdCode
	 * @return
	 */
	int DD_btn(int btn);

	/**
	 * 模拟鼠标滚轮
	 * 
	 * @param whl 1、前滚；2、后滚。
	 * @return
	 */
	int DD_whl(int whl);

	/**
	 * 键盘按键
	 * 
	 * @param ddcode 键盘按键码
	 * @see DdCode
	 * @param flag 1、按下；2、放开。
	 * @return
	 */
	int DD_key(int ddcode, int flag);

	/**
	 * 直接输入键盘上可见字符和空格
	 * 
	 * @param s 输入的字符串
	 * @return
	 */
	int DD_str(String s);

}
