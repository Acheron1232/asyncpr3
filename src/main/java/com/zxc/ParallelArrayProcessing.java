package com.zxc;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class ParallelArrayProcessing {

    public static void main(String[] args) throws InterruptedException, ExecutionException, IOException {
        // Параметри генерації масивів
        int size = 100;
        int rangeStart = 0;
        int rangeEnd = 1000;

        // Генерація трьох масивів із випадковими числами
        int[] array1 = generateRandomArray(size, rangeStart, rangeEnd);
        int[] array2 = generateRandomArray(size, rangeStart, rangeEnd);
        int[] array3 = generateRandomArray(size, rangeStart, rangeEnd);

        // Запис кожного масиву в окремі файли
        saveArrayToFile(array1, "array1.txt");
        saveArrayToFile(array2, "array2.txt");
        saveArrayToFile(array3, "array3.txt");

        // Створення ExecutorService для паралельної обробки масивів
        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Виконання паралельних завдань для обробки масивів
        Future<List<Integer>> future1 = executor.submit(() -> processArray1(loadArrayFromFile("array1.txt")));
        Future<List<Integer>> future2 = executor.submit(() -> processArray2(loadArrayFromFile("array2.txt")));
        Future<List<Integer>> future3 = executor.submit(() -> processArray3(loadArrayFromFile("array3.txt")));

        // Очікуємо на завершення обробки та отримуємо результати
        List<Integer> processedArray1 = future1.get();
        List<Integer> processedArray2 = future2.get();
        List<Integer> processedArray3 = future3.get();

        // Завершуємо роботу ExecutorService
        executor.shutdown();

        // Злиття оброблених масивів та фільтрація результату
        List<Integer> finalMergedArray = mergeAndFilterArrays(processedArray1, processedArray2, processedArray3);

        // Виведення результатів обробки
        System.out.println("Processed Array 1 (Odd numbers): " + processedArray1);
        System.out.println("Processed Array 2 (Divided by 3): " + processedArray2);
        System.out.println("Processed Array 3 (Range [50, 250]): " + processedArray3);
        System.out.println("Final Merged and Filtered Array: " + finalMergedArray);
    }

    // Генерація масиву випадкових чисел у заданому діапазоні
    private static int[] generateRandomArray(int size, int start, int end) {
        Random random = new Random();
        return random.ints(size, start, end).toArray();
    }

    // Запис масиву в файл
    private static void saveArrayToFile(int[] array, String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int num : array) {
                writer.write(num + "\n");
            }
        }
    }

    // Завантаження масиву з файлу
    private static List<Integer> loadArrayFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            return reader.lines().map(Integer::parseInt).collect(Collectors.toList());
        }
    }

    // Обробка першого масиву (залишаємо непарні числа та сортуємо)
    private static List<Integer> processArray1(List<Integer> array) {
        return array.stream()
                .filter(num -> num % 2 != 0)  // Оставляємо лише непарні
                .sorted()                     // Сортуємо
                .collect(Collectors.toList());
    }

    // Обробка другого масиву (ділимо числа на 3 та сортуємо)
    private static List<Integer> processArray2(List<Integer> array) {
        return array.stream()
                .map(num -> num / 3)          // Ділимо на 3
                .sorted()                     // Сортуємо
                .collect(Collectors.toList());
    }

    // Обробка третього масиву (залишаємо числа в діапазоні [50, 250] та сортуємо)
    private static List<Integer> processArray3(List<Integer> array) {
        return array.stream()
                .filter(num -> num >= 50 && num <= 250)  // Залишаємо числа в діапазоні
                .sorted()                              // Сортуємо
                .collect(Collectors.toList());
    }

    // Злиття трьох масивів та фільтрація результату
    private static List<Integer> mergeAndFilterArrays(List<Integer> array1, List<Integer> array2, List<Integer> array3) {
        List<Integer> merged = new ArrayList<>();
        merged.addAll(array1);
        merged.addAll(array2);

        // Перетворення третього масиву в Set для швидкої перевірки
        Set<Integer> array3Set = new HashSet<>(array3);

        // Фільтрація елементів, які вже є в третьому масиві
        return merged.stream()
                .filter(num -> !array3Set.contains(num))  // Фільтруємо елементи, що є в array3
                .sorted()                                   // Сортуємо
                .collect(Collectors.toList());
    }
}
