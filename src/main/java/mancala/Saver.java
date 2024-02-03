package mancala;

import java.io.Serializable;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class Saver {
    private static final Logger LOGGER = Logger.getLogger(Saver.class.getName());

    private Saver() {
        //Private constructor to prevent instantiation
    }

    /**
     * Saves a serializable object to a file.
     *
     * @param toSave   The object to save.
     * @param filename The name of the file to save the object to.
     * @throws IOException If an I/O error occurs during the save operation.
     */
    public static void saveObject(final Serializable toSave, final String filename) throws IOException {
        try (ObjectOutputStream objectOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objectOut.writeObject(toSave);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving object to file", e);
            throw e;
        }
    }

    /**
     * Loads a serializable object from a file.
     *
     * @param filename The name of the file to load the object from.
     * @return The loaded serializable object.
     * @throws IOException            If an I/O error occurs during the load operation.
     * @throws ClassNotFoundException If the class of the object to be loaded cannot be found.
     */
    public static Serializable loadObject(final String filename) throws IOException, ClassNotFoundException {
        Serializable loadedGame;
        try (ObjectInputStream objectIn = new ObjectInputStream(new FileInputStream(filename))) {
            loadedGame = (Serializable) objectIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE, "Error loading object from file", e);
            throw e;
        }
        return loadedGame;
    }
}