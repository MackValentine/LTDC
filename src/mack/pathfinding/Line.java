package mack.pathfinding;

import java.awt.Point;
import java.util.ArrayList;

public class Line {

	public ArrayList<Point> points;

	public Line(Point p1, Point p2) {

		points = new ArrayList<Point>();

		// point de départ
		int x1 = p1.x, y1 = p1.y;
		// point d'arrivée
		int x2 = p2.x, y2 = p2.y;

		int dx = (x2 - x1), dy = (y2 - y1);
		int max = Math.max(Math.abs(dx), Math.abs(dy));
		if (max > 0) {
			for (int t = 0; t <= max; t++) {
				int x = x1 + (t * dx) / max;
				int y = y1 + (t * dy) / max;
				points.add(new Point(x, y));
			}
		}
	}

	public static Line betweenPoint(Point p1, Point p2) {
		return new Line(p1, p2);
	}

}
