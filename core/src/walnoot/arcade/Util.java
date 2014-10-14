package walnoot.arcade;

public class Util {
	public static float wrap(float x, float modulus) {
		return ((x % modulus) + modulus) % modulus;
	}
	
	public static boolean epsilonEquals(float x, float y, float epsilon) {
		return Math.abs(x - y) < epsilon;
	}
}
