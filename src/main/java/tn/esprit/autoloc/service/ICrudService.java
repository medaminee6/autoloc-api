package tn.esprit.autoloc.service;

import java.util.List;
import java.util.Optional;

public interface ICrudService<T> {

    T create(T entity);

    T update(Long id, T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void deleteById(Long id);
}
