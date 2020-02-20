import java.io.File;
import java.util.*;


public class Calc {
    /** 
     * 
     * @param args output answer filename
     */
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
            }

        } catch (Exception e) {
            e.printStackTrace();
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
}

