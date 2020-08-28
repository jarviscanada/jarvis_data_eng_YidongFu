package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class JavaGrepImpl implements JavaGrep {

    final Logger logger = LoggerFactory.getLogger(JavaGrep.class);

    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public void process() throws IOException {
        List<String> matchedLines = new ArrayList<>();

        for (File file : listFiles(rootPath)) {
            for (String line : readLines(file)) {
                if (containsPattern(line)) {
                    matchedLines.add(line);
                }
            }
        }

        if (!matchedLines.isEmpty()){
            writeToFile(matchedLines);
        } else {
            throw new IOException();
        }
    }

    @Override
    public List<File> listFiles(String rootDir) {
        File directory = new File(rootDir);

        List<File> files = new ArrayList<>();

        List<File> path = new ArrayList<File>(Arrays.asList(directory.listFiles()));
        for (File file : path) {
            if (file.isFile()) {
                files.add(file);
            }
        }
        return files;
    }

    @Override
    public List<String> readLines(File inputFile) throws IOException{

        if (inputFile == null) {
            throw new IllegalArgumentException(inputFile +  " is null");
        }
        else if (!inputFile.isFile()){
            throw new IllegalArgumentException(inputFile + " is not a file");
        } else {
            return Files.readAllLines(inputFile.toPath());
        }
    }

    @Override
    public boolean containsPattern(String line) {
        if (line.matches(regex)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        Path path = Paths.get(rootPath, outFile);

        try{
            Files.write(path, lines);
        } catch (IOException ioex) {
            throw new IOException("Failed to write lines to the file", ioex);
        }
    }

    @Override
    public String getRootPath() { return rootPath; }

    @Override
    public void setRootPath(String rootPath) { this.rootPath = rootPath; }

    @Override
    public String getRegex() { return regex; }

    @Override
    public void setRegex(String regex){ this.regex = regex; }

    @Override
    public String getOutFile() { return outFile; }

    @Override
    public void setOutFile(String outFile) { this.outFile = outFile; }

    public static void main(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImpl javaGrepImpl = new JavaGrepImpl();
        javaGrepImpl.setRegex(args[0]);
        javaGrepImpl.setRootPath(args[1]);
        javaGrepImpl.setOutFile(args[2]);

        try {
            javaGrepImpl.process();
        } catch (Exception ex) {
            javaGrepImpl.logger.error(ex.getMessage(), ex);
        }
    }
}