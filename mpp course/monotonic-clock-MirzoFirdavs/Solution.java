import org.jetbrains.annotations.NotNull;

/**
 * В теле класса решения разрешено использовать только переменные делегированные в класс RegularInt.
 * Нельзя volatile, нельзя другие типы, нельзя блокировки, нельзя лазить в глобальные переменные.
 *
 * @author : Zaynidinov Mirzofirdavs
 */
public class Solution implements MonotonicClock {
    private final RegularInt c1 = new RegularInt(0);
    private final RegularInt c2 = new RegularInt(0);
    private final RegularInt c3 = new RegularInt(0);
    private final RegularInt c4 = new RegularInt(0);
    private final RegularInt c5 = new RegularInt(0);

    @Override
    public void write(@NotNull Time time) {
        c1.setValue(time.getD1());
        c2.setValue(time.getD2());
        c3.setValue(time.getD3());
        c4.setValue(time.getD2());
        c5.setValue(time.getD1());
    }

    @NotNull
    @Override
    public Time read() {
        int q1 = c5.getValue();
        int q2 = c4.getValue();
        int q3 = c3.getValue();
        int q4 = c2.getValue();
        int q5 = c1.getValue();

        return new Time(q5, q5 == q1 ? q4 : 0, q5 == q1 && q4 == q2 ? q3 : 0);
    }
}
