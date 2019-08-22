package helpers;

import java.io.*;
import java.util.Scanner;


public class Config
{
    private boolean ioError = false;
    private File configFile = new File("config.ini");
    private String address , port;

    public boolean getErrorStatus() { return ioError; }
    public String getAddress()  { return address; }
    public String getPort() { return port; }

    private void LoadVariables() throws FileNotFoundException
    {
        Scanner scanner = new Scanner(configFile);
        while (scanner.hasNextLine())
        {
            String line = scanner.nextLine();
            if(line.contains("ADDRESS="))
            {
                address = line.replace("ADDRESS=", "");
            }
            else if(line.contains("PORT="))
            {
                port = line.replace("PORT=", "");
            }
        }
    }

    private void ReplaceLine(String oldString, String newString)
    {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader(configFile));
            String line = "", oldText = "";
            while((line = reader.readLine()) != null)
            {
                oldText += line + "\r\n";
            }
            reader.close();
            String newText = oldText.replaceAll(oldString, newString);

            FileWriter writer = new FileWriter("config.ini");
            writer.write(newText);
            writer.close();
        }
        catch (IOException ioe)
        {
            ioError = true;
        }
    }

    public void ReplaceConfig(String whatConfig, String newConfig) throws FileNotFoundException
    {
        String changeConfig;
        String newOption;
        String oldOption;
        switch(whatConfig)
        {
            case "Address":
                changeConfig = "ADDRESS=";
                oldOption = changeConfig + getAddress();
                newOption = changeConfig + newConfig;
                break;

            case "Port":
                changeConfig = "PORT=";
                oldOption = changeConfig + getPort();
                newOption = changeConfig + newConfig;
                break;

            default:
                return;
        }

        ReplaceLine(oldOption, newOption);

        LoadVariables();
    }

    private void WriteDefaultSettings() throws IOException
    {
        FileWriter fileWriter = new FileWriter(configFile, true);
        fileWriter.write("ADDRESS=localhost\nPORT=3000");
        fileWriter.close();
    }

    public Config() throws FileNotFoundException {
        if(!configFile.exists())
        {
            try
            {
                configFile.createNewFile();
                WriteDefaultSettings();
                LoadVariables();
                ioError = false;
            }
            catch(IOException e)
            {
                ioError = true;
            }
        }
        else
        {
            LoadVariables();
        }
    }
}
