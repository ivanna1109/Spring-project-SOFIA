<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Uređivanje knjige</title>
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
<style type="text/css">
    <%@include file="css/prikazJedneKnjige.css" %>
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
 		<h1>Informacije o knjizi</h1><br>
 		<form action="/IzdavackaKuca/radnik/urediKnjigu" method="post">
	 		<label class="lbl-basic lbl-red-invert">Naziv: </label>
	 		<input type="text" class="input-f-red-out" name="naziv" value="${uredjivanje.naziv}"/><br>
	 		<label class="lbl-basic lbl-red-invert">Izdavač: </label>
	 		<input type="text" class="input-f-red-out" name="izdavac" value="${uredjivanje.izdavac}"/><br>
	 		<label class="lbl-basic lbl-red-invert">Godina izdanja: </label>
	 		<input type="number" class="input-f-red-out" name="godinaIzdanja" value="${uredjivanje.godinaIzdanja}"/><br>
	 		<label class="lbl-basic lbl-red-invert">Cena: </label>
	 		<input type="number" class="input-f-red-out" name="cena" value="${uredjivanje.cena }"/><br>
	 		<label class="lbl-basic lbl-red-invert">Broj knjiga na lageru: </label>
	 		<input type="number" class="input-f-red-out" name="lager" value="${uredjivanje.brojKnjigaLager }"/><br>
	 		<p class="lbl-basic lbl-red-invert">Kratak opis: </p><br>
	 		<textarea class="input-f-red" name="opis" rows="4" cols="50" placeholder="${uredjivanje.kratakOpis}"></textarea><br>
	 		<input type="hidden" name="idKnjige" value="${uredjivanje.idKnjiga}"/>
	 		<input type="submit" class="btn-red-out" value="Sačuvaj promene"/>
 		</form>
 		<c:if test="${!empty porukaUredjivanje}">
 			<h2>${porukaUredjivanje}</h2>
 		</c:if>
 	</div> 	
</body>
</html>