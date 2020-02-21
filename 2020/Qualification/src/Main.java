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

    public int getAvgBook(List<Integer> books) {
        Collections.sort(books);
        return books.get(books.size()/10);
    }

    List<Pair<Integer, UsedBooks>> ans = new ArrayList<>();
    public void getAnswer(int b, int l, int d, List<Integer> books, List<Section> sections) {
        int avg = getAvgBook(books);
        while (d > 0) {
            //get max score
            Pair<Integer, UsedBooks> maxScoreIndex = getMaxScoreIndex(d, books, sections, avg);
            if (maxScoreIndex.first < 0 || maxScoreIndex.second.bookIds.size() == 0) {
                return;
            }
            ans.add(maxScoreIndex);
            Section section = sections.get(maxScoreIndex.first);
            section.used = true;
            //update sections
            removeBooks(maxScoreIndex.second, sections);
            d -= section.t;
        }
    }

    private Pair<Integer, UsedBooks> getMaxScoreIndex(int d, List<Integer> books, List<Section> sections, int avg) {
        int max = Integer.MIN_VALUE;
        int cur = -1;
        UsedBooks curUsedBooks = new UsedBooks();
        for (int i = 0; i < sections.size(); i++) {
            Section section = sections.get(i);
            if (section.used) {
                continue;
            }
            UsedBooks usedBooks = getScore(d, books, sections.get(i));
            int score = usedBooks.score/section.t;
//            score -= getLost(section.t, sections, avg);
            if (score > max) {
                max = score;
                cur = i;
                curUsedBooks = usedBooks;
            } else if (usedBooks.score == max && sections.get(cur).m < section.m) {
                cur = i;
                curUsedBooks = usedBooks;
            }
        }
        return new Pair<>(cur, curUsedBooks);
    }

    private int getLost(int day, List<Section> sections, int avg) {
        int sum = 0;
        for (Section section: sections) {
            if (section.used) {
                continue;
            }
            sum += section.n*avg*day;
        }
        return sum;
    }

    private UsedBooks getScore(int d, List<Integer> books, Section section) {
        int day = d - section.t;
        UsedBooks ans = new UsedBooks();
        Iterator<Integer> iterator = section.bookIds.iterator();
//        ans.score = -section.t;
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
        public boolean used = false;

        Section(List<Integer> bookScores) {
            this.bookScores = bookScores;
        }

        public TreeSet<Integer> bookIds = new TreeSet<Integer>(new Comparator<Integer>() {
            @Override
            public int compare(Integer t1, Integer t2) {
                int key = bookScores.get(t2) - bookScores.get(t1);
                if (key == 0) {
                    return t2 - t1;
                } else {
                    return key;
                }
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