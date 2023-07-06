import java.util.List;

public class RecursiveDescentParser {
    int i = 0;
    private List<Token> tokenList;

    public void parser(List<Token> tokenList) {
        this.tokenList = tokenList;
        if (tokenList.isEmpty()){
            System.err.print("Project is Empty");
            System.exit(1);
        }
        project_declaration();
    }

    void project_declaration() {
        project_def();
        if (getToken().getSymbol().equals(".")) {
            return;
        }
        error("You must put \".\" in end of your code");
    }

    void project_def() {
        project_heading();
        declarations();
        compound_stmt();
    }

    void project_heading() {
        if (getToken().getSymbol().equals("project")) {
            nextToken();
            if (getToken().getId() == 100) {
                if (userValidIdentifier(getToken().getSymbol())) {
                    nextToken();
                    if (getToken().getSymbol().equals(";")) {
                        nextToken();
                        return;
                    }
                    error("you have forgotten \";\" at end.");
                }
                error("user defined name must start with letter, must not contain special character");
                return;
            }
            error("Not user defined name.");
            return;
        }
        error("Your Code must Start with \"project\".");
    }

    void declarations() {
        const_decl();
        var_decl();
        subroutine_decl();
    }

    void const_decl() {
        if (getToken().getSymbol().equals("const")) {
            nextToken();
            const_item();
            if (getToken().getSymbol().equals(";")) {
                nextToken();
                if (getToken().getSymbol().equals("const")) {
                    const_decl();
                    return;
                }
                return;
            }
            error("you have forgotten ';' at end.");
        }
    }

    void const_item() {
        if (getToken().getId() == 100) {
            if (userValidIdentifier(getToken().getSymbol())) {
                nextToken();
                if (getToken().getSymbol().equals("=")) {
                    nextToken();
                    if (getToken().getId() == 100) {
                        if (integerValidIdentifier(getToken().getSymbol())) {
                            nextToken();
                            return;
                        }
                        error("Not integer Valid");
                        return;
                    }
                    error("Not user defined.");
                    return;
                }
                error("you have forgotten '='.");
                return;
            }
            error("user defined name must start with letter, must not contain special character");
            return;
        }
        error("Not user defined name.");
    }

    void var_decl() {
        if (getToken().getSymbol().equals("var")) {
            nextToken();
            var_item();
            if (getToken().getSymbol().equals(";")) {
                nextToken();
                if (getToken().getSymbol().equals("var")) {
                    var_item();
                    return;
                }
                return;
            }
            error("you have forgotten ';' at end.");
        }
    }

    void var_item() {
        name_list();
        if (getToken().getSymbol().equals(":")) {
            nextToken();
            if (getToken().getSymbol().equals("int")) {
                nextToken();
                return;
            }
            error("you have forgotten data type.");
            return;
        }
        error("you have forgotten ':'.");
    }

    void name_list() {
        if (getToken().getId() == 100) {
            if (userValidIdentifier(getToken().getSymbol())) {
                nextToken();
                if (getToken().getSymbol().equals(",")) {
                    nextToken();
                    name_list();
                }
                return;
            }
            error("user defined name must start with letter, must not contain special character");
            return;
        }
        error("Not user defined name.");
    }

    void subroutine_decl() {
        if (getToken().getSymbol().equals("routine")) {
            subroutine_heading();
            declarations();
            compound_stmt();
            if (getToken().getSymbol().equals(";")) {
                nextToken();
                return;
            }
            error("you have forgotten ';' at end.");
        }
    }

    void subroutine_heading() {
        if (getToken().getSymbol().equals("routine")) {
            nextToken();
            if (getToken().getId() == 100) {
                if (userValidIdentifier(getToken().getSymbol())) {
                    nextToken();
                    if (getToken().getSymbol().equals(";")) {
                        nextToken();
                        return;
                    }
                    error("you have forgotten ';' at end.");
                    return;
                }
                error("user defined name must start with letter, must not contain special character");
                return;
            }
            error("Not user defined name.");
            return;
        }
        error("you have forgotten 'routine' at start.");
    }

    void compound_stmt() {
        if (getToken().getSymbol().equals("start")) {
            nextToken();
            stmt_list();
            if (getToken().getSymbol().equals("end")) {
                nextToken();
                return;
            }
            error("you have forgotten 'end' at end of statement.");

        }
        error("you have forgotten 'start' at start of statement.");
    }

    void stmt_list() {
        statement();
        if (getToken().getSymbol().equals(";")) {
            nextToken();
            if (!getToken().getSymbol().equals("end")) {
                stmt_list();
            }
            return;
        }
        error("you have forgotten ';' at end.");
    }

    void statement() {

        if (getToken().getId() == 100) {
            ass_stmt();
            return;
        }
        if (getToken().getSymbol().equals("input") || getToken().getSymbol().equals("output")) {
            inout_stmt();
            return;
        }
        if (getToken().getSymbol().equals("if")) {
            if_stmt();
            return;
        }
        if (getToken().getSymbol().equals("loop")) {
            loop_stmt();
            return;
        }
        if (getToken().getSymbol().equals("start")) {
            compound_stmt();
        }
    }

    void ass_stmt() {
        if (userValidIdentifier(getToken().getSymbol())) {
            nextToken();
            if (getToken().getSymbol().equals(":=")) {
                nextToken();
                arith_exp();
                return;

            }
            error("you have forgotten ':='.");
            return;
        }
        error("user defined name must start with letter, must not contain special character");
    }

