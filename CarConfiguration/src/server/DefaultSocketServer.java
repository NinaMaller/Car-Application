package server;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Properties;

import adapter.BuildAuto;
import exception.AutoException;
import model.Automobile;

public class DefaultSocketServer extends Thread implements SocketClientInterface, SocketClientConstants {
	//run an instance of Builx	dCarModelOptions
	// to build automobile using the properties file

	
	//copied from DefaultServer in class notes
    private PrintWriter writer;
    private BufferedReader reader;
    private Socket sock;
    private String strHost;
    private int iPort;
    BuildCarModelOptions build = new BuildCarModelOptions();

    public DefaultSocketServer(String strHost, int iPort) {       
            setPort (iPort);
            setHost (strHost);
    }//constructor

    public DefaultSocketServer(Socket socket, String strHost, int iPort) {
    	sock = socket;
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
    		   if(sock == null){
    	     sock = new Socket(strHost, iPort); 
    		   }
    	   }
    	   
    	   catch(IOException socketError){
    		   System.out.println("faield");
    	     if (DEBUG) System.err.println
    	        ("Unable to connect to " + strHost + " at server package");
    	     return false;
    	   }
    	   try {
      	     writer = new PrintWriter(sock.getOutputStream(), true); // true will flush automatically
    	     reader = new BufferedReader
    	      (new InputStreamReader(sock.getInputStream()));

    	   }
    	  catch (Exception e){
    	     if (DEBUG) System.err.println
    	       ("Unable to obtain stream to/from " + strHost);
    	     return false;
    	  }
    	  return true;
    	}
    
    public void handleSession(){
    	String input = "";
    	Properties prop = null;
    	while(true){
    		try { 
    			input = reader.readLine(); // upload/select
				//System.out.println(input);
				if(input.equalsIgnoreCase("upload")){
					System.out.println("tyring to upload from server side now");
					
					ObjectInputStream inStream;
					inStream = new ObjectInputStream(new BufferedInputStream(sock.getInputStream()));
					
					prop = (Properties) inStream.readObject();

			//		sendOutput("Server recieved property");
					
					//create automobile from properties file
					//then add automobile to LHM
					
					Automobile auto = build.buildCarFromProperties(prop);
		//			System.out.println("adding car to LHM");
					build.addCarToLHM(auto);
				} 
				
				if(input.equalsIgnoreCase("getCar")){
					System.out.println("arrived to get car");
					String carName = reader.readLine();
					System.out.println("1");
					ObjectOutputStream outStream;
					outStream = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
				
					Automobile auto = build.getCarSelected(carName);
					System.out.println("2");
					outStream.writeObject(auto); //send object to server
					outStream.flush();
					System.out.println("Sent car " + auto.getName());
					
				}
				
				if(input.equalsIgnoreCase("select")){
					ArrayList<String> arr = build.getAllCarNames();
					if(arr == null){
						writer.println("empty");
						return;
					}

					String cars = "";
					for(int i=0; i<arr.size(); i++){
						cars += arr.get(i) + "     ";
				//		System.out.println(arr.get(i));
					}
					writer.println(cars);	
					
					System.out.println("\nwaiting for client to give car name");
					//get car chosen
					input = reader.readLine();
					System.out.println("Client chose " + input);
					//send car chosen
					ObjectOutputStream outStream;
					outStream = new ObjectOutputStream(new BufferedOutputStream(sock.getOutputStream()));
				
					Automobile auto = build.getCarSelected(input);
					outStream.writeObject(auto); //send object to server
					outStream.flush();
					System.out.println("Sent car " + auto.getName());
					
				}
				
				if(input.equalsIgnoreCase("look")){
					ArrayList<String> arr = build.getAllCarNames();
					if(arr == null){
						writer.println("empty");
						return;
					}

					String cars = "";
					for(int i=0; i<arr.size(); i++){
						cars += arr.get(i) + ", ";
				//		System.out.println(arr.get(i));
					}
					writer.println(cars);
				}
				
	    		if(input.equalsIgnoreCase("quit")){
	    		//	sendOutput("Thank you, bye!");
	    			return;
	    		}
	    		else{
	    		//	sendOutput("error");
	    		}
    		} catch (Exception e) {
					e.printStackTrace();
				}
				
				
				
    		

    	}
    		
    	}
    	
    	
    	
    	/*  String strInput = "";
    	  if (DEBUG) System.out.println ("Handling session with "
    	            + strHost + ":" + iPort);
    	  try {
    	    while ( (strInput = reader.readLine()) != null)
    	    handleInput (strInput);
    	  }
    	  catch (IOException e){
    	  if (DEBUG) System.out.println ("Handling session with "
    	        + strHost + ":" + iPort);
    	  }*/
    	      

    	public void sendOutput(String strOutput){
  //  	  try {
    	    writer.write(strOutput, 0, strOutput.length());
    //	    writer.newLine();
    	//    writer.flush();
    	  }
   /* 	  catch (IOException e){
    	    if (DEBUG) System.out.println 
    	               ("Error writing to " + strHost);
    	  }
    	}*/
    	
        public void handleInput(String strInput){
            System.out.println(strInput);
    }       

    public void closeSession(){
       try {
          writer = null;
          reader = null;
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
    
    /*
    public static void main (String arg[]){
    	   /* debug main; does daytime on local host */
    /*	    String strLocalHost = "";
    	  try{
    	      strLocalHost = 
    	        InetAddress.getLocalHost().getHostName();
    	  }
    	 catch (UnknownHostException e){
    	      System.err.println ("Unable to find local host");
    	 }
    	  CarConfigurationServer d = new CarConfigurationServer(strLocalHost, iDAYTIME_PORT);
    	  d.start();
    	  }*/

   }// class DefaultSocketClient
	
	
	










