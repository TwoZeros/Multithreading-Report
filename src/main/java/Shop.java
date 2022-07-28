import java.util.List;
import java.util.concurrent.atomic.LongAdder;

public class Shop {
    private List<Long> listSum;
    private LongAdder totalRevenue;
    private String name;
    Shop( List<Long> listSum, LongAdder totalRevenue, String name) {
        this.name = name;
        this.listSum = listSum;
        this.totalRevenue = totalRevenue;
    }

    public List<Long> getListSum() {
        return listSum;
    }

    public void setListSum(List<Long> listSum) {
        this.listSum = listSum;
    }

    public void calculateRevenue() {
        listSum.forEach(i -> totalRevenue.add(i));
        System.out.printf("Магазин %s сдал отчет\n", name);
    }


}
