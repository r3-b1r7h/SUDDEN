package biz.sudden.knowledgeData.competencesManagement.repository.hibernate.testDBData;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;

import biz.sudden.knowledgeData.competencesManagement.domain.Dimension;
import biz.sudden.knowledgeData.competencesManagement.repository.IDimensionRepository;

public class TestDBDataDimensions {

	Logger logger = Logger.getLogger(this.getClass());

	// Access to other entities
	private TestDBDataCategories categories;

	private TestDBDataCVIs cvis;

	// Repositories
	private IDimensionRepository dimensionRepository;
	// Memory Objects
	private Hashtable<String, List<Dimension>> dimensions = new Hashtable<String, List<Dimension>>();

	public TestDBDataCategories getCategories() {
		return categories;
	}

	public TestDBDataCVIs getCvis() {
		return cvis;
	}

	public IDimensionRepository getDimensionRepository() {
		return dimensionRepository;
	}

	public List<Dimension> getDimensionsOf(String competenceName) {
		if (!dimensions.containsKey(competenceName))
			logger.error("KEY not found!!!: " + competenceName);
		return dimensions.get(competenceName);
	}

	public void insertDBTestDataDimensions() {
		/* Adding Dimensions Test Data */
		dimensions.clear();
		insertDBTestDataDimensions_Stammdaten();
		insertDBTestDataDimensions_Technologie_L0();
		insertDBTestDataDimensions_Kommunikationstechnologie_L0();
		insertDBTestDataDimensions_RechtUndHaftung_L0();
		insertDBTestDataDimensions_Logistikmanagement_L0();
		insertDBTestDataDimensions_Umweltmanagement_L0();
		insertDBTestDataDimensions_Qualitatsmanagement_L0();
		insertDBTestDataDimensions_Kundenfokus_L0();
		insertDBTestDataDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L0();
		insertDBTestDataDimensions_Finanzen_L0();
		insertDBTestDataDimensions_Umweltmanagement_L1();
		insertDBTestDataDimensions_Technologie_L1();
		insertDBTestDataDimensions_Kommunikationstechnologie_L1();
		insertDBTestDataDimensions_Mitarbeiterqualifikation_L1();
		insertDBTestDataDimensions_Fuhrungskompetenz_L1();
		insertDBTestDataDimensions_Logistikmanagement_L1();
		insertDBTestDataDimensions_Qualitatsmanagement_L1();
		insertDBTestDataDimensions_RechtUndHaftung_L1();
		insertDBTestDataDimensions_Kundenfokus_L1();
		insertDBTestDataDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L1();
		insertDBTestDataDimensions_Finanzen_L1();
	}

