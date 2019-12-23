package com.api.sudoku.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.api.sudoku.demo.model.SudokuBoard;
import com.api.sudoku.demo.model.TrueSudokuBoard;
import com.api.sudoku.demo.service.SudokuService;

@RestController
public class SudukoController {

	
	@Autowired
	SudokuService service;
	

	@GetMapping("/setupBoard")
	public SudokuBoard setUpNewBoard() {
		return service.setupBoard();
	}
	
	@GetMapping("/solve")
	public SudokuBoard getSolvedSudokuBoard() {
		return service.returnSolvedStaticBoard();
	}

	@PostMapping("/checkBoard")
	public boolean checkSudokuPuzzle(@RequestBody SudokuBoard solvedBoard) {
		return service.isValidBoard(solvedBoard);
	}

	@PostMapping("/check")
	public TrueSudokuBoard validateSudokuPuzzle(@RequestBody SudokuBoard partialBoard) {
		return service.doBoardValidation(partialBoard);
	}

	@GetMapping("/restart")
	public SudokuBoard getNewPuzzle() {
		return service.restart();
	}
}
