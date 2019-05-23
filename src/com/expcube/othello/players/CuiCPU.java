/*
 *
 *   Copyright 2018 Xena.
 *
 *   This software is released under the MIT License.
 *   http://opensource.org/licenses/mit-license.php
 *
 */

package com.expcube.othello.players;

import com.expcube.othello.game.GameData;

/**
 * オセロのCPUクラスです。
 */
public class CuiCPU extends CuiPlayer {

    /**
     * コンストラクタです。
     * <BR><BR>
     * @param name 名前
     */
    public CuiCPU(String name) {
        super(name);
    }

    @Override
    public int[] input() {
        int[] cordinate = new int[2];
        for (int i = 0; i < GameData.getBoard().length; i++) {
            for (int j = 0; j < GameData.getBoard()[i].length; j++) {
                if (GameData.canPutTo(getColor(), j, i)) {
                    cordinate[0] = j;
                    cordinate[1] = i;
                }
            }
        }
        return cordinate;
    }

}
