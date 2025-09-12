package pers.peng.keyMacro.macroCell;

import pers.peng.keyMacro.utils.Constants;
import pers.peng.keyMacro.utils.DD;

/**
 * 鼠标按键宏单元
 * 
 * @author peng
 *
 */
public class MouseClickMC implements MacroCell {

	/**
	 * dd驱动实例
	 */
	private static DD dd = DD.INSTANCE;

	/**
	 * dd代码
	 * 
	 * @see Constants.DdCode
	 */
	private int ddCode;

	/**
	 * 构造鼠标按键宏单元
	 * 
	 * @param ddCode dd代码
	 * @see Constants.DdCode
	 */
	public MouseClickMC(int ddCode) {
		this.ddCode = ddCode;
	}

	/**
	 * 执行宏单元
	 */
	@Override
	public void performed() {
		dd.DD_btn(ddCode);
	}

}
