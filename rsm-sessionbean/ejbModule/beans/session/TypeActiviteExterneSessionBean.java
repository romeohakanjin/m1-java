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

import beans.entity.TypeActiviteExterne;
import beans.entity.TypeUtilisateur;

/**
 * 
 * @author SLI
 *
 */
@Stateful
@LocalBean
@TransactionManagement(TransactionManagementType.BEAN)
public class TypeActiviteExterneSessionBean {
	
	@PersistenceContext(unitName = "RsmProjectService")
	EntityManager entityManager;
	
	@Resource
	UserTransaction userTransaction;
	
	/**
	 * Cr�er un type d'activit� externe
	 * @param libelle
	 * @return
	 */
	public Boolean creerTypeActiviteExterne(String libelle) {
		try {
			userTransaction.begin();
			TypeActiviteExterne typeActivite = new TypeActiviteExterne();
			typeActivite.setLibelle(libelle);
			entityManager.persist(typeActivite);
			userTransaction.commit();
			return true;
		} catch (SecurityException | IllegalStateException | RollbackException | HeuristicMixedException
				| HeuristicRollbackException | SystemException | NotSupportedException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * R�cup�re les types d'activit�es externes
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<TypeUtilisateur> getTypeActiviteesExterne(){
		String queryString = "FROM TypeActiviteExterne";
		Query query = entityManager.createQuery(queryString);
		return (List<TypeUtilisateur>) query.getResultList();
	}
}
