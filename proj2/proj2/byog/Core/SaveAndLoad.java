package byog.Core;

import byog.SaveDemo.World;
import byog.TileEngine.TETile;
import edu.princeton.cs.introcs.StdDraw;

import java.io.*;

public class SaveAndLoad {


    private static void drawEverything(World w) {
        StdDraw.clear(StdDraw.BLACK);
        w.draw();
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(0.5, 0.8, "Press a to add square.");
        StdDraw.text(0.5, 0.85, "Press q to quit and save.");
        StdDraw.text(0.5, 0.9, "Delete world.ser to go back to a blank canvas.");
        StdDraw.show();
        StdDraw.pause(100);
    }

    public static WorldGenerator loadWorld() {
        File f = new File("./gameWorld.txt");
        if (f.exists()) {
            try {
                FileInputStream fs = new FileInputStream(f);
                ObjectInputStream os = new ObjectInputStream(fs);
                WorldGenerator loadWorld = (WorldGenerator) os.readObject();
                os.close();
                return loadWorld;
            } catch (FileNotFoundException e) {
                System.out.println("file not found");
                System.exit(0);
            } catch (IOException e) {
                System.out.println(e);
                System.exit(0);
            } catch (ClassNotFoundException e) {
                System.out.println("class not found");
                System.exit(0);
            }
        }

        /* In the case no World has been saved yet, we return a new one. */
        System.exit(0);
        return null;
    }

    public static void saveWorld(WorldGenerator worldGenerator) {
        File f = new File("./gameWorld.txt");
        try {
            if (!f.exists()) {
                f.createNewFile();
            }
            FileOutputStream fs = new FileOutputStream(f);
            ObjectOutputStream os = new ObjectOutputStream(fs);
            os.writeObject(worldGenerator);
            os.close();
        }  catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(0);
        } catch (IOException e) {
            System.out.println(e);
            System.exit(0);
        }
    }
}