    void arith_exp() {
        term();
        if (getToken().getSymbol().matches("[+-]")) {
            add_sign();
            term();
            if (getToken().getSymbol().matches("[+-]")){
                nextToken();
                arith_exp();
            }
        }
    }

    void term() {
        factor();
        if (getToken().getSymbol().matches("[*/%]")) {
            mul_sign();
            factor();
            if (getToken().getSymbol().matches("[*/%]")){
                nextToken();
                term();
            }
        }
    }

    void factor() {
        if (getToken().getId() == 100) {
            name_value();
            return;
        }
        if (getToken().getSymbol().equals("(")) {
            nextToken();
            arith_exp();
            if (getToken().getSymbol().equals(")")) {
                nextToken();
                return;
            }
            error("you have forgotten ')'.");
        }
        error("");
    }

    void name_value() {
        if (getToken().getId() == 100) {
            if (userValidIdentifier(getToken().getSymbol())) {
                nextToken();
                return;
            }
            if (integerValidIdentifier(getToken().getSymbol())) {
                nextToken();
                return;
            }
        }
        error("Not user defined name.");
    }

    void add_sign() {
        if (getToken().getSymbol().matches("[+-]")) {
            nextToken();
            return;
        }
        error("you have forgotten '+' or '-'.");
    }

    void mul_sign() {
        if (getToken().getSymbol().matches("[*/%]")) {
            nextToken();
            return;
        }
        error("you have forgotten '*', '/' or '%'.");
    }

    void inout_stmt() {
        if (getToken().getSymbol().equals("input")) {
            nextToken();
            if (getToken().getSymbol().equals("(")) {
                nextToken();
                if (getToken().getId() == 100) {
                    if (userValidIdentifier(getToken().getSymbol())) {
                        nextToken();
                        if (getToken().getSymbol().equals(")")) {
                            nextToken();
                            return;
                        }
                        error("you have forgotten ')'.");
                        return;
                    }
                    error("user defined name must start with letter, must not contain special character");
                    return;
                }
                error("Not user defined name.");
                return;
            }
            error("you have forgotten '('.");
            return;
        }
        if (getToken().getSymbol().equals("output")) {
            nextToken();
            if (getToken().getSymbol().equals("(")) {
                nextToken();
                if (getToken().getId() == 100) {
                    name_value();
                    if (getToken().getSymbol().equals(")")) {
                        nextToken();
                        return;
                    }
                    error("you have forgotten ')'.");
                    return;
                }
                error("Not user defined name.");
                return;
            }
            error("you have forgotten '('.");
            return;
        }
        error("");
    }

    void if_stmt() {
        if (getToken().getSymbol().equals("if")) {
            nextToken();
            if (getToken().getSymbol().equals("(")) {
                nextToken();
                bool_exp();
                if (getToken().getSymbol().equals(")")) {
                    nextToken();
                    if (getToken().getSymbol().equals("then")) {
                        nextToken();
                        statement();
                        else_part();
                        if (getToken().getSymbol().equals("endif")) {
                            nextToken();
                            return;
                        }
                        error("you have forgotten 'endif' at end of if statement.");
                        return;
                    }
                    error("you have forgotten 'then'.");
                    return;
                }
                error("you have forgotten ')'.");
                return;
            }
            error("you have forgotten '('.");
            return;
        }
        error("you have forgotten 'if' at start.");
    }

    void else_part() {
        if (getToken().getSymbol().equals("else")) {
            nextToken();
            statement();
        }
    }

    void loop_stmt() {
        if (getToken().getSymbol().equals("loop")) {
            nextToken();
            if (getToken().getSymbol().equals("(")) {
                nextToken();
                bool_exp();
                if (getToken().getSymbol().equals(")")) {
                    nextToken();
                    if (getToken().getSymbol().equals("do")) {
                        nextToken();
                        statement();
                        return;
                    }
                    error("you have forgotten 'do'.");
                    return;
                }
                error("you have forgotten ')'.");
                return;
            }
            error("you have forgotten '('.");
            return;
        }
        error("you have forgotten 'loop' at start.");
    }

    void bool_exp() {
        name_value();
        relational_oper();
        name_value();
    }

    void relational_oper() {
        if (getToken().getSymbol().equals("=")){
            nextToken();
            return;
        }
        if (getToken().getSymbol().equals("<>")){
            nextToken();
            return;
        }
        if (getToken().getSymbol().equals("<")){
            nextToken();
            return;
        }
        if (getToken().getSymbol().equals("<=")){
            nextToken();
            return;
        }
        if (getToken().getSymbol().equals(">")){
            nextToken();
            return;
        }
        if (getToken().getSymbol().equals(">=")){
            nextToken();
            return;
        }
        error("you have forgotten Relational operator");
    }

    private Token getToken(){
        try {
            return tokenList.get(i);
        }catch (Exception e){
            System.err.print(" Error at line : "+tokenList.get(i-1).getLine() + " -- " + " Token : "+tokenList.get(i-1).getSymbol());
            System.exit(1);
        }
        return null;
    }

    private void nextToken() {
        i++;
    }

    void error(String errorMessage) {
        System.err.println(" Error at line : "+getToken().getLine() + " -- " + " Token : "+getToken().getSymbol());
        System.err.println(errorMessage);
        System.exit(1);
    }

    boolean userValidIdentifier(String name) {
        return name.matches("^[A-Za-z][A-Za-z0-9_]*");
    }
    private boolean integerValidIdentifier(String name) {
        return name.matches("^[0-9]*");
    }
}
