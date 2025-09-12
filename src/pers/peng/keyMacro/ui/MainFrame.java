package pers.peng.keyMacro.ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import pers.peng.keyMacro.hook.KeyboardHook;
import pers.peng.keyMacro.macro.Macro;
import pers.peng.keyMacro.main.Main;
import pers.peng.keyMacro.utils.Constants.SystemStatus;
import pers.peng.keyMacro.utils.IconUtils;
import pers.peng.keyMacro.utils.Switch;

/**
 * 主窗口
 * 
 * @author peng
 *
 */
public class MainFrame extends JFrame {

	/**
	 * 序列化核验版本号
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * 系统托盘图标
	 */
	private TrayIcon trayIcon;

	/**
	 * 创建配置按钮
	 */
	private JButton createConfigButton;

	/**
	 * 选择配置下拉列表框
	 */
	private JComboBox<String> selectConfigComboBox;

	/**
	 * 删除配置按钮
	 */
	private JButton deleteConfigButton;

	/**
	 * 导入配置按钮
	 */
	private JButton inputConfigButton;

	/**
	 * 导出配置按钮
	 */
	private JButton outputConfigButton;

	/**
	 * 切换配置快捷键录入框
	 */
	private JTextField switchConfigField;

	/**
	 * 宏表格
	 */
	private JTable macrosTable;

	/**
	 * 创建宏按钮
	 */
	private JButton createMacroButton;

	/**
	 * 修改宏按钮
	 */
	private JButton modifyMacroButton;

	/**
	 * 删除宏按钮
	 */
	private JButton deleteMacroButton;

	/**
	 * 清除宏按钮
	 */
	private JButton clearMacroButton;

	/**
	 * 开关快捷键录入框
	 */
	private JTextField switchField;

	/**
	 * 开关按钮
	 */
	private JButton switchButton;

	/**
	 * 获取系统托盘图标
	 * 
	 * @return 系统托盘图标
	 */
	public TrayIcon getTrayIcon() {
		return trayIcon;
	}

	/**
	 * 获取创建配置按钮
	 * 
	 * @return 创建配置按钮
	 */
	public JButton getCreateConfigButton() {
		return createConfigButton;
	}

	/**
	 * 获取选择配置下拉列表
	 * 
	 * @return 择配置下拉列表
	 */
	public JComboBox<String> getSelectConfigComboBox() {
		return selectConfigComboBox;
	}

	/**
	 * 获取删除配置按钮
	 * 
	 * @return 删除配置按钮
	 */
	public JButton getDeleteConfigButton() {
		return deleteConfigButton;
	}

	/**
	 * 获取导入配置按钮
	 * 
	 * @return 导入配置按钮
	 */
	public JButton getInputConfigButton() {
		return inputConfigButton;
	}

	/**
	 * 获取导出配置按钮
	 * 
	 * @return 导出配置按钮
	 */
	public JButton getOutputConfigButton() {
		return outputConfigButton;
	}

	/**
	 * 获取切换配置快捷键录入框
	 * 
	 * @return 切换配置快捷键录入框
	 */
	public JTextField getSwitchConfigField() {
		return switchConfigField;
	}

	/**
	 * 获取宏表格
	 * 
	 * @return 宏表格
	 */
	public JTable getMacrosTable() {
		return macrosTable;
	}

	/**
	 * 获取创建宏按钮
	 * 
	 * @return 创建宏按钮
	 */
	public JButton getCreateMacroButton() {
		return createMacroButton;
	}

	/**
	 * 获取修改宏按钮
	 * 
	 * @return 修改宏按钮
	 */
	public JButton getModifyMacroButton() {
		return modifyMacroButton;
	}

	/**
	 * 获取删除宏按钮
	 * 
	 * @return 删除宏按钮
	 */
	public JButton getDeleteMacroButton() {
		return deleteMacroButton;
	}

	/**
	 * 获取清除宏按钮
	 * 
	 * @return 清除宏按钮
	 */
	public JButton getClearMacroButton() {
		return clearMacroButton;
	}

	/**
	 * 获取开关快捷键键录入框
	 * 
	 * @return 开关快捷键键录入框
	 */
	public JTextField getSwitchField() {
		return switchField;
	}

