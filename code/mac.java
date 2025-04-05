package code;
import java.io.*;
import java.util.Scanner;

public class mac {
    public static void main(String[]args){
        try {
            Scanner input = new Scanner(System.in);

            System.out.print("Enter interface: ");
            String interfaceName = input.nextLine();

            System.out.print("Enter new MAC address: ");
            String macAddress = input.nextLine();
            input.close();
            
            ProcessBuilder builder = new ProcessBuilder("python", "mac.py","-i", interfaceName, "-m", macAddress);
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(
                new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        }
}
