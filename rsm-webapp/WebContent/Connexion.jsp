<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<h2>Connexion</h2>
			<form method="post" action="Connection">
				<label>Identifiant</label> <input type='text' name='identifiant' />
				<label>Mot de passe</label> <input type='password' name='motDePasse' />
				<input type='submit' name='submit' value="submit">
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />