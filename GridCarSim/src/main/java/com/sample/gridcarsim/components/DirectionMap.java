package com.sample.gridcarsim.components;

import java.util.HashMap;import java.util.Map;


public class DirectionMap {
	
	private static Map<Integer, Direction> directionDegreeMap = new HashMap<>();
	public static enum Direction{
		NORTH(90),
		WEST(180),
		EAST(0),
		SOUTH(270);
		Direction(Integer degree){
			this.degree = degree;
			DirectionMap.directionDegreeMap.put(degree, this);
		}
		private final Integer degree;
		public Integer degree(){
			return degree;
		}
	}
	
	public static Direction degreeToDirection(Integer degree){
		return directionDegreeMap.get(degree);
	}
}
