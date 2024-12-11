package year_2022.main;

import helpers.CodeDay;
import helpers.Helper;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.lang.Integer.parseInt;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.stream.IntStream.range;

public class Day13 extends CodeDay {
    @Override
    public void puzzleOne() {
        var input = Helper.readToString(2022, 13).split("\n\n");
//
//        int total = 0;
//        Gson gson = new Gson();
//        int c = 1;
//        for (var twoItems : input) {
//            var twoArr = twoItems.split("\n");
//            Type collectionType = new TypeToken<ArrayList<Object>>(){}.getType();
//            ArrayList<Object> arrone = gson.fromJson(twoArr[0], collectionType);
//            ArrayList<Object> arrtwo = gson.fromJson(twoArr[1], collectionType);
//            if (parse(arrone, arrtwo)) {
//                total += c;
//            }
//
//            c++;
//        }
//
//        System.out.println(total);
        var in = Arrays.stream(input).map(s -> s.split("\n")).toArray(String[][]::new);
        int res = range(0, in.length).filter(i -> compare(node(in[i][0]), node(in[i][1])).orElse(false)).map(i -> i+1).sum();
        System.out.println(res);
    }

    private Optional<Boolean> compare(Node a, Node b) {
        if(a.value.isB() && b.value.isB()) {
            int na = a.value.getB();
            int nb = b.value.getB();
            if(na < nb) return of(true);
            else if(na > nb) return of(false);
            else return empty();
        } else if(a.value.isA() && b.value.isA()) {
            List<Node> na = a.value.getA();
            List<Node> nb = b.value.getA();
            if(na.isEmpty() && !nb.isEmpty()) return of(true);
            else if(!na.isEmpty() && nb.isEmpty()) return of(false);
            else if(na.isEmpty() && nb.isEmpty()) return empty();
            else return compare(na.get(0), nb.get(0)).or(() -> compare(node(na.subList(1, na.size())), node(nb.subList(1, nb.size()))));
        }
        else if(a.value.isA()) return compare(a, node(b));
        else return compare(node(a), b);
    }

    private Node node(String s) {
        return node(s, findLevels(s));
    }
    private int[] findLevels(String str) {
        AtomicInteger l = new AtomicInteger();
        return str.chars().map(c -> l.addAndGet(c == '[' ? 1 : c == ']' ? -1 : 0)).toArray();
    }
    private Node node(List<Node> nodes) {
        return new Node(new Either<>(nodes, null));
    }

    private Node node(int n) {
        return new Node(new Either<>(null, n));
    }

    private Node node(Node n) {
        return node(List.of(n));
    }
    private Node node(String s, int[] levels) {
        if(s.charAt(0) >= '0' && s.charAt(0) <= '9') return node(parseInt(s));
        if(s.equals("[]")) return node(List.of());
        int[] commas = range(0, levels.length)
                .filter(i -> i == 0 || i == levels.length - 1 || levels[i] == levels[0] && s.charAt(i) == ',')
                .toArray();
        return node(range(1, commas.length)
                .mapToObj(i -> node(s.substring(commas[i-1]+1, commas[i]))).toList());
    }
    public record Node (Either<List<Node>, Integer> value) {}
    public boolean parse(ArrayList<?> arrone, ArrayList<?> arrtwo) {
        int a = 0;
        int m = Integer.max(arrone.size(), arrtwo.size());
        for (int i = 0; i < m; i++) {
            try {
                if (arrone.size() > i - 1 && arrtwo.size() > i - 1 && arrone.get(i) instanceof ArrayList<?> arr && arrtwo.get(i) instanceof ArrayList<?> arr2) {
                    boolean res = parse(arr, arr2);
                    if (res) {
                        return true;
                    }
                }

                if (arrone.size() > i - 1 && arrtwo.size() > i - 1 && arrone.get(i) instanceof Double int1 && arrtwo.get(i) instanceof Double int2) {
                    if (int1 < int2) {
                        return true;
                    }
                }

                if (arrone.size() > i - 1 && arrtwo.size() > i - 1 && arrone.get(i) instanceof ArrayList<?> arr1 && arrtwo.get(i) instanceof Double int2) {
                    Double int1 = getFirst(arr1);
                    if (int1 < int2) {
                        return true;
                    }
                }

                if (arrone.size() > i - 1 && arrtwo.size() > i - 1 && arrone.get(i) instanceof Double int1 && arrtwo.get(i) instanceof ArrayList<?> arr2) {
                    Double int2 = getFirst(arr2);
                    if (int1 < int2) {
                        return true;
                    }
                }

                if (arrone.size() > 0 && arrone.get(0) instanceof ArrayList<?> && arrtwo.get(0) instanceof Integer) {
                    return true;
                }
            } catch (Exception e) {
                if (arrone.size() < arrtwo.size()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Double getFirst(Object arr) {
        if (arr instanceof Double asdf) {
            return asdf;
        } else {
            return getFirst(((ArrayList<?>)arr).get(0));
        }
    }

    @Override
    public void puzzleTwo() {
        var input = Helper.readToString(2022, 13);
        var in = streamLines(input+"\n[[2]]\n[[6]]")
                .filter(s -> !s.isEmpty())
                .map(this::node)
                .sorted((a, b) -> compare(a, b).map(c -> c ? -1 : 1).orElse(0))
                .toList();
        var res = (in.indexOf(node(node(node(2)))) + 1) * (in.indexOf(node(node(node(6)))) + 1);
        System.out.println(res);
    }

    public static Stream<String> streamLines(String s) {
        return Arrays.stream(s.split("\n"));
    }
}

class Either<A, B> {
    private final Optional<A> a;
    private final Optional<B> b;

    public Either(A a, B b){
        verify(a == null || b == null);
        this.a = Optional.ofNullable(a);
        this.b = Optional.ofNullable(b);
    }

    public A getA() {
        return a.get();
    }

    public B getB() {
        return b.get();
    }

    public boolean isA(){
        return a.isPresent();
    }

    public boolean isB(){
        return b.isPresent();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Either<?, ?> either = (Either<?, ?>) o;

        if (!Objects.equals(a, either.a)) return false;
        return Objects.equals(b, either.b);
    }

    @Override
    public int hashCode() {
        int result = a != null ? a.hashCode() : 0;
        result = 31 * result + (b != null ? b.hashCode() : 0);
        return result;
    }

    public static void verify(boolean b) {
        verify(b, "Something went wrong");
    }

    public static void verify(boolean b, String message) {
        if(!b) {
            throw new IllegalStateException(message);
        }
    }
}
