import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class txttest {

    /**
     * 读取txt文件的内容
     *
     * @param file 想要读取的文件对象
     * @return 返回文件内容
     */
    public static String txt2String(File file) {
        StringBuilder result = new StringBuilder();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                Matcher m = p.matcher(s);
                s = m.replaceAll("");

                result.append(s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        // 处理字符串
//        List<String> resultList = new ArrayList<>();
//
//        while (result.substring(result.indexOf("sonLists:["), result.indexOf("]")) != null) {
//            resultList.add(result.substring(result.indexOf("sonLists:["), result.indexOf("]")));
//            result = result.delete(0, result.indexOf("]"));
//        }

        return result.toString();
    }

    public static List<String> dealString(String s) {
        List<String> resultList = new ArrayList<>();

        while (s.indexOf("sonLists:[") != -1) {
            List<String> stringList = new ArrayList<>();
            String newS = s.substring(s.indexOf("sonLists:[") + 10);
            newS = newS.substring(0, newS.indexOf("}]}"));
            while (newS.indexOf("sontitle:'") != -1) {
                newS = newS.substring(newS.indexOf("sontitle:'") + 10);
                String needAdd = newS.substring(0, newS.indexOf("'")).replace("；", "");
                stringList.add(needAdd);
            }
            String temp = "";
            for (String s1 : stringList) {
                temp += s1 + ";";
            }
            resultList.add(temp.substring(0, temp.length() - 1));
            s = s.substring(s.indexOf("sonLists:["));
            s = s.substring(s.indexOf("]"));
        }

        return resultList;
    }

    /**
     * 使用FileOutputStream来写入txt文件
     *
     * @param txtPath txt文件路径
     * @param content 需要写入的文本
     */
    public static void writeTxt(String txtPath, String content) {
        FileOutputStream fileOutputStream = null;
        File file = new File(txtPath);
        try {
            if (file.exists()) {
                //判断文件是否存在，如果不存在就新建一个txt
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(content.getBytes());
            fileOutputStream.flush();
            fileOutputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        File file = new File("D:/content.txt");
//        writeTxt("D:/result.txt", txt2String(file));
        String txtString = txt2String(file);

        List<String> result = dealString(txtString);
        for (String s : result) {
            System.out.println(s);
            System.out.println();
        }
    }
}
