package server;

import java.util.ArrayList;

import model.Automobile;

public interface AutoServer {
	//get Properties object and return automobile
	//add automobile to LHM
	public void addCarToLHM(Automobile auto);
	public ArrayList<String> getAllCarNames();
	public Automobile getCarSelected(String carName);
}
