package com.haswalk.solver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

import com.haswalk.solver.Solver;
import com.haswalk.solver.fvm2d.FVM2DSolverBuilder;

public class SolverTest {
	private String json = null;
	@Before
	public void before() throws IOException{
		json = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir"), 
				"src/main/resources/config.json")));
	}
	@Test
	public void testSolve(){
		FVM2DSolverBuilder builder = new FVM2DSolverBuilder();
		Solver solver = builder.parseConfig(json).create();
		solver.run();
	}
}
