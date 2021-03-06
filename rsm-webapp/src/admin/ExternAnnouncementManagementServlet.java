package admin;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import beans.entity.ActiviteExterne;
import beans.session.ActiviteExterneSessionBean;

/**
 * Servlet implementation class AnnouncementManagementServlet
 */
@WebServlet("/ExternAnnouncementManagementServlet")
public class ExternAnnouncementManagementServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String EXTERN_ANNOUNCEMENT_LIST = "AdminExternAnnouncementManagement";
	private static final String EXTERN_ANNOUNCEMENT_VIEW = "ExternAnnouncement";
	private static final String DELETE_ANNOUNCEMENT = "Delete";
	private static final String EDIT_ANNOUNCEMENT = "Edit";
	private HttpServletRequest request;
	private HttpServletResponse response;
	private String action;
	private String externalActivityId;

	@EJB
	ActiviteExterneSessionBean activiteExterneSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialize();
		getAllExternAnnouncement();
		redirectionToView(EXTERN_ANNOUNCEMENT_LIST);

		switch (this.action) {
		case DELETE_ANNOUNCEMENT:
			if (externalActivityId != null || !externalActivityId.equals("")) {
				Integer idActiviteExterne = Integer.valueOf(externalActivityId);
				ActiviteExterne activityToDelete = activiteExterneSessionBean.getActiviteExterne(idActiviteExterne);
				if (activityToDelete != null) {
					activiteExterneSessionBean.deleteActiviteExterne(idActiviteExterne);
					setVariableToView("alert-success", "Activit� externe supprim�e");
					redirectionToView(EXTERN_ANNOUNCEMENT_LIST);
				}
			}
			break;
		case EDIT_ANNOUNCEMENT:
			if (externalActivityId != null || !externalActivityId.equals("")) {
				int idActiviteExterne = Integer.valueOf(externalActivityId);
				ActiviteExterne activityToEdit = activiteExterneSessionBean.getActiviteExterne(idActiviteExterne);
				request.setAttribute("externAnnouncementEdited", activityToEdit);
				redirectionToView(EXTERN_ANNOUNCEMENT_VIEW);
			}
			break;
		}
	}

	/**
	 * Initialize the values
	 * 
	 * @throws IOException
	 */
	private void initialize() throws IOException {
		this.response.setContentType("text/html");
		this.action = request.getParameter("action");
		if (this.action == null) {
			this.action = "";
		} else {
			this.externalActivityId = request.getParameter("annonceId");
		}
	}

	/**
	 * Redirection to a view
	 * 
	 * @param String
	 *            : the view name
	 * @throws ServletException
	 * @throws IOException
	 */
	private void redirectionToView(String view) throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher("/" + view + ".jsp").forward(request, response);

	}
	
	/**
	 * Feed request attribute
	 * 
	 * @param variable
	 * @param message
	 */
	private void setVariableToView(String variable, String message) {
		request.setAttribute(variable, message);
	}
	
	/**
	 * get all the external activities
	 */
	private void getAllExternAnnouncement() {
		List<ActiviteExterne> activiteExternes = activiteExterneSessionBean.getAllActiviteesExterne();
		this.request.setAttribute("activiteExternes", activiteExternes);
	}
}
