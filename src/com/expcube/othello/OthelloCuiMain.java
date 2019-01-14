/*
 *
 *   Copyright 2018 Xena.
 *
 *   This software is released under the MIT License.
 *   http://opensource.org/licenses/mit-license.php
 *
 */

package com.expcube.othello;

/**
 * オセロのCUI対戦用メインクラスです。
 */
public class OthelloCuiMain {

	/**
	 * メインメソッドです。
	 * <BR><BR>
	 * @param args
	 */
	public static void main(String[] args) {
		OthelloCuiPlayer player1 = new OthelloCuiUser("PLAYER1");
		OthelloCuiPlayer player2 = new OthelloCuiUser("PLAYER2");
		start(player1, player2);
	}

	/**
	 * ゲームを開始します。
	 * <BR><BR>
	 * @param player1 黒を取ります。
	 * @param player2 白を取ります。
	 */
	public static void start(OthelloCuiPlayer player1, OthelloCuiPlayer player2)  {
		try {
			// プレイヤーに色を設定する
			player1.setColor(OthelloCommonConst.BLACK);
			player2.setColor(OthelloCommonConst.WHITE);
			// 現在手番の色
			int currentColor = OthelloCommonConst.WHITE;
			// ゲーム開始
			System.out.println("******* ゲーム開始 *******");
			while (true) {
				// 盤面表示
				printBoard();
				// 現在の手番を設定
				if (currentColor == OthelloCommonConst.WHITE) currentColor = OthelloCommonConst.BLACK;
				else currentColor = OthelloCommonConst.WHITE;
				// ゲームチェック
				if (OthelloData.checkTheGame()) break;
				// プレイヤー１の手番
				if (currentColor == player1.getColor()) player1.logic();
				// プレイヤー２の手番
				else player2.logic();
			}
			// ゲーム終了
			System.out.println("******* ゲーム終了 *******");
			System.out.print("結果：");
			System.out.println(
					"白 : " + OthelloData.countColorOf(OthelloCommonConst.WHITE) +
					"    " +
					"黒 : " + OthelloData.countColorOf(OthelloCommonConst.BLACK));
			System.out.println();
			// 勝者出力
			int winnersColor = OthelloData.getWinnersColor();
			if (winnersColor != 0) {
				String winnersName = player1.getName();
				if (winnersColor == player2.getColor()) {
					winnersName = player2.getName();
				}
				System.out.println("> " + winnersName + "(" + OthelloData.getColorStr(winnersColor) + ")の勝ちです。");
			}
			// 引き分け
			else {
				System.out.println("引き分けです。");
			}
			System.gc();
		}
		catch (Throwable th) {
			th.printStackTrace();
		}
	}

	/**
	 * 盤面の状態を標準出力します。
	 * <BR><BR>
	 */
	public static void printBoard() {
		String[] columns = { "  ", "A ", " B ", " C ", " D ", " E ", " F ", " G ", " H " };
		for (String colum : columns) System.out.print(colum);
		System.out.println();
		for (int i = 0; i < OthelloData.getBoard().length; i++) {
			System.out.print(i + 1);
			for (int j = 0; j < OthelloData.getBoard()[i].length; j++) {
				System.out.print(convertNumToChar(OthelloData.getBoard()[i][j]));
			}
			System.out.println();
		}
		System.out.println(
				"白 : " + OthelloData.countColorOf(OthelloCommonConst.WHITE) +
				"    " +
				"黒 : " + OthelloData.countColorOf(OthelloCommonConst.BLACK));
		System.out.println();
	}

	/**
	 * 数値で表現された色を石文字に変換します。
	 * <BR><BR>
	 * @param COLOR 色
	 * @return 石文字を返却します。
	 */
	public static String convertNumToChar(final int COLOR) {
		if (OthelloCommonConst.WHITE == COLOR) {
			return " ○ ";
		}
		else if (OthelloCommonConst.BLACK == COLOR) {
			return " ● ";
		}
		else {
			return " ― ";
		}
	}

}
