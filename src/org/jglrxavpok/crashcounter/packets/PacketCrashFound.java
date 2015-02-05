package org.jglrxavpok.crashcounter.packets;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class PacketCrashFound extends PacketProblemFound {

    public PacketCrashFound(String header, String[] crashTrace) {
        super(0x2, header, crashTrace);
    }
}
