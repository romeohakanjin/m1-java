<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="core"%>
<%@ page import="beans.entity.Annonce"%>
<%@ page import="java.util.List"%>
<%@ page import="beans.entity.Commentaire"%>
<core:import url="Header.jsp" />

<div id="main-wrapper">
	<div class="container">
		<div id="content">
			<%
				// 				if (request.getAttribute("annonceDetails") != null) {
				// 					Annonce annonce = (Annonce) request.getAttribute("annonceDetails");
			%>
			<form method="get" action="ReservationConfirmationServlet">
				<h3>Confirmer votre r�servation</h3>
				<label>Date d�but</label>
				<p><%=session.getAttribute("reservationDateDebut")%></p>
				<label>Date fin</label>
				<p><%=session.getAttribute("reservationDateFin")%></p>
				<label>Nombre de jour</label>
				<p><%=session.getAttribute("reservationNumberOfDays")%></p>
				<label>Prix</label>
				<p><%=session.getAttribute("reservationPrice")%></p>

				<%
					// 					if (session.getAttribute("session-admin") == null && session.getAttribute("session-hotelier") == null) {
				%>
				<input type="submit" name="submitButtonReservationValidation"
					value="Confirmer" />
				<%
					// 					}
					// 					}
				%>
			</form>
		</div>
	</div>
</div>

<core:import url="Footer.jsp" />