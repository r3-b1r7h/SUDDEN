package biz.sudden.knowledgeData.serviceManagement.Util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import biz.sudden.baseAndUtility.domain.Process;
import biz.sudden.baseAndUtility.domain.connectable.Connectable;
import biz.sudden.knowledgeData.serviceManagement.domain.Item;
import biz.sudden.knowledgeData.serviceManagement.domain.Machine;
import biz.sudden.knowledgeData.serviceManagement.domain.Material;
import biz.sudden.knowledgeData.serviceManagement.domain.Product;
import biz.sudden.knowledgeData.serviceManagement.domain.SupportingService;
import biz.sudden.knowledgeData.serviceManagement.domain.Technology;
import biz.sudden.userOrganizationManagement.organizationManagement.domain.Organization;

public class Util {

	public static Set<Machine> getMachines(Set<Connectable> cL) {
		Set<Machine> mL = new HashSet<Machine>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Machine)
				mL.add((Machine) c);
		}
		return mL;
	}

	public static Set<Technology> getTechnologies(Set<Connectable> cL) {
		Set<Technology> techL = new HashSet<Technology>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Technology)
				techL.add((Technology) c);
		}
		return techL;
	}

	public static List<Organization> getOrganizations(Set<Connectable> cL) {
		List<Organization> manuL = new ArrayList<Organization>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Organization)
				manuL.add((Organization) c);
		}
		return manuL;
	}

	public static Set<Process> getProcesses(List<Connectable> cL) {
		Set<Process> procL = new HashSet<Process>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Process)
				procL.add((Process) c);
		}
		return procL;
	}

	public static Set<Process> getProcesses(Set<Connectable> cL) {
		Set<Process> procL = new HashSet<Process>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Process)
				procL.add((Process) c);
		}
		return procL;
	}

	public static List<Item> getItems(List<Connectable> cL) {
		List<Item> itemL = new ArrayList<Item>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Item)
				itemL.add((Item) c);
		}
		return itemL;
	}

	public static Set<Item> getItems(Set<Connectable> cL) {
		Set<Item> itemL = new HashSet<Item>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Item)
				itemL.add((Item) c);
		}
		return itemL;
	}

	public static Set<Material> getMaterials(List<Connectable> cL) {
		Set<Material> mL = new HashSet<Material>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Material)
				mL.add((Material) c);
		}
		return mL;
	}

	public static Set<Material> getMaterials(Set<Connectable> cL) {
		Set<Material> mL = new HashSet<Material>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Material)
				mL.add((Material) c);
		}
		return mL;
	}

	public static Set<SupportingService> getSupportingServices(
			List<Connectable> cL) {
		Set<SupportingService> sSL = new HashSet<SupportingService>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof SupportingService)
				sSL.add((SupportingService) c);
		}
		return sSL;
	}

	public static Set<SupportingService> getSupportingServices(
			Set<Connectable> cL) {
		Set<SupportingService> sSL = new HashSet<SupportingService>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof SupportingService)
				sSL.add((SupportingService) c);
		}
		return sSL;
	}

	public static List<Product> getProducts(Set<Connectable> cL) {
		List<Product> sSL = new ArrayList<Product>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Product)
				sSL.add((Product) c);
		}
		return sSL;
	}

	public static List<Item> getProductsServices(Set<Connectable> cL) {
		List<Item> sSL = new ArrayList<Item>();
		Iterator<Connectable> cLI = cL.iterator();
		while (cLI.hasNext()) {
			Connectable c = cLI.next();
			if (c instanceof Item)
				sSL.add((Item) c);
		}
		return sSL;
	}

	public static List<Item> getProductsServices(List<Connectable> cL) {
		return getItems(cL);
	}

}
