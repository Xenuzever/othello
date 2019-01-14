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
 * オセロのCPUクラスです。
 */
class OthelloCuiCPU extends OthelloCuiPlayer{

    /**
     * コンストラクタです。
     * <BR><BR>
     * @param color 色
     * @param name 名前
     */
    public OthelloCuiCPU(String name) {
        super(name);
    }

    @Override
    public int[] input() {
        int[] cordinate = new int[2];
        for (int i = 0; i < OthelloData.getBoard().length; i++) {
            for (int j = 0; j < OthelloData.getBoard()[i].length; j++) {
                if (OthelloData.canPutTo(getColor(), j, i)) {
                    cordinate[0] = j;
                    cordinate[1] = i;
                }
            }
        }
        return cordinate;
    }

}
