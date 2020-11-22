package code;

import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private CountDownLatch start, finish;
    private static boolean win = false;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    private static synchronized void winner (Car c) {
        if (!win) {
            System.out.println(c.name + " WIN!");
            win = true;
        }
    }
    public Car(Race race, int speed, CountDownLatch start, CountDownLatch finish) {
        this.start = start;
        this.finish = finish;
        this.race = race;
        this.speed = speed;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
    }
    @Override
    public void run() {
        try {
            System.out.println(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            System.out.println(this.name + " готов");
            start.countDown();
            start.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        winner(this);
        finish.countDown();
    }
}
