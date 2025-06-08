package biz.sudden.designCoordination.collaborativePlanning.domain;

import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 * 
 * A Communication needs at least two "Actors" which talk to each other, further
 * the Communication is stored into "CommunicationHistory" (together with a
 * "CommunicationItem")
 * 
 * @author Thomas Feiner
 * 
 */

@Entity
public class CommunicationRating extends Communication {

	private int rating = 0;

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	@Transient
	public String getFirstStar() {
		if (rating >= 1) {
			return "voll";
		} else {
			return "leer";
		}
	}

	@Transient
	public String getSecondStar() {
		if (rating >= 2) {
			return "voll";
		} else {
			return "leer";
		}
	}

	@Transient
	public String getThirdStar() {
		if (rating >= 3) {
			return "voll";
		} else {
			return "leer";
		}
	}

	@Transient
	public String getFourthStar() {
		if (rating >= 4) {
			return "voll";
		} else {
			return "leer";
		}
	}

	@Transient
	public String getFifthStar() {
		if (rating >= 5) {
			return "voll";
		} else {
			return "leer";
		}
	}

}