package org.jglrxavpok.crashcounter.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class PacketCrashFound extends PacketProblemFound {

    private String wittyComment;
    private String time;
    private String desc;

    public PacketCrashFound(String desc, String wittyComment, String time, String header, String[] crashTrace) {
        super((byte)0x3, header, crashTrace);
        this.desc = desc;
        this.wittyComment = wittyComment;
        this.time = time;
    }

    public void encode(DataOutput out) throws IOException {
        super.encode(out);
        writeString(out, desc);
        writeString(out, wittyComment);
        writeString(out, time);
    }

    public void decode(DataInput input) throws IOException {
        super.decode(input);
        desc = readString(input);
        wittyComment = readString(input);
        time = readString(input);
    }
}
