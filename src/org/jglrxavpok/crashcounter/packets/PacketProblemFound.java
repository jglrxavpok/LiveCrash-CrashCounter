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

    public PacketProblemFound(int id, String header, String[] crashTrace) {
        super(id);
        this.header = header;
        this.crashTrace = crashTrace;
    }

    @Override
    public void decode(DataInput in) throws IOException {
        header = in.readUTF();
        int traceLength = in.readInt();
        crashTrace = new String[traceLength];
        for(int i = 0;i<traceLength;i++){
            crashTrace[i] = in.readUTF();
        }
    }

    @Override
    public void encode(DataOutput out) throws IOException {
        out.writeUTF(header);
        out.writeInt(crashTrace.length);
        for(String s : crashTrace) {
            out.writeUTF(s);
        }
    }
}
