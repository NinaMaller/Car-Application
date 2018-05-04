package web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.Automobile;
import client.DefaultSocketClient;

/**
 * Servlet implementation class GetOptionsets
 */
@WebServlet("/Configurate")
public class Configurate extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	DefaultSocketClient client;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Configurate() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		PrintWriter out = response.getWriter();
		client = (DefaultSocketClient) session.getAttribute("client");

		//get from URL
		String choice = request.getParameter("choice");
		//replace _ back to spaces to find the vehicle
		choice = choice.replace("_", " ");
			
		//get Automobile
		Automobile auto = client.getCar(choice);
		session.setAttribute("automobile", auto);
		client.closeSession();
		
		out.println
		(ServletUtilities2.headWithTitle("Car Configuration") +
				"<body>\n" +
				"<center>"+
				"<form action = \"FinalPage.jsp\" method = \"get\">");
		if(auto != null){
			out.println("<h1 style=\"color:#800000;\"> Configurate the car: </h1>");
			out.println(
					"<table border = \"5\"> <tr> <td> <b> Vehicle </td>"+
							"<td>"+request.getParameter("choice")+"</td> </tr>");
			
			
			ArrayList allOptions = auto.getAllOptions();
			for(int i=0; i<allOptions.size(); i++){
				ArrayList specificOption = auto.getSpecificOptions(auto.getOptionName(i));
				out.println(
						"<tr>"+ "<td> <b>"+ auto.getOptionName(i) + " </b> </td>"+
								"<td> <select name = \"" +auto.getOptionName(i) +"\">");
				for(int j=0; j<specificOption.size() ; j++)
					out.println("<option>"+ specificOption.get(j) +"</option>");
				out.println("</td> </tr>");
			}
			out.println("</table> <br/> <input type=\"submit\" value = \"Configurate\">"); 
			out.println("</form></center></body></html>");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
