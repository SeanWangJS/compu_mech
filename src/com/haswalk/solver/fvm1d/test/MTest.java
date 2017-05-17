package com.haswalk.solver.fvm1d.test;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.haswalk.jsonutil.GeneralDeserializer;
import com.haswalk.jsonutil.testresource.D;
import com.haswalk.jsonutil.testresource.E;
import com.haswalk.jsonutil.testresource.F;
import com.haswalk.jsonutil.testresource.G;
import com.haswalk.jsonutil.testresource.H;
import com.sean.wang.utils.FileIO;

public class MTest {

	@Test
	public void meshGenerate() {
		double left = 0;
		double right = 100;
		double NON = 101;
		double width = (right - left) / (NON - 1);
		List<Double> vertices = new ArrayList<>();
		List<int[]> elements = new ArrayList<>();
		for (int i = 0; i < NON; i++) {
			vertices.add(left + width * i);
		}
		for (int i = 0; i < NON - 1; i++) {
			elements.add(new int[] { i + 1, i });
		}
		FileIO.writeDoubleList(vertices, "E:/fvm1d/1/vertices.txt");
		FileIO.writeIntArrList(elements, "E:/fvm1d/1/elements.txt", "\t");
		FileIO.StandardFormat1DMesh.writeToOne(vertices, elements, "E:/fvm1d/1/mesh.txt");
	}

	@Test
	public void test1() throws IOException {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String configStr = new String(Files.readAllBytes(Paths.get("E:/fvm1d/test/config.json")));
		A a = gson.fromJson(configStr, A.class);
		System.out.println(a.getConfig());
	}

	class A {
		private HashMap<Integer, S> config;

		public HashMap<Integer, S> getConfig() {
			return config;
		}
	}

	class S {

	}

	class B extends S {
		private int id;

		public String toString() {
			return "id: " + id;
		}
	}

	class C extends S {
		private int id2;

		public String toString() {
			return "id2: " + id2;
		}
	}
	
	@Test
	public void test3() {
		
		Class<?> clazz = ArrayList.class;
		try {
			Object o = clazz.newInstance();
			Method method = clazz.getMethod("add", java.lang.Object.class);
			method.invoke(o, 10);
			method.invoke(o, "s");
			method.invoke(o, new F());
			System.out.println(o);
			
			Method getMethod = clazz.getMethod("get", int.class);
			System.out.println((int)getMethod.invoke(o, 0) + 1);
			System.out.println(getMethod.invoke(o, 2).toString());
		} catch (InstantiationException | IllegalAccessException | NoSuchMethodException | SecurityException | IllegalArgumentException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test8() {
		Pattern pattern = Pattern.compile("(?<=<).+?(?=>)");
		Matcher m = pattern.matcher("java.util.List<java.lang.Integer>");
		System.out.println(m.find());
		System.out.println(m.group());
	}
	
	@Test
	public void test7(){
		String str = "{'f':{'list': [1,2,3,4]}}";
		GeneralDeserializer gd = new GeneralDeserializer();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		F d = (F) gd.deserialize(gson.fromJson(str, JsonObject.class).get("f"), F.class);
		System.out.println(d.toString());
		
//		JsonObject jo = gson.fromJson(str, JsonObject.class);
//		JsonElement je = gson.fromJson(str, JsonObject.class);
	}
	
	@Test
	public void test9() {
		String str = "{'g':{'set': [1,2,3,3]}}";
		GeneralDeserializer gd = new GeneralDeserializer();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		G d = (G) gd.deserialize(gson.fromJson(str, JsonObject.class).get("g"), G.class);
		System.out.println(d.toString());
	}
	
	@Test
	public void test10() {
		String str = "{'h':{'map':{'1': 1.1, '2': 2.2, '3': 3.3}}}";
		GeneralDeserializer gd = new GeneralDeserializer();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		H d = (H) gd.deserialize(gson.fromJson(str, JsonObject.class).get("h"), H.class);
		System.out.println(d.toString());
	}
	
	@Test
	public void test5() {
		String str = "{'d':{'p1': 1, 'p2': 's', 'p3': 2.2, 'p4': false, 'p5': 's2', 'p6': {'id': 1, 'p3': {'id': 10}}}}";
		GeneralDeserializer gd = new GeneralDeserializer();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		D d = (D) gd.deserialize(gson.fromJson(str, JsonObject.class).get("d"), D.class);
		System.out.println(d.toString());
		
		
	}

	@Test
	public void test6() {
		String str = "{'e': {'arr': [1,2,3,4], 'iArr':[4,5,67], 'i2':[[1,2],[3,4]], 'd3':[{'id': 1, 'id': 2}]}}";
		GeneralDeserializer gd = new GeneralDeserializer();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		E e = (E) gd.deserialize(gson.fromJson(str, JsonObject.class).get("e"), E.class);
		System.out.println(e.toString());
	}
	
	
	@Test
	public void test4() {
		Class<?> clazz = null;
		Object obj = null;
		try {
			clazz = Class.forName(D.class.getName());
			obj = clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		Method[] methods = clazz.getDeclaredMethods();
		for(Method m: methods) {
			if("setP1".equals(m.getName())) {
				try {
					Object o = 1;
					m.invoke(obj, o);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		D d = (D)obj;
		System.out.println(d.getP1());
	}

}

