package org.jglrxavpok.crashcounter.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public abstract class AbstractPacket {

    private final int id;

    public AbstractPacket(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public abstract void decode(DataInput in) throws IOException;

    public abstract void encode(DataOutput out) throws IOException;
}