	/**
	 * 获取开关按钮
	 * 
	 * @return 开关按钮
	 */
	public JButton getSwitchButton() {
		return switchButton;
	}

	/**
	 * 构造主窗口
	 */
	public MainFrame() {
		KeyboardHook keyboardHook = new KeyboardHook(this);
		keyboardHook.start();
		setIconImage(Toolkit.getDefaultToolkit().getImage("./img/图标.png"));
		setTitle("键鼠宏编辑器");
		setBounds(100, 100, 750, 600);
		setLocationRelativeTo(rootPane);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		addWindowListener(new MainFrameAdapter(keyboardHook));
		MenuItem exitItem = new MenuItem("退出");
		exitItem.addActionListener(new ExitItemListener(keyboardHook));
		PopupMenu popupMenu = new PopupMenu();
		popupMenu.add(exitItem);
		trayIcon = new TrayIcon(IconUtils.getTrayIcon(), "显示主页面", popupMenu);
		trayIcon.addActionListener(new TrayIconListener());
		try {
			SystemTray.getSystemTray().add(trayIcon);
		} catch (AWTException e) {
			e.printStackTrace();
		}
		JPanel mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
		setContentPane(mainPanel);
		selectConfigComboBox = new JComboBox<String>();
		selectConfigComboBox.addItemListener(new SelectConfigComboBoxListener());
		createConfigButton = new JButton();
		createConfigButton.setIcon(IconUtils.getButtonIcon("./img/添加.png", 30, 30));
		createConfigButton.setToolTipText("新建配置");
		createConfigButton.addActionListener(new CreateConfigButtonListener());
		deleteConfigButton = new JButton();
		deleteConfigButton.setIcon(IconUtils.getButtonIcon("./img/删除.png", 30, 30));
		deleteConfigButton.setToolTipText("删除配置");
		deleteConfigButton.addActionListener(new DeleteConfigButtonListener());
		inputConfigButton = new JButton();
		inputConfigButton.setIcon(IconUtils.getButtonIcon("./img/导入.png", 30, 30));
		inputConfigButton.setToolTipText("导入配置");
		inputConfigButton.addActionListener(new InputConfigButtonListener());
		outputConfigButton = new JButton();
		outputConfigButton.setIcon(IconUtils.getButtonIcon("./img/导出.png", 30, 30));
		outputConfigButton.setToolTipText("导出配置");
		outputConfigButton.addActionListener(new OutputConfigButtonListener());
		JLabel selectConfigLabel = new JLabel("切换键：");
		switchConfigField = new JTextField();
		switchConfigField.setColumns(5);
		switchConfigField.addFocusListener(new SwitchConfigFieldListener());
		JScrollPane macrosScrollPane = new JScrollPane();
		MacrosTableModel macrosTableModel = new MacrosTableModel(new String[] { "触发键", "连发键", "触发方式", "连发间隔/ms" });
		macrosTable = new JTable(macrosTableModel);
		macrosTable.getTableHeader().setReorderingAllowed(false);
		macrosTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		macrosTable.setGridColor(Color.LIGHT_GRAY);
		macrosTable.setRowHeight(20);
		TableColumnModel tableColumnModel = macrosTable.getColumnModel();
		TableColumn tableColumn0 = tableColumnModel.getColumn(0);
		tableColumn0.setPreferredWidth(120);
		tableColumn0.setMaxWidth(120);
		tableColumn0.setMinWidth(120);
		tableColumn0.setResizable(false);
		TableColumn tableColumn2 = tableColumnModel.getColumn(2);
		tableColumn2.setPreferredWidth(60);
		tableColumn2.setMaxWidth(60);
		tableColumn2.setMinWidth(60);
		tableColumn2.setResizable(false);
		TableColumn tableColumn3 = tableColumnModel.getColumn(3);
		tableColumn3.setPreferredWidth(80);
		tableColumn3.setMaxWidth(80);
		tableColumn3.setMinWidth(80);
		tableColumn3.setResizable(false);
		macrosScrollPane.setViewportView(macrosTable);
		createMacroButton = new JButton(IconUtils.getButtonIcon("./img/添加.png", 30, 30));
		createMacroButton.setEnabled(false);
		createMacroButton.setToolTipText("创建宏");
		createMacroButton.addActionListener(new CreateMacroButtonListener(keyboardHook));
		modifyMacroButton = new JButton(IconUtils.getButtonIcon("./img/修改.png", 30, 30));
		modifyMacroButton.setEnabled(false);
		modifyMacroButton.setToolTipText("编辑宏");
		modifyMacroButton.addActionListener(new ModifyMacroButtonListener(keyboardHook));
		deleteMacroButton = new JButton(IconUtils.getButtonIcon("./img/删除.png", 30, 30));
		deleteMacroButton.setEnabled(false);
		deleteMacroButton.setToolTipText("删除选中");
		deleteMacroButton.addActionListener(new DeleteMacroButtonListener());
		clearMacroButton = new JButton(IconUtils.getButtonIcon("./img/清空.png", 30, 30));
		clearMacroButton.setEnabled(false);
		clearMacroButton.setToolTipText("清空所有");
		clearMacroButton.addActionListener(new ClearMacroButtonListener());
		JLabel switchLabel = new JLabel("开关键：");
		switchField = new JTextField();
		switchField.setEnabled(false);
		switchField.setColumns(5);
		switchField.addFocusListener(new SwitchFieldListener());
		switchButton = new JButton("开始");
		switchButton.setEnabled(false);
		switchButton.addActionListener(new StartButtonListener());
		getRootPane().setDefaultButton(switchButton);
		GroupLayout gl_contentPane = new GroupLayout(mainPanel);
		gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup().addGap(6).addGroup(gl_contentPane
						.createParallelGroup(
								Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(selectConfigComboBox, GroupLayout.PREFERRED_SIZE, 90,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(createConfigButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(deleteConfigButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(inputConfigButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(outputConfigButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(selectConfigLabel, GroupLayout.PREFERRED_SIZE, 48,
										GroupLayout.PREFERRED_SIZE)
								.addGap(1)
								.addComponent(switchConfigField, GroupLayout.PREFERRED_SIZE, 90,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(macrosScrollPane, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
								.addGap(6)))
						.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(clearMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(deleteMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(modifyMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(createMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE))
						.addGap(6))
				.addGroup(gl_contentPane.createSequentialGroup().addContainerGap(491, Short.MAX_VALUE)
						.addComponent(switchLabel, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE).addGap(1)
						.addComponent(switchField, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(switchButton, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
						.addContainerGap()));
		gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING).addGroup(gl_contentPane
				.createSequentialGroup().addGap(6)
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(selectConfigComboBox, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(selectConfigLabel, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addComponent(switchConfigField, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE))
						.addComponent(createConfigButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(deleteConfigButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(inputConfigButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
						.addComponent(outputConfigButton, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
				.addPreferredGap(ComponentPlacement.RELATED)
				.addGroup(gl_contentPane
						.createParallelGroup(
								Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(createMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(modifyMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(deleteMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(clearMacroButton, GroupLayout.PREFERRED_SIZE, 30,
										GroupLayout.PREFERRED_SIZE)
								.addGap(21))
						.addGroup(gl_contentPane.createSequentialGroup()
								.addComponent(macrosScrollPane, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
								.addPreferredGap(ComponentPlacement.UNRELATED)))
				.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(switchButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_contentPane.createSequentialGroup().addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
										.addComponent(switchField, GroupLayout.PREFERRED_SIZE, 30,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(switchLabel, GroupLayout.PREFERRED_SIZE, 30,
												GroupLayout.PREFERRED_SIZE))))
				.addContainerGap()));
		mainPanel.setLayout(gl_contentPane);
		File directory = new File("./config");
		if (directory.exists()) {
			String[] configFileNames = directory.list();
			for (String configFileName : configFileNames) {
				if (configFileName.endsWith(".ini")) {
					selectConfigComboBox.addItem(configFileName.substring(0, configFileName.lastIndexOf(".")));
				}
			}
		}
		File systemConfigFile = new File("./config.ini");
		if (systemConfigFile.exists()) {
			FileReader fileReader = null;
			BufferedReader bufferedReader = null;
			try {
				fileReader = new FileReader(systemConfigFile);
				bufferedReader = new BufferedReader(fileReader);
				switchConfigField.setText(bufferedReader.readLine());
				selectConfigComboBox.setSelectedItem(bufferedReader.readLine());
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					fileReader.close();
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 将配置写入配置文件保存
	 */
	private void writeConfigToFile() {
		if (selectConfigComboBox.getSelectedIndex() == -1) {
			return;
		}
		String configName = selectConfigComboBox.getSelectedItem().toString().trim();
		if ("".equals(configName)) {
			return;
		}
		File configFile = new File("./config/" + configName + ".ini");
		if (!configFile.exists()) {
			File directory = configFile.getParentFile();
			if (!directory.exists()) {
				directory.mkdirs();
			}
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(configFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(switchField.getText().trim());
			bufferedWriter.newLine();
			int rowCount = macrosTable.getRowCount();
			int columnCount = macrosTable.getColumnCount();
			for (int row = 0; row < rowCount; row++) {
				for (int column = 0; column < columnCount; column++) {
					bufferedWriter.write((String) macrosTable.getValueAt(row, column));
					bufferedWriter.newLine();
				}
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 将系统配置写入配置文件保存
	 */
	private void writeSystemConfigToFile() {
		File configFile = new File("./config.ini");
		if (!configFile.exists()) {
			try {
				configFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
				return;
			}
		}
		FileWriter fileWriter = null;
		BufferedWriter bufferedWriter = null;
		try {
			fileWriter = new FileWriter(configFile);
			bufferedWriter = new BufferedWriter(fileWriter);
			bufferedWriter.write(switchConfigField.getText().trim());
			bufferedWriter.newLine();
			if (null != selectConfigComboBox.getSelectedItem()) {
				bufferedWriter.write(selectConfigComboBox.getSelectedItem().toString());
			} else {
				bufferedWriter.write("");
			}
			bufferedWriter.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fileWriter.close();
				bufferedWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 复制文件
	 * 
	 * @param selectedFile 被复制的文件
	 * @param newFile      新文件
	 * @return 是否成功
	 */
	private boolean copyFile(File selectedFile, File newFile) {
		try {
			newFile.createNewFile();
		} catch (IOException ioException) {
			JOptionPane.showMessageDialog(this, "复制配置文件失败", "复制配置文件失败", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		BufferedInputStream bufferedInputStream = null;
		BufferedOutputStream bufferedOutputStream = null;
		try {
			bufferedInputStream = new BufferedInputStream(new FileInputStream(selectedFile));
			bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(newFile));
			byte[] b = new byte[1024];
			int length = -1;
			while ((length = bufferedInputStream.read(b, 0, 1024)) != -1) {
				bufferedOutputStream.write(b, 0, length);
			}
		} catch (Exception exception) {
			newFile.delete();
			JOptionPane.showMessageDialog(this, "复制配置文件失败", "复制配置文件失败", JOptionPane.ERROR_MESSAGE);
			return false;
		} finally {
			if (bufferedInputStream != null) {
				try {
					bufferedInputStream.close();
				} catch (IOException ioException) {
				}
			}
			if (bufferedOutputStream != null) {
				try {
					bufferedOutputStream.close();
				} catch (IOException ioException) {
				}
			}
		}
		return true;
	}

	/**
	 * 关闭软件过程
	 * 
	 * @param keyboardHook 键盘钩子
	 */
	private void shutdown(KeyboardHook keyboardHook) {
		Iterator<Macro> macrosIterator = Main.macros.values().iterator();
		while (macrosIterator.hasNext()) {
			Macro macro = macrosIterator.next();
			macro.delete();
		}
		keyboardHook.unHook();
		writeSystemConfigToFile();
	}

	/**
	 * 主窗口适配器
	 * 
	 * @author peng
	 *
	 */
	class MainFrameAdapter extends WindowAdapter {

		/**
		 * 键盘钩子
		 */
		private KeyboardHook keyboardHook;

		/**
		 * 主窗口适配器
		 * 
		 * @param keyboardHook 键盘钩子
		 */
		public MainFrameAdapter(KeyboardHook keyboardHook) {
			this.keyboardHook = keyboardHook;
		}

		/**
		 * 主窗口关闭过程被触发执行
		 */
		@Override
		public void windowClosing(WindowEvent e) {
			shutdown(keyboardHook);
		}

		/**
		 * 主窗口最小化过程被触发执行
		 */
		@Override
		public void windowIconified(WindowEvent e) {
			setState(JFrame.NORMAL);
			setVisible(false);
		}
	}

	/**
	 * 系统托盘退出按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class ExitItemListener implements ActionListener {

		/**
		 * 键盘钩子
		 */
		private KeyboardHook keyboardHook;

		/**
		 * 构造系统托盘退出按钮监听器
		 * 
		 * @param keyboardHook 键盘钩子
		 */
		public ExitItemListener(KeyboardHook keyboardHook) {
			this.keyboardHook = keyboardHook;
		}

		/**
		 * 系统托盘退出按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			shutdown(keyboardHook);
			System.exit(0);
		}
	}

	/**
	 * 系统托盘图标监听器
	 * 
	 * @author peng
	 *
	 */
	class TrayIconListener implements ActionListener {

		/**
		 * 系统托盘图标被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			setVisible(true);
		}
	}

	/**
	 * 选择配置下拉列表监听器
	 * 
	 * @author peng
	 *
	 */
	class SelectConfigComboBoxListener implements ItemListener {

		/**
		 * 选择配置下拉列表状态改变
		 */
		@Override
		public void itemStateChanged(ItemEvent e) {
			if (e.getStateChange() == ItemEvent.SELECTED) {// 有配置被选中
				Switch.switchConfig(MainFrame.this);
			} else if (selectConfigComboBox.getSelectedIndex() == -1) {// 无配置被选中
				Switch.switchConfig(MainFrame.this);
			}
		}
	}

	/**
	 * 创建配置按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class CreateConfigButtonListener implements ActionListener {

		/**
		 * 创建配置按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String configName = JOptionPane.showInputDialog(MainFrame.this, "配置名称", "创建配置", JOptionPane.DEFAULT_OPTION)
					.trim();
			if (configName == null || "".equals(configName)) {
				return;
			}
			int itemCount = selectConfigComboBox.getItemCount();
			for (int item = 0; item < itemCount; item++) {
				if (configName.equals(selectConfigComboBox.getItemAt(item))) {
					JOptionPane.showMessageDialog(MainFrame.this, "配置已存在", "错误", JOptionPane.ERROR_MESSAGE);
					selectConfigComboBox.setSelectedIndex(item);
					return;
				}
			}
			selectConfigComboBox.addItem(configName);
			selectConfigComboBox.setSelectedItem(configName);
			DefaultTableModel macrosTableModel = (DefaultTableModel) macrosTable.getModel();
			while (macrosTableModel.getRowCount() != 0) {
				macrosTableModel.removeRow(0);
			}
			switchField.setText("");
			writeConfigToFile();
		}
	}

	/**
	 * 删除配置按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class DeleteConfigButtonListener implements ActionListener {

		/**
		 * 删除配置按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedIndex = selectConfigComboBox.getSelectedIndex();
			if (selectedIndex == -1) {
				JOptionPane.showMessageDialog(MainFrame.this, "未选择配置文件", "未选择配置文件", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (JOptionPane.showConfirmDialog(MainFrame.this, "确定删除已选配置文件吗？", "确认",
					JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) {
				File configFile = new File("./config/" + selectConfigComboBox.getItemAt(selectedIndex) + ".ini");
				if (configFile.exists()) {
					configFile.delete();
				}
				selectConfigComboBox.removeItemAt(selectedIndex);
			}
		}
	}

	/**
	 * 导入配置按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class InputConfigButtonListener implements ActionListener {

		/**
		 * 导入配置按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser configFileChooser = new JFileChooser(new File("."));
			ConfigFileFilter configFileFilter = new ConfigFileFilter();
			configFileChooser.setFileFilter(configFileFilter);
			if (JFileChooser.APPROVE_OPTION != configFileChooser.showOpenDialog(MainFrame.this)) {
				return;
			}
			File selectedConfigFile = configFileChooser.getSelectedFile();
			String selectedFileName = selectedConfigFile.getName();
			String selectedConfigName = selectedFileName.substring(0, selectedFileName.lastIndexOf("."));
			for (int i = selectConfigComboBox.getItemCount() - 1; i >= 0; i--) {
				if (selectConfigComboBox.getItemAt(i).equals(selectedConfigName)) {
					JOptionPane.showMessageDialog(MainFrame.this, "配置文件已存在", "配置文件已存在",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				}
			}
			File newConfigFile = new File("./config/" + selectedFileName);
			if (!copyFile(selectedConfigFile, newConfigFile)) {
				return;
			}
			selectConfigComboBox.addItem(selectedConfigName);
			selectConfigComboBox.setSelectedItem(selectedConfigName);
		}
	}

	/**
	 * 配置文件过滤器
	 * 
	 * @author peng
	 *
	 */
	class ConfigFileFilter extends FileFilter {

		/**
		 * 只显示文件夹和扩展名为“ini”的文件
		 */
		@Override
		public boolean accept(File file) {
			if (file.isDirectory() || file.getAbsolutePath().endsWith(".ini")) {
				return true;
			}
			return false;
		}

		/**
		 * 文件过滤器描述
		 */
		@Override
		public String getDescription() {
			return ".ini";
		}
	}

	/**
	 * 导出配置按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class OutputConfigButtonListener implements ActionListener {

		/**
		 * 导出配置按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			String selectedConfig = (String) selectConfigComboBox.getSelectedItem();
			if (selectedConfig == null) {
				JOptionPane.showMessageDialog(MainFrame.this, "未选择配置文件", "未选择配置文件", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			File selectedConfigFile = new File("./config/" + selectedConfig + ".ini");
			JFileChooser outputConfigFileChooser = new JFileChooser(new File("."));
			OutputDirectoryFilter outputDirectoryFilter = new OutputDirectoryFilter();
			outputConfigFileChooser.setFileFilter(outputDirectoryFilter);
			if (JFileChooser.APPROVE_OPTION != outputConfigFileChooser.showSaveDialog(MainFrame.this)) {
				return;
			}
			String outputConfigFilePath = outputConfigFileChooser.getSelectedFile().getAbsolutePath();
			if (!outputConfigFilePath.endsWith(".ini")) {
				outputConfigFilePath = outputConfigFilePath + ".ini";
			}
			File outputConfigFile = new File(outputConfigFilePath);
			if (outputConfigFile.exists()) {
				JOptionPane.showMessageDialog(MainFrame.this, "文件已存在", "文件已存在", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			if (copyFile(selectedConfigFile, outputConfigFile)) {
				JOptionPane.showMessageDialog(MainFrame.this, "导出配置文件成功", "成功", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}

	/**
	 * 导出文件夹过滤器
	 * 
	 * @author peng
	 *
	 */
	class OutputDirectoryFilter extends FileFilter {

		/**
		 * 只显示文件夹
		 */
		@Override
		public boolean accept(File file) {
			if (file.isDirectory()) {
				return true;
			}
			return false;
		}

		/**
		 * 导出文件夹过滤器描述
		 */
		@Override
		public String getDescription() {
			return ".ini";
		}
	}

	/**
	 * 切换配置录入框焦点监听器
	 * 
	 * @author peng
	 *
	 */
	class SwitchConfigFieldListener implements FocusListener {

		/**
		 * 切换配置录入框获得焦点
		 */
		@Override
		public void focusGained(FocusEvent e) {
			Main.systemStatus = SystemStatus.RECORDIND_SWITCHCONFIGKEY;
		}

		/**
		 * 切换配置录入框失去焦点
		 */
		@Override
		public void focusLost(FocusEvent e) {
			Main.systemStatus = SystemStatus.STOPPED;
		}
	}

	/**
	 * 宏表格模型
	 * 
	 * @author peng
	 *
	 */
	public class MacrosTableModel extends DefaultTableModel {

		/**
		 * 序列化核验版本号
		 */
		private static final long serialVersionUID = 1L;

		/**
		 * 构造宏表格模型
		 * 
		 * @param columnNames 列名
		 */
		public MacrosTableModel(Object[] columnNames) {
			super(columnNames, 0);
		}

		/**
		 * 单元格是否可编辑
		 * 
		 * @return 不可编辑
		 */
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	}

	/**
	 * 创建宏按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class CreateMacroButtonListener implements ActionListener {

		/**
		 * 键盘钩子
		 */
		private KeyboardHook keyboardHook;

		/**
		 * 构造创建宏按钮监听器
		 * 
		 * @param keyboardHook 键盘钩子
		 */
		public CreateMacroButtonListener(KeyboardHook keyboardHook) {
			this.keyboardHook = keyboardHook;
		}

		/**
		 * 创建宏按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (selectConfigComboBox.getItemCount() == 0) {
				JOptionPane.showMessageDialog(MainFrame.this, "请先创建配置文件", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			MacroDialog createMacroDialog = new CreateMacroDialog(MainFrame.this, true);
			keyboardHook.setMacroDialog(createMacroDialog);
			createMacroDialog.setLocationRelativeTo(MainFrame.this);
			createMacroDialog.setVisible(true);
			writeConfigToFile();
		}
	}

	/**
	 * 修改宏按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class ModifyMacroButtonListener implements ActionListener {

		/**
		 * 键盘钩子
		 */
		private KeyboardHook keyboardHook;

		/**
		 * 构造修改宏按钮监听器
		 * 
		 * @param keyboardHook 键盘钩子
		 */
		public ModifyMacroButtonListener(KeyboardHook keyboardHook) {
			this.keyboardHook = keyboardHook;
		}

		/**
		 * 修改宏按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int row = macrosTable.getSelectedRow();
			if (row == -1) {
				JOptionPane.showMessageDialog(MainFrame.this, "请先选择需要修改的宏", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			MacroDialog modifyMacroDialog = new ModifyMacroDialog(MainFrame.this, true, row);
			keyboardHook.setMacroDialog(modifyMacroDialog);
			modifyMacroDialog.setLocationRelativeTo(MainFrame.this);
			modifyMacroDialog.setVisible(true);
			writeConfigToFile();
		}
	}

	/**
	 * 删除宏按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class DeleteMacroButtonListener implements ActionListener {

		/**
		 * 删除宏按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			int selectedRow = macrosTable.getSelectedRow();
			if (selectedRow == -1) {
				JOptionPane.showMessageDialog(MainFrame.this, "请先选择需要删除的宏", "错误", JOptionPane.ERROR_MESSAGE);
				return;
			}
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(MainFrame.this, "确定删除所选宏吗？", "删除",
					JOptionPane.YES_NO_OPTION)) {
				((DefaultTableModel) macrosTable.getModel()).removeRow(selectedRow);
				writeConfigToFile();
			}
		}
	}

	/**
	 * 清除宏按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class ClearMacroButtonListener implements ActionListener {

		/**
		 * 清除宏按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (JOptionPane.OK_OPTION == JOptionPane.showConfirmDialog(MainFrame.this, "确定清空所有宏吗？", "清空",
					JOptionPane.YES_NO_OPTION)) {
				DefaultTableModel macrosTableModel = (DefaultTableModel) macrosTable.getModel();
				while (macrosTableModel.getRowCount() != 0) {
					macrosTableModel.removeRow(0);
				}
				writeConfigToFile();
			}
		}
	}

	/**
	 * 开关键录入框焦点监听器
	 * 
	 * @author peng
	 *
	 */
	class SwitchFieldListener implements FocusListener {

		/**
		 * 开关键录入框获得焦点
		 */
		@Override
		public void focusGained(FocusEvent e) {
			Main.systemStatus = SystemStatus.RECORDIND_SWITCHKEY;
		}

		/**
		 * 开关键录入框失去焦点
		 */
		@Override
		public void focusLost(FocusEvent e) {
			Main.systemStatus = SystemStatus.STOPPED;
			writeConfigToFile();
		}
	}

	/**
	 * 开关按钮监听器
	 * 
	 * @author peng
	 *
	 */
	class StartButtonListener implements ActionListener {

		/**
		 * 开关按钮被触发执行
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			if (Main.systemStatus == SystemStatus.RUNNING) {
				Switch.stop(MainFrame.this);
			} else {
				Switch.start(MainFrame.this);
			}
		}
	}
}
