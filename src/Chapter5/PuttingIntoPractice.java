package Chapter5;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class PuttingIntoPractice {

    public static void main(String... args) {
        Trader raoul = new Trader("Raoul", "Cambridge");
        Trader mario = new Trader("Mario", "Milan");
        Trader alan = new Trader("Alan", "Cambridge");
        Trader brian = new Trader("Brian", "Cambridge");

        List<Transaction> transactions = Arrays.asList(
                new Transaction(brian, 2011, 300),
                new Transaction(raoul, 2012, 1000),
                new Transaction(raoul, 2011, 400),
                new Transaction(mario, 2012, 710),
                new Transaction(mario, 2012, 700),
                new Transaction(alan, 2012, 950)
        );

        // 질의 1: 2011년에 일어난 모든 트랜잭션을 찾아 값을 오름차순으로 정리하시오
        List<Transaction> tr2011 = transactions.stream()
                .filter(transaction -> transaction.getYear() == 2011)
                .sorted(comparing(Transaction::getValue))
                .collect(toList());
        System.out.println(tr2011);

        // 질의 2: 거래자가 근무하는 모든 도시를 중복 없이 나열하시오
        List<String> cities = transactions.stream()
                .map(transaction -> transaction.getTrader().getCity())
                .distinct()
                .collect(toList());
        System.out.println(cities);

        // 질의 3: 케임브리지에서 근무하는 모든 거래자를 찾아서 이름순으로 정렬하시오
        List<Trader> traders = transactions.stream()
                .map(Transaction::getTrader)
                .filter(trader -> trader.getCity().equals("Cambridge"))
                .distinct()
                .sorted(comparing(Trader::getName))
                .collect(toList());
        System.out.println(traders);

        // 질의 4: 모든 거래자의 이름을 알파벳순으로 정렬해서 반환하시오
        String traderStr = transactions.stream()
                .map(transaction -> transaction.getTrader().getName())
                .distinct()
                .sorted()
                .reduce("", (n1, n2) -> n1 + n2);
        System.out.println(traderStr);

        // 질의 5: 밀라노에 거래자가 있는가?
        boolean milanBased = transactions.stream()
                .anyMatch(transaction -> transaction.getTrader().getCity().equals("Milan"));
        System.out.println(milanBased);

        // 질의 6: 케임브리지에 거주하는 거래자의 모든 트랜잭션값을 출력하시오
        transactions.stream()
                .filter(t -> "Cambridge".equals(t.getTrader().getCity()))
                .map(Transaction::getValue)
                .forEach(System.out::println);

        // 질의 7: 전체 트랜잭션 중 최댓값은 얼마인가?
        int highestValue = transactions.stream()
                .map(Transaction::getValue)
                .reduce(0, Integer::max);
        System.out.println(highestValue);

        // 질의 8: 전체 트랜잭션 중 최솟값은 얼마인가?
        Optional<Transaction> smallestTransaction = transactions.stream()
                .min(comparing(Transaction::getValue));
        // 거래가 없을 때 기본 문자열을 사용할 수 있도록 발견된 거래가 있으면 문자열로 바꾸는 꼼수를 사용함(예, the Stream is empty)
        System.out.println(smallestTransaction.map(String::valueOf).orElse("No transactions found"));
    }
}
