package biz.sudden.evaluation.coordinationFit.service.impl;

import java.util.ArrayList;
import java.util.List;

public class Path<T> {
	final List<T> path;
	final double weight;

	public Path(List<T> path, double weight) {
		this.path = path;
		this.weight = weight;
	}

	public List<T> getPath() {
		return path;
	}

	public double getWeight() {
		return weight;
	}

	public Path<T> append(T nodeId, double weight) {
		List<T> path = new ArrayList<T>(this.path);
		path.add(nodeId);
		return new Path<T>(path, this.weight + weight);
	}
}
