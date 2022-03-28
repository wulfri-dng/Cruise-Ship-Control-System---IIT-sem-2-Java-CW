package com.wulfri;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Locale;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        int cabinCount = 12;
        String[] ship = new String[cabinCount];

        initialise(ship);
        mainMenu(ship);
    }

    public static void initialise(String[] ship) {
        int cabinNo = 0;
        while(cabinNo < ship.length) {
            ship[cabinNo] = "e";
            cabinNo++;
        }
    }

    public static void mainMenu(String[] ship) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=======================================================");
        System.out.println("||| Welcome to Cruise Ship passenger control center |||");
        while (true) {
            System.out.println("=======================================================");
            System.out.println("A: Add a customer to a cabin");
            System.out.println("V: View all cabins");
            System.out.println("E: Display Empty cabins");
            System.out.println("D: Delete customer from cabin");
            System.out.println("F: Find cabin from customer name");
            System.out.println("S: Store program data into file");
            System.out.println("L: Load program data from file");
            System.out.println("O: View passengers Ordered alphabetically by name");
            System.out.println("-----------------------------------------------------");
            System.out.print("Select an option above to continue: ");
            String userInput = scanner.next().toUpperCase(Locale.ROOT);

            switch (userInput) {
                case "A" -> addPassenger(ship);
                case "V" -> viewAllCabins(ship);
                case "E" -> displayEmptyCabins(ship);
                case "D" -> deletePassenger(ship);
                case "F" -> findCabinByPassengerName(ship);
                case "S" -> storeShipData(ship);
                case "L" -> loadShipData(ship);
                case "O" -> sortPassengerAlphabetically(ship);
                default -> System.out.println("Invalid input!!! Check the entered number and try again.");
            }
        }
    }

    public static void addPassenger(String[] ship) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter cabin number (0 - " + (ship.length - 1) + ") or " + (ship.length) + " to stop: ");
            try {
                int cabinNo = scanner.nextInt();
                if(cabinNo == ship.length) {
                    break;
                } else if(cabinNo >= 0 && cabinNo < ship.length && ship[cabinNo].equals("e")) {
                    System.out.println("Enter the name of the passenger for cabin " + cabinNo + ": ");
                    String passengerName = scanner.next();
                    try {
                        Integer.parseInt(passengerName);
                        System.out.println("Error!!! You cannot enter a number as a passenger name.");
                    } catch (NumberFormatException ex) {
                        ship[cabinNo] = passengerName;
                    }
                } else if(!ship[cabinNo].equals("e")) {
                    System.out.println("Cabin " + cabinNo + " is already booked. Try another cabin.");
                }
                else {
                    System.out.println("Invalid input!!! Enter a number between 0 - " + (ship.length - 1));
                }
            } catch (InputMismatchException ex) {
                System.out.println("Invalid input!!! Enter a number between 0 - " + (ship.length - 1));
                System.out.println("Exiting add passenger...");
                break;
            }
        }
    }

    public static void viewAllCabins(String[] ship) {
        System.out.println("=====================================================");
        for (int i = 0; i < ship.length; i++) {
            if(ship[i].equals("e")) {
                System.out.println("Cabin " + i + " is empty.");
            } else {
                System.out.println("Cabin " + i + " passenger: " + ship[i]);
            }
        }
        System.out.println("----------------- END OF VIEW CABIN -----------------");
        System.out.println();
    }

    public static void displayEmptyCabins(String[] ship) {
        System.out.println("=====================================================");
        System.out.print("Empty cabin numbers: ");
        for(int i = 0; i < ship.length; i++) {
            if(ship[i].equals("e")) {
                System.out.print(i + " ");
            }
        }
        System.out.println();
        System.out.println("---------------- END OF EMPTY CABINS -----------------");
        System.out.println();
    }

    public static void deletePassenger(String[] ship) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=====================================================");
        System.out.println("Enter cabin number or Passenger's name to remove the passenger");
        System.out.print("Enter: ");
        String userInput = scanner.next();
        try {
            int userInputInt = Integer.parseInt(userInput);
            if(userInputInt >= 0 && userInputInt < ship.length) {
                String deletedPassenger = ship[userInputInt];
                ship[userInputInt] = "e";
                System.out.println("Passenger " + deletedPassenger + " removed successfully!");
            } else {
                System.out.println("Invalid input!!! The number you entered is not belongs to a cabin.");
                System.out.println("Exiting delete...");
            }
        } catch (NumberFormatException ex) {
            int cabinNumb = findCabinByPassengerName(ship, userInput);
            if(findCabinByPassengerName(ship, userInput) != -99) {
                String deletedPassenger = ship[cabinNumb];
                ship[cabinNumb] = "e";
                System.out.println("Passenger " + deletedPassenger + " removed successfully!");
            } else {
                System.out.println("Invalid input!!! Entered passenger name is invalid.");
                System.out.println("Exiting delete...");
            }
        }
        System.out.println("-------------- END OF DELETE PASSENGER --------------");
        System.out.println();
    }

    public static int findCabinByPassengerName(String[] ship, String passengerName) {
        for(int i = 0; i < ship.length; i++) {
            if(ship[i].toLowerCase(Locale.ROOT).equals(passengerName.toLowerCase(Locale.ROOT))) {
                return i;
            }
        }
        return -99;
    }

    public static void findCabinByPassengerName(String[] ship) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=====================================================");
        System.out.println("Enter the passenger's name");
        System.out.print("Enter: ");
        String userInput = scanner.next();
        for(int i = 0; i < ship.length; i++) {
            if(ship[i].toLowerCase(Locale.ROOT).equals(userInput.toLowerCase(Locale.ROOT))) {
                System.out.println(ship[i] + "'s cabin number: " + i);
                break;
            } else if(i == (ship.length - 1)) {
                System.out.println("Invalid passenger name!!!");
                System.out.println("Exiting passenger search...");
            }
        }
        System.out.println("-------------- END OF SEARCH PASSENGER --------------");
    }

    public static void sortPassengerAlphabetically(String[] ship) {
        String[] passengerList = new String[ship.length];
        int listPosition = 0;
        for(String passenger : ship) {
            if(!passenger.equals("e")) {
                passengerList[listPosition] = passenger;
                listPosition++;
            }
        }

        for (int i = 0; i < passengerList.length; i++) {
            if(passengerList[i] != null) {
                if(i > 1) {
                    int currentIndex = i;
                    while(currentIndex > 0) {
                        char previousPassenger = Character.toLowerCase(passengerList[currentIndex-1].charAt(0));
                        char currentPassenger = Character.toLowerCase(passengerList[currentIndex].charAt(0));
                        if(previousPassenger > currentPassenger) {
                            String temp = passengerList[currentIndex-1];
                            passengerList[currentIndex-1] = passengerList[currentIndex];
                            passengerList[currentIndex] = temp;
                            currentIndex--;
                        } else if(previousPassenger == currentPassenger) {
                            int sortIndex = 1;
                            while (true) {
                                char innerSortPrevPassenger = Character.toLowerCase(passengerList[currentIndex-1].charAt(sortIndex));
                                char innerSortCurePassenger = Character.toLowerCase(passengerList[currentIndex].charAt(sortIndex));
                                try {
                                    if(innerSortPrevPassenger > innerSortCurePassenger) {
                                        String temp = passengerList[currentIndex-1];
                                        passengerList[currentIndex-1] = passengerList[currentIndex];
                                        passengerList[currentIndex] = temp;
                                        currentIndex--;
                                        break;
                                    } else if(innerSortPrevPassenger == innerSortCurePassenger) {
                                        sortIndex++;
                                    } else {
                                        currentIndex--;
                                        break;
                                    }
                                } catch (StringIndexOutOfBoundsException ex) {
                                    currentIndex--;
                                    break;
                                }
                            }
                        } else {
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("=====================================================");
        for (int i = 0; i < passengerList.length; i++) {
            if (passengerList[i] != null) {
                System.out.println(i + ") " + passengerList[i]);
            }
        }
        System.out.println("-------------- END OF PASSENGER SORT ----------------");
        System.out.println();
    }

    public static void storeShipData(String[] ship) {
        try {
            FileWriter myWriter = new FileWriter("shipData.txt");
            for (int i = 0; i < ship.length; i++) {
                myWriter.write("Cabin " + i + " : ");
                if(!ship[i].equals("e")) {
                    myWriter.write(ship[i]);
                } else {
                    myWriter.write("EMPTY CABIN");
                }
                myWriter.write(System.lineSeparator());
            }
            myWriter.close();
            System.out.println("Ship data saved successfully!");
        } catch (IOException ex ) {
            System.out.println("Error!!! IOException " + ex );
        }
    }

    public static void loadShipData(String[] ship) {
        Scanner scanner = new Scanner(System.in);
        String filePath = "shipData.txt";
        if(previewStoredData(filePath)) {
            while (true) {
                try {
                    System.out.println("1: Enter loaded data into the cruise ship and update the database");
                    System.out.println("2: Exit to main menu");
                    int userInput = scanner.nextInt();
                    if(userInput == 1) {
                        try {
                            File inputFile = new File(filePath);
                            Scanner readFile = new Scanner(inputFile);
                            String fileLine;
                            String passengerName = "e";

                            while (readFile.hasNext()) {
                                fileLine = readFile.nextLine();
                                int cabinNoInt;
                                try {
                                    String cabinNoString = fileLine.substring(6, 8);
                                    cabinNoInt = Integer.parseInt(cabinNoString);
                                    passengerName = fileLine.substring(11);
                                } catch (NumberFormatException ex) {
                                    String cabinNoString = fileLine.substring(6, 7);
                                    passengerName = fileLine.substring(10);
                                    cabinNoInt = Integer.parseInt(cabinNoString);
                                }
                                if(passengerName.equals("EMPTY CABIN")) {
                                    ship[cabinNoInt] = "e";
                                } else {
                                    ship[cabinNoInt] = passengerName;

                                }
                            }
                            System.out.println("Ship data loaded successfully!");
                        } catch (IOException ex) {
                            System.out.println("Error!!! IOException " + ex );
                        }
                        break;
                    } else if (userInput == 2) {
                        System.out.println("Exiting load data...");
                        break;
                    } else {
                        System.out.println("Invalid input!!!");
                    }
                } catch (InputMismatchException ex) {
                    System.out.println("Invalid input!!! ");
                    System.out.println("Exiting load data...");
                    break;
                }
            }
        } else {
            System.out.println("Empty file detected. Entered file path : " + filePath);
            System.out.println("Exiting load data...");
        }
    }

    public static boolean previewStoredData(String filePath) {
        boolean canRead = false;
        try {
            File inputFile = new File(filePath);
            Scanner readFile = new Scanner(inputFile);
            System.out.println("Reading " + filePath);
            while (readFile.hasNext()) {
                canRead = true;
                System.out.println(readFile.nextLine());
            }
            System.out.println("----------------------------------------------");
        } catch (IOException ex) {
            System.out.println("Error!!! IOException " + ex );
        }
        return canRead;
    }
}