package prepare.create;

import java.io.FileWriter;

public class Test {
	public static void main(String[] args) throws Exception {
		FileWriter fileWritter = new FileWriter("D:\\test.txt", true);
		fileWritter.write("efe哇嘎f");
		fileWritter.close();
	}
}
