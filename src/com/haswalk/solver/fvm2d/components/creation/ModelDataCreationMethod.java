package com.haswalk.solver.fvm2d.components.creation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.haswalk.solver.fvm2d.components.ComponentCreationMethod;
import com.haswalk.solver.fvm2d.components.ModelData;
import com.haswalk.solver.fvm2d.components.ModelData.BoundaryCondition;
import com.haswalk.solver.fvm2d.config.Boundary;
import com.haswalk.solver.fvm2d.config.Config;
import com.haswalk.solver.fvm2d.config.boundary.CForceLoadBoundary;
import com.haswalk.solver.fvm2d.config.boundary.StressLoadBoundary;
import com.haswalk.solver.fvm2d.config.boundary.SymmetricBoundary;

public class ModelDataCreationMethod implements ComponentCreationMethod{

	@Override
	public Object invoke(int partId, Config config) {
		ModelData md = new ModelData();
		md.put(ModelData.VERTICES, config.getParts().get(partId).getMesh().getVertices());
		md.put(ModelData.ELEMENTS, config.getParts().get(partId).getMesh().getElements());
		md.put(ModelData.NODES_AROUDN_NODE, config.getParts().get(partId).getMesh().getNodesN());
		md.put(ModelData.NODES_AROUND_ELEM, config.getParts().get(partId).getMesh().getNodesE());
		
		List<BoundaryCondition> bc = new ArrayList<>();
		List<Integer> ids = config.getParts().get(partId).getBoundaryCondition().getBcIds();
		HashMap<Integer, String> idTypeMap = new HashMap<>();
		ids.forEach(id -> idTypeMap.put(id, config.getBoundaries().get(id).getType()));
		
		idTypeMap.forEach((bcid, type) -> {
			if(Boundary.FORCE.equals(type)) {
				CForceLoadBoundary b = (CForceLoadBoundary) config.getBoundaries().get(bcid);
				bc.add(md.new ForceBoundaryCondition(type, 
												  b.getLoad(),
												  config.getParts().get(partId).getBoundaryCondition().getApplyNodesId(bcid).get(0), 
												  b.getAngle()));
			}else if(Boundary.STRESS.equals(type)) {
				StressLoadBoundary b = (StressLoadBoundary) config.getBoundaries().get(bcid);
				bc.add(md.new StressBoundaryCondition(type, 
												  b.getLoad(), 
												  config.getParts().get(partId).getBoundaryCondition().getApplyNodesId(bcid)));
			}else if(Boundary.TRANSMIT.equals(type)) {
				bc.add(md.new TransmitBoundaryCondition(type));
			}else if(Boundary.SYMMETRIC.equals(type)) {
				SymmetricBoundary b = (SymmetricBoundary) config.getBoundaries().get(bcid);
				bc.add(md.new SymmetricBoundaryCondition(type,
														config.getParts().get(partId).getBoundaryCondition().getApplyNodesId(bcid),
														b.getSymmetric()
														));
			}
		});
		
		md.put(ModelData.BOUNDARY_CONDITION, bc);
		return md;
	}

}
