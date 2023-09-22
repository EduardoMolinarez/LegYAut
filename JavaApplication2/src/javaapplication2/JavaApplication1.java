package javaapplication2;

import java.util.ArrayList;
import java.util.List;

enum TIPOS {
    STRUCT, DOBLE_DIAGONAL, NUMBER, OPERADOR, STRING, NAMES, ENTERO, CARACTER_ESPECIAL, FLOAT, PALABRA_RESERVADA, IF, PARENTESIS_OPEN, PARENTESIS_CLOSE, LLAVE_OPEN, LLAVE_CLOSE, SEMICOLON, EQUAL, NAME, DECLARADOR, BOOL, COMMA, DOS_PUNTOS, PUNTO, FOR, TIPO, WHILE
    , COMA ,FN , MENOR , LOOP , BREAK , CONTINUE , IN, EOF
}

class Token {

    String valor;
    public TIPOS tipo;
    public int linea;

    public Token(String valor, TIPOS tipo, int linea) {
        this.valor = valor;
        this.tipo = tipo;
        this.linea = linea;
    }

    @Override
    public String toString() {
        return "[" + this.tipo + ": " + this.linea + "] - " + this.valor;
    }
    
}

public class JavaApplication1 {

    static int linea_actual = 1;
    

    static boolean palabrasReservadasTipos(String palabra) {    
        String reservadas[] = new String[]{"i8", "i16", "i32", "u32", "u64", "f64"};
        boolean es_reservada = false;

        for (String reservada : reservadas) {
            if (reservada.equals(palabra)) {
                es_reservada = true;
            }
        }
        String tipos[] = new String[]{};

        return es_reservada;
    }
    

    static boolean palabrasReservadas(String palabra) {
        String reservadas[] = new String[]{"fn", "main", "println", "let", "mut", "counter", "for", "loop", "const", "match",
            "if", "else", "break", "as", "bool", "array", "enum", "extern=", "PI","continue","in" /*        "false","impl","in","match","mod","move","pub","ref","return","Self","self","static",
        "super","trait","true","type","union","unsafe","use","where","abstract","become","box","do","final",
        "macro","override","priv","try","typeof","unsized","virtual","yield"*/};
        boolean es_reservada = false;
        for (String reservada : reservadas) {
            if (reservada.equals(palabra)) {
                es_reservada = true;
            }
        }

        return es_reservada;
    }

    /*--------------------------------------------------------------------------------------------------*/
    static int checkNames(String input, int p, List<Token> tokens) {
        String palabra = "";
        char caracter = input.charAt(p);
        if ("_ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".contains((caracter + "").toUpperCase())) {
            palabra = palabra + caracter;
            p++;
            caracter = input.charAt(p);
            while ("_ABCDEFGHIJKLMNÑOPQRSTUVWXYZ0123456789".contains((caracter + "").toUpperCase())) {
                palabra = palabra + caracter;
                p++;
                if (p == input.length()) {
                    break;
                } else {
                    caracter = input.charAt(p);
                }
            }
        }
        if (palabra.length() > 0) {
            boolean es_reservada = palabrasReservadas(palabra);
            if (es_reservada) {
                if (palabra.equals("if")) {
                    tokens.add(new Token(palabra, TIPOS.IF, linea_actual));
                } else if (palabra.equals("for")) {
                    tokens.add(new Token(palabra, TIPOS.FOR, linea_actual));
                }else if (palabra.equals("fn")) {
                    tokens.add(new Token(palabra, TIPOS.FN, linea_actual));
                } else if (palabra.equals("break")) {
                    tokens.add(new Token(palabra, TIPOS.BREAK, linea_actual));
                }else if (palabra.equals("continue")) {
                    tokens.add(new Token(palabra, TIPOS.CONTINUE, linea_actual));
                }else if (palabra.equals("loop")) {
                    tokens.add(new Token(palabra, TIPOS.LOOP, linea_actual));
                } else if (palabra.equals("in")) {
                    tokens.add(new Token(palabra, TIPOS.IN, linea_actual));
                } else if (palabra.equals("let")) {
                    tokens.add(new Token(palabra, TIPOS.DECLARADOR, linea_actual));
                } else if (palabra.equals("const")) {
                    tokens.add(new Token(palabra, TIPOS.DECLARADOR, linea_actual));
                }else {
                    tokens.add(new Token(palabra, TIPOS.PALABRA_RESERVADA, linea_actual));
                }

            } else if (palabrasReservadasTipos(palabra)) {
                tokens.add(new Token(palabra, TIPOS.TIPO, linea_actual));
            } else if (palabra.equals("while")) {
                tokens.add(new Token(palabra, TIPOS.WHILE, linea_actual));
            } else if (palabra.equals("struct")) {
                    tokens.add(new Token(palabra, TIPOS.STRUCT, linea_actual));
            }
             else if (palabra.equals("String")) {
                    tokens.add(new Token(palabra, TIPOS.DECLARADOR, linea_actual));
            }
              else if (palabra.equals("Int")) {
                    tokens.add(new Token(palabra, TIPOS.DECLARADOR, linea_actual));
            }else if (palabra.equals("Number")) {
                    tokens.add(new Token(palabra, TIPOS.DECLARADOR, linea_actual));
            }else {
                tokens.add(new Token(palabra, TIPOS.NAMES, linea_actual));
            }

        }
        return p;
    }

