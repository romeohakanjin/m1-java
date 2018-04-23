package session;

import java.io.IOException;

import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import beans.entity.Utilisateur;
import beans.session.HotelSessionBean;
import beans.session.UtilisateurSessionBean;

/**
 * Servlet implementation class Inscription
 */
@WebServlet("/Inscription")
public class Inscription extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String HOME_PAGE = "Home";
	private static final String INSCRIPTION_PAGE = "Inscription";
	private static final int ID_TYPE_UTILISATEUR_STANDARD = 3;
	private static final int ID_TYPE_UTILISATEUR_HOTELIER = 2;
	private static final int ID_TYPE_UTILISATEUR_ADMIN = 1;
	private HttpServletRequest request;
	private HttpServletResponse response;
	private HttpSession session;

	private String typeUtilisateur;
	private String identifiant;
	private String motDePasse;
	private String nom;
	private String prenom;
	private String telephone;
	private String adresse;
	private String codePostal;
	private String ville;
	private String nomHotel;
	private boolean isHotelier;

	@EJB
	UtilisateurSessionBean utilisateurSessionBean;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.request = request;
		this.response = response;

		initialiser();

		boolean isOkForm = verificationFormulaire();

		if (isOkForm) {
			boolean existingUser = utilisateurSessionBean.isExistingUser(identifiant);
			if (!existingUser) {
				Utilisateur utilisateur = new Utilisateur();
				utilisateur.setMail(identifiant);
				utilisateur.setMot_de_passe(motDePasse);
				utilisateur.setNom(nom);
				utilisateur.setPrenom(prenom);
				utilisateur.setMobile(telephone);
				utilisateur.setAdresse(adresse);
				utilisateur.setCode_postal(codePostal);
				utilisateur.setVille(ville);
				utilisateur.setActif(true);

				if (this.isHotelier) {
					utilisateur.setId_type_utilisateur(2);
					boolean existingHotel = utilisateurSessionBean.checkExistingHotel(nomHotel);
					if (existingHotel) {
						// Hotel already registred
						int idHotel = utilisateurSessionBean.getIdHotel(nomHotel);
						utilisateur.setId_hotel(idHotel);
						
						request.removeAttribute("error-form-inscription");
						utilisateurSessionBean.creerUtilisateur(utilisateur);
						httpSession(identifiant, motDePasse);
						setSession(utilisateur.getId_type_utilisateur());
						redirectionToView(HOME_PAGE);
					} else if (!existingHotel){
						// No hotel registred
						int idHotel = utilisateurSessionBean.createHotel(nomHotel);
						utilisateur.setId_hotel(idHotel);
						
						request.removeAttribute("error-form-inscription");
						utilisateurSessionBean.creerUtilisateur(utilisateur);
						httpSession(identifiant, motDePasse);
						setSession(utilisateur.getId_type_utilisateur());
						redirectionToView(HOME_PAGE);
					} else{
						setVariableToView("error-form-inscription", "H�tel inexistant");
						redirectionToView(INSCRIPTION_PAGE);
					}
				} else {
					utilisateur.setId_type_utilisateur(3);
					request.removeAttribute("error-form-inscription");
					this.setSession(utilisateur.getId_type_utilisateur());
					utilisateurSessionBean.creerUtilisateur(utilisateur);
					httpSession(identifiant, motDePasse);
					
					redirectionToView(HOME_PAGE);
				}
			} else {
				setVariableToView("error-form-inscription", "Cette adresse e-mail est d�j� utilis�e");
				redirectionToView(INSCRIPTION_PAGE);
			}
		} else {
			setVariableToView("error-form-inscription", "Informations manquantes");
			redirectionToView(INSCRIPTION_PAGE);
		}

	}

	/**
	 * V�rification des param�tres saisis dans le formulaire d'inscription
	 * 
	 * @return boolean : true si le formulaire est OK false si le formulaire est NOK
	 */
	private boolean verificationFormulaire() {
		boolean isOkForm = true;
		if (typeUtilisateur != null) {
			if (identifiant == null || "".equals(identifiant)) {
				isOkForm = false;
			}

			if (motDePasse == null || "".equals(motDePasse)) {
				isOkForm = false;
			}

			if (typeUtilisateur.equals("hotelier")) {
				this.isHotelier = true;
				
				if (nomHotel == null || "".equals(nomHotel)) {
					isOkForm = false;
				}
			}

			if (typeUtilisateur.equals("utilisateur")) {
				this.isHotelier = false;

				if (nom == null || "".equals(nom)) {
					isOkForm = false;
				}

				if (prenom == null || "".equals(prenom)) {
					isOkForm = false;
				}

				if (telephone == null || "".equals(telephone)) {
					isOkForm = false;
				}

				if (adresse == null || "".equals(adresse)) {
					isOkForm = false;
				}

				if (codePostal == null || "".equals(codePostal)) {
					isOkForm = false;
				}

				if (ville == null || "".equals(ville)) {
					isOkForm = false;
				}
			}
		}
		return isOkForm;
	}

	/**
	 * Itinaliser les variables
	 * 
	 * @throws IOException
	 */
	private void initialiser() throws IOException {
		this.session = request.getSession();
		this.isHotelier = false;
		this.response.setContentType("text/html");
		this.request.removeAttribute("error-form-inscription");

		this.typeUtilisateur = request.getParameter("selectTypeUtilisateur");
		this.identifiant = request.getParameter("identifiant");
		this.motDePasse = request.getParameter("motDePasse");
		this.nom = request.getParameter("nom");
		this.prenom = request.getParameter("prenom");
		this.telephone = request.getParameter("telephone");
		this.adresse = request.getParameter("adresse");
		this.codePostal = request.getParameter("codePostal");
		this.ville = request.getParameter("ville");
		
		this.nomHotel = request.getParameter("nomHotel");
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
	 * Set the httpSession
	 * 
	 * @param login
	 * @param password
	 */
	protected void httpSession(String login, String password) {
		session.setAttribute("login", login);
		session.setAttribute("password", password);
	}
	
	/**
	 * Set the session type
	 * @param userType
	 */
	protected void setSession(Integer userType) {
		switch (userType) {
		case ID_TYPE_UTILISATEUR_ADMIN:
			session.setAttribute("session-admin", "admin");
			break;
		case ID_TYPE_UTILISATEUR_HOTELIER:
			session.setAttribute("session-hotelier", "hotelier");
			break;
		case ID_TYPE_UTILISATEUR_STANDARD:
			session.setAttribute("session-standard", "standard");
			break;
		default:
			break;
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
		RequestDispatcher dispatcher = request.getRequestDispatcher(view + ".jsp");
		dispatcher.include(request, response);
	}
}
