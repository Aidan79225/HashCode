import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class Main {
    // jackson
    public static void main(String[] args) {
        try {
            File in = new File(args[0]);
            Scanner scanner = new Scanner(in);
            int b = scanner.nextInt();
            int l = scanner.nextInt();
            int d = scanner.nextInt();
            List<Integer> bookScores = new ArrayList<>();
            for (int i = 0; i < b; i++) {
                bookScores.add(scanner.nextInt());
            }
//            System.out.println("bookScores: " + bookScores);
            List<Section> sections = new ArrayList<>();
            for (int i = 0; i < l; i++) {
                Section section = new Section(bookScores);
                section.n = scanner.nextInt();
                section.t = scanner.nextInt();
                section.m = scanner.nextInt();
                for (int j = 0; j < section.n; j++) {
                    section.bookIds.add(scanner.nextInt());
                }
                sections.add(section);
//                System.out.println(section);
//                System.out.println("Score: " + new Main().getScore(d, bookScores, section).score);
            }
            Main main = new Main();
            main.getAnswer(b, l, d, bookScores, sections);
            File out = new File(args[0].split("\\.")[0] + ".out");
            FileOutputStream fo = new FileOutputStream(out);
            fo.write(String.format("%d\n", main.ans.size()).getBytes());
            for (Pair<Integer, UsedBooks> pair: main.ans) {
                fo.write(String.format("%d %d\n", pair.first, pair.second.bookIds.size()).getBytes());
                fo.write(
                        String.format("%s\n", pair.second.bookIds)
                                .replace("[","")
                                .replace("]","")
                                .replace(",","")
                                .getBytes()
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    List<Pair<Integer, UsedBooks>> ans = new ArrayList<>();
    public void getAnswer(int b, int l, int d, List<Integer> books, List<Section> sections) {
        if (d <= 0) {
            return;
        }
        //get max score
        Pair<Integer, UsedBooks> maxScoreIndex = getMaxScoreIndex(d, books, sections);
        if (maxScoreIndex.first < 0 || maxScoreIndex.second.bookIds.size() == 0) {
            return;
        }
        ans.add(maxScoreIndex);
        Section section = sections.get(maxScoreIndex.first);
        sections.remove(maxScoreIndex.first.intValue());
        //update sections
        removeBooks(maxScoreIndex.second, sections);
        //next round
        getAnswer(b, l, d - section.t, books, sections);
    }

    private Pair<Integer, UsedBooks> getMaxScoreIndex(int d, List<Integer> books, List<Section> sections) {
        int max = Integer.MIN_VALUE;
        int cur = -1;
        UsedBooks curUsedBooks = new UsedBooks();
        for (int i = 0; i < sections.size(); i++) {
            UsedBooks usedBooks = getScore(d, books, sections.get(i));
            if (usedBooks.score > max) {
                max = usedBooks.score;
                cur = i;
                curUsedBooks = usedBooks;
            }
        }
        return new Pair<>(cur, curUsedBooks);
    }

    private UsedBooks getScore(int d, List<Integer> books, Section section) {
        int day = d - section.t;
        UsedBooks ans = new UsedBooks();
        Iterator<Integer> iterator = section.bookIds.iterator();
        while (day > 0) {
            for (int i = 0; i < section.m && iterator.hasNext(); i++) {
                int id = iterator.next();
                ans.score += books.get(id);
                ans.bookIds.add(id);
            }
            --day;
        }
        return ans;
    }

    private void removeBooks(UsedBooks usedBooks, List<Section> sections) {
        for (Section section : sections) {
            for (Integer id : usedBooks.bookIds) {
                section.bookIds.remove(id);
            }
        }
    }

    public static class Section {
        public int n;
        public int t;
        public int m;
        public List<Integer> bookScores;

        Section(List<Integer> bookScores) {
            this.bookScores = bookScores;
        }

        public TreeSet<Integer> bookIds = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer t1, Integer t2) {
                return bookScores.get(t2) - bookScores.get(t1);
            }
        });

        @Override
        public String toString() {
            return String.format("n: %d, t: %d, m: %d\n  %s", n, t, m, bookIds.toString());
        }
    }

    public static class UsedBooks {
        int score;
        List<Integer> bookIds = new ArrayList<>();
    }

    public static class Pair<T, S> {
        public T first;
        public S second;

        Pair(T first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
