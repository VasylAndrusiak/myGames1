package sample.checkers;


import javafx.scene.Group;

public class Data { //Клас призначений для постійного збереження інформації і можливості редагування її з любої точки програми
   /* private final static Data instance = new Data();

    */
    /* private constructor forces you to use the getInstance() method below */
    private Data() {}

 /*   public static Data getInstance() {
        return instance;
    }*/
 private static int GameChessRed;
 private static int GameChessWhite;

    public static void GameChessRed() {GameChessRed = 12; }
    public static void killGameChessRed() { GameChessRed--; }
    public static void GameChessWhite() {GameChessWhite = 12;}
    public static void killGameChessWhite() { GameChessWhite--;}
    public static int GameOverRed(){ return GameChessRed;}
    public static int GameOverWhite(){ return GameChessWhite;}
//методи що визначають кількість шашок і призводять до завершення гри

 private static int plya; //зміння для збереження кількості ходів гравців
    public static void addPlayer(int type) { //Метод для перезапису змінної
       plya = type;
    }
    public static void add1Player() { //Метод для збільшення змінної на одиницю
        plya++;
    }
    public static int getPlayer() { //Метод що повертає значення змінної plya
        return plya;
    }

    }

