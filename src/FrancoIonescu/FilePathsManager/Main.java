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
        File file = new File("D:\\Proiecte\\Java\\interview\\src\\FilePaths.txt");
        int option = 0;
        while(option != 6)
        {
            option = menu();
            switch(option)
            {
                case 1: filePaths = addPaths(filePaths);
                    break;
                case 2: displayPaths(filePaths);
                    break;
                case 3: recycleBin = deletePaths(filePaths,recycleBin);
                    break;
                case 4: displayRecycleBin(recycleBin);
                    break;
                case 5: filePaths = recoverFromRecycleBin(filePaths,recycleBin);
                    break;
                case 6: readFromFile(file);
                    break;
                case 7:
                    break;
            }
        }
    }
    static String[] addPaths(String[] filePaths) {
        Scanner scan = new Scanner(System.in);
        String path;
        System.out.println("Enter the path: ");
        path = scan.nextLine();
        if(path.contains("/") || path.contains("\\"))
        {
            filePathIndex++;
            filePaths[filePathIndex - 1] = path;
            filePaths = Arrays.copyOf(filePaths,filePathIndex + 1);
        }
        else
        {
            System.out.println("Enter a valid path");
        }
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
        displayPaths(filePaths);
        System.out.println("Enter the number for the path you want to delete: ");
        while(!scan.hasNextInt())
        {
            System.out.println("Please enter a valid number: ");
            scan.next();
        }
        recycleBinIndex++;
        int option = scan.nextInt();
        recycleBin[recycleBinIndex - 1] = filePaths[option - 1];
        recycleBin = Arrays.copyOf(recycleBin, recycleBinIndex + 1);
        return recycleBin;
    }

    static String[] recoverFromRecycleBin(String[] filePaths, String[] recycleBin)
    {
        Scanner scan = new Scanner(System.in);
        displayRecycleBin(recycleBin);
        System.out.println("enter the number for the path you want to recover: ");
        while(!scan.hasNextInt())
        {
            System.out.println("Please enter a valid number: ");
            scan.next();
        }
        recycleBinIndex--;
        int option = scan.nextInt();
        String[] buffer = Arrays.copyOf(filePaths,filePaths.length + 1);
        buffer[filePaths.length] = recycleBin[option - 1];
        return buffer;
    }

    static void readFromFile(File file)
    {
        try
        {
            Scanner scan = new Scanner(file);
            int numberOfFiles = 0;
            int index = 0;
            while(scan.hasNextLine())
            {
                String line = scan.nextLine();
                numberOfFiles++;
            }
            String[] filePathsFromFile = new String[numberOfFiles + 1];
            numberOfFiles = 0;
            scan.close();
            scan = new Scanner(file);
            while(scan.hasNextLine())
            {
                filePathsFromFile[index] = scan.nextLine();
                System.out.println(filePathsFromFile[index]);
                index++;
            }
            index = 0;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
    static int menu()
    {
        Scanner scan = new Scanner(System.in);
        int option = 0;
        while(option != 5)
        {
            System.out.println("1. Add");
            System.out.println("2. Display paths");
            System.out.println("3. Delete");
            System.out.println("4. Display Recycle Bin");
            System.out.println("5. Recover path");
            System.out.println("6. Read paths from file");
            System.out.println("7. Exit");
            System.out.print("Enter your option: ");
            option = scan.nextInt();
            if(option < 1 || option > 6)
            {
                System.out.println("\nNumber out of bounds!");
            }
            if(option == 1)
            {
                return 1;
            }
            if(option == 2)
            {
                return 2;
            }
            if(option == 3)
            {
                return 3;
            }
            if(option == 4)
            {
                return 4;
            }
            if(option == 5)
            {
                return 5;
            }
            if(option == 6)
            {
                return 6;
            }
            if(option == 7)
            {
                return 7;
            }
        }
        return 0;
    }
}

