package com.haswalk.solver.fvm2d.config.part;

import java.util.List;

import com.sean.wang.utils.FileIO;
import com.sean.wang.utils.mesh.MeshProcessor;

public class Mesh {
	
	private String uri;
	private String[] uri2;
	
	private List<double[]> vertices;
	private List<int[]> elements;
	private List<List<Integer>> nodesE;
	private List<List<Integer>> nodesN;
//	private List<Integer> boundNodesId; 
	
	private int realNON;
	private int realNOE;
	
	public String toString() {
		
		return "mesh path: \n"+ uri +"\nend";
	}
	
	public void init() {
		if(uri == null) {
			uri = new StringBuilder().append(System.getProperty(uri2[0]))
					.append(uri2[1])
					.toString();
		}
		vertices = FileIO.StandartFormat.readVertices(uri);
		elements = FileIO.StandartFormat.readElements(uri);
		MeshProcessor mp = new MeshProcessor(vertices, elements);
		mp.handle();
		nodesE = mp.getSurrE();
		nodesN = mp.getSurrN();
		this.realNOE = vertices.size();
		this.realNOE = elements.size();
//		boundSearch();
	}
//	private void boundSearch() {
//		boundNodesId = new ArrayList<>();
//		for(int i = 0, len = nodesE.size(); i < len; i++) {
//			List<Integer> ean = nodesE.get(i);
//			int eid1 = ean.get(0);
//			int eidL = ean.get(ean.size() - 1);
//			int[] e1 = elements.get(eid1);
//			int[] eL = elements.get(eidL);
//			int s = ArrUtil.findNext(e1, i);
//			int e = ArrUtil.findPre(eL, i);
//			if(s != e) {
//				boundNodesId.add(i);
//			}
//		}
////		FileIO.writeDoubleArrList(LsUtil.select(vertices, boundNodesId), "E:/fvm/7/boundNode.txt","\t");
////		System.out.println("++++++++++++++++++bound nodes size = " + boundNodesId.size());
//	}
	
	public String getWorkspace(){
		String[] str = uri.split("/");
		return uri.replaceAll("/" +  str[str.length - 1], "");
		
	}
	
	public List<double[]> getVertices(){
		return vertices;
	}
	public List<int[]> getElements() {
		return elements;
	}
	
	public List<List<Integer>> getNodesE() {
		return nodesE;
	}

	public List<List<Integer>> getNodesN() {
		return nodesN;
	}

//	public List<Integer> getBoundNodesId() {
//		return boundNodesId;
//	} 
	
	public int getNON(){
		return vertices.size();
	}
	public int getNOE(){
		return elements.size(); 
	}
	
	public int getRealNON() {
		return realNON;
	}
	public int getRealNOE() {
		return realNOE;
	}
}
