import org.junit.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.IntConsumer;

public class TestCollections {
    @Test(expected = UnsupportedOperationException.class)
    public void TestAsList() {
        String[] array = new String[]{"xyz1", "abc", "1234"};
        List<String> stringList = Arrays.asList(array); // 只是一个视图而已

        IntConsumer intConsumer = i -> System.out.println(i);
        for (int i = 0; i < 10; ++i) {
            intConsumer.accept(i);
        }

        array[0] = "kkk";
        System.out.println(stringList.get(0));
        stringList.add("newElement"); // 不可添加
    }

    @Test(expected = UnsupportedOperationException.class)
    public void TestNCopies() {
        List<String> stringList = Collections.nCopies(5, "abc"); //不可修改
        stringList.set(0, "kkk");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void TestSingleton() {
        Set<String> stringSet = Collections.singleton("abc");
        stringSet.add("another");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void TestEmpty() {
        Set<String> empty = Collections.emptySet();
        empty.add("abc");
    }

    @Test
    public void TestSortedSet() {
        SortedSet<String> stringSet = new TreeSet<>();
        stringSet.add("xyz");
        stringSet.add("abc");
        stringSet.add("119");
        stringSet.add("nnn");
        stringSet.add("273");
        for (String s : stringSet) {
            System.out.println(s);
        }
        System.out.println("--------------");
        SortedSet<String> sub = ((TreeSet<String>) stringSet).subSet("119", "nnn");
        for (String s : sub) {
            System.out.println(s);
        }
        Assert.assertEquals(3, sub.size());

        System.out.println("--------------");
        stringSet.add("210");
        Assert.assertEquals(4, sub.size()); // subSet只是一个视图
        for (String s : sub) {
            System.out.println(s);
        }

        System.out.println("--------------");
        String[] newStrings = stringSet.toArray(new String[stringSet.size()]);
        for (String s : newStrings) {
            System.out.println(s);
        }
    }

    @Test(expected = UnsupportedOperationException.class)
    public void TestUnmodifiable() {
        List<String> stringList = new ArrayList<>();
        stringList.add("xyz");
        stringList.add("abc");

        List<String> list = Collections.unmodifiableList(stringList);
        list.add("mmm");
    }

    @Test(expected = ClassCastException.class)
    public void TestCheckedView() {
        ArrayList<String> stringArrayList = new ArrayList<>();
        ArrayList raw = stringArrayList;
        raw.add(new Date());
        raw.add(new Date());
        List safeString = Collections.checkedList(stringArrayList, String.class);
        List raw2 = safeString;
        raw2.add(new Date());
    }

    @Test
    public void TestSort() {
        class Staff {
            private int age;

            public int getAge() {
                return age;
            }

            public Staff(int a) {
                age = a;
            }
        }

        ArrayList<Staff> staffArrayList = new ArrayList<>();
        staffArrayList.add(new Staff(33));
        staffArrayList.add(new Staff(23));
        staffArrayList.add(new Staff(43));
        staffArrayList.sort(Comparator.comparingInt(Staff::getAge).reversed());
        Assert.assertEquals(43, staffArrayList.get(0).getAge());

        Iterator<Staff> it = staffArrayList.iterator();
        it.next();
        it.forEachRemaining(System.out::println);
        System.out.println("---------");
        ListIterator<Staff> listIterator = staffArrayList.listIterator();
        listIterator.next();
        listIterator.remove();
        listIterator.next();
        listIterator.remove();
        listIterator.forEachRemaining(System.out::println);

        NavigableSet<String> set = new TreeSet<>();
        set.add("xyz");
        set.add("abc");
        set.add("kkk");
        Assert.assertEquals("abc", set.floor("ef"));
    }

    @Test
    public void TestPriorityQueue() {
        PriorityQueue<LocalDate> pq = new PriorityQueue<>();
        pq.add(LocalDate.of(2011, 12, 1));
        pq.add(LocalDate.of(2001, 4, 9));
        pq.add(LocalDate.of(2013, 3, 12));
        pq.add(LocalDate.of(2014, 7, 28));
        for (LocalDate ld : pq) {
            System.out.println(ld);
        }
        System.out.println(pq.remove());
    }

    @Test
    public void TestMapCount() {
        Map<String, Integer> stringIntegerMap = new HashMap<>();
        String word = "abc";
        stringIntegerMap.put(word, stringIntegerMap.getOrDefault(word, 0) + 1);

        String another = "xyz";
        stringIntegerMap.putIfAbsent(another, 0);
        stringIntegerMap.put(another, stringIntegerMap.get(another) + 1);

        String yet = "yet";
        stringIntegerMap.merge(yet, 1, Integer::sum); //最好的方式
        stringIntegerMap.merge(yet, 1, (oldValue, newValue) -> oldValue + newValue);
        stringIntegerMap.forEach((k, v) -> System.out.println(k + v));
    }

    @Test
    public void TestConcurrentMapCount() { // 相当于ConcurrentHashMap<String, AtomicLong>
        ConcurrentHashMap<String, Long> map = new ConcurrentHashMap<>();
        String word = "abc";
        Long oldValue, newValue;
        do {
            oldValue = map.get(word);
            newValue = oldValue == null ? 1 : oldValue + 1;
        } while (!map.replace(word, oldValue, newValue));

        // 等效于上面的do while循环
        map.compute(word, (k, v) -> v == null ? 1 : v + 1);
    }

    @Test
    public void TestLongAdder() {
        ConcurrentHashMap<String, LongAdder> map = new ConcurrentHashMap<>();
        String word = "abc";
        map.putIfAbsent(word, new LongAdder()).increment();
        map.computeIfAbsent(word, k -> new LongAdder()).increment();
    }

    @Test
    public void TestAtomic() {
        AtomicLong largest = new AtomicLong();
        Long observed = 3L;
        largest.set(Math.max(largest.get(), observed)); // bad

        Long oldValue, newValue;
        do {
            oldValue = largest.get();
            newValue = Math.max(oldValue, observed);
        } while (!largest.compareAndSet(oldValue, newValue));
    }
}