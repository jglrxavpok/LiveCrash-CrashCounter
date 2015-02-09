package org.jglrxavpok.crashcounter;

import com.google.common.collect.Lists;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.NotNull;
import org.jglrxavpok.crashcounter.filters.MCCrashFilter;
import org.jglrxavpok.crashcounter.filters.ErrorFilter;
import org.jglrxavpok.crashcounter.packets.PacketConnection;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class CrashCounter implements ModuleComponent {

    public static int SOCKET_PORT = 14999;
    public static CrashCounter instance = null;
    private boolean launched;
    private Socket socket;
    private List<ConsoleView> filters;

    public CrashCounter(Module module) {
        instance = this;
        filters = Lists.newArrayList();
    }


    @Override
    public void projectOpened() {

    }

    @Override
    public void projectClosed() {

    }

    @Override
    public void moduleAdded() {

    }

    @Override
    public void initComponent() {
        System.out.println("CrashCounter init ?");
    }

    @Override
    public void disposeComponent() {
        System.out.println("CrashCounter dispose ?");
    }

    @NotNull
    @Override
    public String getComponentName() {
        return "CrashCounter";
    }

    public Socket createSocket() throws IOException {
        return new Socket("localhost", SOCKET_PORT);
    }

    public boolean isLaunched() {
        return launched;
    }

    public Socket getSocket() {
        return socket;
    }

    public void launch(Socket socket) {
        launched = true;
        this.socket = socket;

        try {
            NetworkHelper.write(socket, new PacketConnection());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addFilters(ConsoleView console) {
        filters.add(console);
        console.addMessageFilter(new ErrorFilter());
        console.addMessageFilter(new MCCrashFilter());
    }

    public boolean hasFilter(ConsoleView console) {
        return filters.contains(console);
    }
}
