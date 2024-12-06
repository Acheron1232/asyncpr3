# Паралельна обробка масивів

Ця Java програма демонструє використання паралельної обробки, роботи з файлами та функціональне програмування за допомогою потоків. Програма генерує три випадкові масиви, зберігає їх у окремі файли, обробляє їх паралельно, а потім об'єднує результати з додатковим фільтруванням.

## Функціонал:
- **Генерація випадкових масивів**: Генерується три масиви випадкових цілих чисел у заданому діапазоні.
- **Робота з файлами**: Кожен масив зберігається в окремому текстовому файлі, а потім масиви завантажуються з файлів для подальшої обробки.
- **Паралельна обробка**: Масиви обробляються паралельно за допомогою `ExecutorService`.
- **Обробка масивів**: Кожен масив проходить різну трансформацію:
    - Масив 1: Фільтруються непарні числа та сортуються.
    - Масив 2: Кожен елемент ділиться на 3 та сортується.
    - Масив 3: Фільтруються числа в діапазоні `[50, 250]` та сортуються.
- **Об'єднання та фільтрація масивів**: Оброблені масиви об'єднуються, і значення, які вже є в Масиві 3, видаляються, після чого масив сортується.
