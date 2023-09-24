import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;


import static java.lang.Thread.*;

public class Elevator { // Класс для лифта
    private final int elevatorSize; // вместимость
    private int currentFloorNumber; // номер текущего этажа
    private int destinationFloorNumber; // номер этажа назначения
    private Direction direction; // направление
    private final List<Passenger> passengerList; // список пассажиров в лифте
    private boolean isRun; // для остановки


    Elevator(int currentFloor,int elevatorSize,boolean isRun,Direction direction)
    {
        this.currentFloorNumber = currentFloor;
        this.elevatorSize = elevatorSize;
        this.isRun = isRun;
        this.passengerList = new ArrayList<>();
        this.direction = direction;
    }



    public void run(List<Floor> floorList) throws InterruptedException { // запускаем лифт
        while(isRun) {
            Floor currentFloor = floorList.get(currentFloorNumber-1); // берем из списка этажей текущий
            if (!passengerList.isEmpty()) { // если лифт не пустой
                Predicate<Passenger> passengerPredicate = passenger -> (passenger.getFinish_floor() == currentFloorNumber);
                List<Passenger> passengerGetOut = passengerList.stream().filter(passengerPredicate).toList(); // получаем из списка тех пассажиров которые выходят на текущем этаже
                boolean elevatorCrowded = passengerList.size() == elevatorSize; // true если лифт заполнен
                boolean getOut = !passengerGetOut.isEmpty(); // true если кому-либо из пассажиров необходимо выйти на текущем этаже
                if (elevatorCrowded && !getOut) { // если лифт заполнен и никому не надо выходить на текущем этаже
                    System.out.println("Лифт заполнен, на этаже " + currentFloorNumber + " никто не выходит");
                    sleep(TownWithElevator.threadSleeping);
                    step(); // едем дальше
                    sleep(TownWithElevator.threadSleeping);
                } else if(getOut) { // если кому-либо необходимо выйти
                    System.out.println("Количество человек в лифте " + passengerList.size());
                    sleep(TownWithElevator.threadSleeping);
                    getOut(passengerGetOut,currentFloor); // выпускаем
                    sleep(TownWithElevator.threadSleeping);
                    System.out.println("Количество человек в лифте " + passengerList.size());
                    System.out.println("Конечная остановка " + destinationFloorNumber);
                    check_floor(currentFloor); // проверяем этаж
                    sleep(TownWithElevator.threadSleeping);
                    System.out.println("Количество человек в лифте " + passengerList.size());
                    step(); // едем дальше
                }
                else // если никому не надо выходить на текущем этаже, а лифт не заполнен
                {
                    System.out.println("На этаже " + currentFloorNumber + " никто не выходит");
                    System.out.println("Количество человек в лифте " + passengerList.size());
                    System.out.println("Конечная остановка " + destinationFloorNumber);
                    sleep(TownWithElevator.threadSleeping);
                    check_floor(currentFloor); // проверяем этаж
                    sleep(TownWithElevator.threadSleeping);
                    step(); // едем дальше
                }
            }
            else { // если лифт пустой
                System.out.println("Количество человек в лифте " + 0);
                System.out.println("Лифт на этаже " + currentFloorNumber);
                sleep(TownWithElevator.threadSleeping);
                check_floor(currentFloor); // проверяем этаж
                System.out.println("Количество человек в лифте " + passengerList.size());
                sleep(TownWithElevator.threadSleeping);
                step(); // едем дальше

            }


        }
    }

    public void getOut(List<Passenger> passengerGetOut,Floor currentFloor) // метод удаляет пассажиров из лифта
    {
        System.out.println("На этаже " + currentFloorNumber + " выходят " + passengerGetOut.size() + " человек");
        passengerGetOut.forEach(p -> {
            removePassenger(p); // удаляем пассажира из списка лифта
            System.out.print("Пассажир ");
            p.printf();
            System.out.print(" вышел из лифта на этаже " + currentFloor.getNumber() + "\n");
            p.goOut(); // вызываем метол у пассажира
            currentFloor.addPassenger(p); // добавляем пассажира в список текущего этажа

        });
    }

