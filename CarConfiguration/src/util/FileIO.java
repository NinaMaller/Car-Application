/*Nina Maller
35B

*/

package util;
import java.io.*;
import java.util.Properties;

import exception.AutoException;
import model.Automobile;

public class FileIO {

	//build an Automotive object from file
	public Automobile buildAutoObject(String filename){
		
		Automobile car = new Automobile();
		
		try {
			FileReader file = null;
			boolean isok = true;
			do {
				try {
					file = new FileReader(filename);
					isok = false;
				} catch (FileNotFoundException e){
					AutoException ex = new AutoException(7, "File not found");
					filename = ex.fix();
				}
			} while(isok);
			isok = true;
			BufferedReader buff = new BufferedReader(file);

			String line;
			line = buff.readLine(); //name of the car
			
			do{ 
				try { 
					car.setName(line);
				isok = false; //problem is fixed and we can exit while loop
				} catch (AutoException e){
					line = e.fix();
				}
			} while(isok);
			isok = true;
			
			line = buff.readLine(); // base price
			do{
				try{
					car.setPrice(Double.parseDouble(line));
					isok = false;
				} catch (AutoException e){
					line = e.fix();
				}
			}while (isok);
			isok = true;

			//make an array of options by options number
			line = buff.readLine(); //option num
			int z = 0;
			
	//		do{ try {
				z = Integer.parseInt(line);
			//	car.setOptionArray(z); //create array with z options at Automotive
		//		isok = false;
		//	} catch (AutoException e) {
		//		line = e.fix();
	//		}
	//		} while (isok);
	//		isok = true;
					
			for(int i=0; i<z; i++){ 
				String option = buff.readLine(); //option name
				String num = buff.readLine(); //option number choice
				int optionNum = Integer.parseInt(num);
				try { car.updateOptionSet(i, option); //, optionNum); //create the option
				} catch (AutoException e){	
					// logging purposes
				}
				for(int j=0; j<optionNum; j++){
					//updateOptionSetArray(int i, int j, String nm, double prc)
					line = buff.readLine(); //opt name
					String prc = buff.readLine(); // price
					double prcNum = Double.parseDouble(prc); // price in double
					car.updateOptionSetArray(i, line, prcNum);
				}
			}

			buff.close();
			} catch (Exception e) {
				new AutoException(3, "undefined error in reading from file"); //for logging
				System.out.println("Error ­­ " + e.toString());
			} 
			
		
		return car;
	}
	
	
	public Automobile buildObjectFromProperties(Properties props) throws NumberFormatException, AutoException{
		Automobile auto = new Automobile();
		String carMake = props.getProperty("make");
		
		if(carMake != null){
			auto.setMake(carMake);
			
			
			String line;
			//get the model of the car
			line = props.getProperty("model");
		//	System.out.println("\n\n\n model = " + line);
			auto.setModel(line);
			//get base price
			line = props.getProperty("baseprice");
			
		//	System.out.println("\n\n\n price = " + line);
			
			auto.setPrice(Double.parseDouble(line));	
			int totalNumOptions = Integer.parseInt(props.getProperty("numoptions"));

			
			for(int i=0; i<totalNumOptions; i++){
	//			System.out.println("\n\n trying to get name option 1 = " + props.getProperty("nameoptions" + (i+1)));
				line = props.getProperty("nameoption" + (i+1)); //color
				
		//		System.out.println("key is = " + "numoptionfor" + (i+1) );
				
		//		System.out.println("\n\ntrying to get the integer = " + props.getProperty("numoptionfor" + (i+1)));
				int num = Integer.parseInt(props.getProperty("numoptionfor" + (i+1)));
		//		System.out.println("updating option: " + line);
				auto.updateOptionSet(i, line);
				for(int j=0; j<num; j++){
					//get specific option (red for color)
					line = props.getProperty((i+1) + "option" + (j+1) + "name");
				//	System.out.println("\noption name:" + line);
					//get price for specific option
					double price = Double.parseDouble(props.getProperty((i+1) + "option" + (j+1) + "price"));
				//	System.out.println("Price for this option is " + price);
					//update the car
					auto.updateOptionSetArray(i, line, price);
				}
			}
			
		}
		
		return auto;
	}
	
	//Serialize an automotive object:
	public void writeAutomobile(Automobile a){
		try
	      {
	         FileOutputStream fileOut = new FileOutputStream("automobile.ser");
	         ObjectOutputStream out = new ObjectOutputStream(fileOut);
	         out.writeObject(a);
	         out.close();
	         fileOut.close();
	         System.out.print("Serialized data is saved in automobile.ser");
	      }catch(IOException e)
	      {
	          e.printStackTrace();
	      }
	}
	
	//Deserialize a Automotive object:
	public Automobile readAutomobile(Automobile a1){
		try
	      {
			 a1 = null;
	         FileInputStream fileIn = new FileInputStream("automobile.ser");
	         ObjectInputStream in = new ObjectInputStream(fileIn);
	         a1 = (Automobile) in.readObject();
	         return a1;
	       //  in.close();
	        // fileIn.close();
	         
	      }catch(IOException e)
	      {
	         e.printStackTrace();
	         return null;
	         
	      }catch(ClassNotFoundException c)
	      {
	         System.out.println("Automotive class was not found");
	         c.printStackTrace();
	         return null;
	      }
	}



}

