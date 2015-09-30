package android.graph;

import java.util.Random;

public class MockData {

	// x is the day number, 0, 1, 2, 3
		public static Point getDataFromReceiver(double x)
		{
			//return new Point(x, generateRandomData(),generateRandomData());
			return new Point(x, generateRandomData());
		}
		
		private static double generateRandomData()
		{
			Random random = new Random();
			return random.nextInt(50);
		}
}
