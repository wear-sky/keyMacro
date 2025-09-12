package pers.peng.keyMacro.macroCell;

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;

import pers.peng.keyMacro.utils.Constants;
import pers.peng.keyMacro.utils.DD;

/**
 * 键盘宏单元
 * 
 * @author peng
 *
 */
public class KeyboardMC implements MacroCell {

	public final static int KEY_DOWN = 1;

	public final static int KEY_UP = 2;

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
	 * 按键动作类型
	 * 
	 * @see #KEY_DOWN
	 * @see #KEY_UP
	 */
	private int keyType;

	/**
	 * 构造键盘宏单元
	 * 
	 * @param ddCode  dd代码
	 * @param keyType 按键动作类型
	 * @see Constants.DdCode
	 * @see #KEY_DOWN
	 * @see #KEY_UP
	 */
	public KeyboardMC(int ddCode, int keyType) {
		this.ddCode = ddCode;
		this.keyType = keyType;
	}

	/**
	 * 执行宏单元
	 */
	@Override
	public void performed() {
		if (ddCode == 705) {// page up
			Robot robot;
			try {
				robot = new Robot();
				Rectangle screenRect = new Rectangle(0, 0, 1920, 1080); // 可以根据需要设置屏幕尺寸
				BufferedImage screenImage = robot.createScreenCapture(screenRect);
				for (int x = 0; x < 1920; x++) {
					for (int y = 0; y < 1080; y++) {
						int p = screenImage.getRGB(x, y);
						int r = (p >> 16) & 0xff;
						int g = (p >> 8) & 0xff;
						int b = p & 0xff;
						if (r == 223 && g == 250 && b == 255) {
							MouseMoveMC mouseMoveMC1 = new MouseMoveMC(x, y);
							mouseMoveMC1.performed();
							Thread.sleep(100);
							MouseClickMC mouseClickMC1 = new MouseClickMC(1);
							MouseClickMC mouseClickMC2 = new MouseClickMC(2);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							Thread.sleep(100);
							MouseMoveMC mouseMoveMC2 = new MouseMoveMC(x - 85, y + 20);
							mouseMoveMC2.performed();
							Thread.sleep(100);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							Thread.sleep(100);
							mouseMoveMC1.performed();
							Thread.sleep(100);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							Thread.sleep(100);
							MouseMoveMC mouseMoveMC22 = new MouseMoveMC(x + 180, y + 20);
							mouseMoveMC22.performed();
							Thread.sleep(100);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
						}
					}
				}
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
				return;
			}
			return;
		}
		if (ddCode == 708) {// page down,900ms
			Robot robot;
			try {
				robot = new Robot();
				Rectangle screenRect = new Rectangle(0, 0, 1920, 1080); // 可以根据需要设置屏幕尺寸
				BufferedImage screenImage = robot.createScreenCapture(screenRect);
				for (int x = 0; x < 1920; x++) {
					for (int y = 0; y < 1080; y++) {
						int p = screenImage.getRGB(x, y);
						int r = (p >> 16) & 0xff;
						int g = (p >> 8) & 0xff;
						int b = p & 0xff;
						if (r == 225 && g == 250 && b == 255) {
							MouseMoveMC mouseMoveMC1 = new MouseMoveMC(x, y);
							mouseMoveMC1.performed();
							Thread.sleep(100);
							MouseClickMC mouseClickMC1 = new MouseClickMC(1);
							MouseClickMC mouseClickMC2 = new MouseClickMC(2);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							Thread.sleep(100);
							MouseMoveMC mouseMoveMC2 = new MouseMoveMC(x - 175, y + 25);
							mouseMoveMC2.performed();
							Thread.sleep(100);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							Thread.sleep(100);
							mouseMoveMC1.performed();
							Thread.sleep(100);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							Thread.sleep(100);
							MouseMoveMC mouseMoveMC22 = new MouseMoveMC(x + 370, y + 40);
							mouseMoveMC22.performed();
							Thread.sleep(100);
							mouseClickMC1.performed();
							Thread.sleep(50);
							mouseClickMC2.performed();
							return;
						}
					}
				}
			} catch (AWTException | InterruptedException e) {
				e.printStackTrace();
				return;
			}
			return;
		}
		dd.DD_key(ddCode, keyType);
		System.out.println(ddCode);
	}

}
