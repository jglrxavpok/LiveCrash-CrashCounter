package org.jglrxavpok.crashcounter.packets;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public abstract class AbstractPacket {

    private final byte id;

    public AbstractPacket(byte id) {
        this.id = id;
    }

    public byte getId() {
        return id;
    }

    public abstract void decode(DataInput in) throws IOException;

    public abstract void encode(DataOutput out) throws IOException;

    public void writeString(DataOutput out, String str) throws IOException
    {
        int length = str.length();
        out.writeInt(length);
        for(int i = 0;i<length;i++)
        {
            out.writeChar(str.charAt(i));
        }
    }

    public String readString(DataInput in) throws IOException
    {
        int length = in.readInt();
        char[] chars = new char[length];
        for(int i = 0;i<chars.length;i++)
        {
            chars[i] = in.readChar();
        }
        return new String(chars);
    }
}
