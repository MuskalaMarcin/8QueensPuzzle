package com.muskalanawrot.eightqueenspuzzle.implementation;

import java.util.List;
import java.util.Random;

/**
 * Created by Marcin on 02.07.2016.
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

    public void mutate(int rowsNumber, int columnsNumber, Random random)
    {
	if (random.nextBoolean())
	{
	    setColumn((getColumn() + random.nextInt(columnsNumber)) % columnsNumber);
	}
	else
	{
	    setRow((getRow() + random.nextInt(rowsNumber)) & rowsNumber);
	}
    }
}
