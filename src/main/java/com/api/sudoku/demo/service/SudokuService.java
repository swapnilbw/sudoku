package com.api.sudoku.demo.service;

import com.api.sudoku.demo.model.SudokuBoard;
import com.api.sudoku.demo.model.TrueSudokuBoard;

public interface SudokuService {

    public SudokuBoard setupBoard();
    public SudokuBoard returnSolvedStaticBoard();
    public TrueSudokuBoard doBoardValidation(SudokuBoard board);
	public boolean isValidBoard(SudokuBoard partialBoard);
    public SudokuBoard restart();

}
