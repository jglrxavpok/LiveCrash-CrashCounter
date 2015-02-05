package org.jglrxavpok.crashcounter;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class CounterLaunch extends AnAction {

    public void actionPerformed(AnActionEvent evt) {
        System.out.print("HI CAN I HAS ACTIONS PLZ");
        CrashCounter counter = CrashCounter.instance;
        if(counter == null)
            throw new RuntimeException("CrashCounter not initialized");
        if(!counter.isLaunched()) {
            try {
                Socket socket = counter.createSocket();
                counter.launch(socket);
            } catch (IOException e) {
                e.printStackTrace();
                Messages.showErrorDialog("Error while launching the CrashCounter", "Connection impossible to establish between the counter and the displayer!");
            }

        }
        else {
            Messages.showInfoMessage("", "CrashCounter is already launched!");
        }
    }
}
