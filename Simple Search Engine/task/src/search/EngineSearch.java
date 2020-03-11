package search;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EngineSearch {
    List<String> list = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    Map<String, List<Integer>> map = new HashMap<>();

    public void start(String[] args) {
//        insertPeople();
        String path = "";
        for (int i = 0; i < args.length - 1; i++) {
            if ("--data".equals(args[i])) {
                path = args[i + 1];
            }
        }
        importPeople(path);
        pick();
    }

    void insertPeople() {
        System.out.println("Enter the number of people:");
        int numberOfInputs = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i < numberOfInputs; i++) {
            list.add(scanner.nextLine());
        }
    }

    void importPeople(String path) {
        File file = new File(path);
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                list.add(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        for (int i = 0; i < list.size(); i++) {
            String[] strings = list.get(i).split(" ");
            for (String s: strings) {
                if (map.containsKey(s.toLowerCase())) {
                    List<Integer> l = map.get(s.toLowerCase());
                    l.add(i);
                    map.replace(s.toLowerCase(), l);
                } else {
                    List<Integer> l2 = new ArrayList<>();
                    l2.add(i);
                    map.put(s.toLowerCase(), l2);
                }
            }
        }
    }

    void search() {
        System.out.println("Select a matching strategy: ALL, ANY, NONE");
        String type = scanner.nextLine();
        String query = scanner.nextLine();
        QuerySelectionAlgorithm algorithm = create(type);
        SelectionContext context = new SelectionContext();
        context.setAlgorithm(algorithm);
        List<String> listQuery = context.select(list, map, query);
        if (listQuery.size() == 0) {
            System.out.println("Not found!");
        } else {
            System.out.printf("%d persons found:%n", listQuery.size());
        }
        for (String s: listQuery) {
            System.out.println(s);
        }
    }

    public QuerySelectionAlgorithm create(String type) {
        switch (type) {
            case "ALL":
                return new QueryAll();
            case "ANY":
                return new QueryAny();
            case "NONE":
                return new QueryNone();
            default:
                throw new IllegalArgumentException("Unknown algorithm type " + type);
        }
    }

    void printMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Find a person");
        System.out.println("2. Print all people");
        System.out.println("0. Exit");
    }

    void printList() {
        for (String s : list) {
            System.out.println(s);
        }
    }

    void pick() {
        boolean finished = false;
        while (!finished) {
            printMenu();
            String choice = scanner.nextLine();
            switch (choice) {
                case "1" :
                    search();
                    break;
                case "2":
                    printList();
                    break;
                case "0":
                    finished = true;
                    System.out.println("Bye!");
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
                    break;
            }
        }
    }

}
