package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ITickInstance;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
@Entity
@Table(name = "TICK_INSTANCES")
public class TickInstance extends CMInstanceBaseClass implements ITickInstance,
		Cloneable {

	protected Tick tick;

	@Transient
	public void calculateValue() {
		setAutoCalcValue(0);
		if (this.tick.isQuantifiable()) {
			if (this.tick.autoValue == Tick.TICK_AUTOVALUE_USER) {
				switch (this.tick.type) {
				case Tick.TICK_TYPE_CHECK:
					if (this.value == "true") {
						setAutoCalcValue(this.tick.getTNumValue());
					}
					break;
				case Tick.TICK_TYPE_RADIO:
					if (this.value == "SELECTED") {
						setAutoCalcValue(this.tick.getTNumValue());
					}
					break;
				case Tick.TICK_TYPE_NUMBER:
					if (this.value != null) {
						try {
							setAutoCalcValue(Float.valueOf(this.value));
						} catch (NumberFormatException e) {

						}
					}
					break;
				}
			} else {
				int c0 = 0;
				c0++;
				switch (this.tick.autoValue) {
				case Tick.TICK_AUTOVALUE_MACHINERY_TYPES:
				case Tick.TICK_AUTOVALUE_MATERIAL_NUMBER:
				case Tick.TICK_AUTOVALUE_MATERIAL_PROCESSING:
				case Tick.TICK_AUTOVALUE_PARTS_NUMBER:
					this.value = String.valueOf(organization
							.getCompanySMInfo(this.tick.autoValue));
					setAutoCalcValue(organization
							.getCompanySMInfo(this.tick.autoValue));
					break;
				}
			}
		}
	}

	@Override
	public ITickInstance clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return Id;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Organization getOrganization() {
		return organization;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Tick getTick() {
		return tick;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public float getWeight() {
		return weight;
	}

	@Override
	public void setAutoCalcValue(float autoCalcValue) {
		this.autoCalcValue = autoCalcValue;
	}

	@Override
	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public void setId(Long id) {
		Id = id;
	}

	@Override
	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	@Override
	public void setTick(Tick tick) {
		this.tick = tick;
	}

	@Override
	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		String aux = "";

		return aux;
	}

}
