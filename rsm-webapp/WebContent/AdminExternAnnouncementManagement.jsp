<jsp:directive.page contentType="text/html;charset=UTF-8" />
<%@ page import="java.util.List" %>
<%@ page import="beans.entity.ActiviteExterne" %>
<!DOCTYPE HTML>
<html>
<head>
<title>Gestion Admin</title>
<meta http-equiv="Content-Type" content="text/html">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1" />
<link rel="stylesheet" href="css/main.css" />
<link rel="stylesheet" href="css/style.css" />
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
</head>
<body class="homepage">
	<div id="page-wrapper">
		<div id="header-wrapper">
			<header id="header" class="container">
				<div id="logo">
					<h1>
						<a href="Template.jsp">RSM</a>
					</h1>
				</div>
				<nav id="nav">
					<ul>
						<li><a href="TemplateAdmin.jsp">Accueil</a></li>
						<%
							if (session.getAttribute("session-admin") != null) {
						%>
						<li id="gestionAdmin">
							<a href="TemplateAdmin.jsp">Gestion Admin</a>
							<ul>
								<li id="dashboard"><a href="DashboardServlet">Tableau de bord</a></li>
								<li id="userManagement"><a href="UserManagementServlet">Gestion des utilisateurs</a></li>
								<li class="current" id="announcementManagement"><a href="AnnouncementManagementServlet">Gestion des annonce</a></li>
								<li id="externAnnouncementManagement"><a href="ExternAnnouncementManagementServlet">Gestion des activités externes</a></li>
							</ul>
						</li>
						<%
							}
						%>
						<% 	if (session.getAttribute("login") != null){%>
						<li id="deconnexion"><a href="Deconnection">Déconnexion</a></li>
						<% } %>
					</ul>
				</nav>
			</header>
		</div>
		
			<!-- Contenu partiel -->
		<div class="content">
			<div id="main-wrapper">
				<div class="container">
					<div id="content">
						<%
						List<ActiviteExterne> activiteExternes = (List<ActiviteExterne>) request.getAttribute( "activiteExternes" );
						
						if (activiteExternes.size() == 0) {
						%>
						<p>Vous n'avez pas d'activiteExterne publiée</p>
						<%
						} else {
						%>
						<h2>Liste des activités externes</h2>
						<table class="table table-striped">
						  <thead>
						    <tr>
						      <th scope="col">N°</th>
						      <th scope="col">Titre</th>
						      <th scope="col">Description</th>
						      <th scope="col">Ville</th>
						      <th scope="col">Type d'activité</th>
						    </tr>
						  </thead>
						  <tbody>
						<%
						int cpt= 1;
			            for(ActiviteExterne activiteExterne : activiteExternes){
			            	String libelle = "";
							switch(activiteExterne.getId_type_activite()){
							case 1:	libelle = "Restauration";
								break;
							case 2: libelle = "Musée";
								break;
							case 3: libelle = "Parc d'attractions";
								break;
							case 4: libelle = "Zoo";
								break;
							case 5: libelle = "Evénements";
								break;
							}
			            	%>	
						    <tr>
						      <th scope="row"><%= activiteExterne.getId_activite() %></th>
						      <td><%= activiteExterne.getTitre() %></td>
						      <td><%= activiteExterne.getDescription() %></td>
						      <td><%= activiteExterne.getVille() %></td>
						      <td><%= libelle %></td>
						    </tr>
						    <%
			            }
			            %>	
						  </tbody>
						</table>
						<%
						}
						%>
					</div>
				</div>
			</div>
		</div>
		
		<div id="footer-wrapper">
			<footer id="footer" class="container">
				<div class="row">
					<div class="3u 6u(medium) 12u$(small)">
						<section class="widget links">
							<h3>Random Stuff</h3>
							<ul class="style2">
								<li><a href="#">Etiam feugiat condimentum</a></li>
								<li><a href="#">Aliquam imperdiet suscipit odio</a></li>
								<li><a href="#">Sed porttitor cras in erat nec</a></li>
								<li><a href="#">Felis varius pellentesque potenti</a></li>
								<li><a href="#">Nullam scelerisque blandit leo</a></li>
							</ul>
						</section>
					</div>
					<div class="3u 6u$(medium) 12u$(small)">
						<section class="widget links">
							<h3>Random Stuff</h3>
							<ul class="style2">
								<li><a href="#">Etiam feugiat condimentum</a></li>
								<li><a href="#">Aliquam imperdiet suscipit odio</a></li>
								<li><a href="#">Sed porttitor cras in erat nec</a></li>
								<li><a href="#">Felis varius pellentesque potenti</a></li>
								<li><a href="#">Nullam scelerisque blandit leo</a></li>
							</ul>
						</section>
					</div>
					<div class="3u 6u(medium) 12u$(small)">
						<section class="widget links">
							<h3>Random Stuff</h3>
							<ul class="style2">
								<li><a href="#">Etiam feugiat condimentum</a></li>
								<li><a href="#">Aliquam imperdiet suscipit odio</a></li>
								<li><a href="#">Sed porttitor cras in erat nec</a></li>
								<li><a href="#">Felis varius pellentesque potenti</a></li>
								<li><a href="#">Nullam scelerisque blandit leo</a></li>
							</ul>
						</section>
					</div>
					<div class="3u 6u$(medium) 12u$(small)">
						<section class="widget contact last">
							<h3>Contact Us</h3>
							<ul>
								<li>
									<a href="#" class="icon fa-twitter">
										<span class="label">Twitter</span>
									</a>
								</li>
								<li>
									<a href="#" class="icon fa-facebook"
										><span class="label">Facebook</span>
									</a>
								</li>
								<li>
									<a href="#" class="icon fa-instagram">
										<span class="label">Instagram</span>
									</a>
								</li>
								<li>
									<a href="#" class="icon fa-dribbble">
										<span class="label">Dribbble</span>
									</a>
								</li>
								<li>
									<a href="#" class="icon fa-pinterest">
										<span class="label">Pinterest</span>
									</a>
								</li>
							</ul>
							<p>
								1234 Fictional Road<br /> Nashville, TN 00000<br /> (800)
								555-0000
							</p>
						</section>

					</div>
				</div>
				<div class="row">
					<div class="12u">
						<div id="copyright">
							<ul class="menu">
								<li>&copy; Untitled. All rights reserved</li>
								<li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
							</ul>
						</div>
					</div>
				</div>
			</footer>
		</div>
	</div>

	<!-- Script JS -->
	<script src="js/jquery.min.js"></script>
	<script src="js/jquery.dropotron.min.js"></script>
	<script src="js/skel.min.js"></script>
	<script src="js/util.js"></script>
	<script src="js/main.js"></script>
	<script src="js/templateAdmin.js"></script>

</body>
</html>