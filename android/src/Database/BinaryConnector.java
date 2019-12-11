package Database;
/**
 * This class defines the CRUD operations for when a binary file is used for data storage.
 *
 * @author Ezrie Brant
 * Last Updated: 11/12/2019
 */

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class BinaryConnector implements DBConnectorInterface {

    private FileOutputStream fos = null;

    /**
     * Create an empty save file, the default constructor that's called when first starting the app.
     *
     * @param _ctx
     * @param _file
     */
    @Override
    public void createObject(Context _ctx, String _file) {

        try {
            String fileName = _file + ".txt";
            //Will create file if non-existent. Otherwise will open it.
            fos = _ctx.openFileOutput(fileName, Context.MODE_PRIVATE);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            //If stream is open, close it (prevent memory leaks).
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Opens the file, reads the content, and adds it to a linked list to return.
     *
     * @param _ctx
     * @param _file
     * @return
     */
    @Override
    public LinkedList<String> readObject(Context _ctx, String _file) {
        String fileName = _file + ".txt";

        //Opens file, parses data from file and inserts into linked list.
        LinkedList<String> dataFromFile = new LinkedList<>();

        //FileInputStream is used to write to the file.
        FileInputStream fis = null;

        try {
            fis = _ctx.openFileInput(fileName);
            //Check if current data exists, if not then don't continue.
            File fileObj = new File(fileName);
            if(!fileObj.exists()){
                return dataFromFile;
            }

            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String text;

            while ((text = br.readLine()) != null) {
                sb.append(text).append("\n");
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return dataFromFile;
    }

    /**
     * Opens and empties the save file, and puts the data from a LinkedList into the file.
     *
     * @param _ctx
     * @param _file
     * @param _data
     */
    @Override
    public void updateObject(Context _ctx, String _file, LinkedList _data) {
        String fileName = _file + ".txt";

        try {
            //Will delete content if there's any. Otherwise will open a empty file.
            fos = _ctx.openFileOutput(fileName, Context.MODE_PRIVATE);

            //Write to file
            String dataStr = _data.toString();
            String elem = _data.element().toString();
            fos.write(dataStr.getBytes());
            fos.write(elem.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //If stream is open, close it (prevent memory leaks).
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Opens and clears any text in the save file. Does not delete file itself.
     *
     * @param _ctx
     * @param _file
     */
    @Override
    public void deleteObject(Context _ctx, String _file) {

        String fileName = _file + ".txt";

        try {
            //Will delete content if there's any.
            fos = _ctx.openFileOutput(fileName, Context.MODE_PRIVATE);
            fos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}