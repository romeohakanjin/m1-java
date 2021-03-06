package beans.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author RHA
 *
 */
@Entity
@Table(name = "etat_reservation")
public class EtatReservation {
	private Integer id_etat_reservation;
	private String libelle;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId_etat_reservation() {
		return id_etat_reservation;
	}

	public void setId_etat_reservation(Integer id_etat_reservation) {
		this.id_etat_reservation = id_etat_reservation;
	}

	public String getLibelle() {
		return libelle;
	}

	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
}
