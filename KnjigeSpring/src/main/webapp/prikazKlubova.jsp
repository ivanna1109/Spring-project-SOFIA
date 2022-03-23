<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz klubova čitalaca</title>
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
		<c:if test="${!empty klubovi}">
			<h1>Svi klubovi čitalaca</h1><br>
			<table class="table-basic table-red">
					<tr>
						<th>Naziv</th>
						<th>Datum osnivanja</th>
						<th>Opis</th>
						<th>Učlanjenje</th>
					</tr>
					<c:forEach items="${klubovi}" var="k">
						<tr>
							<td>${k.nazivKluba}</td>
							<td>${k.datumOsnivanja}</td>
							<td>${k.opis}</td>
							<td>
								<c:choose>
		   							<c:when test="${korisnikoviKlubovi.contains(k)}">
										Učlanjen  							
		   							</c:when>    
   									<c:otherwise>
										<form action="/IzdavackaKuca/korisnik/uclaniUKlub" method="post">
											<input type="hidden" name="idKlub" value="${k.idKlubCitalaca}"/>
											<input type="submit" class="btn-red-out" value="Učlani se"/>
										</form>
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
					</c:forEach>		
			</table>	
		</c:if>
		<c:if test="${!empty kluboviKorisnika}">
			<h1>Moji klubovi čitalaca</h1><br>
			<table class="table-basic table-red">
					<tr>
						<th>Naziv</th>
						<th>Učlanjen od:</th>
						<th>Članarina</th>
					</tr>
					<c:forEach items="${kluboviKorisnika}" var="k">
						<tr>
							<td>${k.klubcitalaca.nazivKluba}</td>
							<td>${k.datumIzdavanja}</td>
							<td>${k.clanarina}</td>
						</tr>
					</c:forEach>		
			</table>	
		</c:if>
		<c:if test="${!empty porukaKlub}">
			<h1>Moji klubovi čitalaca</h1><br>
			<h2>${porukaKlub }</h2>
		</c:if>
	</div>
	<spring:url value="js/myJS.js" var="myJS" />
    <script src="${myJS}"></script>
</body>
</html>