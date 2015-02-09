package org.jglrxavpok.crashcounter.filters;

import com.google.common.collect.Lists;
import com.intellij.execution.filters.Filter;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.crashcounter.CrashCounter;
import org.jglrxavpok.crashcounter.NetworkHelper;
import org.jglrxavpok.crashcounter.packets.PacketCrashFound;
import org.jglrxavpok.crashcounter.packets.PacketErrorFound;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class ErrorFilter implements Filter {

    private List<String> stackTrace;
    private String currentException;
    private boolean inCrash;

    public ErrorFilter() {
        stackTrace = Lists.newArrayList();
    }

    @Nullable
    @Override
    public Result applyFilter(String s, int i) {
        Pattern crashStart = Pattern.compile("^\\[\\d\\d:\\d\\d:\\d\\d\\] \\[.+\\] \\[.+\\]: \\-\\-\\-\\- Minecraft Crash Report \\-\\-\\-\\-");
        Pattern crashEnd = Pattern.compile("^\\[\\d\\d:\\d\\d:\\d\\d\\] \\[.+\\] \\[.+\\]: #@!@# Game crashed! Crash report saved to: #@!@# ((.+)?)");
        if(crashStart.matcher(s).find()) {
            inCrash = true;
        } else if(crashEnd.matcher(s).find()) {
            inCrash = false;
        } else if (!s.isEmpty() && !inCrash) {
            boolean foundMethod = searchForMethod(s);
            if (!foundMethod) {
                String stackTraceStart = "\tat ";
                if (s.startsWith(stackTraceStart) && currentException != null) {
                    String element = s.substring(stackTraceStart.length(), s.length());
                    stackTrace.add(element);
                } else { // Not a stack trace entry
                    flushStackTrace();
                }
            }
        }
        return new Result(Lists.newArrayList());
    }

    private void flushStackTrace() {
        if (!stackTrace.isEmpty()) {
            try {
                NetworkHelper.write(CrashCounter.instance.getSocket(), new PacketErrorFound(currentException, stackTrace.toArray(new String[0])));
            } catch (IOException e) {
                e.printStackTrace();
            }
            /*System.out.println("[==== START OF STACK TRACE ====]\nException: " + currentException);
            for (String entry : stackTrace) { // TMP
                System.out.println("Entry: " + entry.replace("\n", ""));
            }
            System.out.println("[==== END OF STACK TRACE ====]");*/
            this.currentException = null;
            stackTrace.clear();
        }
    }

    private boolean searchForMethod(String s) {
        if(s.length() > 1) {
            Pattern exceptionRegex = Pattern.compile("^.+Exception(.+|$)");
            Matcher matcher = exceptionRegex.matcher(s);
            if(matcher.find()) {
                flushStackTrace();
                int max = s.length();
                if (s.contains(":")) {
                    max = s.indexOf(":");
                }
                String exceptionName = s.substring(0, max);
                this.currentException = exceptionName.replace("\n", "").replace("\r", "");
                return true;
            }
        }
        return false;
    }
}
