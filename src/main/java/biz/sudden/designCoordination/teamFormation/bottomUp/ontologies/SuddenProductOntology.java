package biz.sudden.designCoordination.teamFormation.bottomUp.ontologies;

import jade.content.onto.BasicOntology;
import jade.content.onto.Ontology;
import jade.content.onto.OntologyException;
import jade.content.schema.ConceptSchema;
import jade.content.schema.TermSchema;

/**
 * 
 * @author mcassmc
 * 
 *         The ontological structures needed for the creation of a noticeboard.
 * 
 *         These are dependent on the process ontology.
 * 
 *         I did in fact originally set this up with a full scale copy of the
 *         ontology. And due to supplier data not being reliable moving back to
 *         this now.
 * 
 */

public class SuddenProductOntology extends Ontology {

	/**
	 * @return the static command ontology
	 * 
	 *         As traditional with Jade - a static instance of the ontology is
	 *         returned via a getInstance method and the constructor is private.
	 */
	public static Ontology getInstance() {
		return theInstance;
	}

	private static final long serialVersionUID = 1L;

	private static Ontology theInstance = new SuddenProductOntology();

	public static final String ONTOLOGY_NAME = "SUDDEN_Product_Ontology";

	public static final String fullNameSpace = "http://www.Sudden-ontologies.com/SuddenOntology.owl#";
	public static final String nameSpace = "";

	// Product names
	// New(current as of 07/09) ontology

	public static final String ACTUATOR = nameSpace + "Aktuatoren";
	public static final String ACTUATOR_INTERNALS = nameSpace
			+ "Aktuatoren_Internal_Components";
	public static final String ACTUATOR_CASING = nameSpace
			+ "Aktuatoren_Casing";

	// Complex products
	public static final String ABSGASSYSTEM = nameSpace + "Absgasssystem";
	public static final String ABSGASSYSTEM_BRENNSTOFFZELLE = nameSpace
			+ "Absgasssystem_Brennstoff";
	public static final String ABSGASSYSTEM_VERBRENNUNGSMOTOR = nameSpace
			+ "Absgasssystem_Verbrennungsmotor";
	public static final String ANLASSER = nameSpace + "Anlasser";
	public static final String ANSAUGSYSTEME = nameSpace + "Ansaugsysteme";
	public static final String ANTRIEB = nameSpace + "Antrieb";
	public static final String Antrieb_mit_Kraftumwandlung = nameSpace
			+ "Antrieb_mit_Kraftumwandlung";
	public static final String Antrieb_ohne_Kraftumwandlung = nameSpace
			+ "Antrieb_ohne_Kraftumwandlung";
	public static final String Hybridantrieb = nameSpace + "Hybridantrieb";
	public static final String Primaerantrieb = nameSpace + "Primaerantrieb";
	public static final String Sekundaerantrieb = nameSpace
			+ "Sekundaerantrieb";
	public static final String Elektronisches_Management_Brennstoffzelle = nameSpace
			+ "Elektronisches_Management_Brennstoffzelle";
	public static final String Elektronischs_Antriebsmanagement = nameSpace
			+ "Elektronischs_Antriebsmanagement";
	public static final String Elektronischs_Motormanagement = nameSpace
			+ "Elektronischs_Motormanagement";
	public static final String Energieversorgung = nameSpace
			+ "Energieversorgung";
	public static final String Energiegewinnung = nameSpace
			+ "Energiegewinnung";
	public static final String Solarpannels = nameSpace + "Solarpannels";
	public static final String Wasserstoff_Brennstoffzelle = nameSpace
			+ "Wasserstoff-Brennstoffzelle";
	public static final String Energiespeicher = nameSpace + "Energiespeicher";
	public static final String Batterie = nameSpace + "Batterie";
	public static final String Bleibatterie = nameSpace + "Bleibatterie";
	public static final String Hochtemperatur_Batterien = nameSpace
			+ "Hochtemperatur-Batterien";
	public static final String Lithium_Ion_Batterie = nameSpace
			+ "Lithium-Ion_Batterie";
	public static final String Lithium_Polymer_Batterie = nameSpace
			+ "Lithium-Polymer-Batterie";
	public static final String Nickel_Metallhybrid_Batterie = nameSpace
			+ "Nickel-Metallhybrid-Batterie";
	public static final String Zink_Luft_Batterie = nameSpace
			+ "Zink-Luft-Batterie";
	public static final String Gasversorgung = nameSpace + "Gasversorgung";
	public static final String Wasserstoffversorgung = nameSpace
			+ "Wasserstoffversorgung";
	public static final String Wasserstoff_Brenngasversorgung = nameSpace
			+ "Wasserstoff_Brenngasversorgung";
	public static final String Kraftstoffversorgung = nameSpace
			+ "Kraftstoffversorgung";
	public static final String Kraftuebertragung = nameSpace
			+ "Kraftuebertragung";
	public static final String Kuelsystem = nameSpace + "Kuelsystem";
	public static final String Brennstoffzellen_Kuehlsystem = nameSpace
			+ "Brennstoffzellen_Kuehlsystem";
	public static final String Motorkuehlsystem = nameSpace
			+ "Motorkuehlsystem";
	public static final String Motor = nameSpace + "Motor";
	public static final String Elektromotor = nameSpace + "Elektromotor";
	public static final String Asynchronmotor = nameSpace + "Asynchronmotor";
	public static final String Gleichstrommotor = nameSpace
			+ "Gleichstrommotor";
	public static final String Radnabenmotor = nameSpace + "Radnabenmotor";
	public static final String Synchronmotor = nameSpace + "Synchronmotor";
	public static final String Verbrennungsmotor = nameSpace
			+ "Verbrennungsmotor";
	public static final String Fremdzuendender_Verbrennungsmotor = nameSpace
			+ "Fremdzuendender_Verbrennungsmotor";
	public static final String Benzin_Motor = nameSpace + "Benzin_Motor";
	public static final String Gasmotor = nameSpace + "Gasmotor";
	public static final String Wasserstoffverbrennungsmotor = nameSpace
			+ "Wasserstoffverbrennungsmotor";
	public static final String Selbstzuendender_Verbrennungsmotor = nameSpace
			+ "Selbstzuendender_Verbrennungsmotor";
	public static final String Diesel_Motor = nameSpace + "Diesel_Motor";
	public static final String Sauerstoffsystem = nameSpace
			+ "Sauerstoffsystem";
	public static final String Stack = nameSpace + "Stack";
	public static final String Tank = nameSpace + "Tank";
	public static final String Gastank = nameSpace + "Gastank";
	public static final String Fluessigwasserstofftank_Kryotank = nameSpace
			+ "Fluessigwasserstofftank_Kryotank ";
	public static final String Hochdrucktank = nameSpace + "Hochdrucktank";
	public static final String Metallhybridtank = nameSpace
			+ "Metallhybridtank";
	public static final String Kraftstofftank_Benzin_Diesel = nameSpace
			+ "Kraftstofftank_Benzin-Diesel";
	public static final String Wassermanagement = nameSpace
			+ "Wassermanagement";

