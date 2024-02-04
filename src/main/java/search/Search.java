package search;

import java.io.File;
import java.util.Scanner;

public class Search {
    private static int filesCount = 0;
    private static int foldersCount = 0;

    Scanner scanner = new Scanner(System.in);
    public void start(){
        System.out.print("Enter any folder name");
        String mainFile = scanner.nextLine();

        System.out.println("1 - search");
        System.out.println("2 - add inner file");
        int option = scanner.nextInt();

        if (option == 1){
            getSearching(mainFile);
        }else{
            getFilePath(mainFile);
        }

        System.out.println(" - - - - - - - - - - - - - - - - - - - - - - - - - - - - ");
        if (filesCount == 0 && foldersCount == 0) {
            System.out.println("Result: 0");
            System.out.println("no such file");
        } else {
            System.out.println("Results: ");
            System.out.println("Files - " + filesCount);
            System.out.println("Folders - " + foldersCount);
        }
    }

    private void getFilePath(String mainFile) {
        System.out.print("Enter inner file name:  ");
        String inner = scanner.next();

        mainFile+="/"+inner;
        System.out.println("Your main folder is: " + mainFile);

        System.out.println("1 - search");
        System.out.println("2 - add inner file");
        int option = scanner.nextInt();

        if (option == 1){
            getSearching(mainFile);
        }else{
            getFilePath(mainFile);
        }
    }

    private void getSearching(String mainFile){
        if (!checkExit(mainFile)){
            System.err.println(mainFile + " no such directory");
            return;
        }

        System.out.print("Enter file name you need ");
        String searchingFile = scanner.next();

        search(mainFile, searchingFile);
    }

    private boolean checkExit(String file){
        File file1 = new File(file);
        return file1.exists();
    }

    private void search(String mainFile, String searchingFile){
        searchingFile = searchingFile.toLowerCase();
        File file = new File(mainFile);

        File[] files = file.listFiles();

        File currentFile;
        assert files != null;
        for (File value : files) {
            currentFile = value;

            if (currentFile.getName().toLowerCase().contains(searchingFile) && currentFile.isFile()) {
                System.out.println("File : " + currentFile.getAbsolutePath());
                filesCount++;
            }

            if (currentFile.isDirectory()) {
                if (currentFile.getName().contains(searchingFile)){
                    System.out.println("Folder: " + currentFile.getAbsolutePath());
                    foldersCount++;
                }
                search(currentFile.getAbsolutePath(), searchingFile);
            }
        }
    }
}
