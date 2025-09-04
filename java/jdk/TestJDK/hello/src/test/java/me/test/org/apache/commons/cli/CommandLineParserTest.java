package me.test.org.apache.commons.cli;

import org.apache.commons.cli.*;
import org.apache.commons.cli.help.HelpFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

/**
 *
 * @author dangqian.zll
 * @date 2025/9/4
 */
public class CommandLineParserTest {
    Options newOptions() {
        Options options = new Options();
        options.addOption("a", "all", false, "do not hide entries starting with .");
        options.addOption("A", "almost-all", false, "do not list implied . and ..");
        options.addOption("b", "escape", false, "print octal escapes for non-graphic "
                + "characters");
        options.addOption(Option.builder("SIZE").longOpt("block-size")
                .desc("use SIZE-byte blocks")
                .hasArg()
                .get());
        options.addOption("B", "ignore-backups", false, "do not list implied entries "
                + "ending with ~");
        options.addOption("c", false, "with -lt: sort by, and show, ctime (time of last "
                + "modification of file status information) with "
                + "-l:show ctime and sort by name otherwise: sort "
                + "by ctime");
        options.addOption("C", false, "list entries by columns");

        options.addOption(Option.builder("i")
                .longOpt("ips")
                .desc("ip addr list")
                .hasArgs()
                .valueSeparator(',')
                .get());
        return options;
    }

    @Test
    public void printHelp01() throws IOException {
        Options options = newOptions();
        HelpFormatter formatter = HelpFormatter.builder()
                .setShowSince(false)
//                .setOptionFormatBuilder(
//                        OptionFormatter.builder()
//                                .build()
//                )
                .get();
        formatter.printOptions(options);
    }

    @Test
    public void commandLineParse01() throws IOException, ParseException {
        Options options = newOptions();

        CommandLineParser parser = new DefaultParser();
        String[] args = new String[]{"-a", "--block-size", "111"};
        CommandLine line = parser.parse(options, args);
        Assertions.assertTrue(line.hasOption("a"));
        Assertions.assertEquals("111", line.getOptionValue("SIZE"));
    }

    @Test
    public void commandLineParse02() throws IOException, ParseException {
        Options options = newOptions();
        CommandLineParser parser = new DefaultParser();
        String[] args = new String[]{"--block-size=111"};
        CommandLine line = parser.parse(options, args);
        Assertions.assertEquals("111", line.getOptionValue("SIZE"));
    }

    @Test
    public void commandLineParse03() throws IOException, ParseException {
        Options options = newOptions();
        CommandLineParser parser = new DefaultParser();
        String[] args = new String[]{"-i=aaa","-i=bbb","-i=ccc"};
        CommandLine line = parser.parse(options, args);
        Assertions.assertTrue(line.hasOption("i"));
        String[] values = line.getOptionValues("i");
        Assertions.assertEquals(3, values.length);
        Assertions.assertEquals("aaa", values[0]);
        Assertions.assertEquals("bbb", values[1]);
        Assertions.assertEquals("ccc", values[2]);
    }

    @Test
    public void commandLineParse04() throws IOException, ParseException {
        Options options = newOptions();
        CommandLineParser parser = new DefaultParser();
        String[] args = new String[]{"-i=aaa,bbb,ccc"};
        CommandLine line = parser.parse(options, args);
        Assertions.assertTrue(line.hasOption("i"));
        String[] values = line.getOptionValues("i");
        Assertions.assertEquals(3, values.length);
        Assertions.assertEquals("aaa", values[0]);
        Assertions.assertEquals("bbb", values[1]);
        Assertions.assertEquals("ccc", values[2]);
    }

}
