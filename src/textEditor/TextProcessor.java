package textEditor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTextPane;
import javax.swing.JToolBar;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.text.StyledEditorKit;

public class TextProcessor {
	public static void main(String[] args) {
		
		TextProcessorFrame frame = new TextProcessorFrame();
		
	}
}

class TextProcessorFrame extends JFrame{
	
	public TextProcessorFrame() {
		
		add(new TextProcessorPanel());
		
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int width = screen.width;
		int height = screen.height;
		
		setBounds(width / 4, height / 4, width / 2, height / 2);
		setTitle("Text Processor");
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
}

class TextProcessorPanel extends JPanel{
	
	private JMenuBar menuBar;
	private JTextPane textArea;
	private JToolBar toolBar;
	private JPopupMenu popUpColors;
	
	public TextProcessorPanel() {
		
		setLayout(new BorderLayout());
		
		menuBar = new JMenuBar();
		
		String[] availableFonts = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
				
		JComboBox<String> fonts = new JComboBox<String>(availableFonts);		
		fonts.setMaximumSize(new Dimension(300, 30));
		fonts.setFocusable(false);
		
		fonts.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				String seletedFont = (String)fonts.getSelectedItem();
				Action fontFamilyAction = new StyledEditorKit.FontFamilyAction("fontFamily", seletedFont);
				fontFamilyAction.actionPerformed(e);
				
			}
		});
		
		
		menuBar.add(fonts);
		
		String[] fontStyles = {"Bold", "Italic", "Underline"};
		
		createMenus("Font Style", fontStyles);
		 
		createMenus("Font Size", new String[] {"8", "14", "20", "24", "30", "34", "40", "44"});
		
		add(menuBar, BorderLayout.NORTH);
		
		textArea = new JTextPane();
		textArea.setFont(new Font("Serif", Font.PLAIN, 20));
		textArea.setMargin(new Insets(10, 10, 10, 10));
		textArea.setCaretColor(Color.BLUE);
		add(textArea, BorderLayout.CENTER);
		
		JPopupMenu popUpMenu = new JPopupMenu();
		for(String fontStyle : fontStyles) {
			JMenuItem style = new JMenuItem(fontStyle);
			style.addActionListener(fontStyle.equalsIgnoreCase("bold") ? new StyledEditorKit.BoldAction() : 
									fontStyle.equalsIgnoreCase("italic") ? new StyledEditorKit.ItalicAction() : 
									new StyledEditorKit.UnderlineAction());
			popUpMenu.add(style);
		}
		textArea.setComponentPopupMenu(popUpMenu);
		
		//ToolBar
		toolBar = new JToolBar(JToolBar.VERTICAL);
		
		createToolbarButton(new ImageIcon("src/images/bold-button.png"), new StyledEditorKit.BoldAction());
		createToolbarButton(new ImageIcon("src/images/italic.png"), new StyledEditorKit.ItalicAction());
		createToolbarButton(new ImageIcon("src/images/underline.png"), new StyledEditorKit.UnderlineAction());
		 
		toolBar.addSeparator();
		
		popUpColors = new JPopupMenu();
		popUpColors.setPreferredSize(popUpMenu.getPreferredSize());
		popUpColors.setLayout(new GridLayout(4, 3));
		JButton buttonColor = new JButton(new ImageIcon("src/images/text.png"));
		buttonColor.setFocusable(false);
	
		buttonColor.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				Point mousePosition = buttonColor.getMousePosition();
				popUpColors.show(buttonColor, 30, mousePosition.y);
			}
		});
		
		Color[] colors = {
				new Color(51, 51, 51),
				new Color(167, 29, 49),
				new Color(213, 191, 134),
				new Color(163, 231, 252),
				new Color(38, 196, 133),
				new Color(50, 144, 143),
				new Color(255, 140, 198),
				new Color(222, 54, 157),
				new Color(227, 23, 10),
				new Color(225, 96, 54)
		};
		
		createColorButtons(colors);
		
		toolBar.add(buttonColor);
		
		toolBar.addSeparator();
		
		createToolbarButton(new ImageIcon("src/images/justified.png"), new StyledEditorKit.AlignmentAction("justifiedText", 0));
		createToolbarButton(new ImageIcon("src/images/center.png"), new StyledEditorKit.AlignmentAction("centerText", 1));
		createToolbarButton(new ImageIcon("src/images/right-text-alignment.png"), new StyledEditorKit.AlignmentAction("rigthText", 2));
		createToolbarButton(new ImageIcon("src/images/left-text-alignment-option.png"), new StyledEditorKit.AlignmentAction("leftText", 3));
		
		add(toolBar, BorderLayout.WEST);
		
	}
	
	public void createMenus(String menuvalue, String[] options) {
		
		JMenu menu = new JMenu(menuvalue);
		
		for(String option : options) {
			JMenuItem menuOption = new JMenuItem(option);
			
			if(menuvalue.equalsIgnoreCase("font size")) {
				menuOption.addActionListener(new StyledEditorKit.FontSizeAction("fontSize", Integer.parseInt(menuOption.getActionCommand())));				
			}
			
			if(menuvalue.equalsIgnoreCase("font style")) {
				switch (option.toLowerCase()) {
					case "bold" -> {
						menuOption.addActionListener(new StyledEditorKit.BoldAction());
						menuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK));
					}
					case "italic" -> {
						menuOption.addActionListener(new StyledEditorKit.ItalicAction());
						menuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));
					}
					case "underline" -> {
						menuOption.addActionListener(new StyledEditorKit.UnderlineAction());
						menuOption.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, InputEvent.CTRL_DOWN_MASK));
					}
				}
			}
			
			menu.add(menuOption);
		}
		
		menuBar.add(menu);
		
	}
	
	public void createToolbarButton(Icon icon, ActionListener textAction) {
		
		JButton toolBarButton = new JButton(icon);
		toolBarButton.setFocusable(false);
		toolBarButton.addActionListener(textAction);
		toolBar.add(toolBarButton);
		
	}
	
	public void createColorButtons(Color[] colors) {
		
		for(Color color : colors) {
			JButton colorButton = new JButton();
			colorButton.setFocusable(false);
			colorButton.setBackground(color);
			colorButton.setBorderPainted(false);
			colorButton.addActionListener(new StyledEditorKit.ForegroundAction("setColor", color));			
			popUpColors.add(colorButton);
		}
		
		
	}
	
}





























