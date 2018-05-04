package model;
import java.io.*;
import java.util.ArrayList;

import exception.AutoException;

/*Nina Maller
35B

*/

public class Automobile implements Serializable { 
	private String make;
	private String model;
	private double basePrice;
	private ArrayList<OptionSet> optSet;
	private ArrayList<Option> choiceSet;

	public Automobile(){
		make = "";
		model = "";
		basePrice = 0.0; 
		optSet = new ArrayList<>();
		choiceSet = new ArrayList<>();
	}
	
	//Getters:
	public String getName(){
		//name is make and model
		return make + " " + model;
	}
	
	public String getMake(){
		return make;
	}
	
	public String getModel(){
		return model;
	}
	
	public double getBasePrice(){
		return basePrice;
	}
	

	public ArrayList getAllOptions(){
		return optSet;
	}
	
	public ArrayList<String> getAllOptionsString(){
		ArrayList<String> arr = new ArrayList<>();
		for(int i=0; i<optSet.size(); i++){
			arr.add(optSet.get(i).getOptionName());
		}
		return arr;
	}
	
	public int getSpecificOptionPrice(String set, String opt){
		for(int i=0; i<optSet.size(); i++){
			 OptionSet op = optSet.get(i);
			 if(op.getOptionName().equalsIgnoreCase(set)){
				 ArrayList<Option> specificOptions =  op.getAll();
				 for(int j=0; j<specificOptions.size(); j++){
					 if(specificOptions.get(j).getName().equalsIgnoreCase(opt)){
						 return (int) specificOptions.get(j).getPrice();
					 }
				 }
			 }
		 }
		return 0; //if not found
	}
	
	
	//add in other automobile:
	public ArrayList getSpecificOptions(String setName){
		System.out.println("Set name is #" + setName);
		ArrayList<String> arr = new ArrayList<>();
		 for(int i=0; i<optSet.size(); i++){
			 OptionSet op = optSet.get(i);
			 if(op.getOptionName().equalsIgnoreCase(setName)){
				 ArrayList<Option> specificOptions =  op.getAll();
				 for(int j=0; j<specificOptions.size(); j++){
					 arr.add(specificOptions.get(j).getName()); //options as strings 
					 System.out.println(specificOptions.get(j).getName());
				 }
			 }
		 }
		 
		 
		return arr; 
	}
	
	public OptionSet getOption(int i){ // check it works
		return optSet.get(i);
	}
	
	public String getOptionName(int i){ 
		return optSet.get(i).getOptionName();
	}
	
	//setters:
	public void setName(String name) throws AutoException{
		if (name == null || name.trim().length() == 0)
			throw new AutoException(1, "can't assign name");
		//name is make and model
		String[] parts = name.split(" ", 2);
		make = parts[0];
		model = parts[1];
	}
	
	public void setMake(String make){
		this.make = make;
	}
	
	public void setModel(String model){
		this.model = model;
	}
	
	public void setPrice(double price) throws AutoException {
		if(price < 0)
			throw new AutoException(6, "Base price cannot be a negative number");
		this.basePrice = price;
	}
	
	public void setOptionArray(int num) throws AutoException{
		if(num < 2)
			throw new AutoException(2, "option set cannot be set to 0, 1, or a negative number" );
	//	optSet.add(new OptionSet(num));// = new OptionSet[num];
		for(int i=0; i<num; i++)
			optSet.set(i, new OptionSet());
	}

	public void addOption(int i, String name){
		optSet.get(i).setName(name);
	}
	
	public void changeOptionName(String cur, String newop){
		for(int i=0; i<optSet.size(); i++){
			if(optSet.get(i).getOptionName().equals(cur)){
					optSet.get(i).setName(newop);

				return;
			}
		}
	}
	
	public void changeOptionPrice(String optName, String opt, double nprice){
	/*	Iterator<OptionSet> itr = optSet.iterator();
		while(itr.hasNext()){
			OptionSet curr = itr.next();
			if(curr.getOptionName().equals(optName)){
				
			}
		}*/
		
		for(int i=0; i<optSet.size(); i++){
			if(optSet.get(i).getOptionName().equals(optName)){
				optSet.get(i).updateSpecificPrice(opt, nprice);
				return;
			}
		}
	}
	
	//i is the number of array index at automobile
	// name is the name of the option
	// j is the number of options in that option (option set array length)
	public void updateOptionSet(int i, String name) throws AutoException{ //delete i as well
	//	if(i >= optSet.size() )
	//		throw new AutoException (4, "index of option array is greater than array length ");
		optSet.add(new OptionSet(name));// , j));
	}
	
	//i is the option
	// j is the number in the option set array
	public void updateOptionSetArray(int i, String nm, double prc) throws AutoException{
		//this doesnt matter anymore becuase of the change to arraylist:
//		if(j >= optSet[i].getNumberOfOptions())
//			throw new AutoException (5, "index of options for option is greater than array length ");
		optSet.get(i).updateSpecificOption(nm, prc);
	}
	
	
	
