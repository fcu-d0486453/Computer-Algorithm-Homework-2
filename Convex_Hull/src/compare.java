import java.util.Comparator;

public class compare implements Comparator<Point> {

	@Override
	public int compare(Point arg0, Point arg1) {
		if( arg0.getX() > arg1.getX()) {
			return -1;//代表向前排
		}else if(arg0.getX() < arg1.getX()) {
			return 1;//代表向後排
		}else {
			return 0;
		}
	}
	
}