	// One extra for ICSOC
	public static final String Felge = nameSpace + "Felge";

	// Simple ones

	public static final String Abgaskruemmer = nameSpace + "Abgaskruemmer";
	public static final String Abgasrohre = nameSpace + "Abgasrohre";
	public static final String Abgassammelrohre = nameSpace
			+ "Abgassammelrohre";
	public static final String Abscheider = nameSpace + "Abscheider";
	public static final String Aktuatoren = nameSpace + "Aktuatoren";
	public static final String Ansaugrohre = nameSpace + "Ansaugrohre";
	public static final String Anschluesse = nameSpace + "Anschluesse";
	public static final String Antriebswellen = nameSpace + "Antriebswellen";
	public static final String Befeuchter = nameSpace + "Befeuchter";
	public static final String Behaelter = nameSpace + "Behaelter";
	public static final String Bipolarplatten = nameSpace + "Bipolarplatten";
	public static final String DC_AC_Konverter = nameSpace + "DC-AC_Konverter";
	public static final String DC_DC_Konverter = nameSpace + "DC-DC_Konverter";
	public static final String Dichtungen = nameSpace + "Dichtungen";
	public static final String Differentialgetriebe = nameSpace
			+ "Differentialgetriebe";
	public static final String Dosiersysteme = nameSpace + "Dosiersysteme";
	public static final String Drossel_Ansaugsystem = nameSpace
			+ "Drossel_Ansaugsystem";
	public static final String Druckbehaelter_Kuehlmittel = nameSpace
			+ "Druckbehaelter_Kuehlmittel";
	public static final String Druckregelventil = nameSpace
			+ "Druckregelventil";
	public static final String Druckregulator = nameSpace + "Druckregulator";
	public static final String EInrueckrelais = nameSpace + "EInrueckrelais";
	public static final String Einspritzduese = nameSpace + "Einspritzduese";
	public static final String Einspritzleitung = nameSpace
			+ "Einspritzleitung";
	public static final String Einspritzpumpe = nameSpace + "Einspritzpumpe";
	public static final String Einspurgetriebe = nameSpace + "Einspurgetriebe";
	public static final String Elektrische_Leistungsregler = nameSpace
			+ "Elektrische_Leistungsregler";
	public static final String Elektroden = nameSpace + "Elektroden";
	public static final String Elektrolyt = nameSpace + "Elektrolyt";
	public static final String Elektronische_Steuerung = nameSpace
			+ "Elektronische_Steuerung";
	public static final String Endplatten = nameSpace + "Endplatten";
	public static final String Gaseinspritzduesen = nameSpace
			+ "Gaseinspritzduesen";
	public static final String Gasleitung = nameSpace + "Gasleitung";
	public static final String Gasverteiler = nameSpace + "Gasverteiler";
	public static final String Gaszufuehrung = nameSpace + "Gaszufuehrung";
	public static final String Gemischbildungssystem = nameSpace
			+ "Gemischbildungssystem";
	public static final String Generatoren = nameSpace + "Generatoren";
	public static final String Getriebe = nameSpace + "Getriebe";
	public static final String Auotimatik_Getriebe = nameSpace
			+ "Auotimatik_Getriebe";
	public static final String Manuelles_Getriebe = nameSpace
			+ "Manuelles_Getriebe";
	public static final String H2_Sensor = nameSpace + "H2-Sensor";
	public static final String Hydraulikhochdruckpumpen = nameSpace
			+ "Hydraulikhochdruckpumpen";
	public static final String Hydraulikleitungen = nameSpace
			+ "Hydraulikleitungen";
	public static final String Inverter = nameSpace + "Inverter";
	public static final String Isolierung = nameSpace + "Isolierung";
	public static final String Kardangetriebe = nameSpace + "Kardangetriebe";
	public static final String Katalysatoren = nameSpace + "Katalysatoren";
	public static final String Katalytische_Nachverbrennung = nameSpace
			+ "Katalytische_Nachverbrennung";
	public static final String Keil_und_Zahnriemen = nameSpace
			+ "Keil-_und_Zahnriemen";
	public static final String Kolben_mit_Kolbenringen = nameSpace
			+ "Kolben_mit_Kolbenringen";
	public static final String Kompressoren = nameSpace + "Kompressoren";
	public static final String Kondensator = nameSpace + "Kondensator";
	public static final String Kraftstofffilter = nameSpace
			+ "Kraftstofffilter";
	public static final String Kraftstoffleitungen = nameSpace
			+ "Kraftstoffleitungen";
	public static final String Kraftstoffpumpe = nameSpace + "Kraftstoffpumpe";
	public static final String Kraftstoffventile = nameSpace
			+ "Kraftstoffventile";
	public static final String Kryotechnik = nameSpace + "Kryotechnik";
	public static final String Kuehler = nameSpace + "Kuehler";
	public static final String Kuehlerluefter = nameSpace + "Kuehlerluefter";
	public static final String Kuehlmittel = nameSpace + "Kuehlmittel";
	public static final String Kuehlmittelpumpen = nameSpace
			+ "Kuehlmittelpumpen";
	public static final String Kuehlmittelregelventile = nameSpace
			+ "Kuehlmittelregelventile";
	public static final String Kuehlmittelschlaeuche = nameSpace
			+ "Kuehlmittelschlaeuche";
	public static final String Kuehlmittelverteiler = nameSpace
			+ "Kuehlmittelverteiler";
	public static final String Kupplung = nameSpace + "Kupplung";
	public static final String Automatische_Kupplung = nameSpace
			+ "Automatische_Kupplung";
	public static final String Manuelle_Kupplung = nameSpace
			+ "Manuelle_Kupplung";
	public static final String Kurbelgehaeuse = nameSpace + "Kurbelgehaeuse";
	public static final String Kurbelwellen = nameSpace + "Kurbelwellen";
	public static final String Ladedruckregler = nameSpace + "Ladedruckregler";
	public static final String Ladeluftkuehler = nameSpace + "Ladeluftkuehler";
	public static final String Lambdasonden = nameSpace + "Lambdasonden";
	public static final String Luftdruckleitungen = nameSpace
			+ "Luftdruckleitungen";
	public static final String Luftfilter = nameSpace + "Luftfilter";
	public static final String Luftfuehrungen_Ladeluft = nameSpace
			+ "Luftfuehrungen_Ladeluft";
	public static final String Luftkompressor = nameSpace + "Luftkompressor";
	public static final String Luftkonditionierung = nameSpace
			+ "Luftkonditionierung";
	public static final String Luftleitungen = nameSpace + "Luftleitungen";
	public static final String Luftmengenmesser = nameSpace
			+ "Luftmengenmesser";
	public static final String Luftversorgung = nameSpace + "Luftversorgung";
	public static final String Manschetten = nameSpace + "Manschetten";
	public static final String Massenausgleichswellen = nameSpace
			+ "Massenausgleichswellen";
	public static final String Membran = nameSpace + "Membran";
	public static final String Mess_und_Regeltechnik = nameSpace
			+ "Mess-_und_Regeltechnik";
	public static final String Motor_Kontroll_Modul = nameSpace
			+ "Motor_Kontroll_Modul";
	public static final String Motorblock = nameSpace + "Motorblock";
	public static final String Niederdruckleitungen_Oel = nameSpace
			+ "Niederdruckleitungen_Oel";
	public static final String Nockenwellen = nameSpace + "Nockenwellen";
	public static final String Oelfilter = nameSpace + "Oelfilter";
	public static final String Oelkuehler = nameSpace + "Oelkuehler";
	public static final String Oelpumpen = nameSpace + "Oelpumpen";
	public static final String Oelwannen = nameSpace + "Oelwannen";
	public static final String Pleuel = nameSpace + "Pleuel";
	public static final String Reformer = nameSpace + "Reformer";
	public static final String Schalldaempfer = nameSpace + "Schalldaempfer";
	public static final String Sensoren = nameSpace + "Sensoren";
	public static final String Stack_Katalysator = nameSpace
			+ "Stack_Katalysator";
	public static final String Steuerungen_Kuehlungsmanagement = nameSpace
			+ "Steuerungen_Kuehlungsmanagement";
	public static final String Taktventil = nameSpace + "Taktventil";
	public static final String Tankanschluss = nameSpace + "Tankanschluss";
	public static final String Tanknippel = nameSpace + "Tanknippel";
	public static final String Thermostat = nameSpace + "Thermostat";
	public static final String Turbolader = nameSpace + "Turbolader";
	public static final String Unterdruckleitungen = nameSpace
			+ "Unterdruckleitungen";
	public static final String Unterdruckpumpen = nameSpace
			+ "Unterdruckpumpen";
	public static final String Ventil = nameSpace + "Ventil";
	public static final String Ventildeckel = nameSpace + "Ventildeckel";
	public static final String Ventile = nameSpace + "Ventile";
	public static final String Ventile_Brennraumein_und_auslass = nameSpace
			+ "Ventile_Brennraumein-_und_-auslass";
	public static final String Ventiltrieb = nameSpace + "Ventiltrieb";
	public static final String Verbindungselemente = nameSpace
			+ "Verbindungselemente";
	public static final String Vergaser = nameSpace + "Vergaser";
	public static final String Verschlussmechanismus = nameSpace
			+ "Verschlussmechanismus";
	public static final String Waermetauscher = nameSpace + "Waermetauscher";
	public static final String Wandler = nameSpace + "Wandler";
	public static final String Wasserstoffleitung = nameSpace
			+ "Wasserstoffleitung";
	public static final String Wasserstoffsensoren = nameSpace
			+ "Wasserstoffsensoren";
	public static final String Wasserstoffverteiler = nameSpace
			+ "Wasserstoffverteiler";
	public static final String Zuendkerzen = nameSpace + "Zuendkerzen";
	public static final String Zuendspulen = nameSpace + "Zuendspulen";
	public static final String Zuendsystem = nameSpace + "Zuendsystem";
	public static final String Hochspannungs_Zuendsystem = nameSpace
			+ "Hochspannungs_Zuendsystem";
	public static final String Neiderspannungs_Zuendsystem = nameSpace
			+ "Neiderspannungs_Zuendsystem";
	public static final String Zylinderkopf = nameSpace + "Zylinderkopf";

