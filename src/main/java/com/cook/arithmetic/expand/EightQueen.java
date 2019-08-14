package com.cook.arithmetic.expand;

/**
 * 象棋八皇后问题
 *
 * @author zhengjian
 * @date 2018 -05 -16 10:08
 */
public class EightQueen {
    private static final int MAX_NUM = 8;
    private int[][] chessBoard = new int[MAX_NUM][MAX_NUM];
    private int num = 0;
    private int queenNum = 0;

    private boolean checkPlace(int x, int y){
        for (int i = 0; i < y; i++){
            if (chessBoard[x][i] == 1){
                return false;
            }
            if (x - i - 1 >= 0 && chessBoard[x-i-1][y-i-1] == 1){
                return false;
            }
            if (x + i + 1 < MAX_NUM && chessBoard[x + i + 1][y-i-1] == 1){
                return false;
            }
        }
        return true;
    }

    private boolean settleQueen(int y){
        if (y == MAX_NUM){
            return true;
        }
        for (int i = 0; i < MAX_NUM; i++){
            num ++;
            for (int x = 0; x < MAX_NUM; x++){
                chessBoard[x][y] = 0;
            }
            if (checkPlace(i, y)){
                chessBoard[i][y] = 1;
                if (settleQueen(y+1)){
                    return true;
                }
            }
        }
        return false;
    }

    private boolean settleAllQueen(int y){
        if (y == MAX_NUM){
            return true;
        }
        for (int i = 0; i < MAX_NUM; i++){
            num ++;
            for (int x = 0; x < MAX_NUM; x++){
                chessBoard[x][y] = 0;
            }
            if (checkPlace(i, y)){
                chessBoard[i][y] = 1;
                if (settleQueen(y+1)){
                    if (y > 0 && y < MAX_NUM -1) {
                        return true;
                    } else {
                        queenNum ++;
                        printQueen();
                    }
                }
            }
        }
        System.out.println("共寻找了" + num + "次，找到了了" + queenNum + "个queen");
        return false;
    }

    private void printQueen(){
        System.out.println("寻找了" + num + "次");
        for (int i = 0; i < MAX_NUM; i++){
            for (int j = 0; j < MAX_NUM; j++){
                System.out.print(chessBoard[j][i] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        EightQueen eightQueen = new EightQueen();
        /*if (eightQueen.settleQueen(0)) {
            eightQueen.printQueen();
        } else{
            System.out.println("没有找到");
            eightQueen.printQueen();
        }*/
        eightQueen.settleAllQueen(0);
    }
}
