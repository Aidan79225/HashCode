import kotlin.Pair;

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
                System.out.println("Score: " + new Main().getScore(d, bookScores, section));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void getAnswer(int b, int l, int d, List<Integer> books, List<Section> sections, List<Integer> bookScores) {
        //get max score
        Pair<Integer, UsedBooks> maxScoreIndex = getMaxScoreIndex(d, books, sections);
        Section section = sections.get(maxScoreIndex.getFirst());
        sections.remove(maxScoreIndex);
        //update sections
        removeBooks(maxScoreIndex.getSecond(), sections);
        //next round
        getAnswer(b, l, d - section.t, books, sections);
    }

<<<<<<< HEAD
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

    private int getMaxScore(int d, List<Integer> books, List<Section> sections) {
=======
    private Pair<Integer, UsedBooks> getMaxScoreIndex(int d, List<Integer> books, List<Section> sections) {
>>>>>>> c8fff966464ca3ee636e59a7e2591d95d0786e36
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
}
