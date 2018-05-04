package exception;
/*
 * Nina Maller
 * 35B
 */

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AutoException extends Exception {
	private int errorno;
	private String errormsg;
	private static FileWriter writer;
	private DateFormat dtf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    private static File myFile = null;
	
	public AutoException(){
		super();
	}

	
	public AutoException(int errorno, String errormsg){
		super();
		if(myFile == null){
			myFile = new File("ExcpetionFile.txt");
			try {
				writer = new FileWriter(myFile, false); // false on append
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		this.errorno = errorno;
		this.errormsg = errormsg;
		//append error to log
		try {
			writer.write( dtf.format(new Date()) + "   " + errorno + "   " + errormsg + "\n");
			writer.flush();
			//System.out.println((date = new Timestamp(19)).getTime() + "   " + errorno + "   " + errormsg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	public String fix(){
		
		Fix1to100 f1 = new Fix1to100();
		
		switch(errorno){
		case 1: return f1.fix1();
		case 2: return f1.fix2();
		//case 3 is undefined error while reading from file, no fix available (IO exception? )
		//case 4: index of option is greater than array length
		//case 5: index of options for option is greater than array length
		case 6: return f1.fix6();
		case 7: return f1.fix7();
		
		
		default:
			return "";
		
		}
	}
	
	

}
