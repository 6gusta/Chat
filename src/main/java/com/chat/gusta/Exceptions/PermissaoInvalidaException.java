package com.chat.gusta.Exceptions;

public class PermissaoInvalidaException extends RuntimeException {
    public PermissaoInvalidaException(String message) {
        super(message);
    }

    public static class SenhaIncorretaException extends RuntimeException {
        public SenhaIncorretaException(String message) {
            super(message);
        }
    }

    public static class UsuarioNaoEncontradoException extends RuntimeException {
        public UsuarioNaoEncontradoException(String msg) {
            super(msg);
        }
    }
}

