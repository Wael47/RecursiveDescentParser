public class Token {

    private int id ;
    private String symbol;

    private int line;

    public Token(int id, String symbol, int line){
        this.id = id;
        this.symbol = symbol;
        this.line = line;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLine() {
        return line;
    }

    public void setLine(int line) {
        this.line = line;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "Token{" +
                "id=" + id +
                ", symbol='" + symbol + '\'' +
                ", line=" + line +
                '}';
    }
}
