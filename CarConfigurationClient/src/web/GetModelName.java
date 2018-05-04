package web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Automobile;
import client.DefaultSocketClient;

/**
 * Servlet implementation class GetModelName
 */
@WebServlet("/GetModelName")
public class GetModelName extends HttpServlet {
	private static final long serialVersionUID = 1L;

	DefaultSocketClient client;
	Automobile auto;
	String models;
	
    /**
     * Default constructor. 
     */
    public GetModelName() {
		super();
		//open connection with client from lab 5
		//which is now the server for lab 6
		try{
			client = new DefaultSocketClient(new Socket("localhost", 1230), "localhost", 1230);
		//	client.start(); //start the client
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		if(client != null){
			session.setAttribute("client", client);
			if(client.openConnection()){
				System.out.println("how many cars would you like to upload? (choose 2 and provide full path)");
				BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
				int num = Integer.parseInt(br.readLine());
				for(int i=0; i<num; i++)
					client.uploadCarToServer();
				//   /Users/ninamaller/Desktop/workspace3/CarConfigurationClient/MazdaMiataProperties.txt
				//   /Users/ninamaller/Desktop/workspace3/CarConfigurationClient/FordFocusProperties.txt
				br.close();
				models = client.getAvailableCars();
				
	
				System.out.println("Models are :"+models);
			}
		}
		else{
			System.out.println("Could not connect");
			return;
		}

		PrintWriter out = response.getWriter();
		out.println
		(ServletUtilities2.headWithTitle("Car Configuration") +
				"<body>\n" +
				"<center>"+
				"<form action = \"Configurate\" method = \"get\">"); //use GET to post on URL
		if(models == null || models.trim() == "")
			out.println("Please add cars before trying to configurate them.");
		else{
			out.println("Choose a model to configure:");
			String carString[] = models.split(",");
			carString[0] = carString[0].replace(" ", "_");
			carString[1] = carString[1].replace(" ", "_");
			
			out.println("<form ALIGN=\"CENTER\" action=\"ConfigurePage\" method=\"Get\">");
			out.println("<p>" + "Available Vehicles:");

			out.println("<select name = \"choice\">");
			for (int i = 0; i < 2; i++) {
				out.println("<option value=\"" + carString[i] + "\">" + carString[i] + "</option>");
			}
			out.println("</select>");
			out.println("<p>");
			out.println("<input type=\"submit\" value=\"Configurate\">");
		 
		out.println("</form ></BODY></HTML>");
			
		}
			
				
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
