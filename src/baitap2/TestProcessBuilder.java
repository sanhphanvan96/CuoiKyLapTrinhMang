package baitap2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class TestProcessBuilder {

	public static void main(String[] args) {
		String query = "pause";
		try {
			ProcessBuilder pb = new ProcessBuilder("C:\\Users\\Hi-XV\\Desktop\\test.bat");
			Process p = pb.start();
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			StringBuilder builder = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				System.out.println(line);
				builder.append(line);
				builder.append(System.getProperty("line.separator"));
			}
			String result = builder.toString();
			System.out.println(result);
			int exitStatus = p.waitFor();
			System.out.println(exitStatus);
		} catch (IOException | InterruptedException e) {
			e.printStackTrace();
		}
	}

}
