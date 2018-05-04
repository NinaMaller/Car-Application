/*Nina Maller
35B

*/

package util;
import java.io.*;

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
	
	
	//parse the properties file
	
	
	
	
	
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

