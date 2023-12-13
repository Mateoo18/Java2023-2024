package org.smartcity;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;
public class CitySimulation {
    public static void main(String[] args) {
        SmartCity city = new SmartCity();
        for (int day = 1; day <= 10; day++) {
            System.out.println("Day " + day + " in Smart City");
            if (shouldAddNewBuilding()) {
                Building newBuilding = createRandomBuilding(day);
                city.addBuilding(newBuilding);
                System.out.println("New building added: " + newBuilding.getAddress());
            }
            System.out.println();//dodalem to dla bardziej przejrzystego wyswietlania wynikow
            buildingsInThreads(city.getBuildings());
            System.out.println();//dodalem to dla bardziej przejrzystego wyswietlania wynikow
        }
    }

    private static Building createRandomBuilding(int day) {
        Random random = new Random();
        int buildingType = random.nextInt(3);
        String address = "Building " + day + "-" + (random.nextInt(100) + 1);

        switch (buildingType) {
            case 0:
                return new Apartment(address, random.nextInt(10) + 5, random.nextInt(100) + 1);
            case 1:
                return new Office(address, random.nextInt(20) + 10, random.nextInt(300) + 50);
            case 2:
                return new Shop(address, random.nextInt(5) + 1, "Type " + (random.nextInt(3) + 1));
            default:
                return new Apartment(address, random.nextInt(10) + 5, random.nextInt(100) + 1);
        }
    }
    private static boolean shouldAddNewBuilding() {
        Random random = new Random();
        return random.nextDouble() < 0.5;
    }
    private static void buildingsInThreads(List<Building> buildings) {
        List<Thread> threads = new ArrayList<>();

        for (Building building : buildings) {
            Thread thread = new Thread(() -> {

                building.operate();
            });
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
