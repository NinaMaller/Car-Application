package client;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Properties;

public class CarModelOptionsIO {

	//get file name, return properties object 
	public Properties createAndLoadProperties(String file){
		FileInputStream in;
		Properties prop = new Properties();
		try {
		
			in = new FileInputStream(file);
			prop.load(in);
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return prop;	
	}
}



/*
 * Read data from the Properties file; create properties object, using the load

method, which transfers the object from the client to server, using ObjectStream.

b. Receive a response from the Server, verifying that the Car Model object is

created successfully.

c. Use CreateAuto interface to build Automobile and handle different type of files,

passed in filetype.  */