    /*--------------------------------------------------------------------------------------------------*/
    static int checkOperadores(String input, int p, List<Token> tokens) {
        String palabra = "";
        char caracter = input.charAt(p);

        switch (caracter) {
            case '+':
            case '-':
            case '*':
            case '/':
                palabra = palabra + caracter;
                p++; 
                caracter = input.charAt(p);
                if(caracter == '/') {
                    p++;
                    palabra = palabra + caracter;
                    tokens.add(new Token(palabra, TIPOS.DOBLE_DIAGONAL, linea_actual));
                    return p;
                }
                break;
                
         
            case '%':
                palabra = palabra + caracter;
                p++;
                break;
            case '=':
                    p++;
                    palabra = palabra + caracter;
                    tokens.add(new Token(palabra, TIPOS.EQUAL, linea_actual));
                    return p;
            case '>':
            case '<':
            case '!':
                palabra = palabra + caracter;
                p++;
                if (p == input.length()) {
                    break;
                }
                caracter = input.charAt(p);
                if (caracter == '=') {
                    palabra = palabra + caracter;
                    p++;
                }
                break;
        }
        if (palabra.equals("-")) {
            tokens.add(new Token(palabra, TIPOS.MENOR, linea_actual));

        }else if (palabra.length() > 0) {
            tokens.add(new Token(palabra, TIPOS.OPERADOR, linea_actual));
        }
        return p;

    }

    /*--------------------------------------------------------------------------------------------------*/
    static int checkEspeciales(String input, int p, List<Token> tokens) {
        String palabra = "";
        char caracter = input.charAt(p);

        switch (caracter) {
            case ':':
                tokens.add(new Token(palabra, TIPOS.DOS_PUNTOS, linea_actual));
                p++;
                return p;
            case ';':
            case '#':
            case '.':
            case ',':
            case '(':
            case ')':
            case '{':
            case '}':
            case '[':
            case ']':
            case '|':
                palabra = palabra + caracter;
                p++;
        }

        if (palabra.equals("(")) {
            tokens.add(new Token(palabra, TIPOS.PARENTESIS_OPEN, linea_actual));

        } else if (palabra.equals(")")) {
            tokens.add(new Token(palabra, TIPOS.PARENTESIS_CLOSE, linea_actual));

        } else if (palabra.equals("{")) {
            tokens.add(new Token(palabra, TIPOS.LLAVE_OPEN, linea_actual));

        } else if (palabra.equals("}")) {
            tokens.add(new Token(palabra, TIPOS.LLAVE_CLOSE, linea_actual));

        }  else if (palabra.equals(";")) {
            tokens.add(new Token(palabra, TIPOS.SEMICOLON, linea_actual));

        }  else if (palabra.equals(".")) {
            tokens.add(new Token(palabra, TIPOS.PUNTO, linea_actual));

        }  else if (palabra.equals(",")) {
            tokens.add(new Token(palabra, TIPOS.COMA, linea_actual));

        }else if (palabra.length() > 0) {
            tokens.add(new Token(palabra, TIPOS.CARACTER_ESPECIAL, linea_actual));
        }
        return p;

    }

