package org.jglrxavpok.crashcounter.filters;

import com.google.common.collect.Lists;
import com.intellij.execution.filters.Filter;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class ErrorFilter implements Filter {

    private List<String> stackTrace;
    private String currentException;

    public ErrorFilter() {
        stackTrace = Lists.newArrayList();
    }

    @Nullable
    @Override
    public Result applyFilter(String s, int i) {
        // TODO: Regex expressions to avoid false positives/negatives ?
        if(!s.isEmpty()) {
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
            // TODO: send stack trace
            System.out.println("[==== START OF STACK TRACE ====]\nException: " + currentException);
            for (String entry : stackTrace) { // TMP
                System.out.println("Entry: " + entry.replace("\n", ""));
            }
            System.out.println("[==== END OF STACK TRACE ====]");
            this.currentException = null;
            stackTrace.clear();
        }
    }

    private boolean searchForMethod(String s) {
        if(s.length() > 1)
            if((currentException == null && stackTrace.isEmpty()) || (currentException != null && !stackTrace.isEmpty()) && s.contains("Exception") && s.contains(".")) {
                flushStackTrace();
                int max = s.length();
                if(s.contains(":")) {
                    max = s.indexOf(":");
                }
                String exceptionName = s.substring(0, max);
                if(exceptionName.equals("\n"))
                    return false;
                if(exceptionName.contains(":") || exceptionName.contains("\"") || exceptionName.contains(" ") || exceptionName.contains("\t") || exceptionName.contains("/") || exceptionName.contains("\\")) // Yes, that is indeed ugly
                {
                    return false;
                }
                this.currentException = exceptionName.replace("\n", "").replace("\r", "");
                return true;
            }
        return false;
    }
}
