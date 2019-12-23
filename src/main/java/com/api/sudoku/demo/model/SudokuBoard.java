package com.api.sudoku.demo.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class SudokuBoard {

	private List<List<Integer>> board;
	
	public SudokuBoard(List<List<Integer>> board) {
		this.board = board;
	}

	public SudokuBoard() {
		super();
	}

	public List<List<Integer>> getBoard() {
		return board;
	}

	public void setBoard(List<List<Integer>> board) {
		this.board = board;
	}

	
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		SudokuBoard that = (SudokuBoard) o;

		return board != null ? board.equals(that.board) : that.board == null;
	}

	@Override
	public int hashCode() {
		return board != null ? board.hashCode() : 0;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("SudokuBoard \n ");

		for (List<Integer> eachBoard : board)
			builder.append(eachBoard).append('\n');

		return builder.toString();
	}
	
	

}
