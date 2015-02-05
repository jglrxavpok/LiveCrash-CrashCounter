package org.jglrxavpok.crashcounter;

import org.jglrxavpok.crashcounter.packets.AbstractPacket;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class NetworkHelper {

    public static void write(Socket socket, AbstractPacket packet) throws IOException {
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        out.writeInt(packet.getId());
        packet.encode(out);
        socket.getOutputStream().flush();
    }
}
