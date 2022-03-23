<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dodaj kategoriju</title>
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
 		<h1>Dodavanje nove kategorije</h1><br>
 		<form action="/IzdavackaKuca/radnik/dodajKategoriju" method="post">
	 		<label class="lbl-basic lbl-red-invert">Naziv kategorije: </label>
	 		<input type="text" class="input-f-red" name="naziv" placeholder="Unesite naziv"/><br>
	 		<label class="lbl-basic lbl-red">Opis kategorije: </label><br>
			<textarea class="input-f-red" name="opis" rows="4" cols="50">Unesite opis...</textarea><br><br>
	 		<input type="submit" class="btn-red" value="Dodaj"/><br>
 		</form>
 		<c:if test="${!empty porukaDodaj}">
 			<h2>${porukaDodaj }</h2>
 		</c:if>
 	</div> 	
	<spring:url value="js/myJS.js" var="myJS" />
    <script src="${myJS}"></script>
</body>
</html>