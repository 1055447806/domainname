import com.ohh.domainname.util.Algorithm;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> words = Algorithm.builder().setHead("8---").setTail("8zzz").getWords(4);
        System.out.println(words.size());
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
