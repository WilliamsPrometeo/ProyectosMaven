package dam.code.repository;

import java.util.Map;

public interface Repository<K, V> {

    Map<K, V> cargar();
    void guardar(Map<K, V> datos);
}