	private static Ontology basicOnt;

	public SuddenProductOntology() {
		super(ONTOLOGY_NAME, BasicOntology.getInstance());
		basicOnt = BasicOntology.getInstance();
		try {
			// prelims
			TermSchema stringSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.STRING);
			TermSchema intSchema = (TermSchema) basicOnt
					.getSchema(BasicOntology.INTEGER);

			// Prepare for many concepts ;)

			ConceptSchema schema1 = new ConceptSchema(ACTUATOR);
			ConceptSchema schema2 = new ConceptSchema(ACTUATOR_CASING);
			ConceptSchema schema3 = new ConceptSchema(ACTUATOR_INTERNALS);
			ConceptSchema schema4 = new ConceptSchema(Abgaskruemmer);
			ConceptSchema schema5 = new ConceptSchema(Abgasrohre);
			ConceptSchema schema6 = new ConceptSchema(Abgassammelrohre);
			ConceptSchema schema7 = new ConceptSchema(Abscheider);
			ConceptSchema schema8 = new ConceptSchema(ABSGASSYSTEM);
			ConceptSchema schema9 = new ConceptSchema(
					ABSGASSYSTEM_BRENNSTOFFZELLE);
			schema9.addSuperSchema(schema8);
			ConceptSchema schema10 = new ConceptSchema(
					ABSGASSYSTEM_VERBRENNUNGSMOTOR);
			schema9.addSuperSchema(schema8);
			ConceptSchema schema11 = new ConceptSchema(Aktuatoren);
			ConceptSchema schema12 = new ConceptSchema(ANLASSER);
			ConceptSchema schema13 = new ConceptSchema(Ansaugrohre);
			ConceptSchema schema14 = new ConceptSchema(ANSAUGSYSTEME);
			ConceptSchema schema15 = new ConceptSchema(Anschluesse);
			ConceptSchema schema16 = new ConceptSchema(ANTRIEB);
			ConceptSchema schema17 = new ConceptSchema(
					Antrieb_mit_Kraftumwandlung);
			schema17.addSuperSchema(schema16);
			ConceptSchema schema18 = new ConceptSchema(
					Antrieb_ohne_Kraftumwandlung);
			schema18.addSuperSchema(schema16);
			ConceptSchema schema19 = new ConceptSchema(Antriebswellen);
			ConceptSchema schema20 = new ConceptSchema(Asynchronmotor);
			ConceptSchema schema21 = new ConceptSchema(Auotimatik_Getriebe);
			ConceptSchema schema22 = new ConceptSchema(Automatische_Kupplung);
			ConceptSchema schema23 = new ConceptSchema(Batterie);
			ConceptSchema schema24 = new ConceptSchema(Befeuchter);
			ConceptSchema schema25 = new ConceptSchema(Behaelter);
			ConceptSchema schema26 = new ConceptSchema(Benzin_Motor);
			ConceptSchema schema27 = new ConceptSchema(Bipolarplatten);
			ConceptSchema schema28 = new ConceptSchema(Bleibatterie);

			ConceptSchema schema29 = new ConceptSchema(
					Brennstoffzellen_Kuehlsystem);
			ConceptSchema schema30 = new ConceptSchema(DC_AC_Konverter);
			ConceptSchema schema31 = new ConceptSchema(DC_DC_Konverter);
			ConceptSchema schema32 = new ConceptSchema(Dichtungen);
			ConceptSchema schema33 = new ConceptSchema(Diesel_Motor);
			ConceptSchema schema34 = new ConceptSchema(Differentialgetriebe);
			ConceptSchema schema35 = new ConceptSchema(Dosiersysteme);
			ConceptSchema schema36 = new ConceptSchema(Drossel_Ansaugsystem);
			ConceptSchema schema37 = new ConceptSchema(
					Druckbehaelter_Kuehlmittel);
			ConceptSchema schema38 = new ConceptSchema(Druckregelventil);
			ConceptSchema schema39 = new ConceptSchema(Druckregulator);
			ConceptSchema schema40 = new ConceptSchema(EInrueckrelais);
			ConceptSchema schema41 = new ConceptSchema(Einspritzduese);
			ConceptSchema schema42 = new ConceptSchema(Einspritzleitung);
			ConceptSchema schema43 = new ConceptSchema(Einspritzpumpe);
			ConceptSchema schema44 = new ConceptSchema(Einspurgetriebe);
			ConceptSchema schema45 = new ConceptSchema(
					Elektrische_Leistungsregler);
			ConceptSchema schema46 = new ConceptSchema(Elektroden);
			ConceptSchema schema47 = new ConceptSchema(Elektrolyt);
			ConceptSchema schema48 = new ConceptSchema(Elektromotor);
			ConceptSchema schema49 = new ConceptSchema(Elektronische_Steuerung);
			ConceptSchema schema50 = new ConceptSchema(
					Elektronisches_Management_Brennstoffzelle);
			ConceptSchema schema51 = new ConceptSchema(
					Elektronischs_Antriebsmanagement);
			ConceptSchema schema52 = new ConceptSchema(
					Elektronischs_Motormanagement);
			ConceptSchema schema53 = new ConceptSchema(Endplatten);
			ConceptSchema schema54 = new ConceptSchema(Energiegewinnung);
			ConceptSchema schema55 = new ConceptSchema(Energiespeicher);
			ConceptSchema schema56 = new ConceptSchema(Energieversorgung);
			schema54.addSuperSchema(schema56);
			schema55.addSuperSchema(schema56);
			ConceptSchema schema57 = new ConceptSchema(
					Fluessigwasserstofftank_Kryotank);
			ConceptSchema schema58 = new ConceptSchema(
					Fremdzuendender_Verbrennungsmotor);
			ConceptSchema schema59 = new ConceptSchema(Gaseinspritzduesen);
			ConceptSchema schema60 = new ConceptSchema(Gasleitung);
			ConceptSchema schema61 = new ConceptSchema(Gasmotor);
			ConceptSchema schema62 = new ConceptSchema(Gastank);
			ConceptSchema schema63 = new ConceptSchema(Gasversorgung);
			ConceptSchema schema64 = new ConceptSchema(Gasverteiler);
			ConceptSchema schema65 = new ConceptSchema(Gaszufuehrung);
			ConceptSchema schema66 = new ConceptSchema(Gemischbildungssystem);
			ConceptSchema schema67 = new ConceptSchema(Generatoren);
			ConceptSchema schema68 = new ConceptSchema(Getriebe);
			ConceptSchema schema69 = new ConceptSchema(Gleichstrommotor);
			ConceptSchema schema70 = new ConceptSchema(H2_Sensor);
			ConceptSchema schema71 = new ConceptSchema(Hochdrucktank);
			ConceptSchema schema72 = new ConceptSchema(
					Hochspannungs_Zuendsystem);
			ConceptSchema schema73 = new ConceptSchema(Hochtemperatur_Batterien);
			ConceptSchema schema74 = new ConceptSchema(Hybridantrieb);
			schema74.addSuperSchema(schema16);
			ConceptSchema schema75 = new ConceptSchema(Hydraulikhochdruckpumpen);
			ConceptSchema schema76 = new ConceptSchema(Hydraulikleitungen);
			ConceptSchema schema77 = new ConceptSchema(Inverter);
			ConceptSchema schema78 = new ConceptSchema(Isolierung);
			ConceptSchema schema79 = new ConceptSchema(Kardangetriebe);
			ConceptSchema schema80 = new ConceptSchema(Katalysatoren);
			ConceptSchema schema81 = new ConceptSchema(
					Katalytische_Nachverbrennung);
			ConceptSchema schema82 = new ConceptSchema(Keil_und_Zahnriemen);
			ConceptSchema schema83 = new ConceptSchema(Kolben_mit_Kolbenringen);
			ConceptSchema schema84 = new ConceptSchema(Kompressoren);
			ConceptSchema schema85 = new ConceptSchema(Kondensator);
			ConceptSchema schema86 = new ConceptSchema(Kraftstofffilter);
			ConceptSchema schema87 = new ConceptSchema(Kraftstoffleitungen);
			ConceptSchema schema88 = new ConceptSchema(Kraftstoffpumpe);
			ConceptSchema schema89 = new ConceptSchema(
					Kraftstofftank_Benzin_Diesel);
			ConceptSchema schema90 = new ConceptSchema(Kraftstoffventile);
			ConceptSchema schema91 = new ConceptSchema(Kraftstoffversorgung);
			ConceptSchema schema92 = new ConceptSchema(Kraftuebertragung);
			ConceptSchema schema93 = new ConceptSchema(Kryotechnik);
			ConceptSchema schema94 = new ConceptSchema(Kuehler);
			ConceptSchema schema95 = new ConceptSchema(Kuehlerluefter);
			ConceptSchema schema96 = new ConceptSchema(Kuehlmittel);
			ConceptSchema schema97 = new ConceptSchema(Kuehlmittelpumpen);
			ConceptSchema schema98 = new ConceptSchema(Kuehlmittelregelventile);
			ConceptSchema schema99 = new ConceptSchema(Kuehlmittelschlaeuche);
			ConceptSchema schema100 = new ConceptSchema(Kuehlmittelverteiler);
			ConceptSchema schema101 = new ConceptSchema(Kuelsystem);
			ConceptSchema schema102 = new ConceptSchema(Kupplung);
			ConceptSchema schema103 = new ConceptSchema(Kurbelgehaeuse);
			ConceptSchema schema104 = new ConceptSchema(Kurbelwellen);
			ConceptSchema schema105 = new ConceptSchema(Ladedruckregler);
			ConceptSchema schema106 = new ConceptSchema(Ladeluftkuehler);
			ConceptSchema schema107 = new ConceptSchema(Lambdasonden);
			ConceptSchema schema108 = new ConceptSchema(Lithium_Ion_Batterie);
			ConceptSchema schema109 = new ConceptSchema(
					Lithium_Polymer_Batterie);
			ConceptSchema schema110 = new ConceptSchema(Luftdruckleitungen);
			ConceptSchema schema111 = new ConceptSchema(Luftfilter);
			ConceptSchema schema112 = new ConceptSchema(Luftfuehrungen_Ladeluft);
			ConceptSchema schema113 = new ConceptSchema(Luftkompressor);
			ConceptSchema schema114 = new ConceptSchema(Luftkonditionierung);
			ConceptSchema schema115 = new ConceptSchema(Luftleitungen);
			ConceptSchema schema116 = new ConceptSchema(Luftmengenmesser);
			ConceptSchema schema117 = new ConceptSchema(Luftversorgung);
			ConceptSchema schema118 = new ConceptSchema(Manschetten);
			ConceptSchema schema119 = new ConceptSchema(Manuelle_Kupplung);
			ConceptSchema schema120 = new ConceptSchema(Manuelles_Getriebe);
			ConceptSchema schema121 = new ConceptSchema(Massenausgleichswellen);
			ConceptSchema schema122 = new ConceptSchema(Membran);
			ConceptSchema schema123 = new ConceptSchema(Mess_und_Regeltechnik);
			ConceptSchema schema124 = new ConceptSchema(Metallhybridtank);
			ConceptSchema schema125 = new ConceptSchema(Motor);
			ConceptSchema schema126 = new ConceptSchema(Motor_Kontroll_Modul);
			ConceptSchema schema127 = new ConceptSchema(Motorblock);
			ConceptSchema schema128 = new ConceptSchema(Motorkuehlsystem);
			schema29.addSuperSchema(schema101);
			schema128.addSuperSchema(schema101);
			ConceptSchema schema129 = new ConceptSchema(
					Neiderspannungs_Zuendsystem);
			ConceptSchema schema130 = new ConceptSchema(
					Nickel_Metallhybrid_Batterie);
			ConceptSchema schema131 = new ConceptSchema(
					Niederdruckleitungen_Oel);
			ConceptSchema schema132 = new ConceptSchema(Nockenwellen);
			ConceptSchema schema133 = new ConceptSchema(Oelfilter);
			ConceptSchema schema134 = new ConceptSchema(Oelkuehler);
			ConceptSchema schema135 = new ConceptSchema(Oelpumpen);
			ConceptSchema schema136 = new ConceptSchema(Oelwannen);
			ConceptSchema schema137 = new ConceptSchema(Pleuel);
			ConceptSchema schema138 = new ConceptSchema(Primaerantrieb);
			schema138.addSuperSchema(schema16);
			ConceptSchema schema139 = new ConceptSchema(Radnabenmotor);
			ConceptSchema schema140 = new ConceptSchema(Reformer);
			ConceptSchema schema141 = new ConceptSchema(Sauerstoffsystem);
			ConceptSchema schema142 = new ConceptSchema(Schalldaempfer);
			ConceptSchema schema143 = new ConceptSchema(Sekundaerantrieb);
			schema143.addSuperSchema(schema16);
			ConceptSchema schema144 = new ConceptSchema(
					Selbstzuendender_Verbrennungsmotor);
			ConceptSchema schema145 = new ConceptSchema(Sensoren);
			ConceptSchema schema146 = new ConceptSchema(Solarpannels);
			schema146.addSuperSchema(schema54);
			ConceptSchema schema147 = new ConceptSchema(Stack);
			ConceptSchema schema148 = new ConceptSchema(Stack_Katalysator);
			ConceptSchema schema149 = new ConceptSchema(
					Steuerungen_Kuehlungsmanagement);
			ConceptSchema schema150 = new ConceptSchema(Synchronmotor);
			ConceptSchema schema151 = new ConceptSchema(Taktventil);
			ConceptSchema schema152 = new ConceptSchema(Tank);
			ConceptSchema schema153 = new ConceptSchema(Tankanschluss);
			ConceptSchema schema154 = new ConceptSchema(Tanknippel);
			ConceptSchema schema155 = new ConceptSchema(Thermostat);
			ConceptSchema schema156 = new ConceptSchema(Turbolader);
			ConceptSchema schema157 = new ConceptSchema(Unterdruckleitungen);
			ConceptSchema schema158 = new ConceptSchema(Unterdruckpumpen);
			ConceptSchema schema159 = new ConceptSchema(Ventil);
			ConceptSchema schema160 = new ConceptSchema(Ventildeckel);
			ConceptSchema schema161 = new ConceptSchema(Ventile);
			ConceptSchema schema162 = new ConceptSchema(
					Ventile_Brennraumein_und_auslass);
			ConceptSchema schema163 = new ConceptSchema(Ventiltrieb);
			ConceptSchema schema164 = new ConceptSchema(Verbindungselemente);
			ConceptSchema schema165 = new ConceptSchema(Verbrennungsmotor);
			ConceptSchema schema166 = new ConceptSchema(Vergaser);
			ConceptSchema schema167 = new ConceptSchema(Verschlussmechanismus);
			ConceptSchema schema168 = new ConceptSchema(Waermetauscher);
			ConceptSchema schema169 = new ConceptSchema(Wandler);
			ConceptSchema schema170 = new ConceptSchema(Wassermanagement);
			ConceptSchema schema171 = new ConceptSchema(
					Wasserstoff_Brenngasversorgung);

			ConceptSchema schema172 = new ConceptSchema(
					Wasserstoff_Brennstoffzelle);
			schema172.addSuperSchema(schema54);
			ConceptSchema schema173 = new ConceptSchema(Wasserstoffleitung);
			ConceptSchema schema174 = new ConceptSchema(Wasserstoffsensoren);
			ConceptSchema schema175 = new ConceptSchema(
					Wasserstoffverbrennungsmotor);
			ConceptSchema schema176 = new ConceptSchema(Wasserstoffversorgung);
			schema176.addSuperSchema(schema63);
			schema171.addSuperSchema(schema176);
			ConceptSchema schema177 = new ConceptSchema(Wasserstoffverteiler);
			ConceptSchema schema178 = new ConceptSchema(Zink_Luft_Batterie);
			ConceptSchema schema179 = new ConceptSchema(Zuendkerzen);
			ConceptSchema schema180 = new ConceptSchema(Zuendspulen);
			ConceptSchema schema181 = new ConceptSchema(Zuendsystem);
			ConceptSchema schema182 = new ConceptSchema(Zylinderkopf);

			ConceptSchema schema183 = new ConceptSchema(Felge);

			schema28.addSuperSchema(schema23);
			schema73.addSuperSchema(schema23);
			schema108.addSuperSchema(schema23);
			schema109.addSuperSchema(schema23);
			schema130.addSuperSchema(schema23);
			schema178.addSuperSchema(schema23);

			schema48.addSuperSchema(schema125);
			schema165.addSuperSchema(schema125);

			schema20.addSuperSchema(schema48);
			schema69.addSuperSchema(schema48);
			schema139.addSuperSchema(schema48);
			schema150.addSuperSchema(schema48);

			schema58.addSuperSchema(schema165);
			schema144.addSuperSchema(schema165);
			schema26.addSuperSchema(schema58);
			schema61.addSuperSchema(schema58);
			schema175.addSuperSchema(schema61);
			schema33.addSuperSchema(schema144);

			schema62.addSuperSchema(schema152);
			schema89.addSuperSchema(schema152);

			schema57.addSuperSchema(schema62);
			schema71.addSuperSchema(schema62);
			schema124.addSuperSchema(schema62);

			add(schema1);
			add(schema2);
			add(schema3);
			add(schema4);
			add(schema5);
			add(schema6);
			add(schema7);
			add(schema8);
			add(schema9);
			add(schema10);
			add(schema11);
			add(schema12);
			add(schema13);
			add(schema14);
			add(schema15);
			add(schema16);
			add(schema17);
			add(schema18);
			add(schema19);
			add(schema20);
			add(schema21);
			add(schema22);
			add(schema23);
			add(schema24);
			add(schema25);
			add(schema26);
			add(schema27);
			add(schema28);
			add(schema29);
			add(schema30);
			add(schema31);
			add(schema32);
			add(schema33);
			add(schema34);
			add(schema35);
			add(schema36);
			add(schema37);
			add(schema38);
			add(schema39);
			add(schema40);
			add(schema41);
			add(schema42);
			add(schema43);
			add(schema44);
			add(schema45);
			add(schema46);
			add(schema47);
			add(schema48);
			add(schema49);
			add(schema50);
			add(schema51);
			add(schema52);
			add(schema53);
			add(schema54);
			add(schema55);
			add(schema56);
			add(schema57);
			add(schema58);
			add(schema59);
			add(schema60);
			add(schema61);
			add(schema62);
			add(schema63);
			add(schema64);
			add(schema65);
			add(schema66);
			add(schema67);
			add(schema68);
			add(schema69);
			add(schema70);
			add(schema71);
			add(schema72);
			add(schema73);
			add(schema74);
			add(schema75);
			add(schema76);
			add(schema77);
			add(schema78);
			add(schema79);
			add(schema80);
			add(schema81);
			add(schema82);
			add(schema83);
			add(schema84);
			add(schema85);
			add(schema86);
			add(schema87);
			add(schema88);
			add(schema89);
			add(schema90);
			add(schema91);
			add(schema92);
			add(schema93);
			add(schema94);
			add(schema95);
			add(schema96);
			add(schema97);
			add(schema98);
			add(schema99);
			add(schema100);
			add(schema101);
			add(schema102);
			add(schema103);
			add(schema104);
			add(schema105);
			add(schema106);
			add(schema107);
			add(schema108);
			add(schema109);
			add(schema110);
			add(schema111);
			add(schema112);
			add(schema113);
			add(schema114);
			add(schema115);
			add(schema116);
			add(schema117);
			add(schema118);
			add(schema119);
			add(schema120);
			add(schema121);
			add(schema122);
			add(schema123);
			add(schema124);
			add(schema125);
			add(schema126);
			add(schema127);
			add(schema128);
			add(schema129);
			add(schema130);
			add(schema131);
			add(schema132);
			add(schema133);
			add(schema134);
			add(schema135);
			add(schema136);
			add(schema137);
			add(schema138);
			add(schema139);
			add(schema140);
			add(schema141);
			add(schema142);
			add(schema143);
			add(schema144);
			add(schema145);
			add(schema146);
			add(schema147);
			add(schema148);
			add(schema149);
			add(schema150);
			add(schema151);
			add(schema152);
			add(schema153);
			add(schema154);
			add(schema155);
			add(schema156);
			add(schema157);
			add(schema158);
			add(schema159);
			add(schema160);
			add(schema161);
			add(schema162);
			add(schema163);
			add(schema164);
			add(schema165);
			add(schema166);
			add(schema167);
			add(schema168);
			add(schema169);
			add(schema170);
			add(schema171);
			add(schema172);
			add(schema173);
			add(schema174);
			add(schema175);
			add(schema176);
			add(schema177);
			add(schema178);
			add(schema179);
			add(schema180);
			add(schema181);
			add(schema182);
			add(schema183);

		}
		// Pretty well everything we do here might generate an ontology
		// exception.
		catch (OntologyException e) {
			e.printStackTrace();
		}
	}
}
