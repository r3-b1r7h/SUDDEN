package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.domain.RoleDimension;
import biz.sudden.knowledgeData.competencesManagement.repository.IRoleDimensionRepository;

public class TestDBDataRoleDimensions {

	Logger logger = Logger.getLogger(this.getClass());

	// Access to other entities
	private TestDBDataDimensions dimensions;

	// Repositories
	private IRoleDimensionRepository roleDimensionRepository;

	// Memory Objects
	private Hashtable<String, List<RoleDimension>> roleDimensions = new Hashtable<String, List<RoleDimension>>();

	private Dimension getCompetenceDimensionByName(String dimensionName,
			String competenceName) {
		List<Dimension> listOfDimensions = dimensions
				.getDimensionsOf(competenceName);
		if (listOfDimensions == null || listOfDimensions.size() == 0)
			logger.error("!!no dimension for competence found: "
					+ competenceName);
		Dimension dimension;

		Iterator<Dimension> iterator = listOfDimensions.iterator();
		while (iterator.hasNext()) {
			dimension = iterator.next();
			if (dimension.getName().equals(dimensionName)) {
				return dimension;
			}
		}
		logger.error("Competence " + competenceName
				+ " has not this dimension: " + dimensionName);
		return null;
	}

	public TestDBDataDimensions getDimensions() {
		return dimensions;
	}

	public List<RoleDimension> getRoleDimensionsOf(String competenceName) {
		return roleDimensions.get(competenceName);
	}

	public IRoleDimensionRepository getRoleDimensionRepository() {
		return roleDimensionRepository;
	}

	public Hashtable<String, List<RoleDimension>> getRoleDimensions() {
		return roleDimensions;
	}

	public void insertDBTestDataRoleDimensions() {
		/* Adding Role Dimensions Test Data */
		roleDimensions.clear();
		insertDBTestDataRoleDimensions_Stammdaten();
		insertDBTestDataRoleDimensions_Technologie_L0();
		insertDBTestDataRoleDimensions_Kommunikationstechnologie_L0();
		insertDBTestDataRoleDimensions_RechtUndHaftung_L0();
		insertDBTestDataRoleDimensions_Logistikmanagement_L0();
		insertDBTestDataRoleDimensions_Umweltmanagement_L0();
		insertDBTestDataRoleDimensions_Qualitatsmanagement_L0();
		insertDBTestDataRoleDimensions_Kundenfokus_L0();
		insertDBTestDataRoleDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L0();
		insertDBTestDataRoleDimensions_Finanzen_L0();
		insertDBTestDataRoleDimensions_Umweltmanagement_L1();
		insertDBTestDataRoleDimensions_Technologie_L1();
		insertDBTestDataRoleDimensions_Kommunikationstechnologie_L1();
		insertDBTestDataRoleDimensions_Mitarbeiterqualifikation_L1();
		insertDBTestDataRoleDimensions_Fuhrungskompetenz_L1();
		insertDBTestDataRoleDimensions_Logistikmanagement_L1();
		insertDBTestDataRoleDimensions_Qualitatsmanagement_L1();
		insertDBTestDataRoleDimensions_RechtUndHaftung_L1();
		insertDBTestDataRoleDimensions_Kundenfokus_L1();
		insertDBTestDataRoleDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L1();
		insertDBTestDataRoleDimensions_Finanzen_L1();
	}

