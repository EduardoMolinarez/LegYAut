package javaapplication2;

public class Compi {

    static int checkBlock(int p, Token tokens[]) throws Exception {

        int auxP = p;

        do {
            p = auxP;
            auxP = checkAsignacion(auxP, tokens);
            auxP = checkIF(auxP, tokens);
            auxP = checkAsignacionName(auxP, tokens);
            auxP = checkComentarioLinea(auxP, tokens);
            auxP = checkBreak(auxP, tokens);
            auxP = checkContinue(auxP, tokens);
            auxP = checkFOR(auxP, tokens);
            auxP = checkLoop(auxP, tokens);
            auxP = checkWhile(auxP, tokens);
            auxP = checkAsignacionx2(auxP, tokens);
            System.out.println(auxP);
        } while (auxP > p);

        return p;
    }

    static int checkBlockx2(int p, Token tokens[]) throws Exception {

        int auxP = p;
        do {
            p = auxP;
            auxP = checkAsignacionx2(auxP, tokens);

            System.out.println(auxP);
        } while (auxP > p);

        return p;
    }

    static int checkApp(int p, Token tokens[]) throws Exception {

        int auxP = p;
        do {
            p = auxP;
            auxP = checkStruct(auxP, tokens);
            auxP = checkFn(auxP, tokens);
//            auxP = checkBreak(auxP, tokens);
//            auxP = checkIF(auxP, tokens);
//            auxP = checkFOR(auxP, tokens);
            auxP = checkComentarioLinea(auxP, tokens);
//            auxP = checkLoop(auxP, tokens);
//            auxP = checkContinue(auxP, tokens);
//            auxP = checkWhile(auxP, tokens);
            System.out.println(auxP);
        } while (auxP > p);

        return p;
    }

    static int checkComentarioLinea(int p, Token tokens[]) throws Exception {
        if (tokens[p].tipo == TIPOS.DOBLE_DIAGONAL) {
            int linea = tokens[p].linea;
            p++;

            System.out.println(tokens.length);
            while (tokens[p].linea <= linea && tokens.length < p) {
                p++;
            }
            return p;
        } else {
            return p;
        }
    }

    static int checkAsignacion(int p, Token tokens[]) throws Exception {
        TIPOS a;
        if (tokens[p].tipo == TIPOS.DECLARADOR) {
            p++;
            if (tokens[p].tipo == TIPOS.NAMES) {
                p++;
                a = TIPOS.NAMES;
                if (tokens[p].tipo == TIPOS.EQUAL) {
                    p++;
                    int auxP = checkValue(p, tokens);

                    if (auxP <= p) {
                        throw new Exception("te falto un valor");
                    }
                    p = auxP;
                    System.out.println(a + "vale" + checkValue(p, tokens));
                    if (tokens[p].tipo == TIPOS.SEMICOLON) {
                        p++;
                    } else {
                        throw new Exception("te falto el ;");
                    }
                }

            } else {
                throw new Exception("te falto la variable");
            }
            return p;
        } else {
            return p;
        }
    }

    static int checkAsignacionx2(int p, Token tokens[]) throws Exception {
        if (tokens[p].tipo == TIPOS.NAMES) {
            p++;
            if (tokens[p].tipo == TIPOS.DOS_PUNTOS) {
                p++;

                int auxP = checkValuex2(p, tokens);

                if (auxP <= p) {
                    throw new Exception("te falto un valor");
                }
                p = auxP;
                if (tokens[p].tipo == TIPOS.SEMICOLON) {
                    p++;
                } else {
                    throw new Exception("te falto el ;");
                }
            }
            return p;
        } else {
            return p;
        }
    }

