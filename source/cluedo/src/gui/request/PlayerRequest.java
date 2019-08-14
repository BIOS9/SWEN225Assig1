package gui;

import java.util.concurrent.CompletableFuture;

public abstract class PlayerRequest<T> {
    private CompletableFuture<T> future;

    public T waitResponse() {
        return future.get();
    }

    public void setResponse(T response) {
        future.complete(response);
    }
}
