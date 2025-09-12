package pers.peng.keyMacro.macroCell;

import pers.peng.keyMacro.utils.DD;

/**
 * 鼠标移动宏单元
 * 
 * @author peng
 *
 */
public class MouseMoveMC implements MacroCell {

	/**
	 * dd驱动实例
	 */
	private static DD dd = DD.INSTANCE;

	/**
	 * 鼠标移动横坐标，显示器左上角为0
	 */
	private int x;

	/**
	 * 鼠标移动纵坐标，显示器左上角为0
	 */
	private int y;

	/**
	 * 构造鼠标移动宏单元
	 * 
	 * @param x 鼠标移动横坐标，显示器左上角为0
	 * @param y 鼠标移动纵坐标，显示器左上角为0
	 */
	public MouseMoveMC(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * 执行宏单元
	 */
	@Override
	public void performed() {
		dd.DD_mov(x, y);
	}

}
