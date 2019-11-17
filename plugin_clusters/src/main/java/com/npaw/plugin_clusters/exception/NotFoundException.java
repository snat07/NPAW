package com.npaw.plugin_clusters.exception;

public class NotFoundException extends Exception {
    String message;

    public NotFoundException(String msg) {
        message = msg;
    }

    public String toString() {
        return message;
    }
}