    public void step() throws InterruptedException { // метод изменяет currentFloorNumber
        if(direction.getStr().equals("UP")) {
            currentFloorNumber++;
            System.out.println("Едем вверх этаж " + currentFloorNumber);
        }
        else {
            currentFloorNumber--;
            System.out.println("Едем вниз этаж " + currentFloorNumber);
        }
        sleep(TownWithElevator.threadSleeping);
    }

    public void check_floor(Floor floor) throws InterruptedException { // проверяем этаж
        boolean up = floor.getUp() > floor.getDown();
        Predicate<Passenger> passengerPredicateUp = passenger -> passenger.getDirection().getStr().equals("UP"); // предикат для пассажиров которые хотят вверх
        Predicate<Passenger> passengerPredicateDown = passenger -> passenger.getDirection().getStr().equals("DOWN"); // предикат для пассажиров которые хотят вниз
        floor.print();
        if(floor.getNumber() == destinationFloorNumber)
        {
            System.out.println("Конечная остановка. Решаем куда ехать?");

            if(up) { // куда едем?
                System.out.println("Вверх!");
                if(direction.getStr().equals("DOWN"))
                    changeDirection(); // меняем направление лифта
                comeIn(floor,passengerPredicateUp); // сажаем пассажиров
            }
            else
            {
                System.out.println("Вниз!");
                if(direction.getStr().equals("UP"))
                    changeDirection();// меняем направление лифта
                comeIn(floor,passengerPredicateDown); // сажаем пассажиров
            }

        }
        else if(direction.getStr().equals("UP"))
        {
            comeIn(floor,passengerPredicateUp); // сажаем пассажиров
        }
        else
        {
            comeIn(floor,passengerPredicateDown); // сажаем пассажиров
        }
        sleep(TownWithElevator.threadSleeping);
    }

    public int calculateDestinationFloor() throws InterruptedException { // метод рассчитывает конечный этаж
        sleep(TownWithElevator.threadSleeping);
        if(direction.getStr().equals("UP"))
            return passengerList.stream().map(Passenger::getFinish_floor).max(Comparator.comparingInt(Integer::intValue)).orElse(destinationFloorNumber);
        else
            return passengerList.stream().map(Passenger::getFinish_floor).min(Comparator.comparingInt(Integer::intValue)).orElse(destinationFloorNumber);
    }

    public void comeIn(Floor floor,Predicate<Passenger> passengerPredicate) throws InterruptedException { // метод добавляет пассажиров в лифт

        List<Passenger> comeInPassengers = floor.getPassengerList().stream().filter(passengerPredicate).toList(); // возвращаем лист с пассажирами которые могут войти в лифт

        for (Passenger p : comeInPassengers) { // пробегаемся по списку
            if (passengerList.size() < elevatorSize) // если есть место в лифте
            {
                passengerList.add(p); // добавляем пассажира в лифт
                System.out.print("Пассажир ");
                p.printf();
                System.out.print(" зашел в лифт на этаже " + floor.getNumber() + "\n");
                floor.removePassenger(p); // удаляем из списка этажа
            }
        }
        if(!comeInPassengers.isEmpty()) // если есть кого добавить, то пересчитываем конечный этаж
        {
            System.out.println("Пересчитываем конечную остановку");
            destinationFloorNumber = calculateDestinationFloor(); // после добавления необходимо пересчитать конечный этаж
        }

        System.out.println("Конечная остановка " + destinationFloorNumber);
        sleep(TownWithElevator.threadSleeping);
    }

    public void changeDirection() throws InterruptedException { // метод изменяет направление
        if (direction.getStr().equals("UP"))
        {
            direction = Direction.DOWN;
            System.out.println("Лифт поменял направление, теперь едет вниз");
        }
        else
        {
            direction = Direction.UP;
            System.out.println("Лифт поменял направление, теперь едет вверх");
        }
        sleep(TownWithElevator.threadSleeping);
    }
    public void removePassenger(Passenger p) { // удаляет пассажира из списка
        passengerList.remove(p);
    }

    public void stop()
    {
        isRun = false;
    }

}
