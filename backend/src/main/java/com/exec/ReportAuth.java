package com.exec;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
//TODO: Test if working :p
public class ReportAuth {
 
    
    public static Boolean check(String a, String b, String c){
        ProcessBuilder processBuilder = new ProcessBuilder();

	// -- Linux --

	// Run a shell command
	// processBuilder.command("bash", "-c", "ls /home/mkyong/");

	// Run a shell script
	processBuilder.command("node ./check.js ",a,b,c);

	// -- Windows --

	// Run a command
	//processBuilder.command("cmd.exe", "/c", "dir C:\\Users\\mkyong");

	// Run a bat file
	//processBuilder.command("C:\\Users\\mkyong\\hello.bat");

	try {

		Process process = processBuilder.start();

		StringBuilder output = new StringBuilder();

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(process.getInputStream()));

		String line;
		while ((line = reader.readLine()) != null) {
			output.append(line + "\n");
		}
        var ab =output.toString();
        

		int exitVal = process.waitFor();
		if (exitVal == 0 && ab.equals("True")) {
			System.out.println("Success!");
			System.out.println(output);
            return true;
			// System.exit(0);
		} else {
            return false;
			//abnormal...
		}

	} catch (IOException e) {
        
		e.printStackTrace();
        return false;
	} catch (InterruptedException e) {
		e.printStackTrace();
        return false;
	}

    }
}


