package tn.esprit.autoloc.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public abstract class AbstractCrudService<T> implements ICrudService<T> {

    private final JpaRepository<T, Long> repository;

    protected AbstractCrudService(JpaRepository<T, Long> repository) {
        this.repository = repository;
    }

    protected abstract void assignId(T entity, Long id);

    protected abstract String entityName();

    @Override
    @Transactional
    public T create(T entity) {
        assignId(entity, null);
        return repository.save(entity);
    }

    @Override
    @Transactional
    public T update(Long id, T entity) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException(entityName() + " introuvable avec l'id " + id);
        }
        assignId(entity, id);
        return repository.save(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<T> findById(Long id) {
        return repository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<T> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
