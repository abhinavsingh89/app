package org.concordia.kingdoms.board;

/**
 * maintains the score divided into two parts one is the score before a
 * mountain, and the other is the score after mountain if there is no mountain
 * beforeMountain variable must hold the score for the entire row or column
 * 
 * @author Pavan
 * 
 */
public class Score {

	private Integer beforeMountain;

	private Integer afterMountain;

	/**
	 * constructor
	 * 
	 * @param beforeMountain
	 * @param afterMountain
	 * @param index
	 */
	public Score(Integer beforeMountain, Integer afterMountain, Integer index) {
		this.beforeMountain = beforeMountain;
		this.afterMountain = afterMountain;
	}

	/**
	 * @return score gained before the mountain tile, if there is no mountain at
	 *         all then the score is the total score for all the entries lying
	 *         in its way till the end.
	 */
	public Integer getBeforeMountain() {
		return this.beforeMountain;
	}

	/**
	 * @return score gained after the mountain tile; this can be null if there
	 *         is no mountain at all
	 */
	public Integer getAfterMountain() {
		return this.afterMountain;
	}

	/**
	 * returns true if there is a mountain
	 * 
	 * @return
	 */
	public boolean hasMountain() {
		return afterMountain != null;
	}
}
