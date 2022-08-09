/*
<< Pharmacy Management System Project>>
Date of Presentation : 11.May.2021
Group : 7   
Name of Group members : Md. Asaduzzaman Chowdhury  (Id-2021355642 ) , Md. Masud Shohail  (Id- 2022123642 ) , Shahran Rahman Alve (Id-2022253642)
Instructor : Monamy Islam Ma'am
Section : CSE215L.17
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

// abstract class for generic user of the system
abstract class User {
    boolean adminStatus;
    boolean guestStatus;
    boolean loggedIn = false;
    public String enteredUserName;
    public String enteredPassword;
    public String age = "20";

    // clears all temporary files
    static void clearTemp() 
     {
        File tempFile = new File("temp.txt");
        if (tempFile.exists())
            tempFile.delete();
     }

    // matches password with file
    static boolean matchPasswordFromFile(String name, String pass) 
     {
        try
         {
            Scanner fileReader = new Scanner(new File("userInfo.txt"));
            while (fileReader.hasNextLine()) 
             {
                String current = fileReader.nextLine();
                if (current.split(",")[0].equals(name)) 
                 {
                    return current.split(",")[1].equals(pass);
                 }
             }
            fileReader.close();
        }
        catch (IOException e) 
         {
            System.out.println(e);
         }
        return false;
    }

    // sets files initially, on the start of the program
    static void setProjectEnvironment()
     {
        try 
         {
            File medicineFile = new File("medicine.txt");
            File adminInfoFile = new File("adminInfo.txt");
            File userInfoFile = new File("userInfo.txt");
            if (!medicineFile.exists()) 
             {
                FileWriter fileWriter = new FileWriter(medicineFile);
                fileWriter.write("");
                fileWriter.close();
             }
            if (!adminInfoFile.exists()) 
            {
                FileWriter fileWriter = new FileWriter(adminInfoFile);
                fileWriter.write("admin123");
                fileWriter.close();
            }
            if (!userInfoFile.exists()) 
            {
                FileWriter fileWriter = new FileWriter(userInfoFile);
                fileWriter.write("");
                fileWriter.close();
            }

        } 
        catch (IOException e) 
         {
            System.out.println("IoException : " + e.getStackTrace());
         }
    }

    // prints the storage of the farmacy
    static void printCurrentStorage() 
     {
        try 
         {
            Scanner fileReader = new Scanner(new File("Medicine.txt"));
            System.out.println("Serial" + " " + " Name" + "  " + "      Price");
            while (fileReader.hasNextLine()) {
                String current = fileReader.nextLine();
                String serialNo = current.split(",")[0];
                String medicineName = current.split(",")[1];
                String prizeString = current.split(",")[2];
                System.out.println(serialNo + "       " + medicineName + "        " + prizeString);
            }
            fileReader.close();
        }
        catch (IOException e)
         {
            
         }
    }

    // changes admin password
    static void setAdminPasswordToFile(String newPassword)
     {
        try {
            FileWriter fileWriter = new FileWriter("adminInfo.txt");
            fileWriter.write(newPassword);
            fileWriter.close();
        } catch (IOException e) {
            System.out.println("IoException : " + e.getStackTrace());
        }
    }

    // finds nth serialled medicine from storage (file)
    static String findMedicine(String serialNo) {
        String result = "";
        try {
            Scanner fileReader = new Scanner(new File("Medicine.txt"));

            while (fileReader.hasNextLine()) {
                String current = fileReader.nextLine();
                if (current.split(",")[0].equals(serialNo)) {
                    result = current;
                    return result;
                }
            }
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("IoException : " + e.getStackTrace());
        }
        return result;
    }

    // called in sign up process
    static void createOrUpdateUser(String enteredUserName, String enteredPassword, String age) {
        boolean found = false;
        try {
            FileWriter fileWriter = new FileWriter("temp.txt", true);
            Scanner fileReader = new Scanner(new File("userInfo.txt"));
            while (fileReader.hasNextLine()) {
                String current = fileReader.nextLine();
                if (current.split(",")[0].equals(enteredUserName)) {
                    found = true;
                    if (age == null)
                        age = current.split(",")[2];
                    fileWriter.write(enteredUserName + "," + enteredPassword + "," + age + "\n");
                } else {
                    fileWriter.write(current + "\n");
                }
            }
            if (!found)
                fileWriter.write(enteredUserName + "," + enteredPassword + "," + age + "\n");
            fileWriter.close();
            fileReader.close();
            fileWriter = new FileWriter("userInfo.txt", false);
            fileReader = new Scanner(new File("temp.txt"));
            while (fileReader.hasNextLine()) {
                fileWriter.write(fileReader.nextLine() + "\n");
            }
            fileWriter.close();
            fileReader.close();
            File temp = new File("temp.txt");
            temp.delete();
        } catch (IOException e) {
            System.out.println("IoException : " + e.getStackTrace());
        }
    }
}