package org.jglrxavpok.crashcounter.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public abstract class PacketProblemFound extends AbstractPacket {

    private String[] crashTrace;

    public PacketProblemFound(int id, String[] crashTrace) {
        super(id);
        this.crashTrace = crashTrace;
    }

    @Override
    public void decode(DataInput in) throws IOException {
        int traceLength = in.readInt();
        crashTrace = new String[traceLength];
        for(int i = 0;i<traceLength;i++){
            crashTrace[i] = in.readUTF();
        }
    }

    @Override
    public void encode(DataOutput out) throws IOException {
        out.writeInt(crashTrace.length);
        for(String s : crashTrace) {
            out.writeUTF(s);
        }
    }
}
