
import java.io.File;
import java.io.FileOutputStream;
import java.util.*;

public class Main {

    public static void main(String[] args) {
        // write your code here
        try {
            File in = new File(args[0]);
            Scanner scanner = new Scanner(in);
            int m = scanner.nextInt();
            int n = scanner.nextInt();
            List<Integer> p = new ArrayList<>();
            for (int i = 0; i < n; i++) {
                p.add(scanner.nextInt());
            }
            List<Integer> answer = new ArrayList<>();
            Main main = new Main();
            main.getAnswer(m, p, answer);

            int sum = 0;
            for (int pick: answer) {
                sum += p.get(pick);
            }
            System.out.println(m - sum);

            File out = new File(args[0].split("\\.")[0] + ".out");
            FileOutputStream fo = new FileOutputStream(out);
            fo.write(String.format("%d\n", answer.size()).getBytes());
            for (int i : answer) {
                fo.write((String.format("%d ", i)).getBytes());
            }
            fo.write("\n".getBytes());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void getAnswer(int target, List<Integer> p, List<Integer> answer) {
        int temp = target;
        int index = p.size();
        while(temp > 0) {
            index = search(temp, p, index);
            if (index == -1) {
                break;
            }
            temp -= p.get(index);
            answer.add(0, index);
        }
    }

    int search(int target, List<Integer> p, int max) {
        int temp = target;
        for (int i = max-1; i >=0 ; i--) {
            if (p.get(i) <= target) {
                return i;
            }
        }
        return -1;
    }



}
