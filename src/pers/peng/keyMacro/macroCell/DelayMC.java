package pers.peng.keyMacro.macroCell;

import java.util.concurrent.TimeUnit;

/**
 * 延迟宏单元
 * 
 * @author peng
 *
 */
public class DelayMC implements MacroCell {

	/**
	 * 延迟/ms
	 */
	private long delay;

	/**
	 * 构造延迟宏单元
	 * 
	 * @param delay 延迟/ms
	 */
	public DelayMC(long delay) {
		this.delay = delay;
	}

	/**
	 * 执行宏单元
	 */
	@Override
	public void performed() {
		try {
			TimeUnit.MILLISECONDS.sleep(delay);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();// sleep中中断，中断状态会被清空，需要重新中断
		}
	}

}
