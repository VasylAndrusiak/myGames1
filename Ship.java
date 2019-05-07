package sample.battleShip;

import javafx.scene.Parent;

//Морський бій
public class Ship extends Parent {
    public int type;  // тип корабля
    public boolean vertical = true;

    private int health;  //змінна зберігає значення невбитих клітинок корабля

    public Ship(int type, boolean vertical) {
        this.type = type;
        this.vertical = vertical;
        health = type;

    }

    public void hit() {  //якщо влучили в корабель здоров'я зменшуєтьсяна 1
        health--;
    }

    public boolean isAlive() {    //функція перевірки чи вбитий корабель
        return health > 0;
    }
}
