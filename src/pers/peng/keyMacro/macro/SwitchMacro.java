package pers.peng.keyMacro.macro;

import java.util.concurrent.TimeUnit;

import pers.peng.keyMacro.macroCell.MacroCell;
import pers.peng.keyMacro.main.Main;
import pers.peng.keyMacro.utils.Constants.SystemStatus;

/**
 * 开关触发宏，按一下开启，再按一下停止
 * 
 * @author peng
 *
 */
public class SwitchMacro extends Macro {

	/**
	 * 每一轮循环的时间间隔/ms
	 */
	private long intervalTime;

	/**
	 * 构造开关触发宏，按一下开启，再按一下停止
	 * 
	 * @param macroCells   宏单元数组，按顺序触发
	 * @param intervalTime 每一轮循环的时间间隔/ms
	 */
	public SwitchMacro(MacroCell[] macroCells, long intervalTime) {
		this.macroCells = macroCells;
		this.intervalTime = intervalTime;
	}

	/**
	 * 触发键按下
	 */
	@Override
	public void keyDown() {
		if (isRunning) {
			synchronized (this) {
				if (isRunning) {
					isRunning = false;
					this.interrupt();
				}
			}
		} else {
			synchronized (this) {
				if (!isRunning) {
					isRunning = true;
					this.notifyAll();
				}
			}
		}
	}

	/**
	 * 触发键弹起
	 */
	@Override
	public void keyUp() {
		return;
	}

	/**
	 * 删除宏
	 */
	@Override
	public void delete() {
		synchronized (this) {
			isRunning = false;
			isDeleted = true;
			this.interrupt();
		}
		try {
			this.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 启动宏线程
	 */
	@Override
	public void run() {
		while (!isDeleted) {
			if (isRunning && Main.systemStatus == SystemStatus.RUNNING) {
				for (MacroCell macroCell : macroCells) {
					if (this.isInterrupted()) {
						break;
					}
					macroCell.performed();
				}
				try {
					TimeUnit.MILLISECONDS.sleep(intervalTime);
				} catch (InterruptedException e) {
				}
			} else {
				synchronized (this) {
					try {
						this.wait();
					} catch (InterruptedException e) {
					}
				}
			}
		}
	}

}
