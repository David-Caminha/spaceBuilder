package logic;

import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SpaceLogic {

	/** The space. */
	public char [][]space;
	public Map<Point, ArrayList<Double>> wanderers = new HashMap<Point, ArrayList<Double>>();

	/**
	 * Gets the char in the given row and column of the space.
	 *
	 * @param row the row
	 * @param col the column
	 * @return the char
	 */
	public char getChar(int row, int col)
	{
		return space[row][col];
	}

	/**
	 * Sets the char in the given row and column of the space.
	 *
	 * @param row the row
	 * @param col the column
	 * @param symbol the char to be set
	 */
	public void setChar(int row, int col, char symbol)
	{
		space[row][col] = symbol;
	}
	
	public void addWanderer(Point p)
	{
		ArrayList<Double> l = new ArrayList<>();
		l.add(3.0);
		l.add(0.0);
		l.add(5.0);
		l.add(0.5);
		l.add(1.0);
		wanderers.put(p, l);
	}

	public void removeWanderer(Point point)
	{
		wanderers.remove(point);
	}

	public ArrayList<Double> getWanderer(Point point)
	{
		return wanderers.get(point);
	}

	public void updateWanderer(Point point, ArrayList<Double> newList)
	{
		wanderers.replace(point, newList);
	}

	/**
	 * Initialises the space with the given size.
	 *
	 * @param size the space size
	 */
	public void spaceInit(int height, int width)
	{
		space = new char[height][width];
		for(int i = 0; i < height; i++)
			for(int j = 0; j < width; j++)
				space[i][j] = ' ';
	}

	public void saveSpace(Path path)
	{
		String spaceString = "";
		String wanderersString = "";
		spaceString += "height: " + space.length + "\n";
		spaceString += "width: " + space[0].length + "\n";
		for(int i = 0; i < space.length; i++)
		{
			for(int j = 0; j < space[i].length; j++)
			{
				spaceString += space[i][j];
				if(space[i][j] == 'W')
				{
					ArrayList<Double> list = wanderers.get(new Point(j, i));
					wanderersString += "pos:" + j + " " + i + ";force:" + list.get(0) + 
							";panic:" + list.get(1) + ";health:" + list.get(2)  + ";speed:" + list.get(3) +
							";aggressiveness:" + list.get(4) + "\n";
				}
			}
			spaceString += "\n";
		}
		spaceString += wanderersString;
		byte[] bytes = spaceString.getBytes();
		try {
			Files.write(path, bytes);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void loadSpace(Path path)
	{
		if(Files.exists(path) && !Files.isDirectory(path))
		{
			try {
				byte[] bytes = null;
				bytes = Files.readAllBytes(path);
				String spaceInfo = new String(bytes);
				String[] lines = spaceInfo.split("\n");
				String[] height = lines[0].split(" ");
				int spaceHeight = Integer.parseInt(height[1]);
				space = new char[spaceHeight][];
				for(int i = 2; i < 2+spaceHeight; i++)
				{
					space[i-2] = lines[i].toCharArray();
				}
				for(int i = 2+spaceHeight; i < lines.length; i++)
				{
					String[] elems = lines[i].split(";");
					String[] pos = elems[0].split(":")[1].split(" ");
					Double force = Double.parseDouble(elems[1].split(":")[1]);
					Double panic = Double.parseDouble(elems[2].split(":")[1]);
					Double health = Double.parseDouble(elems[3].split(":")[1]);
					Double speed = Double.parseDouble(elems[4].split(":")[1]);
					Double aggressiveness = Double.parseDouble(elems[5].split(":")[1]);

					ArrayList<Double> list = new ArrayList<>();
					list.add(force);
					list.add(panic);
					list.add(health);
					list.add(speed);
					list.add(aggressiveness);
					wanderers.put(new Point(Integer.parseInt(pos[0]),Integer.parseInt(pos[1])), list);
				}
			} catch (Exception e) {
				System.out.println("There was an exception while reading the file. "
						+ "Creating and opening a new file with 20x20 dimentions.");
				spaceInit(20, 20);
			}
		}
		else
		{
			System.out.println("The selected file doesn't exist or is a directory. "
					+ "Creating and opening a new file with 20x20 dimentions.");
			spaceInit(20, 20);
		}
	}
}
