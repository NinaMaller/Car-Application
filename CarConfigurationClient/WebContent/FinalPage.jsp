<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="model.Automobile"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Nina's Configuration Project</title>
</head>
<body>
	<center>
		<font face="Bedrock" size="6" color="#800000" >Car Configuration</font><br />
		<%
			Automobile automobile = (Automobile) request.getSession().getAttribute("automobile");
		%>
		<table border="5">
			<tr>
				<td><b>Name </b></td>
				<td>Value</td>
				<td>Price</td>
			</tr>
			<tr>
				<td><b> Vehicle </b></td>
				<td><%=automobile.getName()%></td>
				<td><%= "$" + automobile.getBasePrice()%></td>
			</tr>

			<%
				
				for (String opsetName : automobile.getAllOptionsString()) {
			%>
			<tr>
				<td><b><%=opsetName%></b></td>
				<td><%=request.getParameter(opsetName)%></td>
				<td><%="$" + automobile.getSpecificOptionPrice(opsetName, request.getParameter(opsetName))%></td>
			</tr>
			<%
				automobile.setOptionChoice(opsetName, request.getParameter(opsetName));
				}
			%>
			<tr>
				<td><b>Cost with Added Options</b></td>
				<td></td>
				<td><%= "$" + automobile.getTotalPrice()%></td>
			</tr>
		</table>
	</center>
</body>
</html>