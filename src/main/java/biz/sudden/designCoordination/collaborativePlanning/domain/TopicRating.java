package biz.sudden.designCoordination.collaborativePlanning.domain;

import java.text.DecimalFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;

@Entity
public class TopicRating extends Topic {

	// private List<CommunicationRating> communicationRating = new
	// ArrayList<CommunicationRating>();
	private String description;

	@Column(length = 2048)
	// Postgre Bug fixed, 1024 was too small
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		System.out.println(description);
		this.description = description;
	}

	//
	// @Transient
	// public List<CommunicationRating> getCommunicationRating() {
	// return communicationRating;
	// }
	//
	// public void setCommunicationRating(List<CommunicationRating>
	// communicationRating) {
	// this.communicationRating = communicationRating;
	// }

	@Transient
	public String getAverageRatings() {

		double sumRatings = 0l;

		for (Communication rating : getCommunications()) {
			sumRatings += ((CommunicationRating) rating).getRating();
		}

		double divisor = getCommunications().size();

		if (divisor != 0) {
			Double result = sumRatings / divisor;
			DecimalFormat f = new DecimalFormat("#0.00");
			return f.format(result);
		} else
			return "";

	}

}
