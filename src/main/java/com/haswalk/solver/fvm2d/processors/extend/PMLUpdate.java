package com.haswalk.solver.fvm2d.processors.extend;

import com.haswalk.solver.fvm2d.processors.Processor;

public class PMLUpdate implements Processor{

	private double delta;
	private double R = 1e-9;
	private double[] vx;
	private double[] vy;
	private double[] ax;
	private double[] ay;
	private double[] nMass;
	private int[] PMLNode;
	private int[] PMLBoundNodesID;
	private double[] dist;
	private double cp;
	
	public PMLUpdate(double delta, double[] vx, double[] vy, double[] ax, double[] ay, double[] nMass,int[] pMLNode, int[] PMLBoundNodesID, double[] dist, double cp) {
		this.delta = delta;
		this.vx = vx;
		this.vy = vy;
		this.ax = ax;
		this.ay = ay;
		this.nMass = nMass;
		this.PMLNode = pMLNode;
		this.PMLBoundNodesID = PMLBoundNodesID;
		this.dist = dist;
		this.cp = cp;
	}

	@Override
	public void calc() {
		if(vx == null) {
			return;
		}
		int NOPMLN = PMLNode.length;
		double damp = Double.MIN_VALUE;
		for(int i = 0; i < NOPMLN; i++) {
			int nid = PMLNode[i];
			double d = dist[i];
			double dampX = nMass[i] * cp * 3 * vx[i] / (2 * delta) * Math.log10(1 / R) * Math.pow(d / delta, 2);
//			double dampY = cp * 3 * vy[i] / (2 * delta) * Math.log10(1 / R) * Math.pow(d / delta, 2);
			
			ax[nid] -= dampX;
			damp = dampX;
//			ay[nid] += dampY;
//			if(Math.abs(dampX) > Math.abs(damp)){
//				damp = dampX;
//			}
//			if(Math.abs(dampY) > Math.abs(damp)) {
//				damp = dampY;
//			}
		}
		System.out.println(damp);
		
		for(int nid: PMLBoundNodesID) {
			ax[nid] = 0;
			ay[nid] = 0;
		}
		
	}
	
}
