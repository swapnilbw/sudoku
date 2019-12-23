package com.api.sudoku.demo.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.api.sudoku.demo.model.SudokuBoard;
import com.api.sudoku.demo.model.TrueSudokuBoard;

@Primary
@Service
public class SudokuServiceImpl implements SudokuService {

	Logger logger = LoggerFactory.getLogger(SudokuServiceImpl.class);

	private static Map<String, SudokuBoard> staticSudokuBoardMap = new HashMap<>();

	private static String UNSOLVED_STATIC_BOARD = "UNSOLVED_STATIC_BOARD";
	private static String SOLVED_STATIC_BOARD = "SOLVED_STATIC_BOARD";
	private static int BOARD_SIZE = 9;

	private SudokuBoard setStaticBoard() {

		logger.info("setStaticBoard() ");
		// create a static board
		List<List<Integer>> boardLists = new ArrayList<>(BOARD_SIZE);

		boardLists.add(0, Arrays.asList(4, 3, 5, 2, 6, 9, 7, 8, 1));
		boardLists.add(1, Arrays.asList(6, 8, 2, 5, 7, 1, 4, 9, 3));
		boardLists.add(2, Arrays.asList(1, 9, 7, 8, 3, 4, 5, 6, 2));
		boardLists.add(3, Arrays.asList(8, 2, 6, 1, 9, 5, 3, 4, 7));
		boardLists.add(4, Arrays.asList(3, 7, 4, 6, 8, 2, 9, 1, 5));
		boardLists.add(5, Arrays.asList(9, 5, 1, 7, 4, 3, 6, 2, 8));
		boardLists.add(6, Arrays.asList(5, 1, 9, 3, 2, 6, 8, 7, 4));
		boardLists.add(7, Arrays.asList(2, 4, 8, 9, 5, 7, 1, 3, 6));
		boardLists.add(8, Arrays.asList(7, 6, 3, 4, 1, 8, 2, 5, 9));

		SudokuBoard staticBoard = new SudokuBoard(boardLists);
		logger.info("setStaticBoard staticBoard " + staticBoard);

		staticSudokuBoardMap.put(SOLVED_STATIC_BOARD, staticBoard);

		// remove few elements (set to -1) randomly to create a SUDOKU puzzle
		List<List<Integer>> newPuzzle = new ArrayList<>(BOARD_SIZE);
		int index = 0;
		for (List<Integer> eachRow : boardLists) {
			List<Integer> newRow = new ArrayList<>(eachRow);
			IntStream itStr = new Random().ints(BOARD_SIZE / 2, 0, BOARD_SIZE);
			IntConsumer intFun = a -> newRow.set(a, -1);
			itStr.iterator().forEachRemaining(intFun);
			logger.info("setStaticBoard randomNumberList for each row " + newRow);
			newPuzzle.add(index++, newRow);
		}
		SudokuBoard newPuzzleBoard = new SudokuBoard(newPuzzle);
		logger.info("setStaticBoard newPuzzleBoard " + newPuzzleBoard);

		staticSudokuBoardMap.put(UNSOLVED_STATIC_BOARD, newPuzzleBoard);

		return staticBoard;
	}

	@Override
	public SudokuBoard setupBoard() {
		setStaticBoard();
		return staticSudokuBoardMap.get(UNSOLVED_STATIC_BOARD);
	}

	@Override
	public SudokuBoard returnSolvedStaticBoard() {
		logger.info("returnSolvedStaticBoard() Returning original static sudoku board "
				+ staticSudokuBoardMap.get(SOLVED_STATIC_BOARD));

		if (staticSudokuBoardMap.get(SOLVED_STATIC_BOARD) == null) {
			setStaticBoard();
		}
		return staticSudokuBoardMap.get(SOLVED_STATIC_BOARD);
	}


	/**
	 * This method checks of the partial board is valid or not
	 */
	@Override
	public boolean isValidBoard(SudokuBoard solvedBoard) {
		return isValidMatrix(solvedBoard.getBoard(),0,BOARD_SIZE) && isEachInnerMatrixValid(solvedBoard.getBoard());
	}

