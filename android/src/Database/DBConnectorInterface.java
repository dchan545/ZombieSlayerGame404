package Database;
/**
 * This class defines the CRUD operations for data storage.
 *
 * @author Ezrie Brant
 * Last Updated: 11/12/2019
 */

import android.content.Context;

import java.util.LinkedList;

public interface DBConnectorInterface {

    //Create a new, blank object from a source (file name, SQL object name, etc.)
    void createObject(Context _ctx, String _source);

    //Read an object given a source. Returns a LL of data associated with object.
    LinkedList<String> readObject(Context _ctx, String _source);

    //Given a source and LL of object data, update the object in the DB.
    void updateObject(Context _ctx, String _source, LinkedList _data);

    //Given a source, delete the object (empty file, mark as deleted on SQL, etc.)
    void deleteObject(Context _ctx, String _source);
}