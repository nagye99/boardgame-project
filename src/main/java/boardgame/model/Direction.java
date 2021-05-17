package boardgame.model;

/**
 * This interface join the different Direction representer classes.
 */
public interface Direction {

    /**
     * This method represents the change of the row number.
     *
     * @return the difference between old and new row number
     */
    int getRowChange();

    /**
     * This method represents the change of the column number.
     *
     * @return the difference between old and new column number
     */
    int getColChange();

}
