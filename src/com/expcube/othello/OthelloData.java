/*
 *
 *   Copyright 2018 Xena.
 *
 *   This software is released under the MIT License.
 *   http://opensource.org/licenses/mit-license.php
 *
 */

package com.expcube.othello;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * オセロデータクラスです。
 * <BR><BR>
 */
abstract class OthelloData {

    /** 盤面 */
    private static byte[][] board;

    /**
     * 盤面を取得します。
     * <BR><BR>
     * @return 盤面を返却します。
     */
    public static byte[][] getBoard() {
        if (board == null) {
            board = new byte[][] {
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 1, -1, 0, 0, 0 },
                    { 0, 0, 0, -1, 1, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0 },
                    { 0, 0, 0, 0, 0, 0, 0, 0 } };
        }
        return board;
    }

    /**
     * 勝者の色を取得します。
     * <BR><BR>
     * @return 勝者の色を返却します。
     */
    public static int getWinnersColor() {
        if (countColorOf(OthelloCommonConst.WHITE) > countColorOf(OthelloCommonConst.BLACK)) {
            return OthelloCommonConst.WHITE;
        }
        else if (countColorOf(OthelloCommonConst.WHITE) < countColorOf(OthelloCommonConst.BLACK)) {
            return OthelloCommonConst.BLACK;
        }
        return 0;
    }

    /**
     * 盤面すべてが埋まっているか検証します。
     * <BR><BR>
     * @return 全て埋まっている場合Trueを返却します。
     */
    public static boolean checkTheGame() {
        // どちらかの色しか残っていない場合
        if (countColorOf(OthelloCommonConst.WHITE) == 0 || countColorOf(OthelloCommonConst.BLACK) == 0) {
            return true;
        }
        // 全て埋まっているか検証
        for (byte[] xBoard : getBoard()) {
            for (byte stone : xBoard) {
                // 一つでも空きが見つかった場合false
                if (stone == OthelloCommonConst.BLANK) return false;
            }
        }
        return true;
    }

    /**
     * 盤面に石が置ける状態かチェックします。
     * <BR><BR>
     * @param color  色
     * @return 盤面に石が置ける場合Trueを返却します。
     */
    public static boolean putable(int color) {
        for (byte i = 0; i < getBoard().length; i++) {
            for (byte j = 0; j < getBoard()[i].length; j++) {
                if (canPutTo(color, i, j))
                    return true;
            }
        }
        return false;
    }

    /**
     * 指定場所に石が置けるかチェックします。
     * <BR> <BR>
     * @param color 色
     * @param x  x座標
     * @param y  y座標
     * @return 指定場所に石が置ける場合Trueを返却します。
     */
    public static boolean canPutTo(int color, int x, int y) {
        // 戻り値用
        boolean retValue = false;
        // 座標をインデックスに変換
        int[] arrayPoints = convertCordinateToArrayPoints(x, y);
        int xIndex = arrayPoints[0];
        int yIndex = arrayPoints[1];
        // すでに石が置いてある場合
        if (getColorOf(xIndex, yIndex) != OthelloCommonConst.BLANK) {
            retValue = false;
        }
        // 上方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_UPPER, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 右方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_RIGHT, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 右方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_BOTTOM, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 左方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_LEFT, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 右上方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_UPPER_RIGHT, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 左上方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_UPPER_LEFT, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 右下方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_BOTTOM_RIGHT, color, xIndex, yIndex)) {
            retValue = true;
        }
        // 左下方向に反転可能な場合
        else if (canPlaceToThe(OthelloCommonConst.VECTOR_BOTTOM_LEFT, color, xIndex, yIndex)) {
            retValue = true;
        }
        return retValue;
    }

    /**
     * 指定した色の数を取得します。
     * <BR><BR>
     * @param COLOR 色
     * @return 色の数を返却します。
     */
    public static int countColorOf(final int COLOR) {
        int count = 0;
        for (int i = 0; i < getBoard().length; i++) {
            for (int j = 0; j < getBoard()[i].length; j++) {
                if (getColorOf(i, j) == COLOR)
                    count++;
            }
        }
        return count;
    }

    /**
     * X、Y座標で渡された情報を、配列のインデックスに変換します。
     * <BR><BR>
     * @param x X座標
     * @param y Y座標
     * @return 配列のインデックスを返却します。
     */
    public static int[] convertCordinateToArrayPoints(int x, int y) {
        int[] arrayPoints = new int[2];
        arrayPoints[0] = y;
        arrayPoints[1] = x;
        return arrayPoints;
    }

    /**
     * 指定場所の色を取得します。
     * <BR><BR>
     * @param x X座標
     * @param y Y座標
     * @return 指定場所の色を取得します。
     */
    public static int getColorOf(int x, int y) {
        return getBoard()[x][y];
    }

    /**
     * 指定場所の指定方向に隣接している色を取得します。
     * <BR> <BR>
     * @param VECTOR 方向
     * @param xIndex Xインデックス
     * @param yIndex Yインデックス
     * @return 色を返却します。
     */
    public static int getColorOf(final int VECTOR, int xIndex, int yIndex) {
        int retColor = OthelloCommonConst.BLANK;
        if (OthelloCommonConst.VECTOR_UPPER == VECTOR) {
            retColor = getColorOf(xIndex - 1, yIndex);
        } else if (OthelloCommonConst.VECTOR_RIGHT == VECTOR) {
            retColor = getColorOf(xIndex, yIndex + 1);
        } else if (OthelloCommonConst.VECTOR_BOTTOM == VECTOR) {
            retColor = getColorOf(xIndex + 1, yIndex);
        } else if (OthelloCommonConst.VECTOR_LEFT == VECTOR) {
            retColor = getColorOf(xIndex, yIndex - 1);
        } else if (OthelloCommonConst.VECTOR_UPPER_RIGHT == VECTOR) {
            retColor = getColorOf(xIndex - 1, yIndex + 1);
        } else if (OthelloCommonConst.VECTOR_UPPER_LEFT == VECTOR) {
            retColor = getColorOf(xIndex - 1, yIndex - 1);
        } else if (OthelloCommonConst.VECTOR_BOTTOM_RIGHT == VECTOR) {
            retColor = getColorOf(xIndex + 1, yIndex + 1);
        } else if (OthelloCommonConst.VECTOR_BOTTOM_LEFT == VECTOR) {
            retColor = getColorOf(xIndex + 1, yIndex - 1);
        }
        return retColor;
    }

    /**
     * 盤面に石を置きます。
     * <BR><BR>
     * @param COLOR 色
     * @param x X座標
     * @param y Y座標
     */
    public static void reverse(final int COLOR, int x, int y) {
        // 座標をインデックスに変換
        int[] arrayPoints = convertCordinateToArrayPoints(x, y);
        int xIndex = arrayPoints[0];
        int yIndex = arrayPoints[1];
        reverseAll(COLOR, xIndex, yIndex);
    }

    /**
     * 盤面に石を起きます。
     * <BR><BR>
     * @param COLOR 色
     * @param list 反転させる座標リスト
     */
    private static void reverse(final int COLOR, List<int[]> list) {
        for (int[] cordinate : new ArrayList<int[]>(list)) {
            getBoard()[cordinate[0]][cordinate[1]] = (byte) COLOR;
        }
    }

    /**
     * 反転させる石の座標をリスト煮詰めます。
     * <BR><BR>
     * @param reverseList 反転させる石の座標リスト
     * @param innerList 繰り返し文の内部で溜め込んでいる座標リスト
     * @param COLOR 手番の色
     * @param xIndex Xインデックス
     * @param yIndex Yインデックス
     * @return break可能な場合Trueを返却します。
     */
    private static boolean addReverseList(List<int[]> reverseList, List<int[]> innerList, final int COLOR, int xIndex, int yIndex) {
        // 相手の石の色
        final int ENEMY_COLOR = COLOR * -1;
        // 範囲外に到達している場合
        if (xIndex < 0 || xIndex > 7 || yIndex < 0 || yIndex > 7) {
            return true;
        }
        // 空白の場合終了
        else if (getColorOf(xIndex, yIndex) == OthelloCommonConst.BLANK) {
            return true;
        }
        // 相手の色の場合続行
        else if (getColorOf(xIndex, yIndex) == ENEMY_COLOR) {
            innerList.add(new int[] {xIndex, yIndex });
            return false;
        }
        // 自身の色が来たら検証終了
        else if (getColorOf(xIndex, yIndex) == COLOR) {
            reverseList.addAll(innerList);
            return true;
        }
        return true;
    }

    /**
     * 盤面に石を起きます。
     * <BR><BR>
     * @param VECTOR 方向
     * @param COLOR 色
     * @param xIndex Xインデックス
     * @param yIndex Yインデックス
     */
    private static void reverseAll(final int COLOR, int xIndex, int yIndex) {
        // 現在の場所に置く
        getBoard()[xIndex][yIndex] = (byte) COLOR;
        // 反転させる座標を詰めるリスト
        List<int[]> reverseList = new LinkedList<int[]>();
        // 相手の石の色
        final int ENEMY_COLOR = COLOR * -1;
        // 上方向に反転可能かチェック
        if (xIndex > 1) {
            // 一つ上が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_UPPER, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex - 1, j = yIndex; 0 <= i; i--) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 右方向に置けるか検証
        if (yIndex < getBoard()[xIndex].length - 2) {
            // 一つ右が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_RIGHT, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex, j = yIndex + 1; j < getBoard()[xIndex].length; j++) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 下方向に置けるか検証
        if (xIndex < getBoard().length - 2) {
            // 一つ下が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_BOTTOM, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex + 1, j = yIndex; i < getBoard().length; i++) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 左方向に置けるか検証
        if (yIndex > 1) {
            // 一つ左が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_LEFT, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex, j = yIndex - 1; 0 <= j; j--) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 右上方向に置けるか検証
        if (xIndex > 1 && yIndex < getBoard()[xIndex].length - 2) {
            // 一つ右上が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_UPPER_RIGHT, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex - 1, j = yIndex + 1; 0 <= i || j < getBoard()[i].length; i--, j++) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 左上方向に置けるか検証
        if (xIndex > 1 && yIndex > 1) {
            // 一つ上が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_UPPER_LEFT, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex - 1, j = yIndex - 1; 0 <= i || 0 <= j; i--, j--) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 右下方向に置けるか検証
        if (xIndex < getBoard().length - 2 && yIndex < getBoard()[xIndex].length - 2) {
            // 一つ下が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_BOTTOM_RIGHT, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex + 1, j = yIndex + 1; i < getBoard().length
                        || j < getBoard()[xIndex].length; i++, j++) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        // 左下方向に置けるか検証
        if (xIndex < getBoard().length - 2 && yIndex > 1) {
            // 一つ下が相手の色の場合検証する
            if (getColorOf(OthelloCommonConst.VECTOR_BOTTOM_LEFT, xIndex, yIndex) == ENEMY_COLOR) {
                List<int[]> innerList = new LinkedList<int[]>();
                for (int i = xIndex + 1, j = yIndex - 1; i < getBoard().length || 0 <= j; i++, j--) {
                    if (addReverseList(reverseList, innerList, COLOR, i, j)) break;
                }
            }
        }
        reverse(COLOR, reverseList);
    }

    /**
     * 指定場所の指定方向に石を置けるか検証します。
     * <BR> <BR>
     * @param VECTOR  方向
     * @param COLOR 色
     * @param xIndex xインデックス
     * @param yIndex  yインデックス
     * @return 空間がある場合Trueを返却します。
     */
    public static boolean canPlaceToThe(final int VECTOR, final int COLOR, int xIndex, int yIndex) {
        // 戻り値用
        boolean retValue = false;
        // 相手の石の色
        final int ENEMY_COLOR = COLOR == OthelloCommonConst.WHITE ? OthelloCommonConst.BLACK : OthelloCommonConst.WHITE;
        // 上方向に反転可能かチェック
        if (OthelloCommonConst.VECTOR_UPPER == VECTOR) {
            if (xIndex > 1) {
                // 一つ上が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_UPPER, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex - 1, j = yIndex; 0 <= i; i--) {
                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 右方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_RIGHT == VECTOR) {
            if (yIndex < getBoard()[xIndex].length - 2) {
                // 一つ右が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_RIGHT, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex, j = yIndex + 1; j < getBoard()[xIndex].length; j++) {
                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 下方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_BOTTOM == VECTOR) {
            if (xIndex < getBoard().length - 2) {
                // 一つ下が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_BOTTOM, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex + 1, j = yIndex; i < getBoard().length; i++) {

                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 左方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_LEFT == VECTOR) {
            if (yIndex > 1) {
                // 一つ左が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_LEFT, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex, j = yIndex - 1; 0 <= j; j--) {

                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 右上方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_UPPER_RIGHT == VECTOR) {
            if (xIndex > 1 && yIndex < getBoard()[xIndex].length - 2) {
                // 一つ右上が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_UPPER_RIGHT, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex - 1, j = yIndex + 1; 0 <= i || j < getBoard()[xIndex].length; i--, j++) {
                        if (i < 0 || j >= 8)
                            break;
                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 左上方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_UPPER_LEFT == VECTOR) {
            if (xIndex > 1 && yIndex > 1) {
                // 一つ上が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_UPPER_LEFT, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex - 1, j = yIndex - 1; 0 <= i || 0 <= j; i--, j--) {
                        if (i < 0 || j < 0)
                            break;
                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 右下方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_BOTTOM_RIGHT == VECTOR) {
            if (xIndex < getBoard().length - 2 && yIndex < getBoard()[xIndex].length - 2) {
                // 一つ下が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_BOTTOM_RIGHT, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex + 1, j = yIndex + 1; i < getBoard().length
                            || j < getBoard()[xIndex].length; i++, j++) {
                        if (i >= 8 || j >= 8)
                            break;
                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        // 左下方向に置けるか検証
        else if (OthelloCommonConst.VECTOR_BOTTOM_LEFT == VECTOR) {
            if (xIndex < getBoard().length - 2 && yIndex > 1) {
                // 一つ下が相手の色の場合検証する
                if (getColorOf(OthelloCommonConst.VECTOR_BOTTOM_LEFT, xIndex, yIndex) == ENEMY_COLOR) {
                    for (int i = xIndex + 1, j = yIndex - 1; i < getBoard().length || 0 <= j; i++, j--) {
                        if (i >= 8 || j < 0)
                            break;
                        // 空白の場合終了
                        if (getColorOf(i, j) == OthelloCommonConst.BLANK) {
                            break;
                        }
                        // 相手の色の場合続行
                        else if (getColorOf(i, j) == ENEMY_COLOR) {
                            continue;
                        }
                        // 自身の色が来たら検証終了
                        else if (getColorOf(i, j) == COLOR) {
                            retValue = true;
                            break;
                        }
                    }
                }
            }
        }
        return retValue;
    }

    /**
     * 色名を取得します。
     * <BR><BR>
     * @param COLOR 色
     * @return 色名を返却します。
     */
    public static String getColorStr(final int COLOR) {
        if (OthelloCommonConst.WHITE == COLOR) {
            return "白";
        } else if (OthelloCommonConst.BLACK == COLOR) {
            return "黒";
        } else {
            return "なし";
        }
    }

}
