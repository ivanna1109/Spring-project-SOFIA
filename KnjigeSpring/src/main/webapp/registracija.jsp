<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Registrovanje korisnika</title>
<style type="text/css">
    <%@include file="css/myCSS.css" %>
</style>
<style type="text/css">
    <%@include file="css/index.css" %>
</style>
</head>
<body>
	<div class="page">
			<div class="box bg-color">
				
				<div class="logo">
					SOFIA
				</div>
				<br>
                <h2>Registruj se</h2>
                <br>
                <form action="/IzdavackaKuca/auth/registracija" method="POST">
                    <label class=" ">
                	<c:choose>
                		<c:when test="${!empty porukaReg}">
                			${porukaReg}
                		</c:when>
                		<c:otherwise>Korisničko ime</c:otherwise>
                	</c:choose></label><br>
                    <input type="text" name="username" class="input-f-red-out" placeholder="marko_mar" pattern="[A-Za-z\d]+" required> <br><br>
                    <label class=" ">Šifra</label><br>
                    <input type="password" name="password" class="input-f-red-out" placeholder="******" required> <br><br>
                    <label class=" ">Ime</label><br>
                    <input type="text" name="name" class="input-f-red-out" placeholder="Marko" pattern="[A-Za-z]+" required> <br><br>
                    <label class=" ">Prezime</label><br>
                    <input type="text" name="surname" class="input-f-red-out" placeholder="Markovic" pattern="[A-Za-z]+" required> <br><br>
                    <label class=" ">Godine</label><br>
                    <input type="number" name="ages" class="input-f-red-out" placeholder="20" required> <br><br>
                    <label class=" ">Adresa</label><br>
                    <input type="text" name="address" class="input-f-red-out" placeholder="Zeleznicka 8" required> <br><br><br>
                    <button class="btn-red" type="submit">Registruj se</button><br><br>
                </form>
            </div>
	</div>
</body>
</html>