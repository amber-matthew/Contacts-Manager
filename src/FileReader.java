import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FileReader {

    private String directoryName; // the data folder (in parent directory already), src/fileIo
    private String fileName;      // the "day18.txt", "jolts.txt" //Path you're reading from
    private String logFileName;   // store any logging info, what went right / wrong   //Path your putting file info into
    private Path directoryPath;   // Path representation of the parent directory for our files
    private Path filePath;        // actual file itself, data/day18.txt
    private Path logFilePath;      // Path representation of the log file // file for reading the file(?)
    private List<String> fileLines;    // the lines of the file line by line, holding spot for the content from data file
    private List<String> logFileLines;  // Holding spot for the content inside of the log file

    public FileReader(String directoryName, String fileName, String logFileName) throws IOException {
        this.directoryName = directoryName;
        this.fileName = fileName;
        this.logFileName = logFileName;
        this.directoryPath = Paths.get(directoryName); // Paths.get( " data " )
        this.filePath = Paths.get(directoryName, fileName); // the directoryName is the path to where you are placing the file in
        this.logFilePath = Paths.get(directoryName, logFileName);

        if (Files.notExists(this.logFilePath)) {
            try {
                Files.createFile(this.logFilePath);

            } catch (IOException e) {
                throw new IOException("Unable to create the logfile (" + this.logFileName + ")!");

            }
        }
        if (Files.notExists(this.directoryPath)) {
            try {
                Files.createDirectories(this.directoryPath);
            } catch (IOException e) {
                Files.write(this.logFilePath, Arrays.asList(e.getMessage()), StandardOpenOption.APPEND);

                throw new IOException("Unable to create the data directory (" + this.directoryName + ")!");
            }
        }
        if (Files.notExists(this.filePath)) {
            try {
                Files.createFile(this.filePath);
            } catch (IOException e) {
                Files.write(this.logFilePath, Arrays.asList(e.getMessage()), StandardOpenOption.APPEND);

                throw new IOException("Unable to create the data file (" + this.fileName + ")!");
            }
        }

        this.fileLines = Files.readAllLines(this.filePath);
    }



    public void writeToLog(Contact person) throws IOException {
        ArrayList<String> List = new ArrayList<>();
        List.add(person.getName());
        List.add(person.getNumber());
    try{
            //write the string message to the log file of This Instance of the File reader object that is instantiated
            Files.write(this.logFilePath, List, StandardOpenOption.APPEND);
        }catch(IOException e){
            Files.write(this.logFilePath, Arrays.asList(e.getMessage()), StandardOpenOption.APPEND);
            throw new IOException("Unable to write custom contact " + person + " to log file");
        }
    }

    public void overwriteLog(List<String> updatedContacts, String deletedContact, String deletedNumber) throws IOException {
        try{
            Files.write(this.logFilePath, updatedContacts);
        }catch(IOException e){
            Files.write(this.logFilePath, Arrays.asList(e.getMessage()), StandardOpenOption.APPEND);
            throw new IOException("Unable to delete contact " + deletedContact + " with number " + deletedNumber);
        }
    }






    // Getters and Setters
    public String getDirectoryName() {
        return directoryName;
    }

    public void setDirectoryName(String directoryName) {
        this.directoryName = directoryName;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getLogFileName() {
        return logFileName;
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public Path getDirectoryPath() {
        return directoryPath;
    }

    public void setDirectoryPath(Path directoryPath) {
        this.directoryPath = directoryPath;
    }

    public Path getFilePath() {
        return filePath;
    }

    public void setFilePath(Path filePath) {
        this.filePath = filePath;
    }

    public Path getLogFilePath() {
        return logFilePath;
    }

    public void setLogFilePath(Path logFilePath) {
        this.logFilePath = logFilePath;
    }

    public List<String> getFileLines() {
        return fileLines;
    }

    public void setFileLines(List<String> fileLines) {
        this.fileLines = fileLines;
    }

    public List<String> getLogFileLines() {
        return logFileLines;
    }

    public void setLogFileLines(List<String> logFileLines) {
        this.logFileLines = logFileLines;
    }


}


/*

     public static void main(String[] args) throws IOException {
        // instantiate a file reader object, and see if it works

        MyFileReader thisFileReader = new MyFileReader("src/data", "day18.txt", "day18Log.txt");

        thisFileReader.writeToLog("Successfully read the " + thisFileReader.getFileName() + " file!"); //using the new method to easily write to the log

        System.out.println("day18 file, here is the first line from the list");
        System.out.println(thisFileReader.getFileLines().get(0));

        // set up a new file reader object with different values to access the "jolts.txt" file
        MyFileReader joltsReader = new MyFileReader("src/fileIo", "jolts.txt", "joltsLog.txt");



    }*/