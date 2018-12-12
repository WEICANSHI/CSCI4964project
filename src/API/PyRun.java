package API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PyRun{
	public static String speechText = "python ./py/st.py ";
	public static String textSpeech = "python ./py/textsp.py ";
	
	public static String runwaive() {
		String s = null;
        try {
            // using the Runtime exec method:
            Process p = Runtime.getRuntime().exec(speechText);
            
            BufferedReader stdInput = new BufferedReader(new 
                 InputStreamReader(p.getInputStream()));
            // read the output from the command
            while ((s = stdInput.readLine()) != null) {
                return s;
            } 

        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
	}
	
	public static void textToSpeach(String text) {
		try {
			Runtime.getRuntime().exec("python ./py/textsp.py " + text);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public static void main(String args[]) {
		PyRun.textToSpeach("I_love_you");
		System.out.println("done");
	}

}
