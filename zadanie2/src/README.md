# Skaner i kolorowanie składni

## Opis

Program w Javie realizuje prosty skaner (lexer), który rozpoznaje tokeny w tekście i generuje pokolorowany kod w formacie HTML.

## Funkcjonalność

* rozpoznawanie tokenów (keyword, identifier, number, operator, separator, string, comment)
* kolorowanie składni
* zapis wyniku do pliku HTML z zachowaniem układu tekstu

## Pliki

* `TokenType.java` – typy tokenów
* `Token.java` – reprezentacja tokenu
* `Lexer.java` – skaner
* `Highlighter.java` – generowanie HTML

## Uruchomienie

Uruchomić klasę `Highlighter`.
Program wczytuje `program.txt` i zapisuje wynik do `output.html`.
