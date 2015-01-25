package com.sample.gridsim;

import java.util.HashMap;
import java.util.Map;

/**
 * A class which defines and stores the relationship between an angular degree
 * to the cardinal direction and vice versa.
 * 
 * @author Milind
 *
 */
public class DirectionMap {

	/**
	 * A reverse look up map, which fetches a predefined cardinal direction
	 * {@link Direction} wrt an angular degree.
	 */
	private static Map<Integer, Direction> directionDegreeMap = new HashMap<>();

	/**
	 * Definitions of Directions and their respective angular degrees.
	 * 
	 * @author Milind
	 *
	 */
	public static enum Direction {
		NORTH(90), WEST(180), EAST(0), SOUTH(270);
		// Angular degree.
		private final Integer degree;

		Direction(Integer degree) {
			this.degree = degree;
			// Populate the reverse lookup map.
			DirectionMap.directionDegreeMap.put(degree, this);
		}

		/**
		 * @return the angular degree.
		 */
		public Integer degree() {
			return degree;
		}
	}

	/**
	 * Gets the cardinal direction wrt to the input angular degree.
	 * 
	 * @param degree
	 * @return
	 */
	public static Direction degreeToDirection(Integer degree) {
		return directionDegreeMap.get(degree);
	}
}
