package com.expcube.othello.players;

public abstract class Player {

    /** 色 */
    private int color;

    /** 名前 */
    private String name;

    /**
     * コンストラクタです。
     * <BR><BR>
     * @param name 名前
     */
    public Player(String name) {
        this.name = name;
    }

    /**
     * 手番処理を行います。
     * <BR><BR>
     */
    public abstract void logic() throws Throwable;

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

}
