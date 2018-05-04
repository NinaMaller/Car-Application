package adapter;
/*
 * Nina Maller
 * 35B
 */
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

import exception.AutoException;
import model.*;
import scale.EditOptions;
import util.*;

public abstract class ProxyAutomobile {
		//This class will contain all the implementation of any method declared in the interface.
		//ProxyAutomobile doesn't implement the interfaces. We will us inheritance to implement.
		// ProxyAutomobile is hidden! we only expose the empty class.
	protected static LinkedHashMap<String, Automobile> autoLot = new LinkedHashMap<>();
	private Iterator<Entry<String, Automobile>> itr; // = autoLot.entrySet().iterator(); //iterate the cars in the autoLot
	
	//Given a text file name a method called BuildAuto can be written to build an instance of
	//Automobile. This method does not have to return the Auto instance.
	public void BuildAuto(int type, String filename) {
		FileIO file = new FileIO();
		Automobile auto = null;
		// type = 1, input is from text file
		if(type == 1){
			auto = file.buildAutoObject(filename);
		}
		//type = 2, properties file is the input
		if(type == 2){
			//auto = file.buildObjectFromProperties(filename); 
		//	Properties prop = 
		//	addCarFromClient();
			
		}
		autoLot.put(auto.getName(), auto);
	//	return a1;
	}

	//This function searches and prints the properties of a given Automodel.
	public void printAuto(String Modelname) {
		itr = autoLot.entrySet().iterator();
		Automobile auto;
		while(itr.hasNext()){
			auto = itr.next().getValue();
			if(auto.getName().equalsIgnoreCase(Modelname)) //name is make and model
				System.out.println(auto);
		}
		
	}

	//This function searches the Model for a given OptionSet and sets the name of OptionSet to
	//newName.
	public void updateOptionSetName(String Modelname, String OptionSetname,String newName) {
		Automobile auto;
		itr = autoLot.entrySet().iterator();
		while(itr.hasNext()){
			auto = itr.next().getValue();
			if(auto.getName().equalsIgnoreCase(Modelname)){
				auto.changeOptionName(OptionSetname, newName);
			}
		}
		
	}

	
	//This function searches the Model for a given OptionSet and Option name, and sets the price to
	//newPrice.
	public void updateOptionPrice(String Modelname, String Optionname, String Option, float newprice) {
		itr = autoLot.entrySet().iterator();
		Automobile auto;
		while(itr.hasNext()){
			auto = itr.next().getValue();
			if(auto.getName().equalsIgnoreCase(Modelname)){
				auto.changeOptionPrice(Optionname, Option, newprice);
			}
		}
	}
	
	public void edit(String Modelname, int ops, String[] arr){
		itr = autoLot.entrySet().iterator();
		Automobile auto;
		while(itr.hasNext()){
			auto = itr.next().getValue();
			if(auto.getName().equalsIgnoreCase(Modelname)){
				EditOptions edit = new EditOptions(ops, arr); // instantiate a thread
			
			}
		}
		
	}
	
	public void addCarToLHM(Automobile auto){
	//	System.out.println("PROXY AUTO -- ADDED TO LHM" + auto.getName());
		autoLot.put(auto.getName(), auto);
	}
	
	public ArrayList<String> getAllCarNames(){
		itr = autoLot.entrySet().iterator();
		String name;
		ArrayList<String> arr = new ArrayList<>();
		while(itr.hasNext()){
			name = itr.next().getValue().getName();
		//	System.out.println("IN PROXY AUTO - ADDING --" + name);
			arr.add(name);
		}
		if(arr.size() == 0){
			return null;
		}
		return arr;
	}
	
	public Automobile getCarSelected(String carName){
		return autoLot.get(carName);
	}
	
	/*
	public void addCarFromClient(Properties prop) throws NumberFormatException, AutoException{
		FileIO in = new FileIO();
		Automobile auto = in.buildObjectFromProperties(prop);
		autoLot.put(auto.getName(), auto);
	}  */
	
	public String fix(){
		return "";
	}
	
}
