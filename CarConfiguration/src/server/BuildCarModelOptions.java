package server;

import java.util.ArrayList;
import java.util.Properties;

import exception.AutoException;
import util.FileIO;
import model.Automobile;
import adapter.BuildAuto;


public class BuildCarModelOptions implements AutoServer{

	private AutoServer auto;
	
	BuildCarModelOptions(){
		auto = new BuildAuto();
	}
	
	//accept properties object from client socket over object stream and create an automobile 
	public Automobile buildCarFromProperties(Properties prop) throws NumberFormatException, AutoException {
		FileIO in = new FileIO();
		Automobile car = in.buildObjectFromProperties(prop);
		return car;
		
	}
	
	//add given car to LHM
	public void addCarToLHM(Automobile car){
		auto.addCarToLHM(car);
	}
	
	public ArrayList<String> getAllCarNames(){
		return auto.getAllCarNames();
		
	}
	public Automobile getCarSelected(String carName){
		return auto.getCarSelected(carName);
	}

}
