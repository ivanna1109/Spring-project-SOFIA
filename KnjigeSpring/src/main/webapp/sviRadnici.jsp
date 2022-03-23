<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Prikaz radnika</title>
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
		<c:if test="${!empty radnici}">
			<h1>Prikaz svih radnika</h1><br>
			<table class="table-basic table-red">
				<tr>
					<th>Ime i prezime</th>
					<th>Godine</th>
					<th>Adresa</th>
					<th>Izmeni</th>
					<th>Obriši</th>
					
				</tr>
				<c:forEach items="${radnici}" var="r">
					<tr>
						<td>${r.ime} ${r.prezime}</td>
						<td>${r.brojGodina}</td>
						<td>${r.adresa}</td>
						<td>								
							<form action="/IzdavackaKuca/administrator/redirekcija" method="get">
								<input type="hidden" name="idRadnik" value="${r.idKorisnik}"/>
								<input type="submit" class="btn-red" value="Izmeni informacije"/>
							</form>
						</td>
						<td>								
							<form action="/IzdavackaKuca/administrator/izbrisiRadnika" method="post">
								<input type="hidden" name="idRadnik" value="${r.idKorisnik}"/>
								<input type="submit" class="btn-red" value="Obriši radnika"/>
							</form>
						</td>
					</tr>
				</c:forEach>
			</table><br><br>
		</c:if>
		<form action="/IzdavackaKuca/dodajRadnika.jsp">
			<br><br><input type="submit" class="btn-red" value="Zaposli radnika"/>
		</form>	
	</div>
</body>
</html>