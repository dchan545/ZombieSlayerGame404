package Database;
/**
 * This class passes the information from the code to the database connector.
 *
 * @author Ezrie Brant
 * Last Updated: 11/12/2019
 */

import android.content.Context;

import java.util.LinkedList;

public class DBTranslator {

    //Data is currently being stored in a text file.
    private static final DBConnectorInterface connector = new BinaryConnector();

    //CRUD operations
    public static void createObject(Context _ctx, String _source) {
        connector.createObject(_ctx, _source);
    }

    public static LinkedList<String> readObject(Context _ctx, String _file) {
        return connector.readObject(_ctx, _file);
    }

    public static void updateObject(Context _ctx, String _file, LinkedList _data) {
        connector.updateObject(_ctx, _file, _data);
    }

    public static void deleteObject(Context _ctx, String _file) {
        connector.deleteObject(_ctx, _file);
    }
}