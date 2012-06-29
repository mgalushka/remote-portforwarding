package com.maximgalushka.attempt;

import com.maximgalushka.attempt.commands.Event;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 29.06.12
 */
public interface NioOperation {

    ByteBuffer read(SelectionKey key) throws IOException, InterruptedException;

    void write(SelectionKey key) throws IOException;

    void accept(SelectionKey key) throws IOException;

    void connect(SelectionKey key) throws IOException, InterruptedException;

    /**
     * Push event onto events stack
     */
    void push(Event event) throws InterruptedException;


}
