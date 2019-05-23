/*
 *
 *   Copyright 2018 Xena.
 *
 *   This software is released under the MIT License.
 *   http://opensource.org/licenses/mit-license.php
 *
 */

package com.expcube.othello;

import com.expcube.othello.game.GameStarter;
import com.expcube.othello.players.CuiCPU;
import com.expcube.othello.players.Player;

/**
 * オセロのCUI対戦用メインクラスです。
 */
public class CuiMain {

	/**
	 * メインメソッドです。
	 * <BR><BR>
	 * @param args
	 */
	public static void main(String[] args) {
		Player player1 = new CuiCPU("PLAYER1");
		Player player2 = new CuiCPU("PLAYER2");
		GameStarter.start(player1, player2);
	}

}
