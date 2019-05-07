package sample.battleShip;

//Морський бій
public class PlaceToShips {
    static public int ships;
    static public int count;  // кількість кораблів
    static void Place(int a, int count){
        ships = a;
        if(count == 10)
        {
            --ships;
            count--;
        } else if(count == 9){
            count--;
        }
        else if(count == 8){
            --ships;
            count--;
        }
        else if(count == 7){

            count--;
        }
        else if(count == 6){
            count--;
        }
        else if(count == 5){
            --ships;
            count--;
        }
        else if(count == 4){

            count--;
        }else if(count == 3){

            count--;
        }
        else if(count == 2){

            count--;
        }
        else if(count == 1){
            --ships;
            count--;
        }
    }

}
