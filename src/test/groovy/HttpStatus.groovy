enum HttpStatus {
    INVALID_REQUEST (422),
    NOT_FOUND (404),
    SERVER_ERROR (500),
    OK (200)

    private final int status;

    HttpStatus(int status) {
        this.status = status;
    }

    int value() {
        return this.status;
    }
}
