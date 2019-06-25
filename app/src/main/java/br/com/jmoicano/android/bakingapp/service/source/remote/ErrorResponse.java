package br.com.jmoicano.android.bakingapp.service.source.remote;

public class ErrorResponse {
    private final int statusCode;
    private final String statusMessage;

    public ErrorResponse(int statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusMessage() {
        return statusMessage;
    }
}
