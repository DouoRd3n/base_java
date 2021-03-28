/**
 * Array based storage for Resumes
 */
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

    Resume get(String uuid) {
        Resume temp = null;
        for (int i = 0; i <countStorageElement ; i++) {

            if (storage[i].uuid.equals(uuid)){
                temp = storage[i];
            }


        }
        return temp;
    }

    void delete(String uuid) {
        for (int i = 0; i < countStorageElement ; i++) {
            if (storage[i].uuid.equals(uuid)){
                storage[i] = null;
            }
        }
        overwriteElements();
    }

    private void overwriteElements() {
        Resume[] temp = storage;
        int count = 0;
        for (int i = 0; i <countStorageElement ; i++) {
            if (temp[i] != null ){
                storage[count] = temp[i];
                count++;
            }
        }
        countStorageElement = count;
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {
        Resume[] resumes = new Resume[countStorageElement];
        for (int i = 0; i <countStorageElement ; i++) {

            resumes[i] = storage[i];
        }

        return resumes;
    }

    int size() {

        return countStorageElement;
    }
}

