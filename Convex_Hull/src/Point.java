
public class Point implements Comparable<Point> {
	private int X;
	private int Y;
	private char name;
	
	@Override
	public int compareTo(Point OtherPoint) {
		return this.X - OtherPoint.X;
	}
	
	public void setXY(int x,int y) {
		this.X = x;
		this.Y = y;
	}
	
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public char getName() {
		return name;
	}
	public void setName(char name) {
		this.name = name;
	}
	
}
