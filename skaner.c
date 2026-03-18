#include <ctype.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>

typedef enum {
	END = 0,    // Koniec wejścia
	INT = 1,    // Liczba całkowita
	VARIABLE = 2,   // Zmienna (pojedyncza litera)
	POWER = 3,    // Potęgowanie
	ADD = 4,      // Dodawanie
	SUBTRACT = 5, // Odejmowanie
	MULTIPLY = 6, // Mnożenie
	DIVIDE = 7,   // Dzielenie
	LP = 8,       // Lewy nawias
	RP = 9,       // Prawy nawias
	ERROR = -1  // Błąd skanera
} TokenType;

typedef struct {
	TokenType type;
	char sign[128];
	char error_message[128];
	size_t column;
} Token;

static Token scan_token(const char *input, size_t *index, size_t *column) {
	Token token = {.type = ERROR, .sign = "", .error_message = "", .column = *column};

	while (input[*index] != '\0' && isspace((unsigned char)input[*index])) {
		(*index)++;
		(*column)++;
	}
	token.column = *column;

	char ch = input[*index];
    
	if (ch == '\0' || ch == '\n') {
		token.type = END;
		strcpy(token.sign, "<END>");
		return token;
	}

	if (isdigit((unsigned char)ch)) {
		size_t start = *index;
		while (isdigit((unsigned char)input[*index])) {
			(*index)++;
			(*column)++;
		}
		size_t length = *index - start;
		memcpy(token.sign, input + start, length);
		token.sign[length] = '\0';
		token.type = INT;
		return token;
	}

	if (isalpha((unsigned char)ch)) {
		token.sign[0] = ch;
		token.sign[1] = '\0';
		token.type = VARIABLE;
		(*index)++;
		(*column)++;
		return token;
	}

	(*index)++;
	(*column)++;

	switch (ch) {
		case '^':
			token.type = POWER;
			strcpy(token.sign, "^");
			break;
		case '+':
			token.type = ADD;
			strcpy(token.sign, "+");
			break;
		case '-':
			token.type = SUBTRACT;
			strcpy(token.sign, "-");
			break;
		case '*':
			token.type = MULTIPLY;
			strcpy(token.sign, "*");
			break;
		case '/':
			token.type = DIVIDE;
			strcpy(token.sign, "/");
			break;
		case '(':
			token.type = LP;
			strcpy(token.sign, "(");
			break;
		case ')':
			token.type = RP;
			strcpy(token.sign, ")");
			break;
		default:
			token.type = ERROR;
			snprintf(token.error_message, sizeof(token.error_message),
					 "Nie ma takiego symbolu: '%c'", ch);
			break;
	}

	return token;
}

int main(void) {
	char buffer[128];
	size_t index = 0;
	size_t column = 1;

	while (true) {
		Token token = scan_token(buffer, &index, &column);

		if (token.type == ERROR) {
			fprintf(stderr, "Błąd skanera w kolumnie %zu: %s\n", token.column, token.error_message[0] ? token.error_message : "Niepoprawny token");
			return 0;
		}


		if (token.type == END) {
			printf("Token: %d, Wartosc wyrazenia:%s\n", token.type, token.sign);
			break;
		}
		printf("Token: %d, Wartosc wyrazenia:%s\n", token.type, token.sign);
	}

	return 1;
}
