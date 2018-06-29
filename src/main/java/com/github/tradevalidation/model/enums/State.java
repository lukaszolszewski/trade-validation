package com.github.tradevalidation.model.enums;

public enum State {
    CORRECT(false), ERROR(true);

    State(boolean hasErrors) {
        this.hasErrors = hasErrors;
    }
    protected boolean hasErrors;

    public boolean hasErrors() {
        return hasErrors;
    }
}
