package com.expcube.othello.game;

import com.expcube.othello.constants.CommonConst;
import com.expcube.othello.players.Player;

import java.util.Arrays;
import java.util.stream.IntStream;

public class GameStarter {

    private static String[] columns = { "  ", "A ", " B ", " C ", " D ", " E ", " F ", " G ", " H " };

    private GameStarter() { }

    /**
     * ゲームを開始します。
     * <BR><BR>
     * @param player1 黒を取ります。
     * @param player2 白を取ります。
     */
    public static void start(Player player1, Player player2)  {
        try {
            // プレイヤーに色を設定する
            player1.setColor(CommonConst.BLACK);
            player2.setColor(CommonConst.WHITE);
            // 現在手番の色
            int currentColor = CommonConst.WHITE;
            // ゲーム開始
            System.out.println("******* ゲーム開始 *******");
            while (true) {
                // 盤面表示
                printBoard();
                // 現在の手番を設定
                if (currentColor == CommonConst.WHITE) {
                    currentColor = CommonConst.BLACK;
                }
                else {
                    currentColor = CommonConst.WHITE;
                }
                // ゲームチェック
                if (GameData.checkTheGame()) break;
                // プレイヤー１の手番
                if (currentColor == player1.getColor()) {
                    player1.logic();
                }
                // プレイヤー２の手番
                else player2.logic();
            }
            // ゲーム終了
            System.out.println("******* ゲーム終了 *******");
            System.out.print("結果：");
            showStatus();
            System.out.println();
            // 勝者出力
            int winnersColor = GameData.getWinnersColor();
            if (winnersColor != 0) {
                String winnersName = player1.getName();
                if (winnersColor == player2.getColor()) {
                    winnersName = player2.getName();
                }
                String msg = String.format("> %s(%s)の勝ちです。", winnersName, GameData.getColorStr(winnersColor));
                System.out.println(msg);
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
    private static void printBoard() {
        // 盤面の標準出力
        Arrays.stream(columns).forEachOrdered(System.out::print);
        System.out.println();
        IntStream.range(0, GameData.getBoard().length).forEachOrdered(i -> {
            System.out.print(i + 1);
            IntStream.range(0, GameData.getBoard()[i].length).forEachOrdered(j -> {
                System.out.print(convertNumToChar(GameData.getBoard()[i][j]));
            });
            System.out.println();
        });
        // 戦況を表示
        showStatus();
        System.out.println();
    }

    /**
     * 数値で表現された色を石文字に変換します。
     * <BR><BR>
     * @param COLOR 色
     * @return 石文字を返却します。
     */
    private static String convertNumToChar(final int COLOR) {
        switch (COLOR) {
            case CommonConst.WHITE: return " ○ ";
            case CommonConst.BLACK: return " ● ";
            default: return " ― ";
        }
    }

    /**
     * 状況を表示します。
     * <BR><BR>
     */
    private static void showStatus() {
        String colorsCnt = String.format("白 : %s    黒 : %s",
                GameData.countColorOf(CommonConst.WHITE),
                GameData.countColorOf(CommonConst.BLACK));
        System.out.println(colorsCnt);
    }

}
