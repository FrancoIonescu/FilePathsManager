package FrancoIonescu.FilePathsManager;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class Main {

    static int filePathIndex = 0;
    static int recycleBinIndex = 0;

    public static void main(String[] args)
    {
        String[] filePaths = new String[1];
        String[] recycleBin = new String[1];
        String[] filePathsFromFile = new String[1];
        File file = new File("FilePaths.txt");
        int option = 0;
        while(option != 8)
        {
            option = menu();
            switch(option)
            {
                case 1: filePaths = addPaths(filePaths);
                    break;
                case 2: displayPaths(filePaths);
                    break;
                case 3: recycleBin = deletePaths(filePaths,recycleBin);
                    filePaths = updatePathsAfterDelete(filePaths, recycleBin);
                    break;
                case 4: displayRecycleBin(recycleBin);
                    break;
                case 5: filePaths = recoverFromRecycleBin(filePaths,recycleBin);
                    break;
                case 6: filePathsFromFile = readFromFile(file, filePathsFromFile);
                    break;
                case 7: filePaths = copyFromFile(file, filePathsFromFile, filePaths);
                    break;
                case 8:
                    System.out.println("Exiting the program. Thank you for using our application!");
                    break;
            }
        }
    }
    static String[] addPaths(String[] filePaths) {
        Scanner scan = new Scanner(System.in);
        String path;
        System.out.println();
        System.out.print("Enter the path: ");
        path = scan.nextLine();
        if(path.contains("/") || path.contains("\\"))
        {
            filePathIndex++;
            filePaths[filePathIndex - 1] = path;
            filePaths = Arrays.copyOf(filePaths,filePathIndex + 1);
        }
        else
        {
            System.out.print("Enter a valid path!");
            filePaths = addPaths(filePaths);
        }
        System.out.println();
        return filePaths;
    }
    static void displayPaths(String[] filePaths)
    {
        System.out.println("\nYour paths are: ");

        for(int i = 0;i < filePathIndex;i++)
        {
            System.out.println(i + 1 + ". " + filePaths[i]);
        }
        System.out.println();
    }

    static void displayRecycleBin(String[] recycleBin)
    {
        System.out.println("\nYour recycle bin contains: ");
        for(int i = 0; i < recycleBinIndex; i++)
        {
            System.out.println(i + 1 + ". " + recycleBin[i]);
        }
        System.out.println();
    }

    static String[] deletePaths(String[] filePaths, String[] recycleBin)
    {
        Scanner scan = new Scanner(System.in);
        int option;
        displayPaths(filePaths);
        System.out.println(filePathIndex + 1 + ". " + "Exit");
        System.out.print("Enter the number for the path you want to delete: ");
        while (true) {
            if (scan.hasNextInt()) {
                option = scan.nextInt();
                if(option == filePathIndex + 1)
                {
                    break;
                }
                if (option >= 1 && option <= filePathIndex) {
                    break;
                } else {
                    System.out.print("Please enter a valid number within the range: ");
                }
            } else {
                System.out.print("Please enter a valid number: ");
                scan.next();
            }
        }
        if(option != filePathIndex + 1)
        {
            recycleBinIndex++;
            filePathIndex--;
            recycleBin[recycleBinIndex - 1] = filePaths[option - 1];
            recycleBin = Arrays.copyOf(recycleBin, recycleBinIndex + 1);
        }

        return recycleBin;
    }

    static boolean isInRecycleBin(String filePath, String[] recycleBin) {
        for (String path : recycleBin) {
            if (filePath.equals(path)) {
                return true;
            }
        }
        return false;
    }
    static String[] updatePathsAfterDelete(String[] filePaths, String[] recycleBin) {
        int newSizeOfFilePath = filePaths.length - 1;
        String[] updatedFilePaths = new String[newSizeOfFilePath];
        int index = 0;

        for (int i = 0; i < updatedFilePaths.length; i++) {
            if (!isInRecycleBin(filePaths[i], recycleBin)) {
                updatedFilePaths[index] = filePaths[i];
                index++;
            }
        }
        return updatedFilePaths;
    }

    static String[] recoverFromRecycleBin(String[] filePaths, String[] recycleBin)
    {
        Scanner scan = new Scanner(System.in);
        int option;
        displayRecycleBin(recycleBin);
        System.out.println(recycleBinIndex + 1 + ". " + "Exit");
        System.out.print("enter the number for the path you want to recover: ");
        while (true) {
            if (scan.hasNextInt()) {
                option = scan.nextInt();
                if(option == recycleBinIndex + 1)
                {
                    break;
                }
                if (option >= 1 && option <= recycleBinIndex) {
                    break;
                } else {
                    System.out.print("Please enter a valid number within the range: ");
                }
            } else {
                System.out.print("Please enter a valid number: ");
                scan.next();
            }
        }
        if(option != recycleBinIndex + 1)
        {
            recycleBinIndex--;
            filePathIndex++;
            filePaths[filePaths.length - 1] = recycleBin[option - 1];
            filePaths = Arrays.copyOf(filePaths,filePaths.length + 1);
        }
        return filePaths;
    }

    static String[] readFromFile(File file, String[] filePathsFromFile)
    {
        try
        {
            Scanner scan = new Scanner(file);
            String path;
            int numberOfFiles = 0;
            int index = 0;
            while(scan.hasNextLine())
            {
                String line = scan.nextLine();
                if (line.contains("/") || line.contains("\\")) {
                    numberOfFiles++;
                }
            }
            filePathsFromFile = new String[numberOfFiles];
            numberOfFiles = 0;
            scan.close();
            scan = new Scanner(file);
            System.out.println();
            while(scan.hasNextLine())
            {
                path = scan.nextLine();
                if(path.contains("/") || path.contains("\\"))
                {
                    filePathsFromFile[index] = path;
                    System.out.println(index + 1 + ". " + filePathsFromFile[index]);
                    index++;
                }
            }
            System.out.println();
            index = 0;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return filePathsFromFile;
    }

    static String[] copyFromFile(File file, String[] filePathsFromFile, String[] filePaths)
    {
        Scanner scan = new Scanner(System.in);
        filePathsFromFile = readFromFile(file, filePathsFromFile);
        System.out.print("Enter the file path you want to add: ");
        int option = scan.nextInt();
        String[] buffer = Arrays.copyOf(filePaths,filePaths.length + 1);
        buffer[filePaths.length - 1] = filePathsFromFile[option - 1];
        filePathIndex++;
        return buffer;
    }

    static int menu()
    {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while(option != 8)
        {
            System.out.println("1. Add");
            System.out.println("2. Display paths");
            System.out.println("3. Delete");
            System.out.println("4. Display Recycle Bin");
            System.out.println("5. Recover path");
            System.out.println("6. Read paths from file");
            System.out.println("7. Copy path from file");
            System.out.println("8. Exit");
            System.out.print("Enter your option: ");
            String validare = scan.nextLine();
            if (validare.matches("\\d+")) {
                option = Integer.parseInt(validare);
            }
            else {
                System.out.println("\nIntroduceti o optiune valida: \n");
                menu();
            }
            if(option < 1 || option > 8)
            {
                System.out.println();
                System.out.println("Number out of bounds!");
                System.out.println();
            }
            return option;
        }
        return 0;
    }
}
