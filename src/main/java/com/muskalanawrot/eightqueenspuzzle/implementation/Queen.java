package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.List;
import java.util.Random;

/**
 * Class representing one queen from chess board.
 */
public class Queen
{
    private int row;
    private int column;

    public Queen(int row, int column)
    {
	this.row = row;
	this.column = column;
    }

    public int getRow()
    {
	return row;
    }

    private void setRow(int row)
    {
	this.row = row;
    }

    public int getColumn()
    {
	return column;
    }

    private void setColumn(int column)
    {
	this.column = column;
    }

    /**
     * Returns number of collisions with other queens from list.
     *
     * @param queenList list of {@link Queen} on the chess board
     * @return number of collisions
     */
    public int getNumberOfCollisions(List<Queen> queenList)
    {
	return (int) queenList.stream().filter(anotherQueen -> {
	    if (anotherQueen.equals(this))
	    {
		return false;
	    }
	    else if (anotherQueen.getRow() == this.getRow() || anotherQueen.getColumn() == this.getColumn())
	    {
		return true;
	    }
	    else
	    {
		return Math.abs((double) (this.getRow() - anotherQueen.getRow())
				/ (double) (this.getColumn() - anotherQueen.getColumn())) == 1d;
	    }
	}).count();
    }

    /**
     * Mutates this object by adding one to row or column number within specified boundaries.
     *
     * @param rowsNumber    max row number
     * @param columnsNumber max column number
     * @param random        {@link Random} random generator
     */
    public void mutate(int rowsNumber, int columnsNumber, Random random)
    {
	if (random.nextBoolean())
	{
	    setColumn((getColumn() + 1) % columnsNumber);
	}
	else
	{
	    setRow((getRow() + 1) % rowsNumber);
	}
    }

    /**
     * Returns copy of this object.
     *
     * @return Queen copy of this
     */
    public Queen getCopy()
    {
	return new Queen(getRow(), getColumn());
    }

    @Override
    public String toString()
    {
	return "row: " + (getRow() + 1) + " column: " + (char) ('A' + getColumn());
    }
}
