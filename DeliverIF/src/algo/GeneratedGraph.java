package algo;

public interface GeneratedGraph {
	
	/**
	 * @return the number of vertices in this
	 */
	public abstract int getNbVertices();

	/**
	 * @param i
	 * @param j
	 * @return the cost of arc (i,j) if (i,j) is an arc; -1 otherwise
	 */
	public abstract Double getCost(int i, int j);

	/**
	 * @param i
	 * @param j
	 * @return true if (i,j) is an arc of this
	 */
	public abstract boolean isArc(int i, int j);

}
