/*
 *
 *   Copyright 2018 Xena.
 *
 *   This software is released under the MIT License.
 *   http://opensource.org/licenses/mit-license.php
 *
 */

package com.expcube.othello;

import java.util.regex.Pattern;

/**
 * オセロプレイヤークラスです。
 * <BR><BR>
 */
abstract class OthelloCuiPlayer {

    /** 色 */
    private int color;

    /** 名前 */
    private String name;

    /**
     * コンストラクタです。
     * <BR><BR>
     * @param color 色
     * @param name 名前
     */
    public OthelloCuiPlayer(String name) {
        this.name = name;
    }

    /**
     * 手番処理を行います。
     * <BR><BR>
     */
    public final void logic() throws Throwable {
        System.out.println("> " + getName() + "（" + OthelloData.getColorStr(getColor()) + "）の番です。");
        if (!OthelloData.putable(getColor())) {
            System.out.println("> 置く場所がないのでスキップします。");
            System.out.println();
        }
        else {
            int[] cordinate = input();
            int x = cordinate[0], y = cordinate[1];
            System.out.println("> 入力値 : " + x + "/" + y);
            OthelloData.reverse(getColor(), x, y);
            Thread.sleep(100);
        }
        System.out.println();
    }

    /**
     * 指し手の入力操作を定義します。
     * <BR><BR>
     */
    public abstract int[] input();

    /**
     * 自身の色を設定します。
     * <BR><BR>
     * @param color 色
     */
    public void setColor(int color) {
        this.color = color;
    }

    /**
     * 自身の色を取得します。
     * <BR><BR>
     * @return 自身の色を返却します。
     */
    public int getColor() {
        return this.color;
    }

    /**
     * 自身の名前を取得します。
     * <BR><BR>
     * @return 自身の名前を返却します。
     */
    public String getName() {
        return this.name;
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
