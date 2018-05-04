package client;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

import model.Automobile;

public class SelectCarOption {
	
	private PrintWriter writer; //write to server
	private BufferedReader inputReader; //read from console
	private BufferedReader reader; //read from server

	//use already working connections
	SelectCarOption(PrintWriter writer, BufferedReader inputReader,BufferedReader reader){
		this.writer = writer;
		this.inputReader = inputReader;
		this.reader = reader;
	}
	
	//return client's car choice
	public String getClientChoice(){
		try{
    		//waiting to get available models from server
	    	String input = reader.readLine(); 
	    	if(input.equalsIgnoreCase("empty")){
	    		System.out.println("list is empty. please add cars first");
	    		return null;
	    	}
	    	System.out.println("Please select car from the following available models: \n" + input);
	    	String choice = inputReader.readLine(); // model name
	    	return choice;
	    	
    		
    	} catch(Exception e){
    		e.printStackTrace();
    	}
		return null; 
	}

	public boolean configurateCar(Automobile car){
		System.out.println("I got the car");
		System.out.println(car.getName());
		
		String choice;
		try{
		System.out.println("\nFor each set, choose your option");
		ArrayList allOptions = car.getAllOptions();
	
		for(int i=0; i<allOptions.size(); i++){
			System.out.println("Option: " + car.getOptionName(i));
			System.out.println("Enter your choice");
			choice = inputReader.readLine();
			car.setOptionChoice(car.getOptionName(i), choice);
		}
		//print options chosen
		System.out.println("\n\n\n");
		System.out.println(car.printChoices());
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	
}
