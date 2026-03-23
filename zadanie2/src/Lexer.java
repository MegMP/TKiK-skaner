import java.sql.Types;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.*;
import java.util.regex.*;

public class Lexer {
    private static final String KEYWORDS_REGEX = "\\b(if|else|while|for|return)\\b";
    private static final String IDENTIFIERS_REGEX = "\\b[a-zA-Z_][a-zA-Z0-9_]*\\b";
    private static final String NUMBERS_REGEX = "\\b\\d+(\\.\\d+)?\\b";
    private static final String OPERATORS_REGEX = "[+\\-*/=<>!]+";
    private static final String SEPARATORS_REGEX = "[;:,(){}\\[\\]]";
    private static final String STRINGS_REGEX = "\"(\\\\.|[^\"])*\"";
    private static final String COMMENTS_REGEX = "//.*";

    private static final List<Pattern> PATTERNS = Arrays.asList(
            Pattern.compile(COMMENTS_REGEX),
            Pattern.compile(STRINGS_REGEX),
            Pattern.compile(KEYWORDS_REGEX),
            Pattern.compile(NUMBERS_REGEX),
            Pattern.compile(OPERATORS_REGEX),
            Pattern.compile(SEPARATORS_REGEX),
            Pattern.compile(IDENTIFIERS_REGEX)
    );

    private static final List<TokenType> TYPES = Arrays.asList(
            TokenType.COMMENT,
            TokenType.STRING,
            TokenType.KEYWORD,
            TokenType.NUMBER,
            TokenType.OPERATOR,
            TokenType.SEPARATOR,
            TokenType.IDENTIFIER
    );

    public List<Token> tokenize(String line) {
        List<Token> tokens = new ArrayList<>();
        int index = 0;
        while(index < line.length()) {
            boolean matched = false;
            String remaining = line.substring(index);

            for(int i = 0; i < PATTERNS.size(); i++) {
                Matcher matcher = PATTERNS.get(i).matcher(remaining);
                if(matcher.find() && matcher.start() == 0) {
                    String value = matcher.group();
                    tokens.add(new Token(TYPES.get(i), value));
                    index += value.length();
                    matched = true;
                    break;
                }
            }

            if(!matched) {
                tokens.add(new Token(TokenType.UNKNOWN, remaining.substring(0,1)));
                index++;
            }
        }
        return tokens;
    }


}