    /*--------------------------------------------------------------------------------------------------*/
    public static List<Token> compilar(String input) {
        linea_actual=1;
        int p = 0;
        List<Token> tokens = new ArrayList<>();
        List<String> errores = new ArrayList<>();
        String palabra = "";
        while (p < input.length()) {
            char caracter = input.charAt(p);
            if (caracter == '"') {
                p = checkString(input, p, tokens, errores);
            } else if (caracter == '+' || caracter == '-' || caracter == '*' || caracter == '/'
                    || caracter == '=' || caracter == '!' || caracter == '>' || caracter == '<' || caracter == '%') {
                p = checkOperadores(input, p, tokens);

            } else if (caracter == ':' || caracter == ';' || caracter == '.' || caracter == ','
                    || caracter == '#' || caracter == '{' || caracter == '}'
                    || caracter == '[' || caracter == ']' || caracter == '(' || caracter == ')'
                    || caracter == '|') {
                p = checkEspeciales(input, p, tokens);
            } else if ("_ABCDEFGHIJKLMNÑOPQRSTUVWXYZ".contains((caracter + "").toUpperCase())) {
                p = checkNames(input, p, tokens);
            } else if ("0123456789".contains((caracter + "").toUpperCase())) {
                p = checkNumbers(input, p, tokens);
            } else if (" ".contains((caracter + "").toUpperCase())) {
                p++;
            } else if ("\n".contains((caracter + "").toUpperCase())) {
                p++;
                linea_actual++;
                
            } else if ("0123456789".contains((caracter + "").toUpperCase())) {
                p = checkNumbers(input, p, tokens);
            } else {
                errores.add("Caracter invalido: " + caracter);
                p++;
            }
        }
        for (Token next : tokens) {
            System.out.println(next);
        }

        for (String next : errores) {
            System.out.println(next);
        }
        tokens.add(new Token(palabra, TIPOS.EOF, linea_actual));

        return tokens;
    }

    public static void main(String[] args) {
        Lexico lexico = new Lexico();
        lexico.main(args);
    }

    /*--------------------------------------------------------------------------------------------------*/
    private static int checkString(String input, int p, List<Token> tokens, List<String> errores) {
        String palabra = "";
        char caracter = input.charAt(p);
        if (caracter == '"') {
            palabra = palabra + caracter;
            while (true) {
                p++;
                if (p == input.length()) {
                    errores.add("No cerraste la ultima comilla");
                    return p;
                } else {
                    caracter = input.charAt(p);
                    if (caracter == '"') {
                        palabra = palabra + caracter;
                        p++;
                        break;
                    } else {
                        palabra = palabra + caracter;
                    }

                }
            }
            if (palabra.length() > 0) {
                tokens.add(new Token(palabra, TIPOS.STRING, linea_actual));
            }
            return p;
        } else {
            return p;
        }
    }

    /*--------------------------------------------------------------------------------------------------*/
    private static int checkNumbers(String input, int p, List<Token> tokens) {
        String palabra = "";
        char caracter = input.charAt(p);
        if ("0123456789".contains((caracter + "").toUpperCase())) {
            palabra = palabra + caracter;
            p++;
            while (true) {
                if (p == input.length()) {
                    break;
                } else {
                    caracter = input.charAt(p);
                    if ("0123456789".contains((caracter + "").toUpperCase())) {
                        palabra = palabra + caracter;
                        p++;
                    } else if (caracter == '.') {
                        palabra = palabra + caracter;
                        p++;
                        
                        if (p == input.length()) {
                            tokens.add(new Token(palabra.substring(0, palabra.length() - 1), TIPOS.ENTERO, linea_actual));
                            tokens.add(new Token(palabra.substring(palabra.length() - 1), TIPOS.CARACTER_ESPECIAL, linea_actual));
                            return p + 1;
                        } else {
                            caracter = input.charAt(p);
                            if ("0123456789".contains((caracter + "").toUpperCase())) {
                                palabra = palabra + caracter;
                                p++;

                                while (true) {
                                    if (p == input.length()) {
                                        tokens.add(new Token(palabra, TIPOS.FLOAT, linea_actual));
                                        return p;
                                    } else {
                                        caracter = input.charAt(p);
                                        if ("0123456789".contains((caracter + "").toUpperCase())) {
                                            palabra = palabra + caracter;
                                            p++;
                                        } else {
                                            tokens.add(new Token(palabra, TIPOS.FLOAT, linea_actual));
                                            return p;
                                        }
                                    }
                                }

                            } else {
                                caracter = input.charAt(p);
                                if (caracter == '.') {
                                    tokens.add(new Token(palabra.substring(0, palabra.length() - 1), TIPOS.ENTERO, linea_actual));
                                    tokens.add(new Token(palabra.substring(palabra.length() - 1), TIPOS.CARACTER_ESPECIAL, linea_actual));
                                    palabra = palabra + caracter;
                                    p++;
                                    tokens.add(new Token(caracter + "", TIPOS.CARACTER_ESPECIAL, linea_actual));

                                    return p;

                                }
                                System.out.println(palabra);
                                tokens.add(new Token(palabra.substring(0, palabra.length() - 1), TIPOS.ENTERO, linea_actual));
                                tokens.add(new Token(palabra.substring(palabra.length() - 1), TIPOS.CARACTER_ESPECIAL, linea_actual));
                                return p;
                            }
                        }

                    } else {
                        break;
                    }
                }
            }
            if (palabra.length() > 0) {
                tokens.add(new Token(palabra, TIPOS.ENTERO, linea_actual));
                return p;
            }
        }
        return p;
    }
}
/*--------------------------------------------------------------------------------------------------*/
