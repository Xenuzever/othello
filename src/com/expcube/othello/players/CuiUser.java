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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * オセロユーザー操作クラスです。
 * <BR><BR>
 */
public class CuiUser extends CuiPlayer {

    /**
     * コンストラクタです。
     * <BR><BR>
     * @param name 名前
     */
    public CuiUser(String name) {
        super(name);
    }

    @Override
    public int[] input() {
        int[] cordinate = null;
        while (true) {
            // 標準入力の準備
            InputStreamReader isr = new InputStreamReader(System.in);
            BufferedReader br = new BufferedReader(isr);
            System.out.println("> 入力を行ってください。（例:A1）");
            String input = "";
            try {
                input = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            cordinate = parseCordinate(input);
            int x = cordinate[0], y = cordinate[1];
            // 正しい値かチェック
            if (!(0 <= x && x < 8 && 0 <= y && y < 8)) {
                System.out.println("不正な値が入力されました。再度入力してください。");
                continue;
            }
            // 入力可能かチェック
            if (GameData.canPutTo(getColor(), x, y)) {
                GameData.reverse(getColor(), x, y);
            }
            else {
                System.out.println(x + " " + y);
                System.out.println("> " + input + "に置くことはできません。");
                continue;
            }
            break;
        }
        return cordinate;
    }

}
