package gui.request;

import game.Player;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

/**
 * Handles requesting information from players of the game.
 * Uses asynchronous operation to ensure the game can wait for user input while not freezing the UI thread
 * @param <T> Type of input expected from the player
 */
public abstract class PlayerRequest<T> {
    private CompletableFuture<T> future;

    public boolean isInputValid(T input) {
        return true;
    }

    public PlayerRequest() {
        future = new CompletableFuture<>();
    }

    /**
     * Halts execution in current thread until the player had responded.
     * If an error occurs while getting the response, null may be returned
     * @return Response of type specified or null
     */
    public T waitResponse() {
        try {
            return future.get();
        } catch (InterruptedException | ExecutionException ex) {
            return null;
        }
    }

    /**
     * Set response from player allowing execution to continue
     * @param response Response to send back to the game
     */
    public void setResponse(T response) {
        if(future.isDone()) // Ignore further input to this future
            return;
        future.complete(response);
    }

    /**
     * Returns true if the player has already responded
     * @return
     */
    public boolean hasResponded() {
        return future.isDone();
    }
}