	private boolean isValidMatrix(List<List<Integer>> board, int start, int end) {
		Set<Integer> rowSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		Set<Integer> colSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		for (int row = start; row < end; row++) {
			for (int col = start; col < end; col++) {
				if (rowSet.contains(board.get(row).get(col)) && colSet.contains(board.get(col).get(row))) {
					rowSet.remove(board.get(row).get(col));
					colSet.remove(board.get(col).get(row));
				} else {
					return false;
				}
			}
			// fill the sets
			rowSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
			colSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		}
		return true;
	}

	private boolean isEachInnerMatrixValid(List<List<Integer>> solvedBoard) {
		int sizeOfSmallMatrix = 3;

		for(int row = 0 ; row < BOARD_SIZE ; row+=sizeOfSmallMatrix) {
			for(int col=0; col < BOARD_SIZE;col+=sizeOfSmallMatrix) {
				if(!isMatrixUnique(solvedBoard,row,col)) return false;
			}
		}
		return true;
	}
	private boolean isMatrixUnique(List<List<Integer>> board, int start, int end) {
		Set<Integer> matrixSet = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));
		int sizeOfSmallMatrix = 3;
		for (int row = start; row < (start + sizeOfSmallMatrix); row++) {
			for (int col = end; col < (end + sizeOfSmallMatrix); col++) {
				if (matrixSet.contains(board.get(row).get(col))) {
					matrixSet.remove(board.get(row).get(col));
				} else {
					return false;
				}
			}
		}
        return true;
	}

	/**
	 * This method validate the partial SudokuBoard By comparing each element with
	 * solved board
	 */
	@Override
	public TrueSudokuBoard doBoardValidation(SudokuBoard partialBoard) {
		// get original solved board
		SudokuBoard solvedBoard = staticSudokuBoardMap.get(SOLVED_STATIC_BOARD);
		List<List<Integer>> solvedBoardList = solvedBoard.getBoard();
		List<List<Integer>> partialBoardList = partialBoard.getBoard();
		List<List<Boolean>> validBoardList = new ArrayList<>(BOARD_SIZE);
		// check each element
		for (int row = 0; row < BOARD_SIZE; row++) {
			List<Boolean> eachRowValidList = new ArrayList<>(BOARD_SIZE);
			for (int col = 0; col < BOARD_SIZE; col++) {
				if (solvedBoardList.get(row).get(col) == partialBoardList.get(row).get(col)) {
					eachRowValidList.add(col, true);
				} else {
					eachRowValidList.add(col, false);
				}
			}
			validBoardList.add(row, eachRowValidList);
		}
		return new TrueSudokuBoard(validBoardList);
	}

	@Override
	public SudokuBoard restart() {
		setStaticBoard();
		return staticSudokuBoardMap.get(UNSOLVED_STATIC_BOARD);
	}
	
	public static void main(String[] args) {
		List<List<Integer>> boardLists = new ArrayList<>(BOARD_SIZE);

		boardLists.add(0, Arrays.asList(4, 3, 5, 2, 6, 9, 7, 9, 1));
		boardLists.add(1, Arrays.asList(6, 8, 2, 5, 7, 1, 4, 9, 3));
		boardLists.add(2, Arrays.asList(1, 9, 7, 8, 3, 4, 5, 6, 2));
		boardLists.add(3, Arrays.asList(8, 2, 6, 1, 9, 5, 3, 4, 7));
		boardLists.add(4, Arrays.asList(3, 7, 4, 6, 8, 2, 9, 1, 5));
		boardLists.add(5, Arrays.asList(9, 5, 1, 7, 4, 3, 6, 2, 8));
		boardLists.add(6, Arrays.asList(5, 1, 9, 3, 2, 6, 8, 7, 4));
		boardLists.add(7, Arrays.asList(2, 4, 8, 9, 5, 7, 1, 3, 6));
		boardLists.add(8, Arrays.asList(7, 6, 3, 4, 1, 8, 2, 5, 9));
	//	boolean result1 = isMatrixUnique(boardLists, 0, 6);
	//	System.out.println(result1);
	//	boolean result = isEachInnerMatrixValid(boardLists);
	//	System.out.println(result);

	}

}
