package com.api.sudoku.demo.model;

import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class TrueSudokuBoard {

	private List<List<Boolean>> validSudokuBoard;
	
	public TrueSudokuBoard(List<List<Boolean>> validSudokuBoard) {
		this.validSudokuBoard = validSudokuBoard;
	}

	public List<List<Boolean>> getValidSudokuBoard() {
		return validSudokuBoard;
	}

	public void setValidSudokuBoard(List<List<Boolean>> validSudokuBoard) {
		this.validSudokuBoard = validSudokuBoard;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((validSudokuBoard == null) ? 0 : validSudokuBoard.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TrueSudokuBoard other = (TrueSudokuBoard) obj;
		if (validSudokuBoard == null) {
			if (other.validSudokuBoard != null)
				return false;
		} else if (!validSudokuBoard.equals(other.validSudokuBoard))
			return false;
		return true;
	}
	
	
}
