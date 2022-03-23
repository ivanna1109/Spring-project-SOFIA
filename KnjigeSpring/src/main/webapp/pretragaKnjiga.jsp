<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Knjige</title>
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
		<h1>Pretraga knjige</h1><br>
		<form action="/IzdavackaKuca/radnik/nadjiKnjigu" method="get">
			<label class="lbl-basic lbl-red-invert">Naziv knjige: </label>
			<input type="text" class="input-f-red" name="nazivKnjige" placeholder="Unesite naziv knjige">
			<input type="submit" class="btn-red" value="Prikaži knjigu"/><br><br>
		</form>
		<c:choose>
		<c:when test="${!empty trazena}">
			<h1>${trazena.naziv}</h1>
				<h2>Ocena knjige: ${ocenaTrazene}</h2>
					
				<div class="slika">
					<img src="/IzdavackaKuca/korisnik/prikaziSliku?id=${trazena.idKnjiga}" alt="Slika">
				</div>
				<h2>Cena: ${trazena.cena}din</h2>
				<form action="/IzdavackaKuca/radnik/redirekcija">
					<input type="hidden" name="knjigaInfo" value="${trazena.naziv}"/>
					<input type="submit" class="btn-red" value="Izmeni informacije"/>
				</form>
		</c:when>
		<c:otherwise>
			<h2>${porukaKnjiga }</h2>
		</c:otherwise>
		</c:choose>
	</div>

</body>
</html>