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
	 * D�sactive un utilisateur
	 * @param user
	 * @return
	 */
	public Boolean deleteUser(Utilisateur user) {
		try {
			Integer userId = user.getId_utilisateur();
			userTransaction.begin();
			String queryString =	"UPDATE Utilisateur AS u "
					+ "SET actif = false "
					+ "WHERE u.id_utilisateur = '" + userId + "' ";
			Query query = entityManager.createQuery(queryString);
			query.executeUpdate();
			userTransaction.commit();
			return true;
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException | HeuristicMixedException | HeuristicRollbackException e) {
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
		String queryString = "FROM Utilisateur WHERE actif = TRUE";
		Query query = entityManager.createQuery(queryString);
		return (List<Utilisateur>) query.getResultList();
	}
	
	/**
	 * R�cup�re tous les utilisateurs avec leurs types d'utilisateurs
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object> getAllUtilisateurWithUserType() {
		String queryString = "FROM Utilisateur AS u "
				+ "JOIN TypeUtilisateur AS tu ON u.id_type_utilisateur = tu.id_type_utilisateur "
				+ "WHERE u.actif = TRUE";
		Query query = entityManager.createQuery(queryString);
		return (List<Object>) query.getResultList();
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
		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "' AND mot_de_passe='" + motDePasse
				+ "' AND actif = true";
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

		String query = "SELECT u.mail FROM Utilisateur u WHERE mail = '" + mail + "' "
				+ "AND u.actif = TRUE ";
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
	//TODO : deplacer dans hotelSessionBean
	public boolean checkExistingHotel(String nomHotel) {
		boolean isExistingHotel = false;

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom_hotel = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listHotel = query2.getResultList();

		if (listHotel.size() != 0) {
			isExistingHotel = true;
		}

		return isExistingHotel;
	}
	
	/**
	 * R�cupere l'id d'un hotel gr�ce � son nom
	 * @param nomHotel
	 * @return
	 */
	//TODO : deplacer dans hotelSessionBean
	public int getIdHotel(String nomHotel) {
		int idHotel = 0;
		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom_hotel = '" + nomHotel + "'";
		Query query2 = entityManager.createQuery(query);

		@SuppressWarnings("rawtypes")
		List listHotel = query2.getResultList();

		for (int i = 0; i < listHotel.size(); i++) {
			idHotel = (int) listHotel.get(i);
		}
		return idHotel;
	}

	/**
	 * Cr�� un hotel
	 * @param nomHotel
	 * @return
	 */
	//TODO : deplacer dans hotelSessionBean
	public int createHotel(String nomHotel) {

		int idHotel = 0;
		Hotel hotel = new Hotel();
		hotel.setNom_hotel(nomHotel);

		try {
			userTransaction.begin();
			entityManager.persist(hotel);
			userTransaction.commit();
		} catch (NotSupportedException | SystemException | SecurityException | IllegalStateException | RollbackException
				| HeuristicMixedException | HeuristicRollbackException e) {
			e.printStackTrace();
		}

		String query = "SELECT h.id_hotel FROM Hotel AS h WHERE id_hotel= h.id_hotel AND h.nom_hotel = '" + nomHotel + "'";
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
	//TODO : deplacer dans typeUtilisateurSessionBean
	public int getIdTypeUtilisateur(String identifiant, String motDePasse) {
		
		int idTypeUtilisateur = 3;
		String query = "SELECT u.id_type_utilisateur FROM Utilisateur u WHERE mail = '" + identifiant + "' AND mot_de_passe='" + motDePasse
				+ "'";
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
	public boolean isExistingUserId(int id_utilisateur) {
		
		boolean isExistingUserId = false;
		String query = "SELECT id_utilisateur FROM Utilisateur WHERE id_utilisateur = '" + id_utilisateur + "'";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List utilisateurs = query2.getResultList();
		if (utilisateurs.size() != 0) {
			isExistingUserId = true;
		}
		return isExistingUserId;
	}
	
	/**
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	public Utilisateur getUserInformation(int idUtilisateur){
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '"+idUtilisateur+"' ";
		Query query = entityManager.createQuery(queryString);
		Utilisateur utilisateur = (Utilisateur) query.getSingleResult();
		return utilisateur;
	}
	
	/**
	 * R�cup�re l'id de l'utilisateur
	 * 
	@SuppressWarnings("unchecked")
	//TODO : REVOIR => PAS CORRECTE ON NE RENVOIE PAS UNE LISTE SI ON RECUPERE QU'UN OBJET
	public List<Utilisateur> getUserInformation(int idUtilisateur){
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '"+idUtilisateur+"' ";
		Query query = entityManager.createQuery(queryString);
		List<Utilisateur> listUser = (List<Utilisateur>) query.getResultList();
		return listUser; 
	}
	
	/**
	 * R�cup�re un utilisateur gr�ce � son id
	 * @param idUtilisateur
	 * @return
	 */
	public Utilisateur getUserInformationById(int idUtilisateur){
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '"+idUtilisateur+"' ";
		Query query = entityManager.createQuery(queryString);
		return (Utilisateur) query.getResultList().get(0);
	}

	/**
	 * 
	 * @param idUtilisateur
	 * @return
	 */
	//TODO : changer le nom, ajouter le commentaire et deplacer dans annonceSessionBean
	public List<Annonce> getAllAnnonceUtilisateur() {
		String query = "FROM Annonce";
		Query query2 = entityManager.createQuery(query);
		@SuppressWarnings("rawtypes")
		List listAnnonce = query2.getResultList();
		return listAnnonce;
	}
	
	/**
	 * R�cup�re  l'id d'un utilisateur gr�ce � son adresse mail
	 * @param identifiant
	 * @return
	 */
	public int getIdUtilisateur(String identifiant) {
		int idUtilisateur = 0;
		String query = "SELECT u.id_utilisateur FROM Utilisateur AS u " + "WHERE u.mail = '" + identifiant + "' AND actif = TRUE";
		Query query2 = entityManager.createQuery(query);
		List listUser = query2.getResultList();
		idUtilisateur = (int) listUser.get(0);
		return idUtilisateur;
	}
	
	/**
	 * Fait un select * sur la table Utilisateur
	 * 
	 * @param identifiant
	 * @return
	 */

	public Utilisateur getUtilisateur(int idUser) {
		
		Utilisateur utilisateur = new Utilisateur();
		String query = "FROM Utilisateur AS u WHERE u.id_utilisateur = '" + idUser + "' AND actif = TRUE";
		Query query2 = entityManager.createQuery(query);

		utilisateur = (Utilisateur) query2.getSingleResult();

		return utilisateur;
	}
	
	/**
	 * Update un utilisateur
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
	
	/**
	 * R�cup�re le nombre d'utilisateur
	 * regroup� par type d'utilisateur
	 * @return
	 */
	public List<Object[]> getNbUserGroupByUserType() {
		String queryString = "SELECT COUNT(*), tu.libelle "
				+ "FROM Utilisateur AS u "
				+ "JOIN TypeUtilisateur AS tu ON u.id_type_utilisateur = tu.id_type_utilisateur "
				+ "GROUP BY tu.libelle";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * R�cup�re un utilisateur avec son id
	 * @param userId
	 * @return
	 */
	public Utilisateur getUserById(Integer userId) {
		String queryString = "FROM Utilisateur AS a " + "WHERE a.id_utilisateur = '" + userId + "'";
		Query query = entityManager.createQuery(queryString);
		Utilisateur user = null;
		for (int i = 0; i < query.getResultList().size(); i++) {
			user = (Utilisateur) query.getResultList().get(i);
		}
		return user;
	}
}
