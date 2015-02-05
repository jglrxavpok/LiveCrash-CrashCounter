package org.jglrxavpok.crashcounter;

import com.google.common.collect.Lists;
import com.intellij.codeEditor.printing.PrintAction;
import com.intellij.execution.actions.ConsoleActionsPostProcessor;
import com.intellij.execution.filters.Filter;
import com.intellij.execution.filters.HyperlinkInfo;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Created by jglrxavpok on 05/02/2015.
 */
public class ConsoleProcessor extends ConsoleActionsPostProcessor {

    @NotNull
    public AnAction[] postProcess(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
        CrashCounter counter = CrashCounter.instance;
        if(/*counter.isLaunched() && */!counter.hasFilter(console)) {
            counter.addFilter(console);
        }
        return actions;
    }

    @NotNull
    public AnAction[] postProcessPopupActions(@NotNull ConsoleView console, @NotNull AnAction[] actions) {
        CrashCounter counter = CrashCounter.instance;
        if(/*counter.isLaunched() && */!counter.hasFilter(console)) {
            counter.addFilter(console);
        }
        return actions;
    }
}
