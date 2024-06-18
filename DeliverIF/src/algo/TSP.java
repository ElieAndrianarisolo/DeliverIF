package algo;

/**
 * The TSP (Traveling Salesman Problem) interface defines methods for searching
 * for a shortest cost Hamiltonian circuit in a graph within a specified time limit.
 * @author H4114
 */
public interface TSP {
	
	/**
	 * Search for a shortest cost hamiltonian circuit in g within
	 * timeLimit milliseconds
	 * (returns the best found tour whenever the time limit is reached)
	 * Warning: The computed tour always start from vertex 0
	 * 
	 * @param timeLimit
	 * @param g
	 */
	public void searchSolution(int timeLimit, GeneratedGraph g);

	/**
	 * @param i
	 * @return the ith visited vertex in the solution computed by searchSolution
	 *         (-1 if searcheSolution has not been called yet, or if i
	 *         is smaller than 0 or i >= g.getNbSommets())
	 */
	public Integer getSolution(int i);

	/**
	 * @return the total cost of the solution computed by searchSolution
	 *         (-1 if searcheSolution has not been called yet).
	 */
	public Double getSolutionCost();

}
