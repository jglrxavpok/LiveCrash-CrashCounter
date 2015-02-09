package org.jglrxavpok.crashcounter.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public abstract class PacketProblemFound extends AbstractPacket {

    private String header;
    private String[] crashTrace;

    public PacketProblemFound(byte id, String header, String[] crashTrace) {
        super(id);
        this.header = header;
        this.crashTrace = crashTrace;
    }

    @Override
    public void decode(DataInput in) throws IOException {
        header = readString(in);
        byte traceLength = in.readByte();
        crashTrace = new String[traceLength];
        for(int i = 0;i<traceLength;i++){
            crashTrace[i] = readString(in);
        }
    }

    @Override
    public void encode(DataOutput out) throws IOException {
        writeString(out, header);
        out.writeByte((byte)crashTrace.length);
        for(String s : crashTrace) {
            writeString(out, s);
        }
    }
}
