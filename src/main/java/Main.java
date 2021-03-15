import com.ohh.domainname.util.Algorithm;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> words = Algorithm.getWords(2);
        System.out.println(words);
//        try (BufferedWriter bw = new BufferedWriter(new FileWriter("C:\\Users\\Administrator\\Desktop\\3位.txt", false))) {
//            for (String word : words) {
//                bw.write(word);
//                bw.newLine();
//                bw.flush();
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }
}
