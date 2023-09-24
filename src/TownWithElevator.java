import java.util.*;

import static java.lang.Thread.sleep;


public class TownWithElevator {
    public static int n;
    public static int k;

    public static int startFloor; // начальный этаж
    public static int threadSleeping; // сон между методами
    static
    {
        startFloor = 1;
        threadSleeping = 5000;
        n = (int) (5 + Math.random() * 16);
        k = (int) (1 + Math.random() * 10);
    }

    public static void main(String[] args) throws InterruptedException {
        Elevator elevator = new Elevator(startFloor,5,true,Direction.UP);
        List<Floor> floorList = new ArrayList<>(n); // создаем список этажей
        System.out.println("Количество этажей в здании " + n);
        System.out.println("Количество людей на каждом этаже " + k);
        for (int i = 1; i <= n; i++) {
            List<Passenger> passengers = new ArrayList<>(k); // создаем список пассажиров
            for (int j = 0; j < k; j++) {
                passengers.add(new Passenger(i)); // инициализируем список пассажиров
            }
            floorList.add(new Floor(i,passengers)); // инициализируем список этажей
        }
        int elevatorRunning = 100000; // время работы программы
            sleep(threadSleeping);
            System.out.println("Лифт запущен");
            elevator.run(floorList); // запускаем лифт
            sleep(elevatorRunning);
            elevator.stop();// тормозим лифт
            System.out.println("Лифт прекратил работу!");

    }
}