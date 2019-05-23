/*
 *
 *   Copyright 2018 Xena.
 *
 *   This software is released under the MIT License.
 *   http://opensource.org/licenses/mit-license.php
 *
 */

package com.expcube.othello.constants;

/**
 * オセロ共通定数クラスです。
 */
public class CommonConst {

    /**
     * 改行コード
     */
    public static String LINE = System.getProperty("line.separator");

    /**
     * 対戦モード：対プレイヤー
     */
    public static final int VS_PLAYER = 1;

    /**
     * 対戦モード：対CPU
     */
    public static final int VS_CPU = 2;

    /**
     * 黒石
     */
    public static final int BLACK = 1;

    /**
     * 白石
     */
    public static final int WHITE = -1;

    /**
     * 空き
     */
    public static final int BLANK = 0;

    /**
     * 上方向
     */
    public static final int VECTOR_UPPER = 1;

    /**
     * 右方向
     */
    public static final int VECTOR_RIGHT = 2;

    /**
     * 下方向
     */
    public static final  int VECTOR_BOTTOM = 3;

    /**
     * 左方向
     */
    public static final int VECTOR_LEFT = 4;

    /**
     * 右上方向
     */
    public static final int VECTOR_UPPER_RIGHT = 5;

    /**
     * 左上方向
     */
    public static final int VECTOR_UPPER_LEFT = 6;

    /**
     * 右下方向
     */
    public static final int VECTOR_BOTTOM_RIGHT = 7;

    /**
     * 左下方向
     */
    public static final int VECTOR_BOTTOM_LEFT = 8;

}

