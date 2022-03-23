<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Stranica za logovanje</title>

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
			
                <h2>Prijava</h2><br><br>
                <form action="${pageContext.request.contextPath}/login" method="POST">
                    <label class=" ">Korisničko ime</label><br><br>
                    <input type="text" name="username" class="input-f-red-out" placeholder="korisnickoIme" pattern="[A-Za-z\d]+" required> <br><br><br>
                    <label class=" ">Šifra</label><br><br>
                    <input type="password" name="password" class="input-f-red-out" placeholder="*********" required> <br><br>
                    <button class="btn-red" type="submit">Prijavi se</button><br><br><br>
                    
                    <span>Nemate nalog? Registrujte se besplatno!</span> <br><br>
                    <a href="/IzdavackaKuca/registracija.jsp" class="btn-red"><small>Registruj se</small></a>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                </form>
            </div>
	</div>
</body>
</html>