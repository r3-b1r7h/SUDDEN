package biz.sudden.designCoordination.handleBO.service;

import java.util.ArrayList;

import com.hp.hpl.jena.ontology.Individual;

/**
 * Ok so not a brilliant name. Just a convenience class to fold together a few
 * little bits of data.
 * 
 * Basically just a decomposition for a complex product.
 * 
 */

public class Decomposition {

	private ArrayList<Individual> simpleProducts;
	private ArrayList<Individual> complexProducts;

	public Decomposition(ArrayList<Individual> complexProductsIn,
			ArrayList<Individual> simpleProductsIn) {
		this.simpleProducts = simpleProductsIn;
		this.complexProducts = complexProductsIn;
	}

	public Decomposition() {
		this.simpleProducts = new ArrayList<Individual>();
		this.complexProducts = new ArrayList<Individual>();
	}

	public void addComplexProduct(Individual cp) {
		this.complexProducts.add(cp);
	}

	public void addSimpleProduct(Individual sp) {
		this.simpleProducts.add(sp);
	}

	public ArrayList<Individual> getComplexProducts() {
		return this.complexProducts;
	}

	public ArrayList<Individual> getSimpleProducts() {
		return this.simpleProducts;
	}

	@Override
	public String toString() {
		String result = "";
		result += " Complex Products : " + this.complexProducts;
		result += " Simple Products " + this.simpleProducts;

		return result;
	}
}
