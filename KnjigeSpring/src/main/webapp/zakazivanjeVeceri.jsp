<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Zakazaivanje večeri</title>
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
		<form action="/IzdavackaKuca/radnik/zakaziVece" method="post">
			<h1>Zakazivanje književne večeri</h1><br>
			<label class="lbl-basic lbl-red-invert">Izaberite knjigu: </label>
			<c:if test="${!empty sveKnjige}">
			 	<select name="izabranaKnjiga" class="select-red">
					<c:forEach items="${sveKnjige}" var="k">
						<option value="${k.idKnjiga}">${k.naziv}</option>
					</c:forEach>
				</select><br>
			</c:if>
			<label class="lbl-basic lbl-red-invert">Izaberite datum: </label>
			<input type="date" class="input-f-red" name="datum"/><br>
			<label class="lbl-basic lbl-red-invert">Mesto održavanja: </label>
			<input type="text" class="input-f-red" name="mesto" placeholder="Unesite mesto"/><br>
			<label class="lbl-basic lbl-red-invert">Maksimalan broj posetilaca: </label>
			<input type="number" class="input-f-red" name="maxPosetilaca" placeholder="20"/><br><br>
			<input type="submit" class="btn-red" value="Zakaži"/>
		</form>
		<c:if test="${!empty porukaVece}">
			<h2>${porukaVece }</h2>
		</c:if>
	</div>
</body>
</html>