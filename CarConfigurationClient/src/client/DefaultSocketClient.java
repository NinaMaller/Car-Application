package client;
import java.net.*;
import java.util.Properties;
import java.io.*;

import model.Automobile;

public class DefaultSocketClient 
		extends Thread implements SocketClientInterface, SocketClientConstants {


	    private BufferedReader inputReader; //read from console
	    private BufferedReader reader; //read from server
	    private PrintWriter writer;
	    private Socket sock;
	    private String strHost;
	    private int iPort;
	   

	    public DefaultSocketClient(String strHost, int iPort) {       
	            setPort (iPort);
	            setHost (strHost);
	    }//constructor
	    
	    public DefaultSocketClient(Socket sock, String strHost, int iPort){
			this.sock = sock;
			setPort (iPort);
            setHost (strHost);
		}

	    public void run(){
	       if (openConnection()){
	          handleSession();
	          closeSession();
	       }
	    }//run
	    
	    public boolean openConnection(){

	    	   try {
	    		   if(sock == null){ //added
	    			   	sock = new Socket(strHost, iPort); 
	    		   }
	    	   }
	    	   catch(IOException socketError){
	    	     if (DEBUG) System.err.println
	    	        ("Unable to connect to " + strHost);
	    	     return false;
	    	   }
	    	   try {
		    	  writer = new PrintWriter(sock.getOutputStream(), true);
	    	     inputReader = new BufferedReader(new InputStreamReader(System.in));
	    	     reader = new BufferedReader(new InputStreamReader(sock.getInputStream()));
	    	   }
	    	  catch (Exception e){
	    	     if (DEBUG) System.err.println
	    	       ("Unable to obtain stream to/from " + strHost);
	    	     return false;
	    	  }
	    	  return true;
	    	}
	    public void handleSession(){
	    	String input;
	    	while(true){
	    		try{

	    		System.out.println("\n\nMenu: (write) "
	    				+ "\n Upload -- to upload a car"
	    				+ "\n Select -- to select a car"
	    				+ "\n Look -- to see availale cars"
	    			//	+ "\n get -- to get car"
	    			//	+ "\n Auto -- to automotically upload available cars"
	    				+ "\n Quit -- to quit the program \n");
	    		input = inputReader.readLine();
	    		
	    		if(input.equalsIgnoreCase("upload")){
	    			
	    		//	System.out.println("trying to send upload to server");
	    			uploadCarToServer();
	    			System.out.println("Car was uploaded");
	    		}
	    	/*(	if(input.equalsIgnoreCase("get")){
    			//	Automobile nina = getCar();
    		}*/
	    		if(input.equalsIgnoreCase("select")){
	    				select();
	    		}
	  /*  		if(input.equalsIgnoreCase("auto")){
	    			writer.println("upload");
	    			AutoUploadCarToServer();
	    		}
	    		*/
	    		if(input.equalsIgnoreCase("look")){
	    			String carsAvailable = getAvailableCars();
	    			System.out.println("Available cars are: \n" + carsAvailable);
	    		}
	    		if(input.equalsIgnoreCase("quit")){
	    			System.out.println("\nThank you, bye!");
	    			//let server know
	    			writer.println("quit");
	    			return;
	    		}
	    		} catch(Exception e){
	    			e.printStackTrace();
	    		}
	    		
	    	}
	    	 
	    	}       

	    public void uploadCarToServer(){
	    	writer.println("upload"); // let server know
	    	try{
		    	CarModelOptionsIO  model = new CarModelOptionsIO();
	    		
	    	System.out.println("Enter file name/path: ");
	    	String file = inputReader.readLine(); //read from console

	    	Properties prop = model.createAndLoadProperties(file);
			ObjectOutputStream outStream = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
			outStream.writeObject(prop); //send object to server
			outStream.flush();

			
	    	} catch(Exception e){
	    		e.printStackTrace();
	    	}
	    		
	    }
	    
	    
	    public Automobile getCar(String carName){
	    	System.out.println("trying to configurate a car from client #" + carName);
	    	if( carName.startsWith(" ")){
	    		carName = carName.substring(1);
	    		System.out.println("*" + carName);
	    	}
	    	Automobile car = null;
	    	writer.println("getCar");
	    	writer.println(carName);
	    	ObjectInputStream inStream;
	    	try {
			inStream = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
	    	
			car = (Automobile) inStream.readObject();
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    	return car;
	    }
	    
	    public void select() throws ClassNotFoundException{
			System.out.println("\nyou chose select");
			writer.println("select");
			SelectCarOption opt = new SelectCarOption(writer, inputReader, reader);
			String choice = opt.getClientChoice();
			if(choice == null){
				//message was already printed
				return;
			}
			writer.println(choice);	
			ObjectInputStream inStream;
			
			try {
				inStream = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
			
			// get car
			Automobile car = (Automobile) inStream.readObject();
			opt.configurateCar(car);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	    public String getAvailableCars(){
	    	String lot = "";
	    	writer.println("look");
	    	try {
				lot = reader.readLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    	return lot;
	    }
	    
	    
	    	public void sendOutput(String strOutput){
	    	  System.out.println("trying to send output " + strOutput );
	    	  writer.write(strOutput, 0, strOutput.length());

	    	}
	        public void handleInput(String strInput){
	            System.out.println(strInput);
	    }       

	    public void closeSession(){
	       try {
	          writer = null;
	          reader = null;
	          inputReader=null;
	          sock.close();
	       }
	       catch (IOException e){
	         if (DEBUG) System.err.println
	          ("Error closing socket to " + strHost);
	       }       
	    }

	    public void setHost(String strHost){
	            this.strHost = strHost;
	    }

	    public void setPort(int iPort){
	            this.iPort = iPort;
	    }
	    
	    public static void main (String arg[]){

	    	DefaultSocketClient d;
			try{
				d = new DefaultSocketClient(new Socket("localhost", 1230), "localhost", 1230);
				d.start();
			} catch (IOException e) {
			e.printStackTrace();
			}
			
	    	 }
}// class DefaultSocketClient

