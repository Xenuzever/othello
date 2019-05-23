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

import java.util.regex.Pattern;

/**
 * オセロプレイヤークラスです。
 * <BR><BR>
 */
public abstract class CuiPlayer extends Player {

    /**
     * コンストラクタです。
     * <BR><BR>
     * @param name 名前
     */
    public CuiPlayer(String name) {
        super(name);
    }

    /**
     * 手番処理を行います。
     * <BR><BR>
     */
    public final void logic() throws Throwable {
        System.out.println("> " + getName() + "（" + GameData.getColorStr(getColor()) + "）の番です。");
        if (!GameData.putable(getColor())) {
            System.out.println("> 置く場所がないのでスキップします。");
            System.out.println();
        }
        else {
            int[] cordinate = input();
            int x = cordinate[0], y = cordinate[1];
            System.out.println("> 入力値 : " + x + "/" + y);
            GameData.reverse(getColor(), x, y);
            Thread.sleep(100);
        }
        System.out.println();
    }

    /**
     * 入力値を解析し、盤上の座標に変換します。
     * <BR><BR>
     * @param input 入力値
     * @return 盤上の座標を返却します。
     */
    protected int[] parseCordinate(String input) {
        int[] cordinate = new int[2];
        if (isMatched("^[A-H][1-8]$", input)) {
            String index0 = String.valueOf(input.charAt(0));
            String index1 = String.valueOf(input.charAt(1));
            int x = -1;
            int y = Integer.parseInt(index1) - 1;
            if (index0.equals("A")) x = 0;
            else if (index0.equals("B")) x = 1;
            else if (index0.equals("C")) x = 2;
            else if (index0.equals("D")) x = 3;
            else if (index0.equals("E")) x = 4;
            else if (index0.equals("F")) x = 5;
            else if (index0.equals("G")) x = 6;
            else if (index0.equals("H")) x = 7;
            cordinate[0] = x;
            cordinate[1] = y;
        }
        else {
            cordinate[0] = -1;
            cordinate[1] = -1;
        }
        return cordinate;
    }

    /**
     * 正規表現のチェックを行います。
     * <BR><BR>
     * @param regex 正規表現
     * @param value チェックする値
     * @return マッチした場合Trueを返却します。
     */
    protected boolean isMatched(String regex, String value) {
        return Pattern.compile(regex).matcher(value).find();
    }

}
