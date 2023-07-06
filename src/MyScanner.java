import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MyScanner {

    public List<String> scanner(String code) {
        List<String> tokens = new ArrayList<>();
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < code.length(); i++) {
            String charAt = String.valueOf(code.charAt(i));
            if (charAt.equals(" ")) {
                if (token.length() > 0) {
                    tokens.add(token.toString());
                    token = new StringBuilder();
                }
                continue;
            }
            if (charAt.equals(":")){
                tokens.add(token.toString());
                String nextChar = String.valueOf(code.charAt(i+1));
                if (nextChar.equals("=")){
                    charAt+=nextChar;
                    i++;
                }
                tokens.add(charAt);
                token = new StringBuilder();
                continue;
            }
            if (charAt.matches("[<>]")) {
                tokens.add(token.toString());
                String nextChar = String.valueOf(code.charAt(i+1));
                if (nextChar.matches("[<>=]")){
                    charAt+=nextChar;
                    i++;
                }
                tokens.add(charAt);
                token = new StringBuilder();
                continue;
            }
            if (charAt.matches("[()+\\-;*/,.%=]")) {
                tokens.add(token.toString());
                tokens.add(charAt);
                token = new StringBuilder();
                continue;
            }
            token.append(charAt);

        }
        if (!token.isEmpty()){
            tokens.add(token.toString());
        }

        tokens.removeAll(Arrays.asList("", null));
        return tokens;
    }
    public int getIdFromTable(String token){
        HashMap<String, Integer> table = new HashMap<>();
        //reserved words 1-15
        table.put("project", 1);
        table.put("const", 2);
        table.put("var", 3);
        table.put("int",4);
        table.put("routine",5);
        table.put("start",6);
        table.put("end",7);
        table.put("input",8);
        table.put("output",9);
        table.put("if",10);
        table.put("then",11);
        table.put("endif",12);
        table.put("else",13);
        table.put("loop",14);
        table.put("do",15);

        //terminals
        table.put(".",16);
        table.put(";",17);
        table.put(":",18);
        table.put(",",19);
        table.put(":=",20);
        table.put("(",21);
        table.put(")",22);
        table.put("+",23);
        table.put("-",24);
        table.put("*",25);
        table.put("/",26);
        table.put("%",27);
        table.put("=",29);
        table.put("<>",30);
        table.put("<",31);
        table.put("<=",32);
        table.put(">",34);
        table.put(">=",35);

        if (table.containsKey(token)){
            return table.get(token);
        }
        return 100;
    }
}
