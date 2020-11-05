package fr.poo.io;

import fr.poo.data.terrain.objects.obstacles.Obstacle;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class FileManager {

    public static void save(String path, ISerializable serializable) throws IOException {
        Files.write(Paths.get(path), serializable.serialize());
    }

    public static <T> T load(String path, ISerializable<T> serializable) throws IOException {
        List<String> list = Files.readAllLines(Paths.get(path));
        return serializable.deserialize(list);
    }

}
