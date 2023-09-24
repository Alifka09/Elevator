import java.util.List;


public class Floor { // Класс для этажа
    private final int number; // номер
    private final List<Passenger> passengerList; // список пассажиров
    private int up; // количество тех кто хочет наверх
    private int down; // количество тех кто хочет наверх

    Floor(int number,List<Passenger> passengerList)
    {
        this.number = number;
        up = 0;
        down = 0;
        this.passengerList = passengerList;
        this.passengerList.forEach(p -> calculateUpDownFloor(p, true));

    }

    public void addPassenger(Passenger p) // добавить пассажира
    {
        passengerList.add(p);
        calculateUpDownFloor(p,true);

    }

    public void removePassenger(Passenger p) // удалить пассажира
    {
        passengerList.remove(p);
        calculateUpDownFloor(p,false);
    }


    public int getNumber() {
        return number;
    }


    private void calculateUpDownFloor(Passenger p,boolean add) // пересчитать переменные up down
    {
        if(add){
            if(p.getDirection().getStr().equals("UP"))
                up++;
            else
                down++;
        }
        else{
            if(p.getDirection().getStr().equals("DOWN"))
                up--;
            else
                down--;
        }

    }


    public int getUp() {
        return up;
    }

    public int getDown() {
        return down;
    }

    public void print()
    {
        System.out.println("На этаже " + getNumber() + " хотят вверх " + getUp() + " вниз " + getDown());
    }

    public List<Passenger> getPassengerList()
    {
        return passengerList;
    }


}
