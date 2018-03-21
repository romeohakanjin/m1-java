package beans.session;										  	

import java.util.List;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateful;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import beans.entity.Annonce;
import beans.entity.Hotel;
import beans.entity.Utilisateur;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class UtilisateurSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Cr�er un utilisateur
	 * 
	 * @param libelle
	 * @return
	 */
	public Boolean creerUtilisateur(Utilisateur utilisateur) {
		try {
			userTransaction.begin();
			entityManager.persist(utilisateur);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * R�cup�re tous les utilisateurs
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Utilisateur> getAllUtilisateur() {
		
		String queryString = "FROM Utilisateur";
		Query query = entityManager.createQuery(queryString);
		return (List<Utilisateur>) query.getResultList();
	}

	/**
	 * V�rifie les identifiants de la connexion d'un utilisateur
	 * 
	 * @param mail
	 *            :
	 * @param motDePasse
	 *            :
	 * 
	 * @return boolean :
	 */
	public boolean isIdentificationValid(String mail, String motDePasse) {
		
		boolean isIdentificationValid = false;
		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "' AND motDePasse='" + motDePasse + "'";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();

		if (listUser.size() != 0) {
			isIdentificationValid = true;
		}
		return isIdentificationValid;
	}

	/**
	 * V�rifie l'email d'un utilisateur
	 * 
	 * @param mail
	 * @return boolean :
	 */
	public boolean isExistingUser(String mail) {
		
		boolean isExitingUser = false;
		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "'";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();

		if (listUser.size() != 0) {
			isExitingUser = true;
		}
		return isExitingUser;
	}

	/**
	 * Check if the hotel already exist
	 * 
	 * @param nomHotel
	 */
	public boolean checkExistingHotel(String nomHotel) {
		boolean isExistingHotel = false;

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listHotel = query2.getResultList();

		if (listHotel.size() != 0) {
			isExistingHotel = true;
		}

		return isExistingHotel;
	}

	public int getIdHotel(String nomHotel) {
		int idHotel = 0;
		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listHotel = query2.getResultList();

		for (int i = 0; i < listHotel.size(); i++) {
			idHotel = (int) listHotel.get(i);
		}
		return idHotel;
	}

	public int createHotel(String nomHotel, String adresseHotel, String codePostalHotel, String villeHotel) {

		int idHotel = 0;
		Hotel hotel = new Hotel();
		hotel.setNom(nomHotel);
		hotel.setAdresse(adresseHotel);
		hotel.setCode_postal(codePostalHotel);
		hotel.setVille(villeHotel);

		try {
			userTransaction.begin();
			entityManager.persist(hotel);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listInfoHotel = query2.getResultList();

		for (int i = 0; i < listInfoHotel.size(); i++) {
			idHotel = (int) listInfoHotel.get(i);
		}
		return idHotel;
	}
	
	/**
	 * Get the id_type_utilisateur from a user
	 * @param identifiant
	 * @param motDePasse
	 * @return
	 */
	public int getIdTypeUtilisateur(String identifiant, String motDePasse) {
		
		int idTypeUtilisateur = 3;
		String query = "SELECT u.id_type_utilisateur FROM Utilisateur u WHERE mail = '" + identifiant + "' AND motDePasse='" + motDePasse + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();

		for (int i = 0; i < listUser.size(); i++) {
			idTypeUtilisateur = (int) listUser.get(i);
		}
		return idTypeUtilisateur;
	}
	/**
	 * 
	 * @param idUser
	 * @return
	 */
	public boolean isMatchingIdUser(int id_utilisateur) {
		
		boolean isMatchingId = false;
		String query = "SELECT id_utilisateur FROM Utilisateur WHERE id_utilisateur = '" + id_utilisateur + "'";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List utilisateurs = query2.getResultList();
		if (utilisateurs.size() != 0) {
			isMatchingId = true;
		}
		return isMatchingId;
	}
	
	/**
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Utilisateur> getUserInformation(int idUtilisateur){
		
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '"+idUtilisateur+"' ";
		Query query = entityManager.createQuery(queryString);
		@SuppressWarnings("rawtypes")
		List listUser = query.getResultList();
		return listUser; 
	}

	/**
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Annonce> getAllAnnonceUtilisateur() {
		String query = "FROM Annonce";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listAnnonce = query2.getResultList();
		return listAnnonce;
	}
	
	/**
	 * 
	 * @param identifiant
	 * @return
	 */
	public int getIdUtilisateur(String identifiant) {
		
		int idUtilisateur = 0;
		String query = "SELECT u.id_utilisateur FROM Utilisateur AS u " + "WHERE u.mail = '" + identifiant + "'";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listUser = query2.getResultList();
		idUtilisateur = (int) listUser.get(0);
		return idUtilisateur;
	}
	
	/**
	 * 
	 * @param identifiant
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Utilisateur getUtilisateur(int idUser) {
		
		Utilisateur utilisateur = new Utilisateur();
		String query = "FROM Utilisateur AS u " + "WHERE u.id_utilisateur = '" + idUser + "'";
		Query query2 = entityManager.createQuery(query);
		List listUser = query2.getResultList();
		for (int i = 0; i < listUser.size(); i++) {
			utilisateur = (Utilisateur) listUser.get(0);
		}
		System.out.println(utilisateur);
		return utilisateur;
	}
	
	/**
	 * 
	 * @param utilisateur
	 */
	public void updateUtilisateur(Utilisateur utilisateur) {
		
		int id_utilisateur = utilisateur.getId_utilisateur();
		String nom = utilisateur.getNom();
		String prenom = utilisateur.getPrenom();
		String mail = utilisateur.getMail();
		String mobile = utilisateur.getMobile();
		String adresse = utilisateur.getAdresse();
		String ville = utilisateur.getVille();
		String codePostal = utilisateur.getCode_postal();
		try {
			userTransaction.begin();
			String query = "UPDATE Utilisateur AS u " +
													"SET nom = '" + nom + "', " + 
													"prenom = '" + prenom + "', " +
													"mail = '" + mail + "', " +
													"mobile = '" + mobile + "', " + 
													"adresse = '" + adresse + "', " +
													"ville = '" + ville + "', " + 
													"code_postal = '" + codePostal + "' " +
													"WHERE u.id_utilisateur = '" + id_utilisateur + "' ";

			Query query1 = entityManager.createQuery(query);
			query1.executeUpdate();
			userTransaction.commit();
		} catch (SecurityException | IllegalStateException | NotSupportedException | SystemException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException exception) {
			exception.printStackTrace();
		}
	}
}
