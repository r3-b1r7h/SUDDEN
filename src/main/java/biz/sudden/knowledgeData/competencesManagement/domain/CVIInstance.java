package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ICVIInstance;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

@SuppressWarnings("serial")
@Entity
@Table(name = "CVI_INSTANCES")
public class CVIInstance extends CMInstanceBaseClass implements ICVIInstance,
		Cloneable {

	protected CVI cvi;
	protected List<TickInstance> ticks = new ArrayList<TickInstance>();

	@Transient
	public void calculateValue() {
		setAutoCalcValue(0);
		setValue("0");
		if (cvi.getNumberOfQuantifiableTicks() > 0) {
			if (cvi.isMultipleChoice()) {
				if (this.cvi.multipleChoiceValues != "") {
					int numberOfChecksChecked = getNumberOfChecksChecked();
					List<Float> tokens = new ArrayList<Float>();
					StringTokenizer st = new StringTokenizer(this.cvi
							.getMultipleChoiceValues(), ";");
					while (st.hasMoreTokens()) {
						tokens.add(Float.valueOf(st.nextToken()));
					}
					if (tokens.size() > 0) {
						if (tokens.size() <= numberOfChecksChecked) {
							setAutoCalcValue(tokens.get(tokens.size() - 1)
									.floatValue());
						} else {
							setAutoCalcValue(tokens.get(numberOfChecksChecked)
									.floatValue());
						}
					}
				} else {
					setAutoCalcValue(getValueOfChecksChecked());
				}
				setValue(Float.toString((this.autoCalcValue / (this.cvi
						.getMaxRange() - this.cvi.getMinRange())) * 10));
			} else { // Radio Buttons and single quantifiable answers
				Iterator<TickInstance> iterator = getQuantifiableTickInstances()
						.iterator();
				float cviValue = 0;
				while (iterator.hasNext()) {
					TickInstance tickInstance = iterator.next();
					if (tickInstance.autoCalcValue > cviValue) {
						cviValue = tickInstance.autoCalcValue;
					}
					setAutoCalcValue(cviValue);
					setValue(Float
							.toString((cviValue / (this.cvi.getMaxRange() - this.cvi
									.getMinRange())) * 10));
				}
			}
		}
	}

	@Override
	public ICVIInstance clone() {
		return null;
	}

	@Override
	public float getAutoCalcValue() {
		return autoCalcValue;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public CVI getCvi() {
		return cvi;
	}

	@Override
	public Date getDate() {
		return date;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	@Override
	public Long getId() {
		return Id;
	}

	@Transient
	public int getNumberOfChecksChecked() {
		int numberOfChecksChecked = 0;

		Iterator<TickInstance> iterator = ticks.iterator();
		while (iterator.hasNext()) {
			TickInstance tick = iterator.next();
			if (tick.tick.isQuantifiable()
					&& tick.tick.type == Tick.TICK_TYPE_CHECK
					&& tick.value == "true") {
				numberOfChecksChecked++;
			}
		}

		return numberOfChecksChecked;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST,
			CascadeType.REFRESH })
	public Organization getOrganization() {
		return organization;
	}

	@Transient
	public List<TickInstance> getQuantifiableTickInstances() {
		List<TickInstance> quantifiableTickInstances = new ArrayList<TickInstance>();

		Iterator<TickInstance> iterator = ticks.iterator();
		while (iterator.hasNext()) {
			TickInstance tick = iterator.next();
			if (tick.tick.isQuantifiable()) {
				quantifiableTickInstances.add(tick);
			}
		}

		return quantifiableTickInstances;
	}

	@Override
	@LazyCollection(LazyCollectionOption.FALSE)
	@OneToMany(cascade = CascadeType.ALL)
	public List<TickInstance> getTicks() {
		return ticks;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Transient
	public float getValueOfChecksChecked() {
		float valueOfChecksChecked = 0;

		Iterator<TickInstance> iterator = ticks.iterator();
		while (iterator.hasNext()) {
			TickInstance tick = iterator.next();
			if (tick.tick.isQuantifiable()
					&& tick.tick.type == Tick.TICK_TYPE_CHECK
					&& tick.value == "true") {
				valueOfChecksChecked = valueOfChecksChecked
						+ tick.getAutoCalcValue();
			}
		}

		return valueOfChecksChecked;
	}

	@Transient
	public int getValueOfRadioSelected() {
		int valueOfRadioSelected = 0;

		Iterator<TickInstance> iterator = ticks.iterator();
		while (iterator.hasNext()) {
			TickInstance tick = iterator.next();
			if (tick.tick.isQuantifiable()
					&& tick.tick.type == Tick.TICK_TYPE_RADIO
					&& tick.value == "SELECTED") {
				valueOfRadioSelected = tick.tick.getAutoValue();
			}
		}

		return valueOfRadioSelected;
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
	public void setCvi(CVI cvi) {
		this.cvi = cvi;
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
	public void setTicks(List<TickInstance> ticks) {
		this.ticks = ticks;
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
