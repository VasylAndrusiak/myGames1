package sample;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.control.*;
import sample.battleShip.Board;
import sample.battleShip.Ship;
import sample.checkers.*;


public class Controller {
    //----------------------------------------------------------------------------------
    //---------------------------------Шашки-------------------------------------------------
    public static final int TILE_SIZE = 80; //розмір
    public static final int WIDTH = 8; //ширина
    public static final int HEIGHT = 8;//висота

    private Tile[][] board = new Tile[WIDTH][HEIGHT]; //створення дошки

    private Group tileGroup = new Group(); //сортування
    private Group pieceGroup = new Group();


    private Parent createContent() {  //функція для створення контенту
        Pane root = new Pane(); //створення панелі

        Data.GameChessRed();//зберігаєм в Data 12 наших фігур
        Data.GameChessWhite();

        root.setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        root.getChildren().addAll(tileGroup, pieceGroup);

        for (int y = 0; y < HEIGHT; y++) { //розкладання кожної клітинки на дошці
            for (int x = 0; x < WIDTH; x++) {
                Tile tile = new Tile((x + y) % 2 == 0, x, y);
                board[x][y] = tile;

                tileGroup.getChildren().add(tile);//збереження всіх клітинок

                Piece piece = null; //поле для збереження наших фігурок

                if (y <= 2 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.RED, x, y); //створення червоних фігур
                }

                if (y >= 5 && (x + y) % 2 != 0) {
                    piece = makePiece(PieceType.WHITE, x, y); //створення білих фігур
                }

                if (piece != null) { //якщо фігура існує то збрегти її на дошці
                    tile.setPiece(piece);
                    pieceGroup.getChildren().add(piece);
                }
            }
        }

        return root; //повернення готової таблиці
    }
    private MoveResult tryMove(Piece piece, int newX, int newY) {
        //якщо ми натиснули на шашку і бажаємо її перемістити

        if (board[newX][newY].hasPiece() || (newX + newY) % 2 == 0) { //перевірка що клітинка на яку ми клікнули не містить фігуру , і чи вона ходить по діагоналі
            return new MoveResult(MoveType.NONE); //переміщення неможливе, повернути фішку в початкове положення
        }

        int x0 = toBoard(piece.getOldX()); //старі координати
        int y0 = toBoard(piece.getOldY()); //старі координати
/////////////////////////////////////// \
//  player_Chekers.setText("Ігрок: "+ plp);
//            //Step_Chekers.setText(""+player);
        /////////////////////////////
        if(Data.getPlayer()%2 == 0 && piece.getType() == PieceType.RED){
//ХОДЯТЬ ЧЕРВОНІ
            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
                Data.add1Player();

                SetPlayerText("Гравець: Червоні"); SetStepText();

                return new MoveResult(MoveType.NORMAL);//якщо виконується умова значить можливий хід
            } else if ((Math.abs(newY - y0) == 2 && Math.abs(newX - x0) == 2)) {

                int x1 = x0 + (newX - x0) / 2; //визначаються координати вбитої шашки
                int y1 = y0 + (newY - y0) / 2; //визначаються координати вбитої шашки

                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                    Data.add1Player();

                    SetPlayerText("Гравець: Червоні"); SetStepText();

                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece()); //якщо ми можем бити значить прибрати зелишену фішку
                }
            }

        }else if(Data.getPlayer()%2 == 1 && piece.getType() == PieceType.WHITE)
//ХОДЯТЬ БІЛІ
            {
            if (Math.abs(newX - x0) == 1 && newY - y0 == piece.getType().moveDir) {
                Data.add1Player();

                SetPlayerText("Гравець: Білі"); SetStepText();

                return new MoveResult(MoveType.NORMAL);//якщо виконується умова значить можливий хід
            } else if ((Math.abs(newY - y0) == 2 && Math.abs(newX - x0) == 2)) {

                int x1 = x0 + (newX - x0) / 2; //визначаються координати вбитої шашки
                int y1 = y0 + (newY - y0) / 2; //визначаються координати вбитої шашки

                if (board[x1][y1].hasPiece() && board[x1][y1].getPiece().getType() != piece.getType()) {
                    Data.add1Player();

                    SetPlayerText("Гравець: Білі"); SetStepText();

                    return new MoveResult(MoveType.KILL, board[x1][y1].getPiece()); //якщо ми можем бити значить прибрати зелишену фішку
                }
            }


        }
