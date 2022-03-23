<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz knjige</title>
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

<spring:url value="js/myJS.js" var="myJS" />
<script src="${myJS}"></script>
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
		
			<c:if test="${!empty knjiga}">
			
				<div class="knjiga">
				
					<h1>${knjiga.naziv}</h1>
					<h2>Ocena knjige: ${ocenaKnjige}</h2>
					
					<div class="slika">
						<img src="/IzdavackaKuca/korisnik/prikaziSliku?id=${knjiga.idKnjiga}" alt="Slika">
					</div>
					<h2>Cena: ${knjiga.cena}din</h2>
					
					<div class="infos">
						<div>
							<span>Pisac:</span>
							<span>${knjiga.pisac.ime} ${knjiga.pisac.prezime}</span>
						</div>
						<div>
							<span>Izdavač:</span>
							<span>${knjiga.izdavac}</span>
						</div>
						<div>
							<span>Godina izdanja:</span>
							<span>${knjiga.godinaIzdanja}</span>
						</div>
					</div>
					
					<div class="opis">
						<p> ${knjiga.kratakOpis} </p>
					</div>
					
					
					<div class="omiljena">
						<p class="lbl-basic lbl-red">Dodaj knjigu u svoje omiljene: </p>
						<form action="/IzdavackaKuca/korisnik/dodajOmiljenu" method="post">
							<input type="hidden" name="idKnjige" value="${knjiga.idKnjiga}"/>
							<input type="submit" class="btn-red" value="Dodaj knjigu u omiljene"/> 
						</form>
						<c:if test="${!empty poruka}">
							<br><h2> ${poruka }!</h2>
						</c:if>
					</div>
					
					<div class="ocena">
						<form action="/IzdavackaKuca/korisnik/oceni" method="post">
							<p class="lbl-basic lbl-red"> Oceni ovu knjigu: </p>
							<input type="hidden" name="idKnjige" value="${knjiga.idKnjiga}">
							<div>
							<label class="r-btn-basic r-btn-red">1
							    <input type="radio" name="ocena" value="1">
							    <span class="checkmark"></span>
							</label>
							<label class="r-btn-basic r-btn-red">2
							    <input type="radio" name="ocena" value="2">
							    <span class="checkmark"></span>
							</label>
							<label class="r-btn-basic r-btn-red">3
							    <input type="radio" name="ocena" value="3">
							    <span class="checkmark"></span>
							</label>
							<label class="r-btn-basic r-btn-red">4
							    <input type="radio" name="ocena" value="4">
							    <span class="checkmark"></span>
							</label>
							<label class="r-btn-basic r-btn-red">5
							    <input type="radio" checked="checked" name="ocena" value="5">
							    <span class="checkmark"></span>
							</label>
							</div>
							<input type="submit" class="btn-red" value="Oceni knjigu"/>
						</form>
						<c:if test="${!empty porukaOcena}">
							<br><h2> ${porukaOcena }! </h2>
						</c:if>
					</div>
					
					<div class="komentar">
						<form action="/IzdavackaKuca/korisnik/komentarisi" method="post">
							<p class="lbl-basic lbl-red">Ostavi komentar: </p>
							
							<textarea class="input-f-red" name="komentar" rows="4" cols="50">Unesite komentar...
					  		</textarea>
					  		<input type="hidden" name="idKnjige" value="${knjiga.idKnjiga}">
					  		<input type="submit" class="btn-red" value="Unesi komentar"/>
						</form>
					</div>
					<c:if test="${!empty porukaKom}">
						<br><h2> ${porukaKom }! </h2>
					</c:if>				
				</div>					
			</c:if>
	</div>
	<spring:url value="js/myJS.js" var="myJS" />
    <script src="${myJS}"></script>
</body>
</html>