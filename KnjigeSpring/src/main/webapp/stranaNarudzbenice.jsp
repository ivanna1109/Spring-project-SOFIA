<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Narudzbenice</title>
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
 			<div class="nav_drop">
			  Kategorije
			  <div class="items">
			  	<c:if test="${!empty kategorije}">
				  	<c:forEach items="${kategorije}" var="k">
				    	<span><a href="/IzdavackaKuca/korisnik/sveKnjige?idKat=${k.idKategorija}">${k.nazivKategorije}</a></span>
				    </c:forEach>
			    </c:if>
			  </div>
			</div>			
 			<div class="nav_drop">
			  Klub čitalaca
			  <div class="items">
			    <span><a href="/IzdavackaKuca/korisnik/sviKlubovi">Svi klubovi</a></span>
			    <span><a href="/IzdavackaKuca/korisnik/kluboviKorisnika">Moji klubovi</a></span>
			  </div>
			</div>			
 			<div class="nav_drop">
			  Književne večeri
			  <div class="items">
			    <span><a href="/IzdavackaKuca/korisnik/sveVeceri">Predstojeće književne večeri</a></span>
			    <span><a href="/IzdavackaKuca/korisnik/sveVeceriKorisnika">Moje književne večeri</a></span>
			  </div>
			</div>			
 			<div class="nav_drop">
			  Narudžbine
			  <div class="items">
			    <span><a href="/IzdavackaKuca/korisnik/sveKategorije">Naruči knjigu</a></span>
			    <span><a href="/IzdavackaKuca/korisnik/sveNarudzbenice">Moje narudžbine</a></span>
			  </div>
			</div>			
 			<div><a href="/IzdavackaKuca/korisnik/omiljeneKnjige">Omiljene knjige</a></div>			
 			<div class="nav_drop">
			  Profil
			  <div class="items">
			    <span><a href="/IzdavackaKuca/urediProfil.jsp">Uredi profil</a></span>
			    <span><a href="/IzdavackaKuca/auth/logout">Odjavi se</a></span>
			  </div>
			</div>			
 		</div>		
	<div class="background"></div>		
 	<div class="body">
 		<h1>Moje narudžbenice</h1><br>	
		<c:if test="${!empty narudzbenice}">
			<table class="table-basic table-red">
					<tr>
						<th>Knjiga</th>
						<th>Pisac</th>
						<th>Cena knjige</th>
						<th>Datum naručivanja</th>
					</tr>
					<c:forEach items="${narudzbenice}" var="n">
						<tr>
							<td>${n.knjiga.naziv}</td>
							<td>${n.knjiga.pisac.ime} ${n.knjiga.pisac.prezime}</td>
							<td>${n.knjiga.cena}</td>
							<td>${n.datumNarucivanja}</td>
						</tr>
					</c:forEach>		
			</table><br><br>
			<form action="/IzdavackaKuca/korisnik/izvestajNarudzbenica" method="get">
				<br><br><input type="submit" class="btn-red" value="Generiši izveštaj"/>
			</form>
		</c:if>
	</div>	
	<spring:url value="js/myJS.js" var="myJS" />
    <script src="${myJS}"></script>
</body>
</html>