package info.kgeorgiy.ja.zaynidinov.student;

import info.kgeorgiy.java.advanced.student.*;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
public class StudentDB implements GroupQuery {
    /**
     * Компаратор для упорядочивания студентов по именам.
     **/
    private final Comparator<Student> NAME_COMPARATOR = Comparator
            .comparing(Student::getLastName)
            .thenComparing(Student::getFirstName)
            .reversed().thenComparingInt(Student::getId);

    /**
     * Метод для воизбежания копирования частей кода, для методов:
     * 'getFirstNames', 'getLastNames', 'getGroups', 'getFullNames', 'getDistinctFirstNames'.
     **/
    private <E> List<E> getBy(List<Student> students, Function<Student, E> mapper) {
        return students
                .stream()
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает имена учащихся.
     **/
    @Override
    public List<String> getFirstNames(List<Student> students) {
        return getBy(students, Student::getFirstName);
    }

    /**
     * Возвращает фамилию учащихся.
     **/
    @Override
    public List<String> getLastNames(List<Student> students) {
        return getBy(students, Student::getLastName);
    }

    /**
     * Возвращает группы учащихся.
     **/
    @Override
    public List<GroupName> getGroups(List<Student> students) {
        return getBy(students, Student::getGroup);
    }

    /**
     * Возвращает полное имя учащихся.
     **/
    @Override
    public List<String> getFullNames(List<Student> students) {
        return getBy(students, (Student s) -> String.format("%s %s", s.getFirstName(), s.getLastName()));
    }

    /**
     * Возвращает имена учащихся в лексиграфическом порядке.
     **/
    @Override
    public Set<String> getDistinctFirstNames(List<Student> students) {
        return new TreeSet<>(getBy(students, Student::getFirstName));
    }

    /**
     * Возвращает имя учащегося с максимальным идентификатором.
     **/
    @Override
    public String getMaxStudentFirstName(List<Student> students) {
        return students
                .stream()
                .max(Comparator.comparingInt(Student::getId))
                .map(Student::getFirstName).orElse("");
    }

    /**
     * Метод для воизбежания копирования частей кода, для методов:
     * 'sortStudentsById', 'sortStudentsByName'.
     **/
    private List<Student> sortStudentsBy(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream()
                .sorted(comparator)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает студентов, упорядоченных по идентификатору.
     **/
    @Override
    public List<Student> sortStudentsById(Collection<Student> students) {
        return sortStudentsBy(students, Comparator.comparing(Student::getId));
    }

    /**
     * Возвращает студентов, упорядоченных по именам.
     **/
    @Override
    public List<Student> sortStudentsByName(Collection<Student> students) {
        return sortStudentsBy(students, NAME_COMPARATOR);
    }

    /**
     * Метод для воизбежания копирования частей кода, для методов:
     * 'getGroupsByName', 'getGroupsById'.
     **/
    private List<Group> getGroupsBy(Collection<Student> students, Comparator<Student> comparator) {
        return students.stream().collect(Collectors.groupingBy(Student::getGroup)).entrySet().stream().sorted(Map.Entry.comparingByKey()).map(e -> new Group(e.getKey(), e.getValue().stream().sorted(comparator).collect(Collectors.toList()))).collect(Collectors.toList());
    }

    /**
     * Возвращает группы учащихся, где и группы, и учащиеся внутри группы упорядочены по именам.
     **/
    @Override
    public List<Group> getGroupsByName(Collection<Student> students) {
        return getGroupsBy(students, NAME_COMPARATOR);
    }

    /**
     * Возвращает группы учащихся, где группы упорядочены по имени, а учащиеся внутри группы упорядочены по идентификатору.
     **/
    @Override
    public List<Group> getGroupsById(Collection<Student> students) {
        return getGroupsBy(students, Comparator.comparing(Student::getId));
    }

    /**
     * Метод для воизбежания копирования частей кода, для методов:
     * 'getLargestGroup', 'getLargestGroupFirstName'.
     **/
    private GroupName getLargestGroupFromStudentsBy(final Collection<Student> students, final Function<List<Student>, Integer> mapper, final Comparator<GroupName> keyComparator) {
        return students
                .stream()
                .collect(Collectors.groupingBy(Student::getGroup))
                .entrySet().stream().collect(Collectors
                        .toMap(Map.Entry::getKey, group -> mapper.apply(group.getValue())))
                .entrySet()
                .stream()
                .max(Map.Entry.<GroupName, Integer>comparingByValue()
                        .thenComparing(Map.Entry::getKey, keyComparator))
                .map(Map.Entry::getKey).orElse(null);
    }

    /**
     * Возвращает группу, содержащую максимальное количество учащихся.
     * Если существует более одной самой большой группы, возвращается та, у которой наибольшее имя.
     **/
    @Override
    public GroupName getLargestGroup(final Collection<Student> students) {
        return getLargestGroupFromStudentsBy(students, List::size, GroupName::compareTo);
    }


    /**
     * Возвращает группу, содержащую максимальное количество учащихся с разными именами.
     * Если существует более одной самой большой группы, возвращается та, у которой наименьшее имя.
     **/
    @Override
    public GroupName getLargestGroupFirstName(final Collection<Student> students) {
        return getLargestGroupFromStudentsBy(students, s -> getDistinctFirstNames(s).size(), Comparator.reverseOrder());
    }

    /**
     * Метод для воизбежания копирования частей кода, для методов:
     * 'findStudentsByFirstName', 'findStudentsByLastName', 'findStudentsByGroup', 'findStudentNamesByGroup'.
     **/
    private List<Student> findStudentsBy(Collection<Student> students, Predicate<Student> predicate) {
        return students.stream()
                .filter(predicate)
                .sorted(NAME_COMPARATOR)
                .collect(Collectors.toList());
    }

    /**
     * Возвращает учащихся с одинаковыми именами, упорядеченнных по именам.
     **/
    @Override
    public List<Student> findStudentsByFirstName(Collection<Student> students, String name) {
        return findStudentsBy(students, s -> s.getFirstName().equals(name));
    }

    /**
     * Возвращает учащихся с одинаковыми фамилиями, упорядеченнных по именам.
     **/
    @Override
    public List<Student> findStudentsByLastName(Collection<Student> students, String name) {
        return findStudentsBy(students, s -> s.getLastName().equals(name));
    }

    /**
     * Возвращает учащихся в одинаковыми группами, упорядеченнных по именам.
     **/
    @Override
    public List<Student> findStudentsByGroup(Collection<Student> students, GroupName group) {
        return findStudentsBy(students, s -> s.getGroup().equals(group));
    }

    /**
     * Returns map of group's student last names mapped to minimal first name.
     **/
    @Override
    public Map<String, String> findStudentNamesByGroup(Collection<Student> students, GroupName group) {
        return findStudentsByGroup(students, group)
                .stream()
                .collect(Collectors.toMap(Student::getLastName, Student::getFirstName, BinaryOperator.minBy(String::compareTo)));
    }
}