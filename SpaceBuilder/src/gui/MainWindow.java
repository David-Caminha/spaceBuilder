package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.net.URL;
import java.text.NumberFormat;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;

import java.awt.Component;
import javax.swing.JRadioButton;
import javax.swing.Box;
import javax.swing.JRadioButtonMenuItem;

public class MainWindow extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow frame = new MainWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainWindow() {
		setTitle("Space builder");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 769, 560);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		//Button menu
		JPanel buttonPane = new JPanel();
		contentPane.add(buttonPane, BorderLayout.SOUTH);
		
		JButton btnNewSpace = new JButton("New Space");
		buttonPane.add(btnNewSpace);
		btnNewSpace.setActionCommand("New space");
		
		JButton btnEditSpace = new JButton("Edit Space");
		buttonPane.add(btnEditSpace);
		btnEditSpace.setActionCommand("Edit space");
		
		JButton btnSaveSpace = new JButton("Save Space");
		buttonPane.add(btnSaveSpace);
		btnSaveSpace.setActionCommand("Save space");
		
		//Item selection menu
		Box verticalBox = Box.createVerticalBox();
		contentPane.add(verticalBox, BorderLayout.EAST);
		
		Component verticalGlue = Box.createVerticalGlue();
		verticalBox.add(verticalGlue);
		
		URL url = MainWindow.class.getResource("/images/wall.png");
		String imgPath = url.toString();
		String html = "<html><body><img src='" + imgPath + "' width=50 height=50>";
		JRadioButton rdbtnItem1 = new JRadioButton(html);
		verticalBox.add(rdbtnItem1, true);
		rdbtnItem1.setActionCommand("Item1");
		rdbtnItem1.setSelected(true);
		
		Component verticalGlue_1 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_1);
		
		url = MainWindow.class.getResource("/images/wanderer.png");
		imgPath = url.toString();
		html = "<html><body><img src='" + imgPath + "' width=50 height=50>";
		JRadioButton rdbtnItem2 = new JRadioButton(html);
		verticalBox.add(rdbtnItem2);
		rdbtnItem2.setActionCommand("Item2");
		
		Component verticalGlue_2 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_2);

		url = MainWindow.class.getResource("/images/exit.jpg");
		imgPath = url.toString();
		html = "<html><body><img src='" + imgPath + "' width=50 height=50>";
		JRadioButton rdbtnItem3 = new JRadioButton(html);
		verticalBox.add(rdbtnItem3);
		rdbtnItem3.setActionCommand("Item3");
		
		Component verticalGlue_3 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_3);

		url = MainWindow.class.getResource("/images/obstacle.png");
		imgPath = url.toString();
		html = "<html><body><img src='" + imgPath + "' width=50 height=50>";
		JRadioButton rdbtnItem4 = new JRadioButton(html);
		verticalBox.add(rdbtnItem4);
		rdbtnItem4.setActionCommand("Item4");
		
		Component verticalGlue_4 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_4);

		url = MainWindow.class.getResource("/images/floor.png");
		imgPath = url.toString();
		html = "<html><body><img src='" + imgPath + "' width=50 height=50>";
		JRadioButton rdbtnItem5 = new JRadioButton(html);
		verticalBox.add(rdbtnItem5);
		rdbtnItem5.setActionCommand("Item5");
		
		Component verticalGlue_5 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_5);
		
		JRadioButton rdbtnItem6 = new JRadioButton("New radio button");
		verticalBox.add(rdbtnItem6);
		rdbtnItem6.setActionCommand("Item6");
		
		Component verticalGlue_6 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_6);
		
		JRadioButton rdbtnItem7 = new JRadioButton("New radio button");
		verticalBox.add(rdbtnItem7);
		rdbtnItem7.setActionCommand("Item7");
		
		Component verticalGlue_7 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_7);
		
		JRadioButton rdbtnItem8 = new JRadioButton("New radio button");
		verticalBox.add(rdbtnItem8);
		rdbtnItem8.setActionCommand("Item8");
		
		Component verticalGlue_8 = Box.createVerticalGlue();
		verticalBox.add(verticalGlue_8);
		
		//Group the radio buttons.
	    ButtonGroup group = new ButtonGroup();
	    group.add(rdbtnItem1);
	    group.add(rdbtnItem2);
	    group.add(rdbtnItem3);
	    group.add(rdbtnItem4);
	    group.add(rdbtnItem5);
	    group.add(rdbtnItem6);
	    group.add(rdbtnItem7);
	    group.add(rdbtnItem8);

		//Create the settings panel for the gameboard
		JPanel settingsPane = new JPanel();

		JLabel agentForce = new JLabel("Agent Force:");
		settingsPane.add(agentForce);

		JFormattedTextField force = new JFormattedTextField(NumberFormat.getNumberInstance());
		force.setText("3.0");
		force.setName("force");
		settingsPane.add(force);
		
		JLabel agentPanic = new JLabel("Agent Panic:");
		settingsPane.add(agentPanic);

		JFormattedTextField panic = new JFormattedTextField(NumberFormat.getNumberInstance());
		panic.setText("0.0");
		panic.setName("panic");
		settingsPane.add(panic);
		
		JLabel agentHealth = new JLabel("Agent Heath:");
		settingsPane.add(agentHealth);

		JFormattedTextField health = new JFormattedTextField(NumberFormat.getNumberInstance());
		health.setText("5.0");
		health.setName("health");
		settingsPane.add(health);
		
		JLabel agentSpeed = new JLabel("Agent Speed:");
		settingsPane.add(agentSpeed);

		JFormattedTextField speed = new JFormattedTextField(NumberFormat.getNumberInstance());
		speed.setText("0.5");
		speed.setName("speed");
		settingsPane.add(speed);
		
		JLabel agentAggressiveness = new JLabel("Agent Aggressiveness:");
		settingsPane.add(agentAggressiveness);

		JFormattedTextField aggressiveness = new JFormattedTextField(NumberFormat.getNumberInstance());
		aggressiveness.setText("1.0");
		aggressiveness.setName("aggressiveness");
		settingsPane.add(aggressiveness);
	    
	    //Create the space and add it as a listener for buttons
	    Space space = new Space(this, settingsPane);
	    contentPane.add(space, BorderLayout.CENTER);

	    btnNewSpace.addActionListener(space);
	    btnEditSpace.addActionListener(space);
	    btnSaveSpace.addActionListener(space);

	    rdbtnItem1.addActionListener(space);
	    rdbtnItem2.addActionListener(space);
	    rdbtnItem3.addActionListener(space);
	    rdbtnItem4.addActionListener(space);
	    rdbtnItem5.addActionListener(space);
	    rdbtnItem6.addActionListener(space);
	    rdbtnItem7.addActionListener(space);
	    rdbtnItem8.addActionListener(space);
	    
	    
	    space.setFocusable(true);
	}

}
