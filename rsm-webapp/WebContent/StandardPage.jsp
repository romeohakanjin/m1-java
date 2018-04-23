<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<core:import url="Header.jsp" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.Utilisateur" %>
<%	Utilisateur utilisateur = (Utilisateur) request.getAttribute("userInformations"); %>
<div class="header-container">
	<div id="content">
		<% if (request.getAttribute("erreurDelete") != null) { %>
		<p class="error-deactivate"><%= request.getAttribute("erreurDelete") %></p>
		<% } %>
	<div>
	<div>
		<ul>
			<li><a href="UpdateUserServlet?action=modifierUtilisateur" class="modifier">Modifier</a></li>
			<li><a href="DeactivateUserAccount?action=deactivateUser&userId=<%=  utilisateur.getId_utilisateur()%>">Désactiver le compte</a></li>
		</ul>
	</div>
	<div>
		<ul>
			<li><a href="StandardListAnnonce" class="annonces">Annonces</a></li>
		</ul>
	</div>
</div>
<div  class="gestion-utilisateur">
	<div class="container">
		<div id="content">
			<h2 class="info-perso">Informations personnelles</h2>
			<div id="content">
					<table class="table table-striped">
					  <thead>
					    <tr>
					      <th scope="col" style = "text-align: center">Id</th>
					      <th scope="col" style = "text-align: center">Nom</th>
					      <th scope="col" style = "text-align: center">Prenom</th>
					      <th scope="col" style = "text-align: center">Telephone</th>
					      <th scope="col" style = "text-align: center">E-mail</th>
					      <th scope="col" style = "text-align: center">Adresse</th>
					      <th scope="col" style = "text-align: center">Ville</th>
					      <th scope="col" style = "text-align: center">Code Postal</th>
					      <th scope="col" style = "text-align: center">Point bonus</th>
					    </tr>
					  </thead>
					  <tbody>
					    <tr>
					      <th scope="row" style = "text-align: center"><%= utilisateur.getId_utilisateur() %></th>
					      <td style = "text-align: center"><%= utilisateur.getNom() %></td>
					      <td style = "text-align: center"><%= utilisateur.getPrenom() %></td>
					      <td style = "text-align: center"><%= utilisateur.getMobile() %></td>
					      <td style = "text-align: center"><%= utilisateur.getMail() %></td>					     
					      <td style = "text-align: center"><%= utilisateur.getAdresse() %></td>
					      <td style = "text-align: center"><%= utilisateur.getVille() %></td>
					      <td style = "text-align: center"><%= utilisateur.getCode_postal() %></td>
					      <td style = "text-align: center"><%= utilisateur.getPoint_bonus() %></td>
					    </tr>
					  </tbody>
					</table>
					</div>            
		</div>
	</div>
</div>
<core:import url="Footer.jsp" />