<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Dodavanje nove knjige</title>
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
		<h1>Dodavanje nove knjige</h1><br>
		<form action="/IzdavackaKuca/radnik/sacuvajKnjigu" method="post"  enctype="multipart/form-data">
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
				<label class="lbl-basic lbl-red-invert">Kategorija: </label>
				<select name="idKategorije" class="select-red">
					<c:forEach items="${kategorijeKnjiga}" var="k">
						<option value="${k.idKategorija}">${k.nazivKategorije}</option>
					</c:forEach>
				</select>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
			<label class="lbl-basic lbl-red-invert">Naziv: </label>
		 	<input type="text" class="input-f-red" name="naziv" placeholder="Unesite naziv"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Pisac: </label>
		 	<input type="text" class="input-f-red" name="pisac" placeholder="Unesite pisca"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Godina rođenja pisca: </label>
		 	<input type="date" class="input-f-red" name="datumRodjenja"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Izdavač: </label>
		 	<input type="text" class="input-f-red" name="izdavac" placeholder="Unesite izdavača"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Godina izdanja: </label>
		 	<input type="number" class="input-f-red" name="godinaIzdanja" placeholder="Unesite godinu"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Cena: </label>
		 	<input type="number" class="input-f-red" name="cena" placeholder="Unesite cenu"/>
			</div>
			<div style="display: flex; justify-content: space-between; align-items: center; width: 450px;">
		 	<label class="lbl-basic lbl-red-invert">Broj knjiga na lageru: </label>
		 	<input type="number" class="input-f-red" name="lager" placeholder="Unesite broj knjiga"/>
			</div>
		 	<p class="lbl-basic lbl-red">Kratak opis: </p><br>
		 	<textarea class="input-f-red" name="opis" rows="4" cols="50" placeholder="Unesite opis knjige..."></textarea>
			<br>
			<label class="lbl-basic lbl-red">Izaberi sliku: </label><br>
	        <input type="file" name="file" class="input-f-red" accept=".png, .jpg, .jpeg"/>
			<br><br>
		 	<input type="submit" class="btn-red" value="Sačuvaj knjigu"/><br>
	 	</form>
	 	<c:if test="${!empty uspesno}">
	 		<h2>${uspesno }</h2>
	 	</c:if>
	</div>
</body>
</html>