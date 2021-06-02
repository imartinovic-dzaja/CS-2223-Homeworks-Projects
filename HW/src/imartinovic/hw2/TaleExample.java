package imartinovic.hw2;

import java.io.IOException;

public class TaleExample {
	public static void main(String[] args) {
		try {
			for (String s : new TaleOfTwoCitiesExtractor(1)) {
				System.out.println(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
