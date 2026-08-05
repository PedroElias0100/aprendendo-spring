package com.pedro.aprendendospring.infrastructure.exceptions;

public class ConflictExxception extends RuntimeException{

    public ConflictExxception(String mensagem) {
        super(mensagem);
    }

    public ConflictExxception(String mensagem, Throwable throwable) {
        super(mensagem);
    }
}