	private void insertDBTestDataDimensions_Finanzen_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension.setCvi(cvis.getCVI("L0 Finanzen: Umsatz"));
		dimension.setName("47.) Umsatz in Mio. Euro in den letzten 3 Jahren");
		dimension.setDescription(dimension.getName());
		dimension.setEText("47.) Umsatz in Mio. Euro in den letzten 3 Jahren");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension.setCvi(cvis.getCVI("L0 Finanzen: Exportanteil"));
		dimension.setName("48.) Exportanteil in % des Gesamtumsatzes");
		dimension.setDescription(dimension.getName());
		dimension.setEText("48.) Exportanteil in % des Gesamtumsatzes");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension.setCvi(cvis.getCVI("L0 Finanzen: Exportanteil"));
		dimension.setName("49.) Einkaufsvolumen in % vom Gesamtumsatz");
		dimension.setDescription(dimension.getName());
		dimension.setEText("49.) Einkaufsvolumen in % vom Gesamtumsatz");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension.setCvi(cvis.getCVI("L0 Finanzen: Deckungsbeitrag"));
		dimension
				.setName("50.) Ist der Deckungsbeitrag steigend, gleichbleibend oder ruecklaeufig?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("50.) Ist der Deckungsbeitrag steigend, gleichbleibend oder ruecklaeufig?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension.setCvi(cvis.getCVI("L0 Finanzen: Materialanteil"));
		dimension
				.setName("51.) Zusammensetzung der Herstellkosten, Materialanteil in % der Herstellkosten");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("51.) Zusammensetzung der Herstellkosten, Materialanteil in % der Herstellkosten");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension.setCvi(cvis.getCVI("L0 Finanzen: Lohnanteil"));
		dimension
				.setName("52.) Zusammensetzung der Herstellkosten, Lohnanteil in % der Herstellkosten");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("52.) Zusammensetzung der Herstellkosten, Lohnanteil in % der Herstellkosten");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Finanzen", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Finanzen_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("144.) Wird der Finanzplan an die Ziele und Prioritaeten des Unternehmens angepasst? Wird die Verwendung von finanziellen Mitteln betreffend Effizienz evaluiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("144.) Wird der Finanzplan an die Ziele und Prioritaeten des Unternehmens angepasst? Wird die Verwendung von finanziellen Mitteln betreffend Effizienz evaluiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("145.) Werden alle Moeglichkeiten fuer die Entwicklung von neuen finanziellen Quellen weitgehend genutzt (z.B. staatliche Subventionen, bundesweite Foerderungen, EU-Programme, Spenden)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("145.) Werden alle Moeglichkeiten fuer die Entwicklung von neuen finanziellen Quellen weitgehend genutzt (z.B. staatliche Subventionen, bundesweite Foerderungen, EU-Programme, Spenden)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("146.) Werden Betriebsanlagen, Ausstattung und Material effektiv und effizient genutzt um die Ziele zu erreichen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("146.) Werden Betriebsanlagen, Ausstattung und Material effektiv und effizient genutzt um die Ziele zu erreichen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("147.) Gibt es Wartungsvertraege und Gebrauchsanweisungen die die Wartung von Betriebsanlagen und Maschinen sicherstellen? Umweltressourcen (Energie, Muell) werden in verantwortungsbewusster Weise genutzt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("147.) Gibt es Wartungsvertraege und Gebrauchsanweisungen die die Wartung von Betriebsanlagen und Maschinen sicherstellen? Umweltressourcen (Energie, Muell) werden in verantwortungsbewusster Weise genutzt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension.setWeightMultiplier(0.5f);
		dimension
				.setName("148a) Werden Arbeitsstunden (als limitierte Ressourcen) zweckmaessig geplant?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("148a) Werden Arbeitsstunden (als limitierte Ressourcen) zweckmaessig geplant?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension.setWeightMultiplier(0.5f);
		dimension
				.setName("148b) Aufgewendete Zeiten werden auf ihre Effizienz ueberwacht.");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("148b) Aufgewendete Zeiten werden auf ihre Effizienz ueberwacht.");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("149.) Fuer die weitere Entwicklung des Unternehmens stehen ausreichend finanzielles und zeitliches Budget zur Verfuegung.");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("149.) Fuer die weitere Entwicklung des Unternehmens stehen ausreichend finanzielles und zeitliches Budget zur Verfuegung.");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Finanzen")
				.getId());
		dimension.setCategoryName("Finanzen");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 3, 0]"));
		dimension
				.setName("150.) Wird die Erreichung von Zielen und Meilensteinen kontinuierlich beobachtet und evaluiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("150.) Wird die Erreichung von Zielen und Meilensteinen kontinuierlich beobachtet und evaluiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Finanzen", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Fuhrungskompetenz_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [7.5, 5, 2.5, 0]"));
		dimension
				.setName("75a) Reagiert das Management auf interne und externe Veraenderungen mit konkreten Aenderungsplaenen und Massnahmen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("75a) Reagiert das Management auf interne und externe Veraenderungen mit konkreten Aenderungsplaenen und Massnahmen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension.setWeightMultiplier(0.25f);
		dimension
				.setName("75b) Werden Ressourcen frei gestellt um diese Veraenderungen zu unterstuetzen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("75b) Werden Ressourcen frei gestellt um diese Veraenderungen zu unterstuetzen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 2.5, 0]"));
		dimension
				.setName("76.) Nimmt das Management aktiv an Verbesserungen teil und fuerdert es die Zusammenarbeit im Team / teamuebergreifend?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("76.) Nimmt das Management aktiv an Verbesserungen teil und fuerdert es die Zusammenarbeit im Team / teamuebergreifend?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 2.5, 0]"));
		dimension
				.setName("77.) Beobachtet das Management sein eigenes Verhalten und verbessert es dieses aktiv?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("77.) Beobachtet das Management sein eigenes Verhalten und verbessert es dieses aktiv?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [5, 2.5, 0, 0]"));
		dimension
				.setName("78a) Garantiert das Management eine systematische Beschreibung und Koordination von operativen Ablaeufen, und Verantwortungen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("78a) Garantiert das Management eine systematische Beschreibung und Koordination von operativen Ablaeufen, und Verantwortungen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [5, 2.5, 0, 0]"));
		dimension
				.setName("78b) Sind Prozess-Ziele, Job-Beschreibungen, etc. dokumentiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("78b) Sind Prozess-Ziele, Job-Beschreibungen, etc. dokumentiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("79.) Achtet das Management aktiv auf Kundenrueckmeldungen und verwendet es diese um Verbesserungsmassnahmen abzuleiten?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("79.) Achtet das Management aktiv auf Kundenrueckmeldungen und verwendet es diese um Verbesserungsmassnahmen abzuleiten?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 2.5, 0]"));
		dimension
				.setName("80.) Fuehrt das Management aktiv strukturierte Mitarbeitergespraeche durch?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("80.) Fuehrt das Management aktiv strukturierte Mitarbeitergespraeche durch?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("81.) Unterstuetzt das Management ihre Mitarbeiter in der Realisierung von Zielen und fuerdert das Management persoenliche Entwicklung und Ausbildung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("81.) Unterstuetzt das Management ihre Mitarbeiter in der Realisierung von Zielen und fuerdert das Management persoenliche Entwicklung und Ausbildung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("82.) Werden die Beduerfnisse und Erwartungen von Mitarbeitern gefuerdert und respektiert bei der Definition von Mitarbeiterzielen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("82.) Werden die Beduerfnisse und Erwartungen von Mitarbeitern gefuerdert und respektiert bei der Definition von Mitarbeiterzielen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [5, 2.5, 0, 0]"));
		dimension
				.setName("83a) Werden in Interviews die Beduerfnisse und Erwartungen von Geschaeftspartnern und Stakeholdern erhoeht?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("83a) Werden in Interviews die Beduerfnisse und Erwartungen von Geschaeftspartnern und Stakeholdern erhoeht?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [5, 2.5, 0, 0]"));
		dimension
				.setName("83b) Wird diese Information bei Zieldefinitionen beruecksichtigt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("83b) Wird diese Information bei Zieldefinitionen beruecksichtigt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("84.)Sind die Ergebnisse von jedem Arbeitstag, von Selbstbewertungen und Qualitaetszirkeln gesammelt und in Verwendung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("84.)Sind die Ergebnisse von jedem Arbeitstag, von Selbstbewertungen und Qualitaetszirkeln gesammelt und in Verwendung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [5, 2.5, 0, 0]"));
		dimension
				.setName("85a) Sind Vision und Ziele bei den Mitarbeitern und Kunden gut bekannt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("85a) Sind Vision und Ziele bei den Mitarbeitern und Kunden gut bekannt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [5, 2.5, 0, 0]"));
		dimension
				.setName("85b) Werden sie in verstaendlicher Weise kommuniziert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("85b) Werden sie in verstaendlicher Weise kommuniziert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("86.) Gibt es einen strukturierten Prozess der beschreibt wie Verbesserungsvorschlaege, Ziele, Strategien und Konzepte des Unternehmens erstellt und geprueft werden?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("86.) Gibt es einen strukturierten Prozess der beschreibt wie Verbesserungsvorschlaege, Ziele, Strategien und Konzepte des Unternehmens erstellt und geprueft werden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("87.) Ist die Beschaeftigung von Mitarbeitern geplant und gesteuert betreffend ihrer Qualifikationen und auch geschlechtsspezifischen Kompetenzen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("87.) Ist die Beschaeftigung von Mitarbeitern geplant und gesteuert betreffend ihrer Qualifikationen und auch geschlechtsspezifischen Kompetenzen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("88.) Werden Kompetenzen, Faehigkeiten und Wissen von Mitarbeitern durch zielgerichtete Planung und Ausbildung weiterentwickelt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("88.) Werden Kompetenzen, Faehigkeiten und Wissen von Mitarbeitern durch zielgerichtete Planung und Ausbildung weiterentwickelt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("89.) Indirekt gemessene Variablen, zB Ruhetage/Krankenstand, personelle Fluktuation erlauben den Grad der Zufriedenheit von Vollzeitbeschaeftigten zu erkennen. Werden diese aufgezeichnet und analysiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("89.) Indirekt gemessene Variablen, zB Ruhetage/Krankenstand, personelle Fluktuation erlauben den Grad der Zufriedenheit von Vollzeitbeschaeftigten zu erkennen. Werden diese aufgezeichnet und analysiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension.setCvi(cvis.getCVI("Ja / Nein / teilweise"));
		dimension
				.setName("90a) Existieren Richtlinien und Regeln fuer das Strukturieren und Planen von Besprechungen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("90a) Existieren Richtlinien und Regeln fuer das Strukturieren und Planen von Besprechungen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Fuehrungskompetenz").getId());
		dimension.setCategoryName("Fuehrungskompetenz");
		dimension.setCvi(cvis.getCVI("Ja / Nein / teilweise"));
		dimension
				.setName("90b) Sind diese Richtlinien in Verwendung und werden sie regelmaessig auf Ihre Effektivitaet ueberprueft?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("90b) Sind diese Richtlinien in Verwendung und werden sie regelmaessig auf Ihre Effektivitaet ueberprueft?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Fuehrungskompetenz", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Kommunikationstechnologie_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis
				.getCVI("L0 Kommunikationstechnologie: CAE/CAM/CAD"));
		dimension.setName("7.) Welche CAE/CAD/ CAM - Tools werden verwendet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("Liste der moeglichen Tools und Versionen fuer computergestuetzte Designer, computergestuetzte Produktion, computergestuetzte Engineering Tools:");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension
				.setCvi(cvis
						.getCVI("L0 Kommunikationstechnologie: CAD Daten-Austausch Tools"));
		dimension
				.setName("8.) Welche CAD Daten-Austausch Tools werden verwendet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("8.) Welche CAD Daten-Austausch Tools werden verwendet?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension
				.setCvi(cvis.getCVI("L0 Kommunikationstechnologie: EDI Tools"));
		dimension
				.setName("9.) Haben Sie EDI Tools (Z.B. Konverter fuer Electronic Data Interchange) in Verwendung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("9.) Haben Sie EDI Tools (Z.B. Konverter fuer Electronic Data Interchange) in Verwendung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis
				.getCVI("L0 Kommunikationstechnologie: EDI Standards"));
		dimension.setName("10.) Welche EDI-Standards sind implementiert?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("10.) Welche EDI-Standards sind implementiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis
				.getCVI("L0 Kommunikationstechnologie: ERP-System"));
		dimension.setName("11.) Verwenden Sie ein ERP-System?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("11.) Verwenden Sie ein ERP-System?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis
				.getCVI("L0 Kommunikationstechnologie: PPS-System"));
		dimension.setName("12.) Verwenden Sie ein PPS-System?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("12.) Verwenden Sie ein PPS-System?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis
				.getCVI("L0 Kommunikationstechnologie: CRM-System"));
		dimension.setName("13.) Verwenden Sie ein CRM-System?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("13.) Verwenden Sie ein CRM-System?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis
				.getCVI("L0 Kommunikationstechnologie: SRM-System"));
		dimension.setName("14.) Verwenden Sie ein SRM-System?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("14.) Verwenden Sie ein SRM-System?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension
				.setCvi(cvis
						.getCVI("L0 Kommunikationstechnologie: Videokonferenzen-System"));
		dimension
				.setName("15.) Verwenden Sie ein System fuer Videokonferenzen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("15.) Verwenden Sie ein System fuer Videokonferenzen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Kommunikationstechnologie", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Kommunikationstechnologie_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis.getCVI("L1 Kommunikationstechnologie: 0%-100%"));
		dimension
				.setName("60.) Entspricht die Informationstechnik dem Stand der Technik?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("60.) Entspricht die Informationstechnik dem Stand der Technik?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("61.) Welche Informationssysteme sind vorhanden? Sind diese integriert bzw. stehen angemessene Schnittstellen zur Verfuegung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("61.) Welche Informationssysteme sind vorhanden? Sind diese integriert bzw. stehen angemessene Schnittstellen zur Verfuegung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Welche"));
		dimension
				.setName("62.) Werden Sicherungsmassnahmen hinsichtlich des Datenschutzes durchgefuehrt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("62.) Werden Sicherungsmassnahmen hinsichtlich des Datenschutzes durchgefuehrt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Welche"));
		dimension.setName("63.) Sind Virenschutzmassnahmen vorhanden?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("63.) Sind Virenschutzmassnahmen vorhanden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("64.) Sind alle Abteilungen miteinander vernetzt und haben E-Mail Moeglichkeit?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("64.) Sind alle Abteilungen miteinander vernetzt und haben E-Mail Moeglichkeit?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Kommunikationstechnologie").getId());
		dimension.setCategoryName("Kommunikationstechnologie");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 4, 0]"));
		dimension
				.setName("65.) Weiss die Oeffentlichkeit Bescheid ueber die Aktivitaeten des Unternehmens, dessen Ziele und Ergebnisse? (zB ueber Zeitungsartikel, Radio- oder TV-Beitraege, Veranstaltungen)? ");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("65.) Weiss die Oeffentlichkeit Bescheid ueber die Aktivitaeten des Unternehmens, dessen Ziele und Ergebnisse? (zB ueber Zeitungsartikel, Radio- oder TV-Beitraege, Veranstaltungen)? ");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Kommunikationstechnologie", listOfDimensions);
	}

	private void insertDBTestDataDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("L0 KontinuierlicheVerbesserungLernenUndInnovation: Umfang"));
		dimension
				.setName("41.) In welchem Umfang (in %) wird in Ihrem Hause Eigenentwicklung betrieben?(0% bedeutet dass Sie aufgrund vom Kunden beigesteuerter Zeichnungen produzieren)");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("41.) In welchem Umfang (in %) wird in Ihrem Hause Eigenentwicklung betrieben?(0% bedeutet dass Sie aufgrund vom Kunden beigesteuerter Zeichnungen produzieren)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("L0 KontinuierlicheVerbesserungLernenUndInnovation: Forschungsarbeit"));
		dimension
				.setName("42.) Wie viel Forschungsarbeit leisten Sie? (Wie hoch ist Ihre F&E-Quote?: Anteil an Forschungs- und Entwicklungsarbeit in % vom Umsatz)");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("42.) Wie viel Forschungsarbeit leisten Sie? (Wie hoch ist Ihre F&E-Quote?: Anteil an Forschungs- und Entwicklungsarbeit in % vom Umsatz)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("L0 KontinuierlicheVerbesserungLernenUndInnovation: Patente"));
		dimension
				.setName("43.) Wie viele Patente haben Sie (inkl. Patent-Nr.)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("43.) Wie viele Patente haben Sie (inkl. Patent-Nr.)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Welche"));
		dimension
				.setName("44.) Haben Sie Testmethoden/Pruefverfahren im Einsatz? Wenn ja, welche?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("44.) Haben Sie Testmethoden/Pruefverfahren im Einsatz? Wenn ja, welche?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension.setName("45.) Sind Sie in der Lage Prototypen zu bauen?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("45.) Sind Sie in der Lage Prototypen zu bauen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("L0 KontinuierlicheVerbesserungLernenUndInnovation: Methoden des Prototypings"));
		dimension
				.setName("46.) Welche Methoden/Verfahren des Prototypings verwenden Sie (siehe Annex 1.4 Methoden des Prototypings)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("46.) Welche Methoden/Verfahren des Prototypings verwenden Sie (siehe Annex 1.4 Methoden des Prototypings)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 KontinuierlicheVerbesserungLernenUndInnovation",
				listOfDimensions);
	}

	private void insertDBTestDataDimensions_KontinuierlicheVerbesserungLernenUndInnovation_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("134.) Gibt es einen strukturierten Rahmen in dem innovative und kreative Ideen diskutiert werden koennen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("134.) Gibt es einen strukturierten Rahmen in dem innovative und kreative Ideen diskutiert werden koennen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("135.) Mitarbeiter wirken in kontinuierlichen Verbesserungsprozessen mit. zB in Form von gemeinsamen Auseinandersetzungen und Qualitaetszirkeln");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("135.) Mitarbeiter wirken in kontinuierlichen Verbesserungsprozessen mit. zB in Form von gemeinsamen Auseinandersetzungen und Qualitaetszirkeln");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("136.) Werden neue, innovative, zukunftsweisende Kommunikationstechnologien, zB Internet eingefuehrt und optimal genuetzt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("136.) Werden neue, innovative, zukunftsweisende Kommunikationstechnologien, zB Internet eingefuehrt und optimal genuetzt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("137.) Werden neue, innovative, zukunftsweisende Methoden- als Ergaenzung zu geprueften und etablierten Beratungsansaetzen und -konzepten - evaluiert und optimal eingesetzt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("137.) Werden neue, innovative, zukunftsweisende Methoden- als Ergaenzung zu geprueften und etablierten Beratungsansaetzen und -konzepten - evaluiert und optimal eingesetzt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("138.) Werden technische und sozialpolitische Informationen, zB aus technischen Fachzeitschriften systematisch gesammelt und unterstuetzt es die Zielerreichung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("138.) Werden technische und sozialpolitische Informationen, zB aus technischen Fachzeitschriften systematisch gesammelt und unterstuetzt es die Zielerreichung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("139.) Wird Wissen und Fachkompetenz kontinuierlich erworben, zB durch erweiterte Schulungsmassnahmen, Fachkonferenzen, und unterstuetzt es die Zielerreichung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("139.) Wird Wissen und Fachkompetenz kontinuierlich erworben, zB durch erweiterte Schulungsmassnahmen, Fachkonferenzen, und unterstuetzt es die Zielerreichung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("140.) Sind die Moeglichkeiten fuer Verbesserungen identifiziert und dokumentiert, zB durch verbale oder schriftliche Befragungen, durch Reflexion im Team und gemeinsam mit Kunden, durch Vergleiche mit anderen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("140.) Sind die Moeglichkeiten fuer Verbesserungen identifiziert und dokumentiert, zB durch verbale oder schriftliche Befragungen, durch Reflexion im Team und gemeinsam mit Kunden, durch Vergleiche mit anderen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension
				.setName("141.) Werden Moeglichkeiten fuer Prozessverbesserungen priorisiert, implementiert und auf Effektivitaet ueberprueft?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("141.) Werden Moeglichkeiten fuer Prozessverbesserungen priorisiert, implementiert und auf Effektivitaet ueberprueft?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("142.) Bringen Mitarbeiter Verbesserungsideen ein, arbeiten sie in Qualitaetszirkeln zusammen, und unterstuetzen sie aktiv die Implementierung von Verbesserungsmassnahmen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("142.) Bringen Mitarbeiter Verbesserungsideen ein, arbeiten sie in Qualitaetszirkeln zusammen, und unterstuetzen sie aktiv die Implementierung von Verbesserungsmassnahmen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Lernen und Innovation").getId());
		dimension.setCategoryName("Lernen und Innovation");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("143.) Werden die implementierten Verbesserungsmassnahmen kontinuierlich ueberprueft, analysiert und wenn notwendig korrigiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("143.) Werden die implementierten Verbesserungsmassnahmen kontinuierlich ueberprueft, analysiert und wenn notwendig korrigiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 KontinuierlicheVerbesserungLernenUndInnovation",
				listOfDimensions);
	}

	private void insertDBTestDataDimensions_Kundenfokus_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension.setCvi(cvis.getCVI("L0 Kundenfokus: Automotiven"));
		dimension
				.setName("36.) Haben Sie automotive Kunden? Wenn ja, benennen Sie Ihre automotiven Kunden (Top 10).");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("36.) Haben Sie automotive Kunden? Wenn ja, benennen Sie Ihre automotiven Kunden (Top 10).");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension.setCvi(cvis.getCVI("L0 Kundenfokus: Hauptkunden"));
		dimension
				.setName("37.) Benennen Sie Ihre Hauptkunden (Top 10 Kunden in Bezug auf Umsatz):");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("37.) Benennen Sie Ihre Hauptkunden (Top 10 Kunden in Bezug auf Umsatz):");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension.setCvi(cvis.getCVI("L0 Kundenfokus: Laendern"));
		dimension.setName("38.) Aus welchen Laendern sind Ihre Kunden?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("38.) Aus welchen Laendern sind Ihre Kunden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension.setCvi(cvis.getCVI("L0 Kundenfokus: Produkten"));
		dimension
				.setName("39.) Welche unterstuetzenden Leistungen als Ergaenzung zu den angebotenen Produkten koennen Sie uebernehmen? (Bzw. welche Rollen koennen Sie uebernehmen?)");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("39.) Welche unterstuetzenden Leistungen als Ergaenzung zu den angebotenen Produkten koennen Sie uebernehmen? (Bzw. welche Rollen koennen Sie uebernehmen?)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension.setCvi(cvis
				.getCVI("L0 Kundenfokus: Qualitaetssicherungsvereinbarungen"));
		dimension
				.setName("40.) Gibt es Qualitaetssicherungsvereinbarungen mit Ihren Kunden? Wenn ja, mit wem? (Bitte benennen Sie max. 10 Kunden)");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("40.) Gibt es Qualitaetssicherungsvereinbarungen mit Ihren Kunden? Wenn ja, mit wem? (Bitte benennen Sie max. 10 Kunden)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Kundenfokus", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Kundenfokus_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("128.) Verfuegt der Zulieferer ueber Plaene, wie im Notfall die Versorgung des Kunden sicherzustellen ist?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("128.) Verfuegt der Zulieferer ueber Plaene, wie im Notfall die Versorgung des Kunden sicherzustellen ist?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("129.) Alle Prozesse, speziell die fuer den Kunden wertschoepfend sind, sind bekannt und dokumentiert.");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("129.) Alle Prozesse, speziell die fuer den Kunden wertschoepfend sind, sind bekannt und dokumentiert.");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("130.) Sind die Ziele der (Sub-) Prozesse festgelegt und existieren anschauliche Messungen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("130.) Sind die Ziele der (Sub-) Prozesse festgelegt und existieren anschauliche Messungen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 1, 0]"));
		dimension
				.setName("131.) Werden Die Erwartungen von verschiedenen Kundengruppen evaluiert und beim Gestalten von Prozessen und Produkten beruecksichtigt (Infoblatt)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("131.) Werden Die Erwartungen von verschiedenen Kundengruppen evaluiert und beim Gestalten von Prozessen und Produkten beruecksichtigt (Infoblatt)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 1, 0]"));
		dimension
				.setName("132.) Sind weitere Massnahmen installiert um Kundenzufriedenheit zu dokumentieren? (zB Anzahl an Dank-Schreiben, Entwicklung der Reklamationszahlen, Anzahl der beratungssuchenden Kunden, verbesserte Antwortzeit auf Anfragen)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("132.) Sind weitere Massnahmen installiert um Kundenzufriedenheit zu dokumentieren? (zB Anzahl an Dank-Schreiben, Entwicklung der Reklamationszahlen, Anzahl der beratungssuchenden Kunden, verbesserte Antwortzeit auf Anfragen)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [6, 4, 1, 0]"));
		dimension
				.setName("133a) Werden Reklamationen und Verbesserungsvorschlaege systematisch dokumentiert und analysiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("133a) Werden Reklamationen und Verbesserungsvorschlaege systematisch dokumentiert und analysiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Kundenfokus")
				.getId());
		dimension.setCategoryName("Kundenfokus");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [4, 3, 1, 0]"));
		dimension
				.setName("133b) Fliessen die Ergebnisse der Analyse in Verbesserungsprojekte ein?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("133b) Fliessen die Ergebnisse der Analyse in Verbesserungsprojekte ein?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Kundenfokus", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Logistikmanagement_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension.setCvi(cvis
				.getCVI("L0 Logistikmanagement: Number of Suppliers"));
		dimension
				.setName("18.) Hauptlieferanten (Top 15): Bitte geben Sie hierfuer die Duns-Nummer der Unternehmen an!");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("(DUNS steht fuer 'Data Universal Numbering System'. Es ist ein einmaliges Nummersystem mit "
						+ "neun Ziffern, dass zur Kennzeichnung von Unternehmen verwendet wird. Sie koennen Ihre DUNS-Nummer unter "
						+ "folgender Adresse suchen http://smallbusiness.dnb.com)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension.setCvi(cvis
				.getCVI("L0 Logistikmanagement: Number of Countries"));
		dimension
				.setName("19.) Aus welchen Laendern kaufen Sie zu? (Alle Laender aus denen Sie beliefert werden)");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("19.) Aus welchen Laendern kaufen Sie zu? (Alle Laender aus denen Sie beliefert werden)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension.setCvi(cvis.getCVI("L0 Logistikmanagement: Logistiksysteme"));
		dimension
				.setName("23.) Welche Logistiksysteme werden von Ihnen bereits verwendet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("23.) Welche Logistiksysteme werden von Ihnen bereits verwendet?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension.setCvi(cvis
				.getCVI("L0 Logistikmanagement: Transportlogistik"));
		dimension
				.setName("24.) Wie wird Transportlogistik fuer genannte Logistiksysteme durchgefuehrt:");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("24.) Wie wird Transportlogistik fuer genannte Logistiksysteme durchgefuehrt:");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Logistikmanagement", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Logistikmanagement_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("91.) Sind die Transporteinrichtungen angemessen ausgewaehlt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("91.) Sind die Transporteinrichtungen angemessen ausgewaehlt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension.setName("92.) Ist die Lagerung zweckmaessig?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("92.) Ist die Lagerung zweckmaessig?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("93.) Ist das Handling der Waren angemessen, und sind die noetigen Hilfseinrichtungen vorhanden?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("93.) Ist das Handling der Waren angemessen, und sind die noetigen Hilfseinrichtungen vorhanden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 2.5, 0]"));
		dimension
				.setName("94.) Bestehen entsprechende Bewertungsgrundlagen fuer die Lieferzuverlaessigkeit vorhandener Kunden und welche Einstufung wurde getroffen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("94.) Bestehen entsprechende Bewertungsgrundlagen fuer die Lieferzuverlaessigkeit vorhandener Kunden und welche Einstufung wurde getroffen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 2.5, 0]"));
		dimension
				.setName("95.) Existiert ein System nach dem die Lieferzuverlaessigkeit von Unterlieferanten ueberwacht wird? Schliesst es den Nachweis entsprechender Korrekturmassnahmen ein?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("95.) Existiert ein System nach dem die Lieferzuverlaessigkeit von Unterlieferanten ueberwacht wird? Schliesst es den Nachweis entsprechender Korrekturmassnahmen ein?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein / teilweise"));
		dimension
				.setName("96a) Gibt es ein System zur Rueckverfolgbarkeit von: Teilen und Baugruppen / Stuecklisten oder aehnlichem?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("96a) Gibt es ein System zur Rueckverfolgbarkeit von: Teilen und Baugruppen / Stuecklisten oder aehnlichem?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Logistikmanagement").getId());
		dimension.setCategoryName("Logistikmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein / teilweise"));
		dimension
				.setName("96b) Existieren Querverweise auf technischen Zeichnungen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("96b) Existieren Querverweise auf technischen Zeichnungen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Logistikmanagement", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Mitarbeiterqualifikation_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("66.) Sind Ihre Mitarbeiter im Umgang mit Pruefmittel und Pruefeinrichtungen hinreichend geschult?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("66.) Sind Ihre Mitarbeiter im Umgang mit Pruefmittel und Pruefeinrichtungen hinreichend geschult?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("67.) Sind die Mitarbeiter fuer die auszufuehrenden Taetigkeiten hinreichend qualifiziert und wird die Qualifikation durch Schulungsmassnahmen angepasst?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("67.) Sind die Mitarbeiter fuer die auszufuehrenden Taetigkeiten hinreichend qualifiziert und wird die Qualifikation durch Schulungsmassnahmen angepasst?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("68.) Werden Ihre Mitarbeiter regelmaessig bezueglich Arbeitssicherheit unterwiesen (Krane, Gabelstapler, Gefahrstoffe)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("68.) Werden Ihre Mitarbeiter regelmaessig bezueglich Arbeitssicherheit unterwiesen (Krane, Gabelstapler, Gefahrstoffe)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("69.) Werden entsprechende Schulungsaufzeichnungen gepflegt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("69.) Werden entsprechende Schulungsaufzeichnungen gepflegt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("70.) Sind Kompetenzen, Faehigkeiten, und Wissen der Mitarbeiter  zB in Mitarbeitergespraechen dokumentiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("70.) Sind Kompetenzen, Faehigkeiten, und Wissen der Mitarbeiter  zB in Mitarbeitergespraechen dokumentiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("71.) Werden Schulungsmassnahmen und Wissen den Kollegen strukturiert praesentiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("71.) Werden Schulungsmassnahmen und Wissen den Kollegen strukturiert praesentiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("72.) Sind Faehigkeiten und Wissen von Mitarbeitern determiniert und werden sie gefuerdert mit fortgeschrittenen Schulungsmassnahmen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("72.) Sind Faehigkeiten und Wissen von Mitarbeitern determiniert und werden sie gefuerdert mit fortgeschrittenen Schulungsmassnahmen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("73.) Gibt es eine Dokumentation ueber Rueckmeldungen (Mitarbeitergespraech) ueber den Motivationsgrad der Mitarbeiter betreffend Arbeitsklima, Kommunikation, Teilnahme, Anschluss, Anerkennung und Selbstverantwortung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("73.) Gibt es eine Dokumentation ueber Rueckmeldungen (Mitarbeitergespraech) ueber den Motivationsgrad der Mitarbeiter betreffend Arbeitsklima, Kommunikation, Teilnahme, Anschluss, Anerkennung und Selbstverantwortung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Mitarbeiterqualifikation").getId());
		dimension.setCategoryName("Mitarbeiterqualifikation");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("74.) Erwerben Mitarbeiter aktiv noetige Kompetenzen und bringen sie Ideen von fortgeschrittenen Schulungsmassnahmen in das Unternehmen ein?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("74.) Erwerben Mitarbeiter aktiv noetige Kompetenzen und bringen sie Ideen von fortgeschrittenen Schulungsmassnahmen in das Unternehmen ein?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Mitarbeiterqualifikation", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Qualitatsmanagement_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension.setCvi(cvis.getCVI("Qualitaetsmanagement, L0: Audit"));
		dimension
				.setName("26.) Zulassungen / Zertifizierungen / Validierungen / Auszeichnungen.");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("26.) Zulassungen / Zertifizierungen / Validierungen / Auszeichnungen.");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Qualitaetsmanagement", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Qualitatsmanagement_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("97.) Wie gut ist die Qualitaetssicherung organisiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("97.) Wie gut ist die Qualitaetssicherung organisiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("98.) Werden Prozess - FMEA (Fehler- Moeglichkeits- und Einfluss-Analysen) durchgefuehrt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("98.) Werden Prozess - FMEA (Fehler- Moeglichkeits- und Einfluss-Analysen) durchgefuehrt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("99.) Werden Prozessfaehigkeitsuntersuchungen/Statistical Process Control (SPC)? verwendet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("99.) Werden Prozessfaehigkeitsuntersuchungen/Statistical Process Control (SPC)? verwendet?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Qualitaetsmanagement, L1: Prozessfaehigkeitsuntersuchungen"));
		dimension
				.setName("100.) Welche Formen der Prozessfaehigkeitsuntersuchungen / Statistical Process Control werden verwendet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("100.) Welche Formen der Prozessfaehigkeitsuntersuchungen / Statistical Process Control werden verwendet?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("101.) Verwenden Sie Auswertung der Massahmen fuer SPC?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("101.) Verwenden Sie Auswertung der Massahmen fuer SPC?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("102.) Verwenden Sie Prozessfaehigkeits- und Maschinenuntersuchungen (Cp und Cpk)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("102.) Verwenden Sie Prozessfaehigkeits- und Maschinenuntersuchungen (Cp und Cpk)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7, 3, 0]"));
		dimension.setName("103.) Verwenden Sie Prozessbeschreibungen?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("103.) Verwenden Sie Prozessbeschreibungen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("104.) Fuehren Sie Variantenreduktionskennzahlen und ueberpruefen Sie diese?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("104.) Fuehren Sie Variantenreduktionskennzahlen und ueberpruefen Sie diese?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("105.) Verwenden Sie experimentelles/auf Erfahrung begruendetes Design?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("105.) Verwenden Sie experimentelles/auf Erfahrung begruendetes Design?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("106.) Verwenden Sie Qualitaets-Problemloesungstechniken?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("106.) Verwenden Sie Qualitaets-Problemloesungstechniken?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension.setName("107.) Verwenden Sie Ursachen- Wirkungsdiagramme?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("107.) Verwenden Sie Ursachen- Wirkungsdiagramme?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension.setName("108.) Haben Sie ein Materialpruef-Labor?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("108.) Haben Sie ein Materialpruef-Labor?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 0, 0, 0]"));
		dimension
				.setName("109.) Wird das Qualitaetsbewusstsein aller Mitarbeiter gefuerdert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("109.) Wird das Qualitaetsbewusstsein aller Mitarbeiter gefuerdert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("110.) Prueft die oberste Leitung des Lieferanten die Wirksamkeit des QM - Systems in festgelegten Zeitabstaenden, um die Eignung und Effektivitaet zu gewaehrleisten?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("110.) Prueft die oberste Leitung des Lieferanten die Wirksamkeit des QM - Systems in festgelegten Zeitabstaenden, um die Eignung und Effektivitaet zu gewaehrleisten?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("111.) Gibt es einen dokumentierten Prozess zur Messung der Kundenzufriedenheit einschliesslich der Haeufigkeit der Festlegung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("111.) Gibt es einen dokumentierten Prozess zur Messung der Kundenzufriedenheit einschliesslich der Haeufigkeit der Festlegung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("112.) Hat der Lieferant geeignete Projekte zur Qualitaets- und Produktivitaets-verbesserung eingefuehrt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("112.) Hat der Lieferant geeignete Projekte zur Qualitaets- und Produktivitaets-verbesserung eingefuehrt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("113.) Werden Qualitaetsaufzeichnungen ueber zugelassene Unterlieferanten erstellt und gepflegt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("113.) Werden Qualitaetsaufzeichnungen ueber zugelassene Unterlieferanten erstellt und gepflegt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("114.) Sind die eingesetzten Pruefmittel und Pruefeinrichtungen geeignet, um die geforderte Messaufgabe zu erfuellen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("114.) Sind die eingesetzten Pruefmittel und Pruefeinrichtungen geeignet, um die geforderte Messaufgabe zu erfuellen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("115.) Sind Pruefgeraete (z.B. 3-Koordinatenmessmaschine, Schwingungsmessgeraet, Temperaturmessgeraet, Geraeuschpegelmessgeraet) vorhanden?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("115.) Sind Pruefgeraete (z.B. 3-Koordinatenmessmaschine, Schwingungsmessgeraet, Temperaturmessgeraet, Geraeuschpegelmessgeraet) vorhanden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("116.) Werden die Pruefmittel und Pruefeinrichtungen regelmaessig ueberprueft?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("116.) Werden die Pruefmittel und Pruefeinrichtungen regelmaessig ueberprueft?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension.setName("117.) Gibt es einen QM - Plan (Kontrollplan)?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("117.) Gibt es einen QM - Plan (Kontrollplan)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("118.) Gibt es einen Messraum und wird dort unter geeigneten Bedingungen gearbeitet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("118.) Gibt es einen Messraum und wird dort unter geeigneten Bedingungen gearbeitet?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("119.) Werden kaufmaennische und technische Wareneingangspruefungen durchgefuehrt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("119.) Werden kaufmaennische und technische Wareneingangspruefungen durchgefuehrt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("120.) Gibt es eine Festlegung fuer die Archivierung von Pruefnachweisen von Lieferanten?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("120.) Gibt es eine Festlegung fuer die Archivierung von Pruefnachweisen von Lieferanten?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("121.) Ist eine vollstaendige Endpruefung nach aktuellem Aenderungsstand gewaehrleistet und wird diese dokumentiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("121.) Ist eine vollstaendige Endpruefung nach aktuellem Aenderungsstand gewaehrleistet und wird diese dokumentiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("122.) Werden Pruefnachweise auf Wunsch des Kunden ausgeliefert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("122.) Werden Pruefnachweise auf Wunsch des Kunden ausgeliefert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("123.) Wurde die Zustaendigkeit fuer die Pruefung und Behandlung von fehlerhaften Produkten festgelegt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("123.) Wurde die Zustaendigkeit fuer die Pruefung und Behandlung von fehlerhaften Produkten festgelegt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Qualitaetsmanagement").getId());
		dimension.setCategoryName("Qualitaetsmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 5, 0, 0]"));
		dimension
				.setName("124.) Werden ueber die Ergebnisse aller Qualitaetspruefungen systematisch Aufzeichnungen gemacht und aufbewahrt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("124.) Werden ueber die Ergebnisse aller Qualitaetspruefungen systematisch Aufzeichnungen gemacht und aufbewahrt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Qualitaetsmanagement", listOfDimensions);
	}

	private void insertDBTestDataDimensions_RechtUndHaftung_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Recht und Haftung").getId());
		dimension.setCategoryName("Recht und Haftung");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("16.) Ist eine Ersatzteilversorgung fuer Systeme / Module nach Produktionsauslauf fuer weitere 15 Jahre sichergestellt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("16.) Ist eine Ersatzteilversorgung fuer Systeme / Module nach Produktionsauslauf fuer weitere 15 Jahre sichergestellt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Recht und Haftung").getId());
		dimension.setCategoryName("Recht und Haftung");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("17.) Ist eine Ersatzteilversorgung fuer Einzelteile nach Produktionsauslauf fuer weitere 15 Jahre sichergestellt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("17.) Ist eine Ersatzteilversorgung fuer Einzelteile nach Produktionsauslauf fuer weitere 15 Jahre sichergestellt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Recht und Haftung").getId());
		dimension.setCategoryName("Recht und Haftung");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable Inverted"));
		dimension
				.setName("21.) Sind in den gelieferten Produkten, fuer die bei der Entsorgung gesetzlichen Vorschriften gelten, Problemteile oder -stoffe enthalten?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("21.) Sind in den gelieferten Produkten, fuer die bei der Entsorgung gesetzlichen Vorschriften gelten, Problemteile oder -stoffe enthalten?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Recht und Haftung").getId());
		dimension.setCategoryName("Recht und Haftung");
		dimension.setCvi(cvis
				.getCVI("L0 Recht und Haftung: Produkte abgewickelt"));
		dimension
				.setName("25.) Wie werden fehlerhaft angelieferte Produkte abgewickelt:");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("25.) Wie werden fehlerhaft angelieferte Produkte abgewickelt:");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Recht und Haftung", listOfDimensions);
	}

	private void insertDBTestDataDimensions_RechtUndHaftung_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Recht und Haftung").getId());
		dimension.setCategoryName("Recht und Haftung");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 7.5, 2.5, 0]"));
		dimension
				.setName("125.) Sind die Aktivitaeten des Unternehmens schritthaltend mit den Gesetzen und gesetzlichen Bestimmungen (GG, SGB, ethische Richtlinien)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("125.) Sind die Aktivitaeten des Unternehmens schritthaltend mit den Gesetzen und gesetzlichen Bestimmungen (GG, SGB, ethische Richtlinien)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Recht und Haftung", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Stammdaten() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Geschaeftsfuehrung");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Geschaeftsfuehrung");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Kundenfokus (Vertrieb)");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Kundenfokus (Vertrieb)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Produktion");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Produktion");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension
				.setName("Technologie (inkl. Entwicklung, Forschung und Entwicklung)");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("Technologie (inkl. Entwicklung, Forschung und Entwicklung)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Recht und Haftung");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Recht und Haftung");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Kommunikationstechnologie");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Kommunikationstechnologie");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Mitarbeiterqualifikation");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Mitarbeiterqualifikation");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Fuehrungskompetenz");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Fuehrungskompetenz");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Logistikmanagement");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Logistikmanagement");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Qualitaetsmanagement");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Qualitaetsmanagement");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Umweltmanagement");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Umweltmanagement");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension
				.setName("Kontinuierliche Verbesserung, Lernen und Innovation");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("Kontinuierliche Verbesserung, Lernen und Innovation");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Name, Tel. Nr., E-Mail"));
		dimension.setName("Finanzen");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Finanzen");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Empty"));
		dimension.setName("Firma");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Firma");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Adresse");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Adresse: Postfach / Strasze, PLZ, Ort, Land");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Duns.-Nr.");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("Duns.-Nr.: (DUNS steht fuer 'Data Universal Numbering System'. Es ist ein einmaliges Nummersystem mit neun Ziffern, "
						+ "dass zur Kennzeichnung von Unternehmen verwendet wird. Sie koennen Ihre DUNS-Nummer unter folgender Adresse suchen "
						+ "http://smallbusiness.dnb.com");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Telefonnummer (inkl. Laendervorwahl)");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Telefonnummer (inkl. Laendervorwahl)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Faxnummer (inkl. Laendervorwahl)");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Faxnummer (inkl. Laendervorwahl)");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("E-Mail-Adresse");
		dimension.setDescription(dimension.getName());
		dimension.setEText("E-Mail-Adresse");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Homepage");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Homepage");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Unternehmensgruendung");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Unternehmensgruendung");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Rechtsform"));
		dimension.setName("Rechtsform");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Rechtsform");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Industrie"));
		dimension.setName("Industrie");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Industrie");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Ja / NEIN"));
		dimension.setName("Konzernzugehoerigkeit");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Konzernzugehoerigkeit");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension
				.setName("Wie viele Jahre besteht Gewaehrleistung bei Ihren Hauptprodukten?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("Wie viele Jahre besteht Gewaehrleistung bei Ihren Hauptprodukten?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Wie viele Monate wird Garantie gegeben?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Wie viele Monate wird Garantie gegeben?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Ja / NEIN"));
		dimension.setName("Wird Produkthaftung Uebernommen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("Wird Produkthaftung Uebernommen? Wenn Ja, in welcher Form und in welchem Ausmasz");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Unternehmensgröße"));
		dimension.setName("Unternehmensgroesze");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Unternehmensgroesze");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Beschaeftigte in der Entwicklung");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Beschaeftigte in der Entwicklung");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Beschaeftigte in der Konstruktion");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Beschaeftigte in der Konstruktion");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Beschaeftigte in der Produktion");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Beschaeftigte in der Produktion");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Beschaeftigte im Qualitaets-Management");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Beschaeftigte im Qualitaets-Management");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("STRING"));
		dimension.setName("Beschaeftigte im Verwaltungsbereich?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Beschaeftigte im Verwaltungsbereich?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Stammdaten")
				.getId());
		dimension.setCategoryName("Stammdaten");
		dimension.setCvi(cvis.getCVI("Leitungspanne"));
		dimension.setName("Wie hoch ist die durchschnittliche Leitungspanne?");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Wie hoch ist die durchschnittliche Leitungspanne?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Stammdaten", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Technologie_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Produktspektrum"));
		dimension
				.setName("1.) Produktspektrum (Serienproduktion) der direkten und indirekten Produkte Liste der angebotenen Hauptprodukte/ Dienstleistungen:");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("1.) Produktspektrum (Serienproduktion) der direkten und indirekten Produkte Liste der angebotenen Hauptprodukte/ Dienstleistungen:");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Produktionsmethoden"));
		dimension
				.setName("2.) Produktionsmethoden, -verfahren im Haus (siehe Annex 1.2 Uebersicht Be- und Verarbeitungs-kompetenzen):");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("2.) Produktionsmethoden, -verfahren im Haus (siehe Annex 1.2 Uebersicht Be- und Verarbeitungs-kompetenzen):");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Verarbeitete Materialien"));
		dimension
				.setName("3.) Verarbeitete Materialien (siehe Annex 1.3 Uebersicht Materialgruppen):");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("3.) Verarbeitete Materialien (siehe Annex 1.3 Uebersicht Materialgruppen):");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Maschinenpark"));
		dimension.setName("4.) Maschinenpark (Typ, Quantitaet, Volumen):");
		dimension.setDescription(dimension.getName());
		dimension.setEText("4.) Maschinenpark (Typ, Quantitaet, Volumen):");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Ja / Nein / Trifft nicht zu"));
		dimension
				.setName("5.) Gibt es einen dokumentierten Entwicklungsprozess?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("5.) Gibt es einen dokumentierten Entwicklungsprozess?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Ja / Nein / Trifft nicht zu"));
		dimension
				.setName("6.) Ist ein Aenderungsprozess in der Entwicklung definiert?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("6.) Ist ein Aenderungsprozess in der Entwicklung definiert?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Technologie", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Technologie_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("54.) Umfassen di e gesteuerten Prozesse die Verwendung einer geeigneten Produktions-, Instandhaltungs- und Wartungsausruestung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("54.) Umfassen di e gesteuerten Prozesse die Verwendung einer geeigneten Produktions-, Instandhaltungs- und Wartungsausruestung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("55.) Zustand der Maschinen: Ist der Maschinenpark auf dem angemessenen Stand?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("55.) Zustand der Maschinen: Ist der Maschinenpark auf dem angemessenen Stand?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("56.) Instandhaltung der Maschinen: Sind die Maschinen gepflegt und ausreichend gewartet?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("56.) Instandhaltung der Maschinen: Sind die Maschinen gepflegt und ausreichend gewartet?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("57.) Sind Wartungs- und Instandhaltungsplaene vorhanden?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("57.) Sind Wartungs- und Instandhaltungsplaene vorhanden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("58.) Werden Verfahren und Einrichtungen erst nach einer dokumentierten Abnahme freigegeben, und bestehen angemessene Vorschriften zur Prozessauslegung?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("58.) Werden Verfahren und Einrichtungen erst nach einer dokumentierten Abnahme freigegeben, und bestehen angemessene Vorschriften zur Prozessauslegung?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension("Technologie")
				.getId());
		dimension.setCategoryName("Technologie");
		dimension.setCvi(cvis.getCVI("Quantifiable CVIs - % 25 Step"));
		dimension
				.setName("59.) Werden Spezielle Prozesse (z.B.: Schweissen, Loeten, Galvanische Verfahren, Waermebehandlungen) systematisch abgesichert (z.B. durch entsprechende Pruefverfahren, wie Ultraschall, Risspruefung, Schichtdickenpruefung, Roentgenpruefung oder durch Verifizierung erprobter Prozessparameter)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("59.) Werden Spezielle Prozesse (z.B.: Schweissen, Loeten, Galvanische Verfahren, Waermebehandlungen) systematisch abgesichert (z.B. durch entsprechende Pruefverfahren, wie Ultraschall, Risspruefung, Schichtdickenpruefung, Roentgenpruefung oder durch Verifizierung erprobter Prozessparameter)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Technologie", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Umweltmanagement_L0() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis
				.getCVI("L0 Umweltmanagement: Produkte entsorgt werden"));
		dimension
				.setName("20.) Wie kann die Verpackung der gelieferten Produkte entsorgt werden?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("20.) Wie kann die Verpackung der gelieferten Produkte entsorgt werden?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein / Trifft nicht zu"));
		dimension
				.setName("22.) Ist eine Ruecknahme der Produkte zu Entsorgungszwecken, gemaess den gesetzlichen Vorschriften, moeglich?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("22.) Ist eine Ruecknahme der Produkte zu Entsorgungszwecken, gemaess den gesetzlichen Vorschriften, moeglich?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension
				.setCvi(cvis
						.getCVI("L0 Umweltmanagement: Ja / Nein, geplant / Nein, nicht geplant"));
		dimension
				.setName("27a.) Haben Sie ein Umweltmanagementsystem eingerichtet? zB nach Verordnung (EWG) 1836/93, DIN ISO 14001 oder anderen Verordnungen");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("27a.) Haben Sie ein Umweltmanagementsystem eingerichtet? zB nach Verordnung (EWG) 1836/93, DIN ISO 14001 oder anderen Verordnungen");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("L0 Umweltmanagement: Wenn ja welches"));
		dimension
				.setName("27b.) zB nach Verordnung (EWG) 1836/93, DIN ISO 14001 oder anderen Verordnungen");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("27b.) zB nach Verordnung (EWG) 1836/93, DIN ISO 14001 oder anderen Verordnungen");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("L0 Umweltmanagement: Zertifikat"));
		dimension
				.setName("28.) Haben Sie ein Zertifikat fuer Umweltmanagement: Wenn ja, welches?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("28.) Haben Sie ein Zertifikat fuer Umweltmanagement: Wenn ja, welches?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("29a.) Wird Folgendes im Unternehmen auf Umweltvertraeglichkeit untersucht.");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Produktionsprozess:");
		dimension.setQText(dimension.getEText());
		dimension.setWeightMultiplier(0.33f);
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("29b.) Wird Folgendes im Unternehmen auf Umweltvertraeglichkeit untersucht.");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Ver- und Entsorgungsprozesse:");
		dimension.setQText(dimension.getEText());
		dimension.setWeightMultiplier(0.33f);
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("29c.) Wird Folgendes im Unternehmen auf Umweltvertraeglichkeit untersucht.");
		dimension.setDescription(dimension.getName());
		dimension.setEText("Produktion:");
		dimension.setQText(dimension.getEText());
		dimension.setWeightMultiplier(0.34f);
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein / Trifft nicht zu"));
		dimension
				.setName("30.) Sind Umweltschutzaspekte fester Bestandteil Ihres Produkt- und Service-Designs?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("30.) Sind Umweltschutzaspekte fester Bestandteil Ihres Produkt- und Service-Designs?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("31.) Orientiert sich Ihr Unternehmen im Umweltschutz an schriftlich festgelegten Richtlinien?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("31.) Orientiert sich Ihr Unternehmen im Umweltschutz an schriftlich festgelegten Richtlinien?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("32.) Werden in Ihrem Unternehmen Umweltschutzmassnahmen und -ergebnisse definiert und dokumentiert");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("32.) Werden in Ihrem Unternehmen Umweltschutzmassnahmen und -ergebnisse definiert und dokumentiert");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("33.) Haben Sie in Ihrem Unternehmen Ziele zur Verbesserung des Umweltschutzes definiert und dokumentieren Sie deren?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("33.) Haben Sie in Ihrem Unternehmen Ziele zur Verbesserung des Umweltschutzes definiert und dokumentieren Sie deren?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("34.)Werden Ihre Mitarbeiter/-innen regelmaessig zum Thema Umweltschutz informiert und geschult?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("34.)Werden Ihre Mitarbeiter/-innen regelmaessig zum Thema Umweltschutz informiert und geschult?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("Ja / Nein - Quantifiable"));
		dimension
				.setName("35.) Wirken Sie auf die Verbesserung des Umweltschutzes bei Ihren Lieferanten und Vertragspartnern hin?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("35.) Wirken Sie auf die Verbesserung des Umweltschutzes bei Ihren Lieferanten und Vertragspartnern hin?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L0 Umweltmanagement", listOfDimensions);
	}

	private void insertDBTestDataDimensions_Umweltmanagement_L1() {
		List<Dimension> listOfDimensions = new ArrayList<Dimension>();

		Dimension dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension.setCvi(cvis.getCVI("L1 Umweltmanagement: 0%-100%"));
		dimension
				.setName("53.) Entspricht der Produktionsbereich den Erwartungen hinsichtlich Zustand, Organisation, Ordnung, Uebersicht und "
						+ "Sauberkeit in Bezug auf:\n- Optischer Eindruck (Boeden, Waende, Ecken, Tueren, Fenster)?"
						+ "\n- Abfalltrennung?"
						+ "\n- Sicherheitshinweise (Feuerloescher, Notausgaenge)?"
						+ "\n- Aktualitaet ausgehaengter Unterlagen?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("53.) Entspricht der Produktionsbereich den Erwartungen hinsichtlich Zustand, Organisation, Ordnung, Uebersicht und "
						+ "Sauberkeit in Bezug auf:\n- Optischer Eindruck (Boeden, Waende, Ecken, Tueren, Fenster)?"
						+ "\n- Abfalltrennung?"
						+ "\n- Sicherheitshinweise (Feuerloescher, Notausgaenge)?"
						+ "\n- Aktualitaet ausgehaengter Unterlagen?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("126.) Werden Sicherheits- und Umweltvorschriften eingehalten? Werden Sicherheitsvorschriften einmal jaehrlich nach BGV A1 (allg. Vorschriften / UVV) ueberprueft und ergaenzt?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("126.) Werden Sicherheits- und Umweltvorschriften eingehalten? Werden Sicherheitsvorschriften einmal jaehrlich nach BGV A1 (allg. Vorschriften / UVV) ueberprueft und ergaenzt?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimension = new Dimension();
		dimension.setCategoryId(categories.getCategoryDimension(
				"Umweltmanagement").getId());
		dimension.setCategoryName("Umweltmanagement");
		dimension
				.setCvi(cvis
						.getCVI("Quantifiable CVIs - ja immer / fast immer / selten / nie - [10, 6.6, 3.3, 0]"));
		dimension
				.setName("127.) Ist der Energieverbrauch und der effiziente Gebrauch von Ressourcen ueberwacht und wird er verbessert? (zB durch reduzierten Papierkonsum, Rueckgabe von Druckerpatronen, etc.)?");
		dimension.setDescription(dimension.getName());
		dimension
				.setEText("127.) Ist der Energieverbrauch und der effiziente Gebrauch von Ressourcen ueberwacht und wird er verbessert? (zB durch reduzierten Papierkonsum, Rueckgabe von Druckerpatronen, etc.)?");
		dimension.setQText(dimension.getEText());
		dimensionRepository.addDimension(dimension);
		listOfDimensions.add(dimension);

		dimensions.put("L1 Umweltmanagement", listOfDimensions);
	}

	public void setCategories(TestDBDataCategories categories) {
		this.categories = categories;
	}

	public void setCvis(TestDBDataCVIs cvis) {
		this.cvis = cvis;
	}

	public void setDimensionRepository(IDimensionRepository dimensionRepository) {
		this.dimensionRepository = dimensionRepository;
	}

}