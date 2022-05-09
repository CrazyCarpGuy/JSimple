package jsimple.utility;

public class StringUtils {

	public static int count(char c, String string) {
		
		int count = 0;
		
		for (int i = 0; i < string.length(); i++) {
			if (string.charAt(i) == c) {
				count++;
			}
		}
		
		return count;
		
	}
	
	public static boolean hasTexture(String token) {
		
		int slashCount = count('/', token);
		return ((slashCount == 1) || (slashCount == 2 && token.charAt(token.indexOf('/') + 1) != '/')); 
	
	}
	
	public static boolean hasNormal(String token) {
		
		return (count('/', token) == 2);
		
	}
	
}