package exception;
/*
 * Nina Maller
 * 35B
 */

import java.util.Scanner;



public class Fix1to100 {
	Scanner in = new Scanner(System.in);
	
	// #1
	//car name cannot be set
	public String fix1(){
		System.out.println("Car name could not be set. Please fix car name: ");
		return in.nextLine();
	}
	
	// #2
	// option set was set for < 2 (minimum is 2: present or not present), or is not an integer
	public String fix2(){
		System.out.println("Option set cannot be set to an integer less than 2. Please reenter option set: ");
		return in.next();
	}
	
	// #6
	// base price is a negative integer
	public String fix6(){
		System.out.println("Base price cannot be negative. Please reenter base price: ");
		return in.next();
	}
	
	// #7
	// file name not found
	public String fix7(){
		System.out.println("File name could not be found. Please reenter: (Format: filename.txt) ");
		return in.nextLine();
	}

}
