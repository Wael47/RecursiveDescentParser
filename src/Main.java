import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter the path of your file code:");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();

        File file = new File(filePath);
        MyScanner compiler = new MyScanner();
        List<Token> tokens = new ArrayList<>();

        try (FileReader fileReader = new FileReader(file);
             BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String line;
            int lineNum = 1;
            while ((line = bufferedReader.readLine()) != null) {
                List<String> list = compiler.scanner(line);
                for (String s : list) {
                    Token token = new Token(compiler.getIdFromTable(s), s, lineNum);
                    tokens.add(token);
                }
                lineNum++;
            }
        } catch (IOException e) {
            System.err.println("File Not Found "+ e.getMessage());
            System.exit(1);
        }

        //tokens.forEach(token -> System.out.println(token.toString()));

        RecursiveDescentParser recursiveDescentParser = new RecursiveDescentParser();
        recursiveDescentParser.parser(tokens);
        System.out.println("your script have no Syntax error");
    }

}
