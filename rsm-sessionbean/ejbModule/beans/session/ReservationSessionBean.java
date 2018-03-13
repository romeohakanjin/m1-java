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

import beans.entity.Reservation;

/**
 * @author SLI
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class ReservationSessionBean {

	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;

	@Resource
	UserTransaction userTransaction;

	/**
	 * Cr�er une r�servation
	 * @param reservation
	 * @return
	 */
	public Boolean creerReservation(Reservation reservation) {
		try {
			userTransaction.begin();
			entityManager.persist(reservation);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * R�cup�re toutes les r�servations
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getAllReservation(){
		String queryString = "FROM Reservation";
		Query query = entityManager.createQuery(queryString);
		return (List<Reservation>) query.getResultList();
	}
	
	/**
	 * R�cup�re le nombre de r�servation
	 * regroup� par �tat de r�servation
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Object[]> getNbReservationGroupByReservationState(){
		String queryString = "SELECT COUNT(*), er.libelle "
				+ "FROM Reservation AS r "
				+ "JOIN EtatReservation AS er ON er.id_etat_reservation = r.id_etat_reservation "
				+ "GROUP BY er.libelle";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * R�cup�re les r�servations ayant pour statut A venir ou En cours
	 * li�es � une annonce
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getReservationByAnnonceId(Integer annonceId){
		String queryString = "FROM Reservation "
				+ "WHERE id_annonce = '"+annonceId+"' ";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * R�cup�re les r�servations faites par un utilisateur
	 * ayant pour statut En attente, A venir ou En cours
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getReservationByUserId(Integer userId){
		String queryString = "FROM Reservation "
				+ "WHERE id_utilisateur = '"+userId+"' "
				+ "AND id_statut_reservation IN (1, 2, 3)";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
	
	/**
	 * R�cup�re les r�servations faites par un utilisateur
	 * ayant pour Termin�e
	 * @param userId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Reservation> getFinishedReservationByUserId(Integer userId){
		String queryString = "FROM Reservation "
				+ "WHERE id_utilisateur = '"+userId+"' "
				+ "AND id_statut_reservation IN (1, 2, 3)";
		Query query = entityManager.createQuery(queryString);
		return query.getResultList();
	}
}