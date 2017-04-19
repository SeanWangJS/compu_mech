package com.haswalk.solver.fvm2d.config.part;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.sean.wang.utils.Geom;

public class Gauge {
	private boolean fixed = false;
	private double[][] points;
	private double tolerance = 0.001;
	
	private List<Integer> gaugeNodesID;
	
	public void init(List<double[]> vertices) {
		gaugeNodesID = new ArrayList<>();
		for(double[] p: points) {
			gaugeNodesID.add(Geom.nearest(vertices, p, tolerance));
		}
	}
	
	public List<Integer> getGaugeNodesID() {
		return gaugeNodesID;
	}
	
	public boolean isFixed() {
		return fixed;
	}

	public double[][] getPoints() {
		return points;
	}

	public String toString(){
		StringBuilder builder  = new StringBuilder();
		builder.append("gauge points: \n");
		for(double[] point: points) {
			builder.append(Arrays.toString(point) + "\n");
		}
		builder.append("gauge nodes id: " + gaugeNodesID+"\n");
		builder.append("end\n");
		return builder.toString();
	}
}
