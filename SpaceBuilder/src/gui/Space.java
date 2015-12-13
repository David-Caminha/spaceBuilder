package gui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.NumberFormat;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.text.JTextComponent;

import logic.SpaceLogic;

public class Space extends JPanel implements MouseListener, ActionListener {

	SpaceLogic spaceLogic = new SpaceLogic();

	Image imgCover, imgWall, imgFloor, imgWandererAgent, imgExit, imgObstacle, imgFinalExit, imgBase;

	private boolean creating = false;

	private int objectType = 1;

	private Frame frame;

	private JDialog settings = new JDialog(frame, "Wanderer properties", true);

	/**
	 * Instantiates a new space object.
	 *
	 * @param frame Frame where the game is played
	 * @param settingsPane JPanel with the game settings 
	 */
	public Space(JFrame frame, JPanel settingsPane)
	{
		this.frame = frame;
		addMouseListener(this);
		try {
			imgCover = ImageIO.read(new File("resources\\images\\cover.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgWall = ImageIO.read(new File("resources\\images\\wall.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgFloor = ImageIO.read(new File("resources\\images\\floor.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgWandererAgent = ImageIO.read(new File("resources\\images\\wanderer.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgExit = ImageIO.read(new File("resources\\images\\exit.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgObstacle = ImageIO.read(new File("resources\\images\\obstacle.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgFinalExit = ImageIO.read(new File("resources\\images\\finalExit.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			imgBase = ImageIO.read(new File("resources\\images\\base.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		settings.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				ArrayList<Double> newList = new ArrayList<Double>();
				String posLbl = ((JLabel) settings.getContentPane().getComponent(0)).getText();
				String[] pos = posLbl.split(":")[1].split(" ");
				Double force = Double.parseDouble(((JFormattedTextField) settings.getContentPane().getComponent(2)).getText());
				Double panic = Double.parseDouble(((JFormattedTextField) settings.getContentPane().getComponent(4)).getText());
				Double health = Double.parseDouble(((JFormattedTextField) settings.getContentPane().getComponent(6)).getText());
				Double speed = Double.parseDouble(((JFormattedTextField) settings.getContentPane().getComponent(8)).getText());
				Double agressiveness = Double.parseDouble(((JFormattedTextField) settings.getContentPane().getComponent(10)).getText());
				newList.add(force);
				newList.add(panic);
				newList.add(health);
				newList.add(speed);
				newList.add(agressiveness);
				spaceLogic.updateWanderer(new Point(Integer.parseInt(pos[1]), Integer.parseInt(pos[2])), newList);
				requestFocusInWindow();
			}
		});
	}

	/**
	 * Used to print the image on the space.
	 */
	public void paintComponent(Graphics g)
	{
		Dimension size = this.getSize();
		if(!creating)
		{
			super.paintComponent(g);
			int imgHeight = (int) (size.width*(671.0/1150.0));
			g.drawImage(imgCover, 0, (size.height-imgHeight)/2, size.width, imgHeight, null);
		}
		else if(creating)
		{
			super.paintComponent(g);
			int wScale = 0, hScale = 0;
			hScale = (size.height)/spaceLogic.space.length;
			wScale = (size.width)/spaceLogic.space[0].length;
			for(int i = 0; i < spaceLogic.space.length; i++)
			{
				for(int j = 0; j < spaceLogic.space[i].length; j++)
				{
					char tile = spaceLogic.getChar(i, j);
					switch(tile)
					{
					case 'X':
						g.drawImage(imgWall, j*wScale, i*hScale, wScale, hScale, null);
						break;
					case ' ':
						g.drawImage(imgFloor, j*wScale, i*hScale, wScale, hScale, null);
						break;
					case 'W':
						g.drawImage(imgWandererAgent, j*wScale, i*hScale, wScale, hScale, null);
						break;
					case 'E':
						g.drawImage(imgExit, j*wScale, i*hScale, wScale, hScale, null);
						break;
					case 'O':
						g.drawImage(imgObstacle, j*wScale, i*hScale, wScale, hScale, null);
						break;
					case 'B':
						g.drawImage(imgBase, j*wScale, i*hScale, wScale, hScale, null);
						break;
					case 'F':
						g.drawImage(imgFinalExit, j*wScale, i*hScale, wScale, hScale, null);
						break;
					}
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	/**
	 * Used to listen to the mouse while the user is creating the space.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
		if(creating)
		{
			int x =arg0.getX();
			int y = arg0.getY();
			int height = this.getSize().height;
			int width = this.getSize().width;
			int xs = x/(width/spaceLogic.space[0].length);
			int ys = y/(height/spaceLogic.space.length);
			if(ys == spaceLogic.space.length || xs == spaceLogic.space[0].length)
				repaint();
			else
			{
				if(spaceLogic.getChar(ys, xs) == 'W' && objectType != 2)
				{
					spaceLogic.removeWanderer(new Point(xs, ys));
				}
				switch (objectType)
				{
				case 1:
					spaceLogic.setChar(ys, xs, 'X');
					break;
				case 2:
					if(spaceLogic.getChar(ys, xs) == 'W')
					{
						settings.setContentPane(createSettingsPane(new Point(xs,ys)));
						settings.setPreferredSize(new Dimension(244, 385));
						settings.pack();
						settings.setVisible(true);
					}
					else
					{
						spaceLogic.setChar(ys, xs, 'W');
						spaceLogic.addWanderer(new Point(xs, ys));
					}
					break;
				case 3:
					spaceLogic.setChar(ys, xs, 'E');
					break;
				case 4:
					spaceLogic.setChar(ys, xs, 'O');
					break;
				case 5:
					spaceLogic.setChar(ys, xs, ' ');
					break;
				case 6:
					spaceLogic.setChar(ys, xs, 'B');
					break;
				case 7:
					spaceLogic.setChar(ys, xs, 'F');
					break;
				}
				repaint();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {

	}

	/**
	 * Used to listen to the buttons and do the appropriate action.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if ("New space".equals(e.getActionCommand()))
		{
			int startCreating = JOptionPane.NO_OPTION;
			startCreating = JOptionPane.showConfirmDialog(this,
					"Would you like to create a new simulation space?",
					"Create a simulation space",
					JOptionPane.YES_NO_OPTION);
			if(startCreating == JOptionPane.YES_OPTION)
			{
				int created = 0;
				boolean choosingHeight = true;
				boolean choosingWidth = true;
				int height = 10, width = 10;
				while(choosingHeight && created == 0)
				{
					String option = (String) JOptionPane.showInputDialog(this,
							"Please choose the height of the simulation space you wish to create (10-99).",
							"Create window",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							"10");
					if((option != null && option.length() > 1) && option.length() < 3)
						for(int i = 0; i < option.length(); i++)
						{
							if(!Character.isDigit(option.charAt(i)))
								break;
							if(i+1 == option.length())
								choosingHeight = false;
						}
					else if(option == null)
						break;
					if(!choosingHeight)
					{
						height = Integer.parseInt(option);
						created++;
					}
				}
				while(choosingWidth && created == 1)
				{
					String option = (String) JOptionPane.showInputDialog(this,
							"Please choose the width of the simulation space you wish to create (10-99).",
							"Create window",
							JOptionPane.PLAIN_MESSAGE,
							null,
							null,
							"10");
					if((option != null && option.length() > 1) && option.length() < 3)
						for(int i = 0; i < option.length(); i++)
						{
							if(!Character.isDigit(option.charAt(i)))
								break;
							if(i+1 == option.length())
								choosingWidth = false;
						}
					else if(option == null)
						break;
					if(!choosingWidth)
					{
						width = Integer.parseInt(option);
						created++;
					}
				}
				if(created == 2)
				{
					creating = true;
					if(height > 50)
						height = 50;
					if(width > 50)
						width = 50;
					spaceLogic.spaceInit(height, width);
				}
				requestFocusInWindow();
			}
		}
		if("Edit space".equals(e.getActionCommand()))
		{
			JFileChooser chooser = new JFileChooser(new File("."))
			{
				public void approveSelection()
				{
					if(Files.isDirectory(this.getSelectedFile().toPath()))
					{
						this.setCurrentDirectory(this.getSelectedFile());
					}
					else
						super.approveSelection();
				}
			};
			chooser.setDialogTitle("Choose which file to load");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				creating = true;
				Path path = chooser.getSelectedFile().toPath();
				spaceLogic.loadSpace(path);
			}
		}
		if("Save space".equals(e.getActionCommand()))
		{
			JFileChooser chooser = new JFileChooser(new File("."))
			{
				public void approveSelection()
				{
					if(Files.isDirectory(this.getSelectedFile().toPath()))
					{
						this.setCurrentDirectory(this.getSelectedFile());
					}
					else if(Files.exists(this.getSelectedFile().toPath()))
					{
						int overwriteFile = JOptionPane.NO_OPTION;
						overwriteFile = JOptionPane.showConfirmDialog(this,
								"This file already exists. Would you like to overwite it?",
								"Overwrite file?",
								JOptionPane.YES_NO_OPTION);
						if(overwriteFile == JOptionPane.YES_OPTION)
							super.approveSelection();
					}
					else
						super.approveSelection();
				}
			};
			chooser.setDialogTitle("Choose where to save your file");
			chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
			chooser.setAcceptAllFileFilterUsed(false);

			if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION)
			{
				Path path = chooser.getSelectedFile().toPath();
				spaceLogic.saveSpace(path);
			}
		}
		if ("Item1".equals(e.getActionCommand()))
		{
			objectType = 1;
		}
		if ("Item2".equals(e.getActionCommand()))
		{
			objectType = 2;
		}
		if ("Item3".equals(e.getActionCommand()))
		{
			objectType = 3;
		}
		if ("Item4".equals(e.getActionCommand()))
		{
			objectType = 4;
		}
		if ("Item5".equals(e.getActionCommand()))
		{
			objectType = 5;
		}
		if ("Item6".equals(e.getActionCommand()))
		{
			objectType = 6;
		}
		if ("Item7".equals(e.getActionCommand()))
		{
			objectType = 7;
		}
		repaint();
	}

	public JPanel createSettingsPane(Point point)
	{
		ArrayList<Double> list = spaceLogic.getWanderer(point);
		
		//Create the settings panel for the gameboard
		JPanel settingsPane = new JPanel();
		
		JLabel agentPosition = new JLabel("Agent at position: " + (int)point.getX() + " " + (int)point.getY());
		settingsPane.add(agentPosition);

		JLabel agentForce = new JLabel("Agent Force:");
		settingsPane.add(agentForce);

		JFormattedTextField force = new JFormattedTextField(NumberFormat.getNumberInstance());
		force.setText(list.get(0).toString());
		force.setName("force");
		settingsPane.add(force);

		JLabel agentPanic = new JLabel("Agent Panic:");
		settingsPane.add(agentPanic);

		JFormattedTextField panic = new JFormattedTextField(NumberFormat.getNumberInstance());
		panic.setText(list.get(1).toString());
		panic.setName("panic");
		settingsPane.add(panic);

		JLabel agentHealth = new JLabel("Agent Heath:");
		settingsPane.add(agentHealth);

		JFormattedTextField health = new JFormattedTextField(NumberFormat.getNumberInstance());
		health.setText(list.get(2).toString());
		health.setName("health");
		settingsPane.add(health);

		JLabel agentSpeed = new JLabel("Agent Speed:");
		settingsPane.add(agentSpeed);

		JFormattedTextField speed = new JFormattedTextField(NumberFormat.getNumberInstance());
		speed.setText(list.get(3).toString());
		speed.setName("speed");
		settingsPane.add(speed);

		JLabel agentAggressiveness = new JLabel("Agent Aggressiveness:");
		settingsPane.add(agentAggressiveness);

		JFormattedTextField aggressiveness = new JFormattedTextField(NumberFormat.getNumberInstance());
		aggressiveness.setText(list.get(4).toString());
		aggressiveness.setName("aggressiveness");
		settingsPane.add(aggressiveness);
		
		return settingsPane;
	}

}
