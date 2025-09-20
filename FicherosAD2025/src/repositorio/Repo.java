package repositorio;

import java.util.List;

public interface Repo<T> {
    void guardar(T objeto) throws Exception;
    List<T> cargar() throws Exception;
}
