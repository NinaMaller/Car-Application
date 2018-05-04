package driver;
/*Nina Maller
35B
*/
import adapter.*;
import scale.EditOptions;
import util.FileIO;
import model.Automobile;

public class Driver{
	

	public static void main(String [] args){
		//testLab1()
		//testLab2()
		//testLab3();
		
		//test multithreading
		testLab4();

	}
	
	public static void testLab4(){
		
		//create an auto instance through CreateAuto interface
		CreateAuto car = new BuildAuto();
		car.BuildAuto("FordFocusData.txt");
		
		String[] arr = new String[3];
		arr[0] = "Ford Focus Wagon ZTW";
		arr[1] = "Color";
		arr[2] = "Exterior color";

		
		String[] arr2 = new String[3];
		arr2[0] = "Ford Focus Wagon ZTW";
		arr2[1] = "Color";
		arr2[2] = "Interior color";
				

	//	EditOptions one = new EditOptions(1, arr); //change color to exterior
	//	EditOptions two = new EditOptions(1, arr2); // change color to interior 
		
		//String name, String make, String model, int price
		String[] bmw = new String[4];
		bmw[0] = "Ford Focus Wagon ZTW";
		bmw[1] = "BMW";
		bmw[2] = "Z4";
		bmw[3] = "50000";
		
		String[] miata = new String[4];
		miata[0] = "Ford Focus Wagon ZTW";
		miata[1] = "Mazda";
		miata[2] = "MX-5";
		miata[3] = "35000";
		
		EditOptions three = new EditOptions(3, bmw);
		EditOptions four = new EditOptions(3, miata);

		
		String[] priceOne = new String[4];
		priceOne[0] =  "Ford Focus Wagon ZTW";
		priceOne[1] = "Color";
		priceOne[2] = "Cloud 9 White Clearcoat";
		priceOne[3] = "900";
		
		String[] priceTwo = new String[4];
		priceTwo[0] =  "Ford Focus Wagon ZTW";
		priceTwo[1] = "Color";
		priceTwo[2] = "Cloud 9 White Clearcoat";
		priceTwo[3] = "-700";
		

		
//		EditOptions five = new EditOptions(2, priceOne); //change price to 900
//		EditOptions six = new EditOptions(2, priceTwo); //change price to -700
		
		

	}
	
	
	//Testing: select choices for automobile and see that price is calculated correctly.
	public static void testLab3(){
		//build new auto
		FileIO file = new FileIO();
		
		//Build Automobile Object from a file.
		Automobile car = file.buildAutoObject("FordFocusData.txt");
		
		//print car with options available and their corresponding prices
		System.out.println(car);	
		
		//choose some options
		car.setOptionChoice("Color", "Infra-Red Clearcoat" );
		car.setOptionChoice("Transmission", "manual");
		car.setOptionChoice("Brakes/Traction Control", "ABS with Advance Trac");
		car.setOptionChoice("Power Moonroof", "present");
		
		//print options chosen
		System.out.println("\n\n\n");
		System.out.println(car.printChoices());
		
		//build and print Mazda Miata
		car = file.buildAutoObject("MazdaMiata.txt");
		System.out.println("\n\n\n" + car);
		
		//build and print ford mustang
		car = file.buildAutoObject("FordMustang.txt");
		System.out.println("\n\n\n" + car);
		
		// test linked hash map
		System.out.println("\n\n\n\n\nTESTING LHM:");
		
		//build all cars
		CreateAuto auto = new BuildAuto();
		auto.BuildAuto("FordFocusData.txt");
		auto.BuildAuto("FordMustang.txt");
		auto.BuildAuto("MazdaMiata.txt");
		
		//print all cars
		System.out.println("\n\nPRINT FOCUS: \n\n");
		auto.printAuto("Ford Focus Wagon ZTW");
		System.out.println("\n\nPRINT MUSTANG: \n\n");
		auto.printAuto("Ford Mustang Fastback");
		System.out.println("\n\nPRINT MIATA: \n\n");
		auto.printAuto("Mazda Miata MX-5");
	}
	
	
	//testing: build through interfaces, update through different interfaces (use static object),
	//and printing. Also tests the self healing software.
	public static void testLab2(){
		//create an auto instance through CreateAuto interface
		CreateAuto car = new BuildAuto();
		car.BuildAuto("FordFocusData.txt");
		//print through createAuto interface
		car.printAuto("Ford Focus Wagon ZTW");
		
		UpdateAuto auto = new BuildAuto();
		//update option for car
		auto.updateOptionSetName("Ford Focus Wagon ZTW", "Color", "Colour"); //British change?
		//update price
		auto.updateOptionPrice("Ford Focus Wagon ZTW", "Transmission", "automatic", 1000); //change from 0 to $1000
		
		System.out.println("\n\n\nPRINTING AFTER CHANGE : \n\n\n");
		car.printAuto("Ford Focus Wagon ZTW");
		
		
		System.out.println(" \n\n\nTesting excpetion handling \n\n\n");
		car.BuildAuto("Wrong.txt");
		car.printAuto("Ford Focus");
	}
	
	
	//test serializing, deserializing, building an auto object by reading from file, and printing
	public static void testLab1(){
		FileIO file = new FileIO();
		
		//Build Automobile Object from a file.
		Automobile FordZTW = file.buildAutoObject("ForddFocusData.txt");

		//Print attributes before serialization
		System.out.println(FordZTW);

		//Serialize the object
		file.writeAutomobile(FordZTW);
		
		Automobile car = new Automobile();
		
		
		//Deserialize the object and read it into memory.
		car = (Automobile) file.readAutomobile(car);
		
		System.out.println("\n\n\n\nPrint Automotive object after deserialization: \n\n");
		System.out.println(car);
	}
	


	}