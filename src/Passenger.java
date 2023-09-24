import java.util.Objects;

public class Passenger { // Класс для пассажира
    private final int id; // id
    private int start_floor; // начальный этаж
    private int finish_floor; // конечный этаж
    private Direction direction; // направление

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return id == passenger.id && start_floor == passenger.start_floor && finish_floor == passenger.finish_floor;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    Passenger(int start_floor)
    {
        this.id = (int) ((Math.random() * 13112 + 78) % 567); // пришлось генерить id, чтобы различать пассажиров
        this.start_floor = start_floor;
        finish_floor = generateFloor();
        while(this.start_floor==finish_floor)
            finish_floor = generateFloor();
        calculateDirection();
    }


    public void goOut() // метод вызывается у пассажира выходящего из лифта
    {
        start_floor = finish_floor; // его конечный этаж становится начальным
        finish_floor = generateFloor();
        while (start_floor==finish_floor)
            finish_floor = generateFloor(); // конечный этаж генерируется пока не будет отличен от начального
        calculateDirection();
    }

    private void calculateDirection() // метод пересчитывает направление пассажира
    {
        if(start_floor < finish_floor)
            direction = Direction.UP;
        else
            direction = Direction.DOWN;
    }


    private int generateFloor() // метод генерирует номер этажа
    {
        return 1 + (int) ((Math.random() * 5456) % TownWithElevator.n);
    }
    public Direction getDirection() {
        return direction;
    }

    public int getFinish_floor() {
        return finish_floor;
    }

    public void printf()
    {
        System.out.printf("{id=%d начальный этаж=%d конечный этаж=%d направление=%s}",id,start_floor,finish_floor,direction.getStr().equals("UP") ? "вверх": "вниз");
    }



}
