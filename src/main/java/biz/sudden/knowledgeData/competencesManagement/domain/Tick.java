package biz.sudden.knowledgeData.competencesManagement.domain;

import java.util.Hashtable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import biz.sudden.knowledgeData.competencesManagement.domain.interfaces.ITick;

@SuppressWarnings("serial")
@Entity
@Table(name = "TICKS")
public class Tick extends CMBaseClass implements ITick, Cloneable {

	public final static int TICK_QUANTIFIABLE_NO = 1;
	public final static int TICK_QUANTIFIABLE_YES = 0;
	public static Hashtable<Integer, String> TICK_QUANTIFIABLE_STRINGS;

	public final static int TICK_TYPE_NUMBER = 2;
	public final static int TICK_TYPE_STRING = 3;
	public final static int TICK_TYPE_CHECK = 4;
	public final static int TICK_TYPE_RADIO = 5;
	public static Hashtable<Integer, String> TICK_TYPE_STRINGS;

	public final static int TICK_AUTOVALUE_USER = 6;
	public final static int TICK_AUTOVALUE_PARTS_NUMBER = 7;
	public final static int TICK_AUTOVALUE_MATERIAL_PROCESSING = 8;
	public final static int TICK_AUTOVALUE_MATERIAL_NUMBER = 9;
	public final static int TICK_AUTOVALUE_MACHINERY_TYPES = 10;
	public static Hashtable<Integer, String> TICK_AUTOVALUE_STRINGS;

	static {
		TICK_QUANTIFIABLE_STRINGS = new Hashtable<Integer, String>();
		TICK_QUANTIFIABLE_STRINGS.put(TICK_QUANTIFIABLE_YES,
				"Quantifiable Tick");
		TICK_QUANTIFIABLE_STRINGS.put(TICK_QUANTIFIABLE_NO,
				"Non Quantifiable Tick");

		TICK_TYPE_STRINGS = new Hashtable<Integer, String>();
		TICK_TYPE_STRINGS.put(TICK_TYPE_NUMBER, "NUMBER");
		TICK_TYPE_STRINGS.put(TICK_TYPE_STRING, "STRING");
		TICK_TYPE_STRINGS.put(TICK_TYPE_CHECK, "CHECK");
		TICK_TYPE_STRINGS.put(TICK_TYPE_RADIO, "RADIO");

		TICK_AUTOVALUE_STRINGS = new Hashtable<Integer, String>();
		TICK_AUTOVALUE_STRINGS.put(TICK_AUTOVALUE_USER, "USER");
		TICK_AUTOVALUE_STRINGS.put(TICK_AUTOVALUE_PARTS_NUMBER, "PARTS NUMBER");
		TICK_AUTOVALUE_STRINGS.put(TICK_AUTOVALUE_MATERIAL_PROCESSING,
				"MATERIAL PROCESSING");
		TICK_AUTOVALUE_STRINGS.put(TICK_AUTOVALUE_MATERIAL_NUMBER,
				"MATERIAL NUMBER");
		TICK_AUTOVALUE_STRINGS.put(TICK_AUTOVALUE_MACHINERY_TYPES,
				"MACHINERY TYPES");
	}

	protected int autoValue = 0;
	protected boolean quantifiable = true;
	protected boolean selected = true;
	protected float tNumValue = 0;
	protected String tTextValue = "";
	protected int type = 1;

	@Override
	public ITick clone() {
		Tick iTick = new Tick();

		super.clone(iTick);
		iTick.setTTextValue(this.tTextValue);
		iTick.setTNumValue(this.tNumValue);
		iTick.setSelected(this.selected);
		iTick.setQuantifiable(this.quantifiable);

		return iTick;
	}

	@Override
	public int getAutoValue() {
		return autoValue;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Override
	public Long getId() {
		return this.Id;
	}

	@Override
	public float getTNumValue() {
		return tNumValue;
	}

	@Override
	public String getTTextValue() {
		return tTextValue;
	}

	@Override
	public int getType() {
		return type;
	}

	@Override
	public boolean isQuantifiable() {
		return quantifiable;
	}

	@Override
	public boolean isSelected() {
		return selected;
	}

	@Override
	public void setAutoValue(int autoValue) {
		this.autoValue = autoValue;
	}

	@Override
	public void setId(Long Id) {
		this.Id = Id;
	}

	@Override
	public void setQuantifiable(boolean quantifiable) {
		this.quantifiable = quantifiable;
	}

	@Override
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	@Override
	public void setTNumValue(float numValue) {
		tNumValue = numValue;
	}

	@Override
	public void setTTextValue(String textValue) {
		tTextValue = textValue;
	}

	@Override
	public void setType(int type) {
		this.type = type;
	}

	@Override
	public String toString() {
		String aux = "";

		aux = "------ TICK ------- \n" + super.toString();

		aux = aux + "Type: " + TICK_TYPE_STRINGS.get(this.type);
		aux = aux + " || Auto Value: "
				+ TICK_AUTOVALUE_STRINGS.get(this.autoValue);
		aux = aux + " || Text Value: " + this.tTextValue;
		if (quantifiable) {
			aux = aux + " || Type: " + TICK_QUANTIFIABLE_STRINGS.get(0);
		} else {
			aux = aux + " || Type: " + TICK_QUANTIFIABLE_STRINGS.get(1);
		}
		aux = aux + " || Num Value: " + this.tNumValue;

		return aux;
	}

}
