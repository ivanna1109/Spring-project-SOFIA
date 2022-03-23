<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Početna</title>

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
	<security:authorize access="!hasAnyRole('gost', 'radnik', 'admin', 'korisnik')">
		<div class="page">
			<div class="box bg-color">			
				<div class="logo">
					SOFIA
				</div>			
				<h2>Poštovani, da biste koristili ovu aplikaciju, morate biti ulogovani.</h2>
				<h2>Klik na dugme ispod će vas odvesti na stranicu za logovanje.</h2>				
				<a href="/IzdavackaKuca/login.jsp" class="btn-red">Uloguj se</a>
			</div>
		</div>
    </security:authorize>

	<security:authorize access="hasRole('admin')">
	    <div class="nav">  
			<div><a href="/IzdavackaKuca/index.jsp">Početna</a></div> 
			<div><a href="/IzdavackaKuca/administrator/radnici">Radnici</a></div>
			<div><a href="/IzdavackaKuca/administrator/izvestajKorisnika">Izveštaj korisnika</a></div>
			<div><a href="/IzdavackaKuca/auth/logout">Odjavi se</a></div>			
		</div>	
		<div class="background"></div>		
 		<div class="body"> 		
 			<h1>Izdavačka kuća "SOFIA"</h1>
			<h2>Prikaz svih korisnika i radnika</h2><br>
			<c:if test="${!empty sviKorisnici}">
				<table class="table-basic table-red">
					<tr>
						<th>Uloga</th>
						<th>Korisnik</th>
						<th>Korisnik ime</th>
						<th>Godine</th>
						<th>Adresa</th>
					</tr>
					<c:forEach items="${sviKorisnici}" var="k">
						<tr>
							<td>${k.uloga.naziv}</td>
							<td>${k.ime} ${k.prezime}</td>
							<td>${k.korisnickoIme}</td>
							<td>${k.brojGodina}</td>
							<td>${k.adresa}</td>
						</tr>
					</c:forEach>
				</table>
			</c:if>
        </div> 
    </security:authorize>    
    
        <security:authorize access="hasRole('radnik')">
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
 			<h1>Izdavačka kuća "SOFIA"</h1>
			<h2>Uspešno ste se ulogovali kao radnik.</h2>
 			 <p> 
				Ovom web aplikacijom Vam je predviđen niz funkcionalnosti koje omogućavaju da dodajete nove i menjate postojeće
				informacije o kategorijama, knjigama, mogućnost zakazivanja i otkazivanja književnih večeri, kao i generišete izveštaj
				o knjigama, narudžbinama i klubovima čitalaca.
 			</p>
        </div> 
    </security:authorize> 

	<security:authorize access="hasRole('korisnik')"> 			
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
 			<h2>${korisnik.ime}, dobro došli na online portal izdavačke kuće</h2>
			<h1>"SOFIA"	</h1>		
 			<p> 
			Izdavačka kuća "SOFIA" nastala je u decembru 2020. godine pod sloganom „Erupcija ideja“. 
			Osnovu naše ponude čine knjige kako domaćih, tako i stranih izdavača. 
			Vodili smo se idejom da čitalačkoj publici na jednom mestu ponudimo kvalitetan izbor. 
			Možete birati trilere ili akcije, drame, naučnu i epsku fantastiku, klasike, bigorafiju,
			ili možda poeziju, sve što poželite da čitate u zavisnosti od Vaših afiniteta.
			Ovde ste prilici da pronađete i veliki broj svetskih hitova koji još nisu prevedeni na srpski jezik.
			Izdavačka kuća "SOFIA" potrudiće se da vam pomogne da živite bogatije i zadovoljnije kroz zabavu,
			obrazovanje i inspiraciju!
 			</p>
 		</div>   
    </security:authorize>
    
 	<spring:url value="js/myJS.js" var="myJS" />
    <script src="${myJS}"></script>
</body>
</html>