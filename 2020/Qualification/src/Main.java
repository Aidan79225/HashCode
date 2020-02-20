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

    public void getAnswer(int b, int l, int d, List<Integer> books, List<Section> sections) {
        //get max score
        //update sections
        //next round
    }

    private int getMaxScore(int d, List<Integer> books, List<Section> sections) {
        int max = 0;
        int cur = 0;
        for (int i = 0; i < sections.size(); i++) {
            int score = getScore(d, books, sections.get(i));
            if (score > max) {
                cur = i;
            }
        }
        return cur;
    }

    private int getScore(int d, List<Integer> books, Section section) {
        int day = d - section.t;
        int sum = 0;
        while (day > 0) {
            for (int i = 0; i < section.m && !section.bookIds.isEmpty(); i++) {
                int id = section.bookIds.first();
                sum += books.get(id);
                section.bookIds.remove(id);
            }
            --day;
        }
        return sum;
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
}
