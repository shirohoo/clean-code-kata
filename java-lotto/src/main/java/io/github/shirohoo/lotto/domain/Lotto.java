package io.github.shirohoo.lotto.domain;


import java.util.Set;
import java.util.TreeSet;
import java.util.function.ToIntFunction;

public class Lotto {
    public static final int PRICE = 1_000;

    private final Set<Integer> numbers;

    private Lotto(Set<Integer> numbers) {
        int lottoIsSixNumbers = 6;
        if (numbers.size() != lottoIsSixNumbers || isNotRanged(numbers)) {
            throw new IllegalArgumentException("lotto numbers must be not duplicated 6 numbers in range 1-45");
        }
        this.numbers = new TreeSet<>(numbers);
    }

    private boolean isNotRanged(Set<Integer> numbers) {
        return min(numbers) < 1 || max(numbers) > 45;
    }

    private Integer min(Set<Integer> numbers) {
        return numbers.stream().min(Integer::compare).orElse(Integer.MIN_VALUE);
    }

    private Integer max(Set<Integer> numbers) {
        return numbers.stream().max(Integer::compare).orElse(Integer.MAX_VALUE);
    }

    public static Lotto from(Set<Integer> numbers) {
        return new Lotto(numbers);
    }

    public MatchPrize drawn(Lotto winningLotto) {
        int matchCount = matchCount(this, winningLotto);
        return MatchPrize.findByMatchCount(matchCount);
    }

    private int matchCount(Lotto target, Lotto winningLotto) {
        long matchCount = target.numbers.stream()
                .mapToInt(unboxed())
                .filter(winningLotto.numbers::contains)
                .count();

        return Math.toIntExact(matchCount);
    }

    private ToIntFunction<Integer> unboxed() {
        return i -> i;
    }

    public String stringValue() {
        return numbers.toString();
    }
}