    public static void main(String[] args) {
        try {
            Token tokens[] = new Token[]{ //TIPOS.DECLARADOR,
            //                TIPOS.NAME, TIPOS.EQUAL,
            //                TIPOS.NUMBER, TIPOS.OP, TIPOS.NUMBER, TIPOS.OP, TIPOS.NUMBER, TIPOS.OP, TIPOS.NUMBER, TIPOS.OP,
            //                TIPOS.PARENTESIS_OPEN,
            //                TIPOS.PARENTESIS_OPEN,
            //                TIPOS.NUMBER, TIPOS.OP, TIPOS.NUMBER,
            //                TIPOS.OP, TIPOS.NAME, TIPOS.PUNTO, TIPOS.NAME, TIPOS.PARENTESIS_OPEN,
            //                TIPOS.NUMBER, TIPOS.COMMA, TIPOS.NUMBER, TIPOS.COMMA,
            //                TIPOS.NAME, TIPOS.PUNTO, TIPOS.NAME, TIPOS.PARENTESIS_OPEN,
            //                TIPOS.NUMBER, TIPOS.COMMA,
            //                TIPOS.PARENTESIS_CLOSE,
            //                TIPOS.PARENTESIS_CLOSE,
            //                TIPOS.PARENTESIS_CLOSE,
            //                TIPOS.PARENTESIS_CLOSE, TIPOS.SEMICOLON
            //            TIPOS.IF, TIPOS.PARENTESIS_OPEN,TIPOS.BOOL,TIPOS.PARENTESIS_CLOSE,
            //                TIPOS.LLAVE_OPEN,
            //                TIPOS.IF, TIPOS.PARENTESIS_OPEN,TIPOS.BOOL,TIPOS.PARENTESIS_CLOSE,
            //                TIPOS.LLAVE_OPEN,TIPOS.LLAVE_CLOSE,
            //                TIPOS.LLAVE_CLOSE
            };

            int p = checkStruct(0, tokens);
            System.out.println(p);
            if (p == tokens.length) {
                System.out.println("Salvaste la materia, ¡Se compilo bien!");
            } else {
                System.out.println("no se hizo una declaracion");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void compilar(Token[] tokens) {

        try {
            int p = checkApp(0, tokens);
            System.out.println(p);
            if (p == tokens.length - 1) {
                if (tokens[p].tipo == TIPOS.EOF) {
                    System.out.println("compilo bien");
                } else {

                    System.out.println("no llegamos al final del archivo");
                }
            } else {
                System.out.println("no se hizo una declaracion");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static int checkParams(int p, Token tokens[]) throws Exception {
        int auxP = checkValue(p, tokens);
        if (auxP > p) {
            p = auxP;
            if (tokens[p].tipo == TIPOS.COMMA) {
                p++;
                auxP = checkParams(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto un valor despues de la coma");
                }
                p = auxP;
            }
        }
        return auxP;
    }

    static int checkParamsFn(int p, Token tokens[]) throws Exception {
        System.out.println(tokens[p].tipo);
        if (tokens[p].tipo == TIPOS.NAMES) {
            p++;
            if (tokens[p].tipo == TIPOS.DOS_PUNTOS) {
                p++;
                if (tokens[p].tipo == TIPOS.TIPO) {
                    p++;
                    if (tokens[p].tipo == TIPOS.COMA) {
                        p++;
                        int auxP = checkParamsFn(p, tokens);
                        if (auxP > p) {
                            p = auxP;
                            return p;
                        } else {
                            throw new Exception("falto completar el parametro");
                        }
                    } else {
                        return p;
                    }
                } else {
                    throw new Exception("te falto poner tipo");
                }
            } else {
                throw new Exception("te falto poner dos puntos");
            }
        } else {
            return p;
        }
    }

    static int checkMethodsCalls(int p, Token tokens[]) throws Exception {
        if (tokens[p].tipo == TIPOS.NAMES) {
            p++;
            if (tokens[p].tipo == TIPOS.PUNTO) {
                p++;
                int auxP = checkMethodsCalls(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto un valor despues del punto");
                }
                p = auxP;
            } else if (tokens[p].tipo == TIPOS.PARENTESIS_OPEN) {
                p++;
                p = checkParams(p, tokens);
                if (tokens[p].tipo == TIPOS.PARENTESIS_CLOSE) {
                    p++;
                    if (tokens[p].tipo == TIPOS.PUNTO) {
                        p++;
                        int auxP = checkMethodsCalls(p, tokens);
                        if (auxP <= p) {
                            throw new Exception("te falto un valor despues del punto");
                        }
                        p = auxP;
                    }
                } else {
                    throw new Exception("te falto cerrar el parentesis");
                }
            }
            return p;
        } else {
            return p;
        }
    }

    static int checkWhile(int p, Token tokens[]) throws Exception {
        if (tokens[p].tipo == TIPOS.WHILE) {
            p++;
            if (tokens[p].tipo == TIPOS.PARENTESIS_OPEN) {
                p++;
                System.out.println("aaaaaa-" + tokens[p] + "-aaaaaa");
                int auxP = checkValue(p, tokens);
                if (auxP > p) {
                    p = auxP;
                    if (tokens[p].tipo == TIPOS.PARENTESIS_CLOSE) {
                        p++;
                        if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
                            p++;
                            p = checkBlock(p, tokens);
                            if (tokens[p].tipo == TIPOS.LLAVE_CLOSE) {
                                p++;
                            } else {
                                throw new Exception("te falto cerrar llave L:" + tokens[p].linea);
                            }
                        } else {
                            throw new Exception("te falto abrir llave L:" + tokens[p].linea);
                        }
                    } else {
                        throw new Exception("te falto cerrar el parentesis L:" + tokens[p].linea);
                    }
                } else {
                    throw new Exception("te falto valor dentro del parentesis");
                }
            } else {
                throw new Exception("te falto abrir parentesis");
            }
            return p;
        } else {
            return p;
        }
    }

    private static int checkValue(int p, Token[] tokens) throws Exception {
        int auxP;
        Token t = tokens[p];

        switch (t.tipo) {

            case NAMES:
                auxP = checkMethodsCalls(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto una variable");
                }
                p = auxP;
                p--;
            case BOOL:
            case STRING:
            case DECLARADOR:
            case NUMBER:
            case ENTERO:
            case FLOAT:
                p++;
                if (tokens[p].tipo == TIPOS.OPERADOR) {
                    p++;
                    auxP = checkValue(p, tokens);
                    if (auxP <= p) {
                        throw new Exception("te falto un valor despues del operador");
                    }
                    p = auxP;
                } else if (tokens[p].tipo == TIPOS.PARENTESIS_OPEN) {
                    p++;
                    auxP = checkValue(p, tokens);
                    if (auxP <= p) {
                        throw new Exception("te falto un valor despues del parentesis");
                    }
                    p = auxP;
                    if (tokens[p].tipo != TIPOS.PARENTESIS_CLOSE) {
                        throw new Exception("te falto cerrar el parentesis");
                    }
                    p++;
                }
                break;
            case PARENTESIS_OPEN:
                p++;
                auxP = checkValue(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto un valor despues del parentesis");
                }
                p = auxP;
                if (tokens[p].tipo != TIPOS.PARENTESIS_CLOSE) {
                    throw new Exception("te falto cerrar el parentesis");
                }
                p++;
                break;
        }
        return p;
    }

    private static int checkValuex2(int p, Token[] tokens) throws Exception {
        int auxP;
        Token t = tokens[p];

        switch (t.tipo) {

            case NAMES:
            case BOOL:
            case STRING:
            case ENTERO:
            case DECLARADOR:
                p++;
                break;
            case TIPO:
                p++;
                break;
            case FLOAT:

            case PARENTESIS_OPEN:

        }
        return p;
    }

    static int checkAsignacionName(int p, Token tokens[]) throws Exception {
        TIPOS a;
        if (tokens[p].tipo == TIPOS.NAMES) {
            p++;
            if (tokens[p].tipo == TIPOS.EQUAL) {
                p++;
                int auxP = checkValue(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto un valor");
                }
                p = auxP;
                a = TIPOS.NAMES;
                System.out.println(a + "vale" + checkValue(p, tokens));
                if (tokens[p].tipo == TIPOS.SEMICOLON) {
                    p++;
                } else {
                    throw new Exception("te falto el ;");
                }
            } else {
                throw new Exception("te falto un =");
            }
            return p;
        } else {
            return p;
        }
    }

    static void checkEOF(int p, Token tokens[], String message) throws Exception {
        if (p == tokens.length) {
            throw new Exception(message);
        }
    }

    static int checkIF(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.IF) {
            p++;
            checkEOF(p, tokens, "te falto abrir parentesis en el if");
            if (tokens[p].tipo == TIPOS.PARENTESIS_OPEN) {
                p++;
                auxP = checkValue(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto poner valores en los parentesis");
                }
                p = auxP;
                if (tokens[p].tipo != TIPOS.PARENTESIS_CLOSE) {
                    throw new Exception("falto cerrar el parentesis");
                }
                p++;
                if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
                    p++;
                    p = checkBlock(p, tokens);
                    if (tokens[p].tipo != TIPOS.LLAVE_CLOSE) {
                        throw new Exception("te falto cerrar la llave if");
                    }
                    p++;
                } else {
                    throw new Exception("te falto abrir la llave if");
                }
            } else {
                throw new Exception("te falto abrir la (");
            }
        }
        return p;
    }

    static int checkStruct(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.STRUCT) {
            p++;
            if (tokens[p].tipo == TIPOS.NAMES) {
                p++;
                if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
                    p++;
                    p = checkParamsFn(p, tokens);
                    if (tokens[p].tipo != TIPOS.LLAVE_CLOSE) {
                        throw new Exception("te falto cerrar la llave");
                    }
                    p++;
                } else {
                    throw new Exception("te falto una llave");
                }
            } else {
                throw new Exception("falto nombre");
            }
        }
        return p;
    }

    static int checkFn(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.FN) {
            p++;
            if (tokens[p].tipo == TIPOS.NAMES) {
                p++;
                if (tokens[p].tipo == TIPOS.PARENTESIS_OPEN) {
                    p++;
                    p= checkParamsFn(p, tokens);
                 
                    if (tokens[p].tipo != TIPOS.PARENTESIS_CLOSE) {
                        throw new Exception("falto cerrar el parentesis P@");
                    }
                    p++;
                    if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
                        p++;
                        p = checkBlock(p, tokens);
                        if (tokens[p].tipo != TIPOS.LLAVE_CLOSE) {
                            throw new Exception("te falto cerrar la llave");
                        }
                        p++;
                    } else {
                        throw new Exception("te falto abrir la llave");
                    }
                } else {
                    throw new Exception("te falto abrir la (");
                }

            } else {
                throw new Exception("te falto el nombre");
            }
        }
        return p;
    }

    static int checkLoop(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.LOOP) {
            p++;
            if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
                p++;
                p = checkBlock(p, tokens);
                p = checkWhile(p, tokens);
                if (tokens[p].tipo != TIPOS.LLAVE_CLOSE) {
                    throw new Exception("te falto cerrar la llave");
                }
                p++;
            } else {
                throw new Exception("te falto abrir una llave");
            }
        }
        return p;
    }

    static int checkBreak(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.BREAK) {
            p++;
            if (tokens[p].tipo == TIPOS.SEMICOLON) {
                p++;
                return p;
            } else {
                throw new Exception("te falto el semicolon");
            }
        }
        return p;
    }

    static int checkContinue(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.CONTINUE) {
            p++;
            if (tokens[p].tipo == TIPOS.SEMICOLON) {
                p++;
                return p;
            } else {
                throw new Exception("te falto el semicolon");
            }
        }
        return p;
    }

    static int checkFOR(int p, Token tokens[]) throws Exception {
        int auxP;
        if (tokens[p].tipo == TIPOS.FOR) {
            p++;
            if (tokens[p].tipo == TIPOS.NAMES) {
                p++;

                if (tokens[p].tipo == TIPOS.IN) {
                    p++;

                    auxP = checkValue(p, tokens);
                    if (auxP > p) {
                        p = auxP;
                        if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
                            p++;
                            p = checkBlock(p, tokens);

                            if (tokens[p].tipo != TIPOS.LLAVE_CLOSE) {

                                throw new Exception("te falto cerrar la llave");
                            }
                            p++;
                        } else {
                            throw new Exception("te falto abrir la llave");
                        }
                    } else {
                        throw new Exception("te falto un valor en el for");
                    }

//                    if (tokens[p].tipo == TIPOS.ENTERO) {
//                        p++;
//
//                        if (tokens[p].tipo == TIPOS.PUNTO) {
//                            p++;
//
//                            if (tokens[p].tipo == TIPOS.PUNTO) {
//                                p++;
//
//                                if (tokens[p].tipo == TIPOS.ENTERO) {
//                                    p++;
//
//                                    if (tokens[p].tipo == TIPOS.LLAVE_OPEN) {
//                                        p++;
//                                        p = checkBlock(p, tokens);
//
//                                        if (tokens[p].tipo != TIPOS.LLAVE_CLOSE) {
//
//                                            throw new Exception("Alto ahi loca, te falto cerrar la llave");
//                                        }
//                                        p++;
//                                    } else {
//                                        throw new Exception("Alto ahi loca, te falto abrir la llave");
//                                    }
//
//                                } else {
//                                    throw new Exception("Alto ahi loca, te falto el numero");
//                                }
//                            } else {
//                                throw new Exception("Alto ahi loca, te falto el punto");
//                            }
//
//                        } else {
//                            throw new Exception("Alto ahi loca, te falto el punto");
//                        }
//
//                    } else {
//                        throw new Exception("Alto ahi loca, te falto el numero");
//                    }
                } else {

                    throw new Exception("falto poner el in");
                }

            } else {
                throw new Exception("declara la perra variable");
            }
        }
        return p;
    }
    
    static int checkAsignacionValor(int p, Token tokens[]) throws Exception {
        TIPOS a;
        if (tokens[p].tipo == TIPOS.NAMES) {
            p++;
            if (tokens[p].tipo == TIPOS.EQUAL) {
                p++;
                int auxP = checkValue(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto un valor");
                }
                p = auxP;
                a = TIPOS.NAMES;
                System.out.println(a + "vale" + checkValue(p, tokens));
                if (tokens[p].tipo == TIPOS.SEMICOLON) {
                    p++;
                } else {
                    throw new Exception("te falto el ;");
                }
            } else {
                throw new Exception("te falto un =");
            }
            return p;
        } else {
            return p;
        }
    }
    
    static int checkValorOperacion(int p, Token tokens[]) throws Exception {
        if (tokens[p].tipo == TIPOS.NAMES) {
            p++;
            if (tokens[p].tipo == TIPOS.EQUAL) {
                p++;
                int auxP = checkValue(p, tokens);
                if (auxP <= p) {
                    throw new Exception("te falto un valor");
                }
                p = auxP;
                if (tokens[p].tipo == TIPOS.SEMICOLON) {
                    p++;
                } else {
                    throw new Exception("te falto el ;");
                }
            } else {
                throw new Exception("te falto un =");
            }
            return p;
        } else {
            return p;
        }
    }

}
