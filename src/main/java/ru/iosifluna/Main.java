package ru.iosifluna;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите количество кубиков от 1 до 9: ");
        int k = scanner.nextInt();
        System.out.print("Введите количество граней на каждом кубике от 1 до 9: ");
        int n = scanner.nextInt();

        LocalTime timeStart = LocalTime.now();
        Stack<String> combinations = findCombinations(k, n);
        LocalTime timeEnd = LocalTime.now();
        System.out.printf("Время выполнения: %s\n", Duration.between(timeStart, timeEnd).toString());
        System.out.printf("Получилось %d вариантов", combinations.size());

        if (!combinations.isEmpty()) {
            String combination;
            System.out.println();

            do {
                System.out.println(combinations.pop());
            } while (!combinations.empty());
        }
    }

    // поиск всех возможных комбинаций игральных кубиков k с количеством граней n
    public static Stack<String> findCombinations(int k, int n) {
        Stack<String> result = new Stack<>();

        char[] onesArray = new char[k];
        Arrays.fill(onesArray, '1');
        String currString = new String(onesArray);
        result.push(currString);
        StringBuilder sbCurrString = new StringBuilder(currString);

        // начинаем обход с грани последнего кубика
        int i = sbCurrString.length() - 1;

        while (true) {
            // получаем номер грани
            int num = sbCurrString.charAt(i) - '0';
            // достигли последнюю грань этого кубика?
            if (num == n) {
                // достигли, это был последний кубик?
                if (i == 0) {
                    // да
                    return result;
                } else {
                    // нет, пробуем увеличить на единицу грань предыдущего кубика
                    --i;

                    do {
                        num = sbCurrString.charAt(i) - '0';
                        // достигли последнюю грань этого кубика?
                        if (num == n) {
                            // да, идём на грань предыдущего кубика
                            --i;
                            continue;
                        } else {
                            // нет, не  достигли, увеличиваем значение на единицу
                            sbCurrString.setCharAt(i, (char) (num + '1'));
                            // и устанавливаем единицы в качестве значений граней всех оставшихся кубиков
                            for (++i; i < sbCurrString.length(); ++i) {
                                sbCurrString.setCharAt(i, '1');
                            }
                            // запоминаем получившуюся строку с номерами граней
                            // устанавливам индекс опять на грань последнего кубика
                            // и идём на внешний цикл
                            result.push(sbCurrString.toString());
                            i = sbCurrString.length() - 1;
                            break;
                        }
                    } while (i >= 0);
                    // специальный случай: всё обошли, но ничего не нашли
                    if (i < 0) {
                        i = 0;
                    }
                }
            } else {
                // нет, последнюю грань не достигли, увеличиваем номер грани
                // и запоминаем получившуюся строку с номерами граней
                sbCurrString.setCharAt(i, (char) (num + '1'));
                result.push(sbCurrString.toString());
            }
        }
    }
}