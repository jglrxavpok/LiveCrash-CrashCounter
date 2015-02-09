package org.jglrxavpok.crashcounter.filters;

import com.google.common.collect.Lists;
import com.intellij.execution.filters.Filter;
import org.jetbrains.annotations.Nullable;
import org.jglrxavpok.crashcounter.CrashCounter;
import org.jglrxavpok.crashcounter.NetworkHelper;
import org.jglrxavpok.crashcounter.packets.PacketCrashFound;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jglrxavpok on 06/02/2015.
 */
public class MCCrashFilter implements Filter {

    @Nullable
    @Override
    public Result applyFilter(String s, int i) {
        Pattern pattern = Pattern.compile("^\\[\\d\\d:\\d\\d:\\d\\d\\] \\[.+\\] \\[.+\\]: #@!@# Game crashed! Crash report saved to: #@!@# ((.+)?)");
        Matcher fileMatcher = pattern.matcher(s);
        if(!fileMatcher.find())
            return new Result(Lists.newArrayList());
        try {
            String file = fileMatcher.group(1);
            System.out.println("[CrashCounter] File is: "+file);
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = null;
            int lineIndex = 0;
            boolean searchingForTrace = false;
            List<String> stackTrace = Lists.newArrayList();
            String time = null;
            String desc = null;
            String wittyComment = null;
            String exception = null;
            while((line = reader.readLine()) != null) {
                if(lineIndex == 1) {
                    wittyComment = line.replace("// ", ""); // TODO: Use it
                } else if(lineIndex == 3) { // Time
                    time = line.replace("Time: ", "");
                } else if(lineIndex == 4) { // Description
                    desc = line.replace("Description: ", "");
                } else if(lineIndex == 6) { // Start of stack trace
                    searchingForTrace = true;
                    exception = line;
                }
                else if(lineIndex > 6) {
                    if (searchingForTrace) {
                        if (line.trim().isEmpty()) {
                            searchingForTrace = false;
                        } else {
                            stackTrace.add(line.replace("\tat", ""));
                        }
                    }
                }
                lineIndex++;
            }
            reader.close();

            PacketCrashFound packet = new PacketCrashFound(desc, wittyComment, time, exception, stackTrace.toArray(new String[0]));
            NetworkHelper.write(CrashCounter.instance.getSocket(), packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // [18:44:55] [Client thread/INFO] [STDOUT]: [net.minecraft.client.Minecraft:displayCrashReport:398]: #@!@# Game crashed! Crash report saved to: #@!@# D:\Code\Java\NewWorkspace\Modding\forge-1.7.10-10.13.2.1230-src\.\crash-reports\crash-2015-02-06_18.44.55-client.txt
        return new Result(Lists.newArrayList());
    }
}
