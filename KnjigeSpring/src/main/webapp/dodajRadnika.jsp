<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dodavanje novog radnika</title>
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
		<h1>Dodavanje novog radnika</h1><br><br>
		<form action="/IzdavackaKuca/administrator/sacuvajRadnika" method="post">
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
			<label class="lbl-basic lbl-red-invert">Ime: </label>
		 	<input type="text" class="input-f-red" name="ime" placeholder="Unesite ime"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Prezime: </label>
		 	<input type="text" class="input-f-red" name="prezime" placeholder="Unesite prezime"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Broj godina: </label>
		 	<input type="number" class="input-f-red" name="brojGodina"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Adresa: </label>
		 	<input type="text" class="input-f-red" name="adresa" placeholder="Unesite adresu"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Korisničko ime: </label>
		 	<input type="text" class="input-f-red" name="korisnickoIme" placeholder="Unesite korisničko ime"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Šifra: </label>
		 	<input type="password" class="input-f-red" name="sifra" placeholder="********"/>
			</div>
			<input type="submit" class="btn-red" value="Sačuvaj radnika"/>
		</form>	
		<c:if test="${!empty porukaDodaj}">
			<h2>${porukaDodaj}</h2>
		</c:if>
	</div>
</body>
</html>