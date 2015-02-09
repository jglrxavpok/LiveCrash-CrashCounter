package org.jglrxavpok.crashcounter.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class PacketConnection extends AbstractPacket {

    public PacketConnection() {
        super((byte)0x1);
    }

    @Override
    public void decode(DataInput in) throws IOException {

    }

    @Override
    public void encode(DataOutput out) throws IOException {

    }
}
