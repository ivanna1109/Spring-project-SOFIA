<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Izveštaji</title>
<style type="text/css">
    <%@include file="css/myCSS.css" %>
</style>
<style type="text/css">
    <%@include file="css/index.css" %>
</style>
<style type="text/css">
    <%@include file="css/navigation.css" %>
</style>
<style type="text/css">
    <%@include file="css/background.css" %>
</style>
</head>
<body>
        <div class="nav">   
         	<div><a href="/IzdavackaKuca/index.jsp">Početna</a></div> 	
         	<div><a href="/IzdavackaKuca/kategorijeRadnik.jsp">Kategorije</a></div>
         	<div class="nav_drop">
				Knjige
				<div class="items">
					<span><a href="/IzdavackaKuca/pretragaKnjiga.jsp">Pretraga knjiga</a></span>					
					<span><a href="/IzdavackaKuca/radnik/sveKategorijeDodaj">Dodaj knjigu</a></span>
			  	</div>
			</div>
         	<div class="nav_drop">
				Književne večeri
				<div class="items">
					<span><a href="/IzdavackaKuca/radnik/sveVeceri">Prikaži književne večeri</a></span>					
					<span><a href="/IzdavackaKuca/radnik/sveKnjige">Zakaži književno veče</a></span>
			  	</div>
			</div>
			<div><a href="/IzdavackaKuca/izvestaji.jsp">Izveštaji</a></div>
			<div><a href="/IzdavackaKuca/auth/logout">Odjavi se</a></div>
         </div>  
	<div class="background"></div>
	<div class="body">
		<h1>Izveštaji</h1><br>
		<form action="/IzdavackaKuca/radnik/izvestajKnjiga" method="get">
			<label class="lbl-basic lbl-red-invert">Izveštaj o knjigama: </label>
			<input type="submit" class="btn-red" value="Generiši izveštaj"/><br><br>
		</form>
		<form action="/IzdavackaKuca/radnik/izvestajNarudzbina" method="get">
			<label class="lbl-basic lbl-red-invert">Izveštaj svih narudžbina: </label>
			<input type="submit" class="btn-red" value="Generiši izveštaj"/><br><br>
		</form>
		<form action="/IzdavackaKuca/radnik/izvestajKlubova" method="get">
			<label class="lbl-basic lbl-red-invert">Izveštaj o klubovima čitalaca: </label>
			<input type="submit" class="btn-red" value="Generiši izveštaj"/><br><br>
		</form>

	</div>
</body>
</html>