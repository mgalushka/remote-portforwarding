package com.maximgalushka.attempt.commands;

import java.nio.ByteBuffer;

/**
 * <p></p>
 *
 * @author Maxim Galushka
 * @since 29.06.12
 */
public class ReadEvent implements Event{

    private ByteBuffer buffer;

    public ReadEvent(ByteBuffer buffer) {
        this.buffer = buffer;
    }

    public ByteBuffer getBuffer() {
        return buffer;
    }
}
