package scale;


import exception.AutoException;
import model.Automobile;
import adapter.ProxyAutomobile;


public class EditOptions extends ProxyAutomobile implements Runnable{ //extends ProxyAutomobile

	private Thread thread;
	private int option;
	private String[] arr;
	
	//gets from proxy auto
	public EditOptions(int option, String[] arr){
		this.option = option;
		this.arr = arr;
		thread = new Thread(this);
		thread.start();
		

	}
	
	@Override
	public void run() {
		switch (option){
		case 1: 
			//given name of the car, prev options name, new option name
			updateOptionSetNameForThis(arr[0], arr[1], arr[2]);
			break;
		case 2: 
			//change price
			//given car name, optionSet, option, new price
			updatePriceForThis(arr[0], arr[1], arr[2], Integer.parseInt(arr[3]));
			break;
		//	car.changeOptionPrice(prev1, optn, price);
		case 3:
			try {
				changeMakeModelPrice(arr[0], arr[1], arr[2], Integer.parseInt(arr[3]));
			} catch (AutoException e) {	}
			break;
		default:
			break;
		}
		
	}
	
	public void randomWait() {
		try {
			Thread.currentThread().sleep((long)(3000*Math.random()));
		} catch(InterruptedException e) {
			System.out.println("Interrupted!");
			}
		}
	
	private void updateOptionSetNameForThis(String name, String cur, String newOp){
		Automobile auto = autoLot.get(name); //get the car
		
		synchronized(auto){
		//randomly wait 
		 randomWait();
		

		auto.changeOptionName(cur, newOp);

		System.out.println(auto);
		}
		
	}
	
	private void updatePriceForThis(String name, String opts, String opt, int price){
		Automobile auto = autoLot.get(name);
		synchronized(auto){
			randomWait();
			auto.changeOptionPrice(opts, opt, price);
		}
		System.out.println(auto);
	}
	
	private void changeMakeModelPrice(String name, String make, String model, int price) throws AutoException{
		//get car
		 
		randomWait();
		
		Automobile auto = autoLot.get(name);
		synchronized(auto){
		
		randomWait();
		
		//change make for auto
		auto.setMake(make);
	//	System.out.println("Changing make to " + make);		
		randomWait();
		
		//change model
		auto.setModel(model);
	//	System.out.println("Changing model to " + model);	
		
		randomWait();
		
		//change price
		auto.setPrice(price);
	//	System.out.println("Changing price to " + price);	
		
	//	System.out.println("Finished thread");
		System.out.println("\n\n\n\n" + auto);
		 }
	}
	

}
