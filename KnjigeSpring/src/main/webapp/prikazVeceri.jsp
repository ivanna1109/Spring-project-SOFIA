<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Književne večeri</title>
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
		<c:if test="${!empty veceri}">
			<h1>Sve književne večeri</h1><br>
			<table class="table-basic table-red">
				<tr>
					<th>Datum održavanja</th>
					<th>Mesto</th>
					<th>Knjiga</th>
					<th>Prisustvo</th>
				</tr>
				<c:forEach items="${veceri}" var="v">
					<tr>
						<td>${v.datum}</td>
						<td>${v.mesto}</td>
						<td>${v.knjiga.naziv}</td>
						<c:choose>
   							<c:when test="${veceriKor.contains(v)}">
								<td>Prisustvujem</td>   							
   							</c:when>    
   							<c:otherwise>
								<td>								
								<form action="/IzdavackaKuca/korisnik/prisustvujVeceri" method="post">
									<input type="hidden" name="idVece" value="${v.idKnjizevnoVece}"/>
									<input type="submit" class="btn-red-out" value="Prisustvuj"/>
								</form></td>
							</c:otherwise>
						</c:choose>
					</tr>
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${!empty veceriKorisnika}">
			<h1>Moje književne večeri</h1><br>
			<table class="table-basic table-red">
				<tr>
					<th>Datum održavanja</th>
					<th>Mesto</th>
					<th>Knjiga</th>
				</tr>
				<c:forEach items="${veceriKorisnika}" var="v">
					<tr>
						<td>${v.datum}</td>
						<td>${v.mesto}</td>
						<td>${v.knjiga.naziv}</td>
					</tr>	
				</c:forEach>
			</table>
		</c:if>
		<c:if test="${!empty porukaVece}">
			<h1>Moje književne večeri</h1><br>
			<h2>${porukaVece }!</h2>
		</c:if>
	</div>
	<spring:url value="js/myJS.js" var="myJS" />
    <script src="${myJS}"></script>
</body>
</html>