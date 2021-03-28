/**
 * Array based storage for Resumes
 */

import java.util.Arrays;

/**
 * Array based storage for Resumes
 *Реализуйте класс ArrayStorage, организовав хранение резюме на основе массива с методами save, get, delete, size, clear, getAll
 * При этом храните все резюме в начале storage (без дырок в виде null), чтобы не перебирать каждый раз все 10000 элементов
 * Схема хранения резюме в storage (от 0 до size - 1 элементов null нет):
 * r1, r2, r3,..., rn, null, null,..., null
 * <----- size ----->
 * <------- storage.length (10000) ------->
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int countStorageElement = 0;

    void clear() {
        storage = new Resume[10000];
        countStorageElement = 0;
    }

    void save(Resume r) {
        storage[countStorageElement] = r;
        countStorageElement++;
    }
    void update(Resume r){
        for (int i = 0; i < countStorageElement ; i++) {
            if (storage[i].uuid == r.uuid){
                storage[i] = r;
            }
        }
    }

    Resume get(String uuid) {

        for (int i = 0; i <countStorageElement ; i++) {

            if (storage[i].uuid.equals(uuid)){
                return storage[i];
            }
        }
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < countStorageElement ; i++) {
            if (storage[i].uuid.equals(uuid)){
                storage[i] = storage[countStorageElement-1];
                countStorageElement--;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        return Arrays.copyOf(storage, countStorageElement );
    }

    int size() {

        return countStorageElement;
    }
}

