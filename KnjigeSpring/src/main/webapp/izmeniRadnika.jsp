<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Ažuriranje informacija</title>
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
		<div><a href="/IzdavackaKuca/administrator/radnici">Radnici</a></div>
		<div><a href="/IzdavackaKuca/administrator/izvestajKorisnika">Izveštaj korisnika</a></div>
		<div><a href="/IzdavackaKuca/auth/logout">Odjavi se</a></div>			
	</div>	
	<div class="background"></div>
	<div class="body">
	<h1>Informacije o radniku:</h1><br>
 		<form action="/IzdavackaKuca/administrator/urediRadnika" method="post">
	 		<label class="lbl-basic lbl-red-invert">Ime: </label>
	 		<input type="text" class="input-f-red-out" name="ime" value="${radnik.ime}"/><br>
	 		<label class="lbl-basic lbl-red-invert">Prezime: </label>
	 		<input type="text" class="input-f-red-out" name="prezime"value="${radnik.prezime}"/><br>
	 		<label class="lbl-basic lbl-red-invert">Broj godina: </label>
	 		<input type="number" class="input-f-red-out" name="brojGodina" value="${radnik.brojGodina}"/><br>
	 		<label class="lbl-basic lbl-red-invert">Adresa: </label>
	 		<input type="text" class="input-f-red-out" name="adresa" value="${radnik.adresa}"/><br>
	 		<input type="submit" class="btn-red" value="Sačuvaj promene"/>
 		</form>
 		<c:if test="${!empty porukaRadnik}">
 			<h2>${porukaRadnik }</h2>
 		</c:if>
	
	</div>

</body>
</html>