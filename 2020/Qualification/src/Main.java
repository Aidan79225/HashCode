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
            List<Integer> books = new ArrayList<>();
            for (int i = 0; i < b; i++) {
                books.add(scanner.nextInt());
            }
            List<Section> sections = new ArrayList<>();
            for (int i = 0; i < l; i++) {
                Section section = new Section();
                section.n = scanner.nextInt();
                section.t = scanner.nextInt();
                section.m = scanner.nextInt();
                for (int j = 0; j < section.n; j++) {
                    section.bookIds.add(scanner.nextInt());
                }
                sections.add(section);
                System.out.println(section);
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
          if () {

          }
        }
        return max;
    }

    private int getScore(int d, Section section) {
        
    }

    public static class Section {
        public int n;
        public int t;
        public int m;
        public Set<Integer> bookIds = new HashSet<>();

        @Override
        public String toString() {
            return String.format("n: %d, t: %d, m: %d\n  %s", n, t, m, bookIds.toString());
        }
    }
}
