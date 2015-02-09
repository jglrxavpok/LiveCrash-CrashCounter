package org.jglrxavpok.crashcounter;

import com.intellij.execution.actions.ConsoleActionsPostProcessor;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import org.jetbrains.annotations.NotNull;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class ConsoleProcessor extends ConsoleActionsPostProcessor {

    @NotNull
    public AnAction[] postProcess(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
        CrashCounter counter = CrashCounter.instance;
        if(counter.isLaunched() && !counter.hasFilter(console)) {
            counter.addFilters(console);
        }
        return actions;
    }

    @NotNull
    public AnAction[] postProcessPopupActions(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
        CrashCounter counter = CrashCounter.instance;
        if(counter.isLaunched() && !counter.hasFilter(console)) {
            counter.addFilters(console);
        }
        return actions;
    }
}
