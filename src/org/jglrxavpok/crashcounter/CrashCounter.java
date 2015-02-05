package org.jglrxavpok.crashcounter;

import com.google.common.collect.Lists;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.module.ModuleComponent;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.jgoodies.common.collect.ArrayListModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
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

    public void addFilter(ConsoleView console) {
        filters.add(console);
        console.addMessageFilter(new ErrorFilter());

    }

    public boolean hasFilter(ConsoleView console) {
        return filters.contains(console);
    }
}
