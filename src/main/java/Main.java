import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

public class Main {
    private static final int MIN_VALUE = 100;
    private static final int MAX_VALUE = 10000;

    LongAdder total = new LongAdder();
    public static void main(String[] args) {
        LongAdder total = new LongAdder();
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        List<Long> list1 = generateListValue(500, 3000);
        List<Long> list2 = generateListValue(600, 6000);
        List<Long> list3 = generateListValue(600, 6009);

        List<Shop> shopList = List.of(
                new Shop(list1,total, "Магазин1"),
                new Shop(list2,total,"Магазин2"),
                new Shop(list3,total, "Магазин3"));
        //Каждый магазин сообщает кассе свою выручку
        shopList.forEach( shop -> executorService.submit(shop::calculateRevenue));
        executorService.shutdown();
        try {
            executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);
            //считаем всю выручку и выводим её на экран
            System.out.println("Общая сумма: " + total.sum());
        } catch (InterruptedException e) {
            System.out.println("Ошибка в сдаче отчетности");
        }
    }

    public static List<Long> generateListValue(int minCountOperation, int maxCountOperation) {
        Random random = new Random();
        int countOperation = random.nextInt(minCountOperation, maxCountOperation);
        return LongStream.range(0, countOperation).map((i) -> random.nextInt(MIN_VALUE, MAX_VALUE)).boxed().toList();
    }
}
