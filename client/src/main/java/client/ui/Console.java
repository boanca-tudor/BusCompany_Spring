package client.ui;

import client.controllers.BusController;
import client.controllers.CityController;
import client.controllers.StationController;
import client.controllers.StopController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.util.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class Console {
    private static final Logger logger = LoggerFactory.getLogger(Console.class);

    private final BusController busController;
    private final CityController cityController;
    private final StationController stationController;
    private final StopController stopController;
    private final Map<Integer, Runnable> commandDict = new HashMap<>();

    public Console(BusController busController, CityController cityController, StationController stationController, StopController stopController) {
        this.busController = busController;
        this.cityController = cityController;
        this.stationController = stationController;
        this.stopController = stopController;

        setUpMenu();
    }

    private void setUpMenu(){
        commandDict.put(1, this::printAllBuses);
        commandDict.put(2, this::printAllStations);
        commandDict.put(3, this::printAllStops);
        commandDict.put(4, this::printAllCities);
        commandDict.put(5, this::addBus);
        commandDict.put(6, this::addStation);
        commandDict.put(7, this::addStop);
        commandDict.put(8, this::addCity);
        commandDict.put(9, this::updateBus);
        commandDict.put(10, this::updateStation);
        commandDict.put(11, this::updateStop);
        commandDict.put(12, this::updateCity);
        commandDict.put(13, this::deleteBus);
        commandDict.put(14, this::deleteStation);
        commandDict.put(15, this::deleteStop);
        commandDict.put(16, this::deleteCity);
        commandDict.put(17, this::printMenu);
    }

    private void printMenu() {
        System.out.println("1. Print all buses");
        System.out.println("2. Print all bus stations");
        System.out.println("3. Print all bus stops");
        System.out.println("4. Print all cities");
        System.out.println("5. Add a bus");
        System.out.println("6. Add a bus station");
        System.out.println("7. Add a bus stop");
        System.out.println("8. Add a city");
        System.out.println("9. Update a bus");
        System.out.println("10. Update a bus station");
        System.out.println("11. Update a bus stop");
        System.out.println("12. Update a city");
        System.out.println("13. Delete a bus");
        System.out.println("14. Delete a bus station");
        System.out.println("15. Delete a bus stop");
        System.out.println("16. Delete a city");
        System.out.println("17. Print Menu");
    }

    private void printAllBuses() {
        logger.trace("show buses - method entered");
        this.busController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.getMessage());
        });
        logger.trace("show buses - method finished");
    }

    private void printAllCities() {
        logger.trace("show cities - method entered");
        this.cityController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.getMessage());
        });
        logger.trace("show cities - method finished");
    }

    private void printAllStations() {
        logger.trace("show stations - method entered");
        this.stationController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.getMessage());
        });
        logger.trace("show stations - method finished");
    }

    private void printAllStops() {
        logger.trace("show stops - method entered");
        this.stopController.findAll().whenComplete((result, exception) -> {
            if(exception == null)
                StreamSupport.stream(result.spliterator(), false).forEach(System.out::println);
            else
                System.out.println(exception.getMessage());
        });
        logger.trace("show stops - method finished");
    }

    private void addBus() {
        logger.trace("add bus - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Model: ");
        String model = scanner.nextLine();

        busController.save(model).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("add bus - method finished");
    }

    private void addCity() {
        logger.trace("add city - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Name: ");
        String name = scanner.nextLine();

        System.out.println("Population: ");
        Integer population = scanner.nextInt();

        cityController.save(name, population).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("add city - method finished");
    }

    private void addStation() {
        logger.trace("add station - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("City ID: ");
        Long cityId = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Name: ");
        String name = scanner.nextLine();

        stationController.save(name, cityId).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("add station - method finished");
    }

    private void addStop() {
        logger.trace("add stop - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long busId = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Station ID: ");
        Long stationId = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Time: ");
        String timeString = scanner.nextLine();

        stopController.save(busId, stationId, timeString).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("add stop - method finished");
    }

    private void updateBus() {
        logger.trace("update bus - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long id = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Model: ");
        String model = scanner.nextLine();

        busController.update(id, model).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("update bus - method finished");
    }

    private void updateCity() {
        logger.trace("update city - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("City ID: ");
        Long id = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Name: ");
        String name = scanner.nextLine();

        System.out.println("Population: ");
        Integer population = scanner.nextInt();

        cityController.update(id, name, population).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("update city - method finished");
    }

    private void updateStation() {
        logger.trace("update station - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Station ID: ");
        Long id = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Name: ");
        String name = scanner.nextLine();

        stationController.update(id, name).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("update station - method finished");
    }

    private void updateStop() {
        logger.trace("update stop - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Stop ID: ");
        Long id = scanner.nextLong();

        scanner.nextLine();
        System.out.println("Time: ");
        String timeString = scanner.nextLine();

        stopController.update(id, timeString).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("update stop - method finished");
    }

    private void deleteBus() {
        logger.trace("delete bus - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bus ID: ");
        Long id = scanner.nextLong();

        busController.delete(id).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("delete bus - method finished");
    }

    private void deleteCity() {
        logger.trace("delete city - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("City ID: ");
        Long id = scanner.nextLong();

        cityController.delete(id).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("delete city - method finished");
    }

    private void deleteStation() {
        logger.trace("delete station - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Station ID: ");
        Long id = scanner.nextLong();

        stationController.delete(id).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("delete station - method finished");
    }

    private void deleteStop() {
        logger.trace("delete stop - method entered");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Stop ID: ");
        Long id = scanner.nextLong();

        stopController.delete(id).whenComplete((status, exception) -> {
            if(exception == null){
                System.out.println(status);
            }
            else {
                System.out.println(exception.getMessage());
            }
        });

        logger.trace("delete stop - method finished");
    }

    private int readInstructionNumber() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }

    public void start() {
        logger.info("Started UI");

        printMenu();
        IntStream.generate(() -> 0)
                .forEach(i -> {
                    try {
                        int choice = readInstructionNumber();
                        Stream.of(commandDict.get(choice))
                                .filter(Objects::nonNull)
                                .findAny()
                                .ifPresentOrElse(Runnable::run, () -> System.out.println("Bad choice"));
                    } catch (InputMismatchException inputMismatchException){
                        System.out.println("Invalid integer");
                    } catch (Exception exception){
                        System.out.println(exception.getMessage());
                    }
                });
    }
}
