package android.graph;

import java.util.Random;

public class MockData {

	    // x is the day number, 0, 1, 2, 3
		public static Point getDataFromReceiver(double x)
		{
			return new Point(x, generateRandomData(),generateRandomData());
			//return new Point(x, generateRandomData());
		}
		
		private static double generateRandomData()
		{
			Random random = new Random();
			return random.nextInt(50);
		}
		
		public static Point create(double a,double b,double c){
			return new Point(a,b,c);
		}
		
}