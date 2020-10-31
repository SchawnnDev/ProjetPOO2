package fr.poo.io;

import java.util.List;

public interface ISerializable<T> {

    List<String> serialize();

    T deserialize(List<String> lines);

}
