import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Highlighter {
    private static final Map<TokenType, String> COLORS = Map.of(
            TokenType.KEYWORD, "#FF0000",
            TokenType.IDENTIFIER, "#FF0FD5",
            TokenType.NUMBER, "#0000FF",
            TokenType.OPERATOR, "#FFA500",
            TokenType.SEPARATOR, "#800080",
            TokenType.STRING, "#008000",
            TokenType.COMMENT, "#808080",
            TokenType.UNKNOWN, "#000000"
    );

    public static void main(String[] args) throws IOException {
        String inputFile = "program.txt";
        String outputFile = "output.html";


        List<String> lines = Files.readAllLines(Paths.get(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        Lexer lexer = new Lexer();

        writer.write("<html><body><pre>\n");

        for(String line : lines) {
            List<Token> tokens = lexer.tokenize(line);
            for(Token token : tokens) {
                String color = COLORS.get(token.getTokenType());
                writer.write("<span style=\"color:" + color + ";\">" + escapeHTML(token.getValue()) + "</span>");
            }
            writer.write("\n");
        }

        writer.write("</pre></body></html>");
        writer.close();
        System.out.println("Kolorowanie zakończone. Wynik zapisano w " + outputFile);
    }

    private static String escapeHTML(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }

}