///////////////////////////////////////
        ///////////////////////////////
        return new MoveResult(MoveType.NONE);
    }


    private int toBoard(double pixel) {
        return (int)(pixel + TILE_SIZE / 2) / TILE_SIZE;
    }


    private Piece makePiece(PieceType type, int x, int y) {
        Piece piece = new Piece(type, x, y);

        piece.setOnMouseReleased(e -> { //функція наводить вказівник миші на шашку куди буде здійснюватися хід
            int newX = toBoard(piece.getLayoutX()); //наведення шашки на нові координати
            int newY = toBoard(piece.getLayoutY()); //наведення шашки на нові координати

            MoveResult result; //показує результат переміщення

            if (newX < 0 || newY < 0 || newX >= WIDTH || newY >= HEIGHT) { //перевірка чи не можливий хід
                result = new MoveResult(MoveType.NONE);            //якщо умова виконується, то хід не можливий
            } else {                                   //якщо не виконується умова
                result = tryMove(piece, newX, newY);   //перемістити шашку на нові координати
            }

            int x0 = toBoard(piece.getOldX()); //початкове положення шашки
            int y0 = toBoard(piece.getOldY()); //початкове положення шашки

            switch (result.getType()) {
                case NONE:                  //у цьому випадку не можна ходити
                    piece.abortMove();      //повернути шашку назад
                    break;
                case NORMAL:                //у цьому випадку можна ходити
                    piece.move(newX, newY); //рух на нові координати
                    board[x0][y0].setPiece(null); //звільнення поля на старих координатах, звідки забрали шашку
                    board[newX][newY].setPiece(piece);//встановлення нового поля куди гравець походив
                    break;
                case KILL:             // цьому випадку можна бити
                    //зменчуємо кількість шашок на 1
                    if(Data.getPlayer()%2 == 0 && piece.getType() == PieceType.RED){ Data.killGameChessRed(); }
                    else if(Data.getPlayer()%2 == 1 && piece.getType() == PieceType.WHITE){Data.killGameChessWhite(); }

                    //якщо шашок не залишилося завершити гру
                    if(Data.GameOverRed() == 0){ pieceGroup.getChildren().clear(); tileGroup.getChildren().clear();
                        Label label1 = new Label();
                        label1.setText("----!!!Виграли Білі!!!---");
                        label1.setFont(new Font("Strokes RUS-LAT", 30));
                        label1.getStylesheets().add("path/style/Dark_theme.css");
                        Spisok1.getChildren().add(label1);
                    }
                    else  if(Data.GameOverWhite() == 0){ pieceGroup.getChildren().clear(); tileGroup.getChildren().clear();
                        Label label1 = new Label();
                        label1.setText("----!!!Виграли Червоні!!!---");
                        label1.getStylesheets().add("path/style/Dark_theme.css");
                        //label1.setFont(new Font("Strokes RUS-LAT", 30));
                        Spisok1.getChildren().add(label1);
                    }
                    piece.move(newX, newY);     //переміщення на нові координати
                    board[x0][y0].setPiece(null);//очищення старих координат
                    board[newX][newY].setPiece(piece);//встановлення нових координат

                    Piece otherPiece = result.getPiece(); //отримання шашки, яку можна бити
                    board[toBoard(otherPiece.getOldX())][toBoard(otherPiece.getOldY())].setPiece(null); //очищення поля на якому була бита шашка
                    pieceGroup.getChildren().remove(otherPiece);  //вилучення шашки
                    break;
            }
        });

        return piece;
    }



    //----------------------------------------------------------------------------------
    //---------------------------------Морський бій-------------------------------------------------

    public int countShips = 10;  // кількість кораблів
    private boolean running = false;
    private Board enemyBoard, playerBoard;

    private int shipsToPlace = 4; // кількість парусів корабля
    private boolean enemyTurn = false;

    private Random random = new Random();

    private Parent createContent2() {
        BorderPane root = new BorderPane();
        root.setPrefSize(600, 800);   // розмір вікна

        //root.setRight(new Text("RIGHT SIDEBAR - CONTROLS"));

        enemyBoard = new Board(true, event -> {
            if (!running)
                return;

            Board.Cell cell = (Board.Cell) event.getSource();
            if (cell.wasShot)
                return;

            enemyTurn = !cell.shoot();

            if (enemyBoard.ships == 0) {  // перевірка чи вбиті всі кораблі суперника
                System.out.println("YOU WIN"); // якщо всі кораблі суперника вбиті вивести повідомлення про виграш
                System.exit(0);
            }

            if (enemyTurn)
                enemyMove();
        });

        playerBoard = new Board(false, event -> {
            if (running)
                return;

            Board.Cell cell = (Board.Cell) event.getSource();
            if (playerBoard.placeShip(new Ship(shipsToPlace, event.getButton() == MouseButton.PRIMARY), cell.x, cell.y)) {

                if(countShips == 10)
                {
                    --shipsToPlace;
                    countShips--;
                } else if(countShips == 9){
                    countShips--;
                }
                else if(countShips == 8){
                    --shipsToPlace;
                    countShips--;
                }
                else if(countShips == 7){
                    countShips--;
                }
                else if(countShips == 6){
                    countShips--;
                }
                else if(countShips == 5){
                    --shipsToPlace;
                    countShips--;
                }
                else if(countShips == 4){
                    countShips--;
                }else if(countShips == 3){
                    countShips--;
                }
                else if(countShips == 2){
                    countShips--;
                }
                else if(countShips == 1){
                    countShips--;
                    shipsToPlace--;
                }

                if (shipsToPlace == 0) {
                    startGame();
                }
            }
        });

        VBox vbox = new VBox(50, enemyBoard, playerBoard);
        vbox.setAlignment(Pos.TOP_CENTER);

        root.setCenter(vbox);

        return root;
    }

    private void enemyMove() {
        while (enemyTurn) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            Board.Cell cell = playerBoard.getCell(x, y);
            if (cell.wasShot)
                continue;

            enemyTurn = cell.shoot();

            if (playerBoard.ships == 0) { // перевірка чи вбито всі ваші кораблі
                System.out.println("YOU LOSE"); // якщо всі вбиті - виводиться повідомлення про програш
                System.exit(0);
            }
        }
    }

    private void startGame() {
        // місця розташування ворожих кораблів
        int type = 4;
        int countEnemyShips = 10;

        while (type > 0) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);

            if (enemyBoard.placeShip(new Ship(type, Math.random() < 0.4), x, y)) {
                switch(countEnemyShips){
                    case 10 :
                        countEnemyShips--;
                        type--;
                        break;
                    case 9 :
                        countEnemyShips--;
                        break;
                    case 8 :
                        countEnemyShips--;
                        type--;
                        break;
                    case 7 :
                        countEnemyShips--;
                        break;
                    case 6 :
                        countEnemyShips--;
                        break;
                    case 5 :
                        countEnemyShips--;
                        type--;
                        break;
                    case 4 :
                        countEnemyShips--;
                        break;
                    case 3 :
                        countEnemyShips--;
                        break;
                    case 2 :
                        countEnemyShips--;
                        break;
                    case 1 :
                        countEnemyShips--;
                        type--;
                        break;
                    //default :
                    //Операторы
                }

                //type--;
            }
        }

        running = true;
    }