	private void insertDBTestDataRoleDimensions_Finanzen_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"47.) Umsatz in Mio. Euro in den letzten 3 Jahren",
				"L0 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"48.) Exportanteil in % des Gesamtumsatzes", "L0 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"49.) Einkaufsvolumen in % vom Gesamtumsatz", "L0 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"50.) Ist der Deckungsbeitrag steigend, gleichbleibend oder ruecklaeufig?",
						"L0 Finanzen"));
		roleDimension.setWeight(1.25f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"51.) Zusammensetzung der Herstellkosten, Materialanteil in % der Herstellkosten",
						"L0 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"52.) Zusammensetzung der Herstellkosten, Lohnanteil in % der Herstellkosten",
						"L0 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Finanzen", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Finanzen_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"144.) Wird der Finanzplan an die Ziele und Prioritaeten des Unternehmens angepasst? Wird die Verwendung von finanziellen Mitteln betreffend Effizienz evaluiert?",
						"L1 Finanzen"));
		roleDimension.setWeight(1.25f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"145.) Werden alle Moeglichkeiten fuer die Entwicklung von neuen finanziellen Quellen weitgehend genutzt (z.B. staatliche Subventionen, bundesweite Foerderungen, EU-Programme, Spenden)?",
						"L1 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"146.) Werden Betriebsanlagen, Ausstattung und Material effektiv und effizient genutzt um die Ziele zu erreichen?",
						"L1 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"147.) Gibt es Wartungsvertraege und Gebrauchsanweisungen die die Wartung von Betriebsanlagen und Maschinen sicherstellen? Umweltressourcen (Energie, Muell) werden in verantwortungsbewusster Weise genutzt?",
						"L1 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"148a) Werden Arbeitsstunden (als limitierte Ressourcen) zweckmaessig geplant?",
						"L1 Finanzen"));
		roleDimension.setWeight(1.25f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"148b) Aufgewendete Zeiten werden auf ihre Effizienz ueberwacht.",
						"L1 Finanzen"));
		roleDimension.setWeight(1.25f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"149.) Fuer die weitere Entwicklung des Unternehmens stehen ausreichend finanzielles und zeitliches Budget zur Verfuegung.",
						"L1 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"150.) Wird die Erreichung von Zielen und Meilensteinen kontinuierlich beobachtet und evaluiert?",
						"L1 Finanzen"));
		roleDimension.setWeight(0.625f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Finanzen", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Fuhrungskompetenz_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"75a) Reagiert das Management auf interne und externe Veraenderungen mit konkreten Aenderungsplaenen und Massnahmen?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"75b) Werden Ressourcen frei gestellt um diese Veraenderungen zu unterstuetzen?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"76.) Nimmt das Management aktiv an Verbesserungen teil und fuerdert es die Zusammenarbeit im Team / teamuebergreifend?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"77.) Beobachtet das Management sein eigenes Verhalten und verbessert es dieses aktiv?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"78a) Garantiert das Management eine systematische Beschreibung und Koordination von operativen Ablaeufen, und Verantwortungen?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"78b) Sind Prozess-Ziele, Job-Beschreibungen, etc. dokumentiert?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"79.) Achtet das Management aktiv auf Kundenrueckmeldungen und verwendet es diese um Verbesserungsmassnahmen abzuleiten?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"80.) Fuehrt das Management aktiv strukturierte Mitarbeitergespraeche durch?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"81.) Unterstuetzt das Management ihre Mitarbeiter in der Realisierung von Zielen und fuerdert das Management persoenliche Entwicklung und Ausbildung?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"82.) Werden die Beduerfnisse und Erwartungen von Mitarbeitern gefuerdert und respektiert bei der Definition von Mitarbeiterzielen?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"83a) Werden in Interviews die Beduerfnisse und Erwartungen von Geschaeftspartnern und Stakeholdern erhoeht?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"83b) Wird diese Information bei Zieldefinitionen beruecksichtigt?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"84.)Sind die Ergebnisse von jedem Arbeitstag, von Selbstbewertungen und Qualitaetszirkeln gesammelt und in Verwendung?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"85a) Sind Vision und Ziele bei den Mitarbeitern und Kunden gut bekannt?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"85b) Werden sie in verstaendlicher Weise kommuniziert?",
				"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"86.) Gibt es einen strukturierten Prozess der beschreibt wie Verbesserungsvorschlaege, Ziele, Strategien und Konzepte des Unternehmens erstellt und geprueft werden?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"87.) Ist die Beschaeftigung von Mitarbeitern geplant und gesteuert betreffend ihrer Qualifikationen und auch geschlechtsspezifischen Kompetenzen?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"88.) Werden Kompetenzen, Faehigkeiten und Wissen von Mitarbeitern durch zielgerichtete Planung und Ausbildung weiterentwickelt?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"89.) Indirekt gemessene Variablen, zB Ruhetage/Krankenstand, personelle Fluktuation erlauben den Grad der Zufriedenheit von Vollzeitbeschaeftigten zu erkennen. Werden diese aufgezeichnet und analysiert?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"90a) Existieren Richtlinien und Regeln fuer das Strukturieren und Planen von Besprechungen?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"90b) Sind diese Richtlinien in Verwendung und werden sie regelmaessig auf Ihre Effektivitaet ueberprueft?",
						"L1 Fuehrungskompetenz"));
		roleDimension.setWeight(0.667f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Fuehrungskompetenz", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Kommunikationstechnologie_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"7.) Welche CAE/CAD/ CAM - Tools werden verwendet?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"8.) Welche CAD Daten-Austausch Tools werden verwendet?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"9.) Haben Sie EDI Tools (Z.B. Konverter fuer Electronic Data Interchange) in Verwendung?",
						"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"10.) Welche EDI-Standards sind implementiert?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.4f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"11.) Verwenden Sie ein ERP-System?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"12.) Verwenden Sie ein PPS-System?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"13.) Verwenden Sie ein CRM-System?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"14.) Verwenden Sie ein SRM-System?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"15.) Verwenden Sie ein System fuer Videokonferenzen?",
				"L0 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions
				.put("L0 Kommunikationstechnologie", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Kommunikationstechnologie_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"60.) Entspricht die Informationstechnik dem Stand der Technik?",
						"L1 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"61.) Welche Informationssysteme sind vorhanden? Sind diese integriert bzw. stehen angemessene Schnittstellen zur Verfuegung?",
						"L1 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"62.) Werden Sicherungsmassnahmen hinsichtlich des Datenschutzes durchgefuehrt?",
						"L1 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"63.) Sind Virenschutzmassnahmen vorhanden?",
				"L1 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"64.) Sind alle Abteilungen miteinander vernetzt und haben E-Mail Moeglichkeit?",
						"L1 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"65.) Weiss die Oeffentlichkeit Bescheid ueber die Aktivitaeten des Unternehmens, dessen Ziele und Ergebnisse? (zB ueber Zeitungsartikel, Radio- oder TV-Beitraege, Veranstaltungen)? ",
						"L1 Kommunikationstechnologie"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions
				.put("L1 Kommunikationstechnologie", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"41.) In welchem Umfang (in %) wird in Ihrem Hause Eigenentwicklung betrieben?(0% bedeutet dass Sie aufgrund vom Kunden beigesteuerter Zeichnungen produzieren)",
						"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"42.) Wie viel Forschungsarbeit leisten Sie? (Wie hoch ist Ihre F&E-Quote?: Anteil an Forschungs- und Entwicklungsarbeit in % vom Umsatz)",
						"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"43.) Wie viele Patente haben Sie (inkl. Patent-Nr.)?",
				"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"44.) Haben Sie Testmethoden/Pruefverfahren im Einsatz? Wenn ja, welche?",
						"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"45.) Sind Sie in der Lage Prototypen zu bauen?",
				"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"46.) Welche Methoden/Verfahren des Prototypings verwenden Sie (siehe Annex 1.4 Methoden des Prototypings)?",
						"L0 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 KontinuierlicheVerbesserungLernenUndInnovation",
				listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"134.) Gibt es einen strukturierten Rahmen in dem innovative und kreative Ideen diskutiert werden koennen?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"135.) Mitarbeiter wirken in kontinuierlichen Verbesserungsprozessen mit. zB in Form von gemeinsamen Auseinandersetzungen und Qualitaetszirkeln",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"136.) Werden neue, innovative, zukunftsweisende Kommunikationstechnologien, zB Internet eingefuehrt und optimal genuetzt?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"137.) Werden neue, innovative, zukunftsweisende Methoden- als Ergaenzung zu geprueften und etablierten Beratungsansaetzen und -konzepten - evaluiert und optimal eingesetzt?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"138.) Werden technische und sozialpolitische Informationen, zB aus technischen Fachzeitschriften systematisch gesammelt und unterstuetzt es die Zielerreichung?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"139.) Wird Wissen und Fachkompetenz kontinuierlich erworben, zB durch erweiterte Schulungsmassnahmen, Fachkonferenzen, und unterstuetzt es die Zielerreichung?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"140.) Sind die Moeglichkeiten fuer Verbesserungen identifiziert und dokumentiert, zB durch verbale oder schriftliche Befragungen, durch Reflexion im Team und gemeinsam mit Kunden, durch Vergleiche mit anderen?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"141.) Werden Moeglichkeiten fuer Prozessverbesserungen priorisiert, implementiert und auf Effektivitaet ueberprueft?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.7f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"142.) Bringen Mitarbeiter Verbesserungsideen ein, arbeiten sie in Qualitaetszirkeln zusammen, und unterstuetzen sie aktiv die Implementierung von Verbesserungsmassnahmen?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"143.) Werden die implementierten Verbesserungsmassnahmen kontinuierlich ueberprueft, analysiert und wenn notwendig korrigiert?",
						"L1 KontinuierlicheVerbesserungLernenUndInnovation"));
		roleDimension.setWeight(0.6f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 KontinuierlicheVerbesserungLernenUndInnovation",
				listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Kundenfokus_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"36.) Haben Sie automotive Kunden? Wenn ja, benennen Sie Ihre automotiven Kunden (Top 10).",
						"L0 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"37.) Benennen Sie Ihre Hauptkunden (Top 10 Kunden in Bezug auf Umsatz):",
						"L0 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"38.) Aus welchen Laendern sind Ihre Kunden?",
						"L0 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"39.) Welche unterstuetzenden Leistungen als Ergaenzung zu den angebotenen Produkten koennen Sie uebernehmen? (Bzw. welche Rollen koennen Sie uebernehmen?)",
						"L0 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"40.) Gibt es Qualitaetssicherungsvereinbarungen mit Ihren Kunden? Wenn ja, mit wem? (Bitte benennen Sie max. 10 Kunden)",
						"L0 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Kundenfokus", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Kundenfokus_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"128.) Verfuegt der Zulieferer ueber Plaene, wie im Notfall die Versorgung des Kunden sicherzustellen ist?",
						"L1 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"129.) Alle Prozesse, speziell die fuer den Kunden wertschoepfend sind, sind bekannt und dokumentiert.",
						"L1 Kundenfokus"));
		roleDimension.setWeight(0.86f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"130.) Sind die Ziele der (Sub-) Prozesse festgelegt und existieren anschauliche Messungen?",
						"L1 Kundenfokus"));
		roleDimension.setWeight(0.92f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"131.) Werden Die Erwartungen von verschiedenen Kundengruppen evaluiert und beim Gestalten von Prozessen und Produkten beruecksichtigt (Infoblatt)?",
						"L1 Kundenfokus"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"132.) Sind weitere Massnahmen installiert um Kundenzufriedenheit zu dokumentieren? (zB Anzahl an Dank-Schreiben, Entwicklung der Reklamationszahlen, Anzahl der beratungssuchenden Kunden, verbesserte Antwortzeit auf Anfragen)?",
						"L1 Kundenfokus"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"133a) Werden Reklamationen und Verbesserungsvorschlaege systematisch dokumentiert und analysiert?",
						"L1 Kundenfokus"));
		roleDimension.setWeight(1.06f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"133b) Fliessen die Ergebnisse der Analyse in Verbesserungsprojekte ein?",
						"L1 Kundenfokus"));
		roleDimension.setWeight(1.06f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Kundenfokus", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Logistikmanagement_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"18.) Hauptlieferanten (Top 15): Bitte geben Sie hierfuer die Duns-Nummer der Unternehmen an!",
						"L0 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"19.) Aus welchen Laendern kaufen Sie zu? (Alle Laender aus denen Sie beliefert werden)",
						"L0 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"23.) Welche Logistiksysteme werden von Ihnen bereits verwendet?",
						"L0 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"24.) Wie wird Transportlogistik fuer genannte Logistiksysteme durchgefuehrt:",
						"L0 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Logistikmanagement", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Logistikmanagement_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"91.) Sind die Transporteinrichtungen angemessen ausgewaehlt?",
				"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"92.) Ist die Lagerung zweckmaessig?",
						"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"93.) Ist das Handling der Waren angemessen, und sind die noetigen Hilfseinrichtungen vorhanden?",
						"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"94.) Bestehen entsprechende Bewertungsgrundlagen fuer die Lieferzuverlaessigkeit vorhandener Kunden und welche Einstufung wurde getroffen?",
						"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"95.) Existiert ein System nach dem die Lieferzuverlaessigkeit von Unterlieferanten ueberwacht wird? Schliesst es den Nachweis entsprechender Korrekturmassnahmen ein?",
						"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"96a) Gibt es ein System zur Rueckverfolgbarkeit von: Teilen und Baugruppen / Stuecklisten oder aehnlichem?",
						"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"96b) Existieren Querverweise auf technischen Zeichnungen?",
				"L1 Logistikmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Logistikmanagement", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Mitarbeiterqualifikation_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"66.) Sind Ihre Mitarbeiter im Umgang mit Pruefmittel und Pruefeinrichtungen hinreichend geschult?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"67.) Sind die Mitarbeiter fuer die auszufuehrenden Taetigkeiten hinreichend qualifiziert und wird die Qualifikation durch Schulungsmassnahmen angepasst?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1.24f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"68.) Werden Ihre Mitarbeiter regelmaessig bezueglich Arbeitssicherheit unterwiesen (Krane, Gabelstapler, Gefahrstoffe)?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1.13f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"69.) Werden entsprechende Schulungsaufzeichnungen gepflegt?",
				"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"70.) Sind Kompetenzen, Faehigkeiten, und Wissen der Mitarbeiter  zB in Mitarbeitergespraechen dokumentiert?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1.24f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"71.) Werden Schulungsmassnahmen und Wissen den Kollegen strukturiert praesentiert?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"72.) Sind Faehigkeiten und Wissen von Mitarbeitern determiniert und werden sie gefuerdert mit fortgeschrittenen Schulungsmassnahmen?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1.13f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"73.) Gibt es eine Dokumentation ueber Rueckmeldungen (Mitarbeitergespraech) ueber den Motivationsgrad der Mitarbeiter betreffend Arbeitsklima, Kommunikation, Teilnahme, Anschluss, Anerkennung und Selbstverantwortung?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1.13f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"74.) Erwerben Mitarbeiter aktiv noetige Kompetenzen und bringen sie Ideen von fortgeschrittenen Schulungsmassnahmen in das Unternehmen ein?",
						"L1 Mitarbeiterqualifikation"));
		roleDimension.setWeight(1.13f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Mitarbeiterqualifikation", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Qualitatsmanagement_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"26.) Zulassungen / Zertifizierungen / Validierungen / Auszeichnungen.",
						"L0 Qualitaetsmanagement"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Qualitaetsmanagement", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Qualitatsmanagement_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"97.) Wie gut ist die Qualitaetssicherung organisiert?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"98.) Werden Prozess - FMEA (Fehler- Moeglichkeits- und Einfluss-Analysen) durchgefuehrt?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"99.) Werden Prozessfaehigkeitsuntersuchungen/Statistical Process Control (SPC)? verwendet?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"100.) Welche Formen der Prozessfaehigkeitsuntersuchungen / Statistical Process Control werden verwendet?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"101.) Verwenden Sie Auswertung der Massahmen fuer SPC?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"102.) Verwenden Sie Prozessfaehigkeits- und Maschinenuntersuchungen (Cp und Cpk)?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"103.) Verwenden Sie Prozessbeschreibungen?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"104.) Fuehren Sie Variantenreduktionskennzahlen und ueberpruefen Sie diese?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"105.) Verwenden Sie experimentelles/auf Erfahrung begruendetes Design?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"106.) Verwenden Sie Qualitaets-Problemloesungstechniken?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"107.) Verwenden Sie Ursachen- Wirkungsdiagramme?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"108.) Haben Sie ein Materialpruef-Labor?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"109.) Wird das Qualitaetsbewusstsein aller Mitarbeiter gefuerdert?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"110.) Prueft die oberste Leitung des Lieferanten die Wirksamkeit des QM - Systems in festgelegten Zeitabstaenden, um die Eignung und Effektivitaet zu gewaehrleisten?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"111.) Gibt es einen dokumentierten Prozess zur Messung der Kundenzufriedenheit einschliesslich der Haeufigkeit der Festlegung?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"112.) Hat der Lieferant geeignete Projekte zur Qualitaets- und Produktivitaets-verbesserung eingefuehrt?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"113.) Werden Qualitaetsaufzeichnungen ueber zugelassene Unterlieferanten erstellt und gepflegt?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"114.) Sind die eingesetzten Pruefmittel und Pruefeinrichtungen geeignet, um die geforderte Messaufgabe zu erfuellen?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"115.) Sind Pruefgeraete (z.B. 3-Koordinatenmessmaschine, Schwingungsmessgeraet, Temperaturmessgeraet, Geraeuschpegelmessgeraet) vorhanden?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"116.) Werden die Pruefmittel und Pruefeinrichtungen regelmaessig ueberprueft?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"117.) Gibt es einen QM - Plan (Kontrollplan)?",
				"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"118.) Gibt es einen Messraum und wird dort unter geeigneten Bedingungen gearbeitet?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"119.) Werden kaufmaennische und technische Wareneingangspruefungen durchgefuehrt?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"120.) Gibt es eine Festlegung fuer die Archivierung von Pruefnachweisen von Lieferanten?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"121.) Ist eine vollstaendige Endpruefung nach aktuellem Aenderungsstand gewaehrleistet und wird diese dokumentiert?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"122.) Werden Pruefnachweise auf Wunsch des Kunden ausgeliefert?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"123.) Wurde die Zustaendigkeit fuer die Pruefung und Behandlung von fehlerhaften Produkten festgelegt?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"124.) Werden ueber die Ergebnisse aller Qualitaetspruefungen systematisch Aufzeichnungen gemacht und aufbewahrt?",
						"L1 Qualitaetsmanagement"));
		roleDimension.setWeight(0.333f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Qualitaetsmanagement", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_RechtUndHaftung_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"16.) Ist eine Ersatzteilversorgung fuer Systeme / Module nach Produktionsauslauf fuer weitere 15 Jahre sichergestellt?",
						"L0 Recht und Haftung"));
		roleDimension.setWeight(2);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"17.) Ist eine Ersatzteilversorgung fuer Einzelteile nach Produktionsauslauf fuer weitere 15 Jahre sichergestellt?",
						"L0 Recht und Haftung"));
		roleDimension.setWeight(2);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"21.) Sind in den gelieferten Produkten, fuer die bei der Entsorgung gesetzlichen Vorschriften gelten, Problemteile oder -stoffe enthalten?",
						"L0 Recht und Haftung"));
		roleDimension.setWeight(2);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"25.) Wie werden fehlerhaft angelieferte Produkte abgewickelt:",
						"L0 Recht und Haftung"));
		roleDimension.setWeight(2);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Recht und Haftung", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_RechtUndHaftung_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"125.) Sind die Aktivitaeten des Unternehmens schritthaltend mit den Gesetzen und gesetzlichen Bestimmungen (GG, SGB, ethische Richtlinien)?",
						"L1 Recht und Haftung"));
		roleDimension.setWeight(2);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Recht und Haftung", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Stammdaten() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Geschaeftsfuehrung", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Kundenfokus (Vertrieb)", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Produktion",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Technologie (inkl. Entwicklung, Forschung und Entwicklung)",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Recht und Haftung", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Kommunikationstechnologie", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Mitarbeiterqualifikation", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Fuehrungskompetenz", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Logistikmanagement", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Qualitaetsmanagement", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Umweltmanagement", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Kontinuierliche Verbesserung, Lernen und Innovation",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Finanzen",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Firma",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Adresse",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Duns.-Nr.",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Telefonnummer (inkl. Laendervorwahl)", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Faxnummer (inkl. Laendervorwahl)", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"E-Mail-Adresse", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Homepage",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Unternehmensgruendung", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Rechtsform",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName("Industrie",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Konzernzugehoerigkeit", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"Wie viele Jahre besteht Gewaehrleistung bei Ihren Hauptprodukten?",
						"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Wie viele Monate wird Garantie gegeben?", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Wird Produkthaftung Uebernommen?", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Unternehmensgroesze", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Beschaeftigte in der Entwicklung", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Beschaeftigte in der Konstruktion", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Beschaeftigte in der Produktion", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Beschaeftigte im Qualitaets-Management", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Beschaeftigte im Verwaltungsbereich?", "L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"Wie hoch ist die durchschnittliche Leitungspanne?",
				"L0 Stammdaten"));
		roleDimension.setWeight(0);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Stammdaten", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Technologie_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"1.) Produktspektrum (Serienproduktion) der direkten und indirekten Produkte Liste der angebotenen Hauptprodukte/ Dienstleistungen:",
						"L0 Technologie"));
		roleDimension.setWeight(1.43f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"2.) Produktionsmethoden, -verfahren im Haus (siehe Annex 1.2 Uebersicht Be- und Verarbeitungs-kompetenzen):",
						"L0 Technologie"));
		roleDimension.setWeight(1.43f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"3.) Verarbeitete Materialien (siehe Annex 1.3 Uebersicht Materialgruppen):",
						"L0 Technologie"));
		roleDimension.setWeight(1.57f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"4.) Maschinenpark (Typ, Quantitaet, Volumen):",
				"L0 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"5.) Gibt es einen dokumentierten Entwicklungsprozess?",
				"L0 Technologie"));
		roleDimension.setWeight(0.3f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"6.) Ist ein Aenderungsprozess in der Entwicklung definiert?",
				"L0 Technologie"));
		roleDimension.setWeight(0.3f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Technologie", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Technologie_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"54.) Umfassen di e gesteuerten Prozesse die Verwendung einer geeigneten Produktions-, Instandhaltungs- und Wartungsausruestung?",
						"L1 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"55.) Zustand der Maschinen: Ist der Maschinenpark auf dem angemessenen Stand?",
						"L1 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"56.) Instandhaltung der Maschinen: Sind die Maschinen gepflegt und ausreichend gewartet?",
						"L1 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension.setDimension(getCompetenceDimensionByName(
				"57.) Sind Wartungs- und Instandhaltungsplaene vorhanden?",
				"L1 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"58.) Werden Verfahren und Einrichtungen erst nach einer dokumentierten Abnahme freigegeben, und bestehen angemessene Vorschriften zur Prozessauslegung?",
						"L1 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"59.) Werden Spezielle Prozesse (z.B.: Schweissen, Loeten, Galvanische Verfahren, Waermebehandlungen) systematisch abgesichert (z.B. durch entsprechende Pruefverfahren, wie Ultraschall, Risspruefung, Schichtdickenpruefung, Roentgenpruefung oder durch Verifizierung erprobter Prozessparameter)?",
						"L1 Technologie"));
		roleDimension.setWeight(0.71f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Technologie", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Umweltmanagement_L0() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"20.) Wie kann die Verpackung der gelieferten Produkte entsorgt werden?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"22.) Ist eine Ruecknahme der Produkte zu Entsorgungszwecken, gemaess den gesetzlichen Vorschriften, moeglich?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"27a.) Haben Sie ein Umweltmanagementsystem eingerichtet? zB nach Verordnung (EWG) 1836/93, DIN ISO 14001 oder anderen Verordnungen",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"27b.) zB nach Verordnung (EWG) 1836/93, DIN ISO 14001 oder anderen Verordnungen",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"28.) Haben Sie ein Zertifikat fuer Umweltmanagement: Wenn ja, welches?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(1);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"29a.) Wird Folgendes im Unternehmen auf Umweltvertraeglichkeit untersucht.",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"29b.) Wird Folgendes im Unternehmen auf Umweltvertraeglichkeit untersucht.",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"29c.) Wird Folgendes im Unternehmen auf Umweltvertraeglichkeit untersucht.",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"30.) Sind Umweltschutzaspekte fester Bestandteil Ihres Produkt- und Service-Designs?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"31.) Orientiert sich Ihr Unternehmen im Umweltschutz an schriftlich festgelegten Richtlinien?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"32.) Werden in Ihrem Unternehmen Umweltschutzmassnahmen und -ergebnisse definiert und dokumentiert",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"33.) Haben Sie in Ihrem Unternehmen Ziele zur Verbesserung des Umweltschutzes definiert und dokumentieren Sie deren?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"34.)Werden Ihre Mitarbeiter/-innen regelmaessig zum Thema Umweltschutz informiert und geschult?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"35.) Wirken Sie auf die Verbesserung des Umweltschutzes bei Ihren Lieferanten und Vertragspartnern hin?",
						"L0 Umweltmanagement"));
		roleDimension.setWeight(0.5f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L0 Umweltmanagement", listOfRoleDimensions);
	}

	private void insertDBTestDataRoleDimensions_Umweltmanagement_L1() {
		List<RoleDimension> listOfRoleDimensions = new ArrayList<RoleDimension>();

		RoleDimension roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"53.) Entspricht der Produktionsbereich den Erwartungen hinsichtlich Zustand, Organisation, Ordnung, Uebersicht und "
								+ "Sauberkeit in Bezug auf:\n- Optischer Eindruck (Boeden, Waende, Ecken, Tueren, Fenster)?"
								+ "\n- Abfalltrennung?"
								+ "\n- Sicherheitshinweise (Feuerloescher, Notausgaenge)?"
								+ "\n- Aktualitaet ausgehaengter Unterlagen?",
						"L1 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"126.) Werden Sicherheits- und Umweltvorschriften eingehalten? Werden Sicherheitsvorschriften einmal jaehrlich nach BGV A1 (allg. Vorschriften / UVV) ueberprueft und ergaenzt?",
						"L1 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimension = new RoleDimension();
		roleDimension
				.setDimension(getCompetenceDimensionByName(
						"127.) Ist der Energieverbrauch und der effiziente Gebrauch von Ressourcen ueberwacht und wird er verbessert? (zB durch reduzierten Papierkonsum, Rueckgabe von Druckerpatronen, etc.)?",
						"L1 Umweltmanagement"));
		roleDimension.setWeight(0.75f);
		roleDimensionRepository.addRoleDimension(roleDimension);
		listOfRoleDimensions.add(roleDimension);

		roleDimensions.put("L1 Umweltmanagement", listOfRoleDimensions);
	}

	public void setDimensions(TestDBDataDimensions dimensions) {
		this.dimensions = dimensions;
	}

	public void setRoleDimensionRepository(
			IRoleDimensionRepository roleDimensionRepository) {
		this.roleDimensionRepository = roleDimensionRepository;
	}

	public void setRoleDimensions(
			Hashtable<String, List<RoleDimension>> roleDimensions) {
		this.roleDimensions = roleDimensions;
	}

}