	//CHOICE RELATED METHODS:
	
	//for choosing a particular option in an option
	public void setOptionChoice(String setName, String optionName){
		for(int i=0; i<optSet.size(); i++){
			if(optSet.get(i).getOptionName().equalsIgnoreCase(setName)){
				optSet.get(i).setOptionChoice(optionName); //set
				choiceSet.add(optSet.get(i).getOptionChoice()); //add to option choice
			}
		}
	}
	
	public String getOptionChoice(String setName){
		for(int i=0; i<optSet.size(); i++){
			if(optSet.get(i).getOptionChoice().getName().equalsIgnoreCase(setName)){
				return optSet.get(i).getOptionChoice().getName();
			}
		}
		return null;
	}
	
	public double getOptionChoicePrice(String setName){ //or is the return type an int?
		for(int i=0; i<optSet.size(); i++){
			if(optSet.get(i).getOptionChoice().getName().equalsIgnoreCase(setName)){
				return optSet.get(i).getOptionChoice().getPrice();
			}
		}
		return 0; //or something else if not found?
	}
	
	
	public double getTotalPrice(){
		double total = basePrice; //start from base price
		// add all price options
		for(int i=0; i<optSet.size(); i++){
				if(optSet.get(i).getOptionChoice() != null)
					total += optSet.get(i).getOptionChoice().getPrice();
				}
		return total;
		
	}
	
	public String printChoices(){
		String car = "";
		car = make + " " + model + "\n";
		for(int i=0; i<optSet.size(); i++){
			if(optSet.get(i).getOptionChoice() != null)
				car += "\n" + optSet.get(i).getOptionName() + " : " + optSet.get(i).getOptionChoice().getName() +
						" at the price of $" + optSet.get(i).getOptionChoice().getPrice();
		}
		car += "\n\nBase Price is: $" + basePrice + "\nPrice after added options: $" + this.getTotalPrice();
		return car;
	}
	
	@Override
	public String toString(){
		String car = "";
		car = make + " " + model + "\nBase price: $" + basePrice + "\n";
		for(int i=0; i<optSet.size(); i++){
			car += "\n" + (optSet.get(i)).getString();
		}
		
		return car;
	}
	
	
}
	class OptionSet implements Serializable{ //implement serializeble
		private String optionName;
		private ArrayList<Option> opt;
		private Option choice;
		
		public OptionSet(){
			optionName = "";
			opt = new ArrayList<>();
			choice = null;
		}
		
		public OptionSet(String name, Option o){
			optionName = name;
			opt = new ArrayList<>();
			opt.add(o);
			choice = null;
		}
		
		public OptionSet(String name) { //, int num){
			optionName = name;
			opt = new ArrayList<>();
			choice = null;
		//	for(int i=0; i<num; i++)
		//		opt.add(i, new Option());
		}
		
		//getters: 
		protected String getOptionName(){
			return optionName;
		}
		
		
		protected ArrayList getAll(){
			return opt;
		}
		
		protected int getNumberOfOptions(){
			return opt.size();
		}
				
		protected String getSpecificOptionName(int i){
			return opt.get(i).getName();
		
		}
		
		//get option chosen by user
		protected Option getOptionChoice(){
			return choice; //if no choice is present, returns null
		}
		
		//setters:
		protected void setName(String name){
			optionName = name;
		}
		
		protected void updateSpecificOption(String nm, double prc){
			opt.add( new Option(nm, prc));
		}
		
		protected void updateSpecificPrice(String optname, double prc){
			for(int i=0; i<opt.size(); i++){
				if(opt.get(i).getName().equals(optname)){
					opt.get(i).setPrice(prc);
					return;
				}				
			}
		}
		
		//given option name, like red, link the option to the option set
		protected void setOptionChoice(String optionName){
			for(int i=0; i<opt.size(); i++){
				if(opt.get(i).getName().equalsIgnoreCase(optionName)){
					choice = opt.get(i); //link the chosen option to choice variable
				}
			}
		}
		
		//toString but protected
		protected String getString(){
			String str = optionName + "\nOption is available with:\n";
			for(int i=0; i<opt.size(); i++){
				str += opt.get(i).getName() + " at the price of $" + opt.get(i).getPrice() + "\n";
			}
			return str;
		}
		

	}

		class Option implements Serializable{ 
			private String name;
			private double price;
			
			public Option(){
				name = "";
				price = 0.0;
			}
			
			public Option(String name, double price){
				this.name = name;
				this.price = price;
			}
			
			//getters:
			protected String getName(){
				return name;
			}
			
			protected double getPrice(){
				return price;
			}
			
			//setters:
			protected void setPrice(double price){
				this.price = price;
			}
			
			protected void setName(String name){
				this.name = name;
			}
			
		}
	