//-------------------------------------------------------------------------------------------
    //----------------------------------Сапер-------------------------------------------------------------
private static final int TILE_SIZE33 = 40;
    private static final int W = 800;
    private static final int H = 600;

    private static final int X_TILES = W / TILE_SIZE33;
    private static final int Y_TILES = H / TILE_SIZE33;

    private Tile33[][] grid = new Tile33[X_TILES][Y_TILES];
    private Scene scene;

    private Parent createContent3() {
        Pane root = new Pane();
        root.setPrefSize(W, H);

        for (int y33 = 0; y33 < Y_TILES; y33++) {
            for (int x33 = 0; x33 < X_TILES; x33++) {
                Tile33 tile = new Tile33(x33, y33, Math.random() < 0.2);

                grid[x33][y33] = tile;
                root.getChildren().add(tile);
            }
        }

        for (int y33 = 0; y33 < Y_TILES; y33++) {
            for (int x33 = 0; x33 < X_TILES; x33++) {
                Tile33 tile = grid[x33][y33];

                if (tile.hasBomb)
                    continue;

                long bombs = getNeighbors(tile).stream().filter(t -> t.hasBomb).count();

                if (bombs > 0)
                    tile.text.setText(String.valueOf(bombs));
            }
        }

        return root;
    }

    private List<Tile33> getNeighbors(Tile33 tile) {
        List<Tile33> neighbors = new ArrayList<>();

        // ttt
        // tXt
        // ttt

        int[] points = new int[]{
                -1, -1,
                -1, 0,
                -1, 1,
                0, -1,
                0, 1,
                1, -1,
                1, 0,
                1, 1
        };

        for (int i = 0; i < points.length; i++) {
            int dx = points[i];
            int dy = points[++i];

            int newX = tile.x33 + dx;
            int newY = tile.y33 + dy;

            if (newX >= 0 && newX < X_TILES
                    && newY >= 0 && newY < Y_TILES) {
                neighbors.add(grid[newX][newY]);
            }
        }

        return neighbors;
    }

    private class Tile33 extends StackPane {
        private int x33, y33;
        private boolean hasBomb;
        private boolean isOpen = false;

        private Rectangle border = new Rectangle(TILE_SIZE33 - 2, TILE_SIZE33 - 2);
        private Text text = new Text();

        public Tile33(int x33, int y33, boolean hasBomb) {
            this.x33 = x33;
            this.y33 = y33;
            this.hasBomb = hasBomb;

            border.setStroke(Color.LIGHTGRAY);

            text.setFont(Font.font(18));
            text.setText(hasBomb ? "x33" : "");
            text.setVisible(false);

            getChildren().addAll(border, text);

            setTranslateX(x33 * TILE_SIZE33);
            setTranslateY(y33 * TILE_SIZE33);

            setOnMouseClicked(e -> open());
        }

        public void open() {
            if (isOpen)
                return;

            if (hasBomb) {
                System.out.println("Game Over");
                VBox content = new VBox();
                content.getChildren().add(createContent3());
                APaneSaper.getChildren().clear();
                APaneSaper.getChildren().add(content);
                return;
            }

            isOpen = true;
            text.setVisible(true);
            border.setFill(null);

            if (text.getText().isEmpty()) {
                getNeighbors(this).forEach(Tile33::open);
            }
        }
    }

    @FXML
    JFXButton bbt;
    @FXML
    JFXButton player_Chekers;
    @FXML
    JFXButton Step_Chekers;
    @FXML
    JFXButton Time_Chekers;

    @FXML
    Pane Spisok1;
    @FXML
    TitledPane Spisok2;
    @FXML
    TitledPane Spisok3;
    @FXML
    Pane ShipPane;
    @FXML
    AnchorPane APaneSaper;


    //Присвоює текст в player_Chekers
    @FXML private void SetPlayerText(String text) {
        this.player_Chekers.setText(text);
    }

   //Присвоює кількість ходів в Step_Chekers
    @FXML private void SetStepText() {
        this.Step_Chekers.setText("Хід: "+String.valueOf(Data.getPlayer()));
    }


    @FXML public void onNumberClick(ActionEvent event) {
        Data.addPlayer(1);
        if(event.getSource() instanceof Button) {
            SetPlayerText("Гравець: Білі"); SetStepText();
            // Content for TitledPane
/*
          Main.window.setScene(scene2);*/
         /*   createContent().getParent().getChildrenUnmodifiable().removeAll();
            Spisok1.getChildren().removeAll();*/
            pieceGroup.getChildren().clear();
            tileGroup.getChildren().clear();
           VBox content = new VBox();
            Spisok1.getChildren().add(createContent());
            //Spisok1.setContent(content);



        }
    }
    @FXML public void onShipClick(ActionEvent event) {




        if(event.getSource() instanceof Button) {
            SetPlayerText("Гравець: Білі"); SetStepText();

            ShipPane.getChildren().clear();
            ShipPane.getChildren().add(createContent2());




        }
    }
    @FXML public void onSaperClick(ActionEvent event) {
        if (event.getSource() instanceof Button) {
            VBox content = new VBox();
            content.getChildren().add(createContent3());

            APaneSaper.getChildren().add(content);
        }
    }


    @FXML public void onColorClick(ActionEvent e) {

            JFXColorPicker Cop = (JFXColorPicker ) e.getSource(); // получаю ссылку на панель которую нужно удалить
            AnchorPane parent = (AnchorPane) Cop.getParent();

            Color SelectedColor = Cop.getValue();
        parent.setBackground(new Background(new BackgroundFill(Paint.valueOf(SelectedColor.toString()),CornerRadii.EMPTY, Insets.EMPTY)));

    }
}
