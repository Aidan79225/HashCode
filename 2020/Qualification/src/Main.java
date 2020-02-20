import java.io.File;
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
            System.out.println("bookScores: " + bookScores);
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
                System.out.println(section);
                System.out.println("Score: " + new Main().getScore(d, bookScores, section).score);
            }
            new Main().getAnswer(b, l, d, bookScores, sections);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 
     * @param b Books left
     * @param l Liberies number
     * @param d deadline left
     * @param books score of books
     * @param sections liberies list
     */
    public void getAnswer(int b, int l, int d, List<Integer> books, List<Section> sections) {
        if (d <= 0) {
            return;
        }

        //get max score
        Pair<Integer, UsedBooks> maxScoreIndex = getMaxScoreIndex(d, books, sections);
        Section section = sections.get(maxScoreIndex.first);
        System.out.println(String.format("%d %d", maxScoreIndex.first, maxScoreIndex.second.bookIds.size()));
        System.out.println(maxScoreIndex.second.bookIds);
        sections.remove(maxScoreIndex);
        //update sections
        removeBooks(maxScoreIndex.second, sections);
        //next round
        getAnswer(b, l, d - section.t, books, sections);
    }

    /**
     * @param bookOrders bookOrders[0] is the books that the first liberary will send to scan
     * @return total score of submission
     */
    public int evaluateFinalAnswer(List<List<Integer>> bookOrders, List<Integer> bookScores){
        int score = 0;
        for(List<Integer> booksPerLib : bookOrders){
            for(int book : booksPerLib){
                score += bookScores.get(book);
            }
        }
        return score;
    }

    private Pair<Integer, UsedBooks> getMaxScoreIndex(int d, List<Integer> books, List<Section> sections) {

        int max = 0;
        int cur = 0;
        UsedBooks curUsedBooks = new UsedBooks();
        for (int i = 0; i < sections.size(); i++) {
            UsedBooks usedBooks = getScore(d, books, sections.get(i));
            if (usedBooks.score > max) {
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
