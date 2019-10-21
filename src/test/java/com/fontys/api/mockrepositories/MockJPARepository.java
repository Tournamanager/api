package com.fontys.api.mockrepositories;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MockJPARepository<T, D> implements JpaRepository<T, D>
{
    private List<T> findAllReturnValue = null;
    public void setFindAllReturnValue(List<T> findAllReturnValue)
    {
        this.findAllReturnValue = findAllReturnValue;
    }

    @Override
    public List<T> findAll()
    {
        return findAllReturnValue;
    }

    private List<T> findAllWithSortReturnValue = null;
    public void setFindAllWithSortReturnValue(List<T> findAllWithSortReturnValue)
    {
        this.findAllWithSortReturnValue = findAllWithSortReturnValue;
    }

    private Sort findAllWithSortCalledWithParameter = null;
    public Sort getFindAllWithSortCalledWithParameter()
    {
        return this.findAllWithSortCalledWithParameter;
    }

    @Override
    public List<T> findAll(Sort sort)
    {
        this.findAllWithSortCalledWithParameter = sort;
        return findAllWithSortReturnValue;
    }

    private Page<T> findAllWithPageableReturnValue = null;
    public void setFindAllWithPageableReturnValue(Page<T> findAllWithPageableReturnValue)
    {
        this.findAllWithPageableReturnValue = findAllWithPageableReturnValue;
    }

    private Pageable findAllWithPageableCalledWithParameter = null;
    public Pageable getFindAllWithPageableCalledWithParameter()
    {
        return this.findAllWithPageableCalledWithParameter;
    }

    @Override
    public Page<T> findAll(Pageable pageable)
    {
        this.findAllWithPageableCalledWithParameter = pageable;
        return this.findAllWithPageableReturnValue;
    }

    private List<T> findAllByIdReturnValue = null;
    public void setFindAllByIdReturnValue(List<T> findAllByIdReturnValue)
    {
        this.findAllByIdReturnValue = findAllByIdReturnValue;
    }

    private Iterable<D> findAllByIdCalledWithParameter = null;
    public Iterable<D> getFindAllByIdCalledWithParameter()
    {
        return this.findAllByIdCalledWithParameter;
    }

    @Override
    public List<T> findAllById(Iterable<D> iterable)
    {
        this.findAllByIdCalledWithParameter = iterable;
        return this.findAllByIdReturnValue;
    }

    private long countReturnValue = -1;
    public void setCountReturnValue(long countReturnValue)
    {
        this.countReturnValue = countReturnValue;
    }

    @Override
    public long count()
    {
        return countReturnValue;
    }

    private D deletedByIdCalledWithParameter = null;
    public D getDeletedByIdCalledWithParameter()
    {
        return this.deletedByIdCalledWithParameter;
    }

    @Override
    public void deleteById(D d)
    {
        this.deletedByIdCalledWithParameter = d;
    }

    private T deleteCalledWithParameter = null;
    public T getDeleteCalledWithParameter()
    {
        return this.deleteCalledWithParameter;
    }

    @Override
    public void delete(T t)
    {
        this.deleteCalledWithParameter = t;
    }

    private Iterable<? extends T> deleteAllWithIterableCalledWithParameter = null;

    public Iterable<? extends T> getDeleteAllWithIterableCalledWithParameter()
    {
        return deleteAllWithIterableCalledWithParameter;
    }

    @Override
    public void deleteAll(Iterable<? extends T> iterable)
    {
        this.deleteAllWithIterableCalledWithParameter = iterable;
    }

    @Override
    public void deleteAll()
    {

    }

    //ToDo: Fix Generic Typing

    private T saveReturnValue = null;
    public void setSaveReturnValue(T t)
    {
        this.saveReturnValue = t;
    }

    private T saveCalledWithParameter = null;
    public T getSaveCalledWithParameters()
    {
        return saveCalledWithParameter;
    }

    @Override
    public <S extends T> S save(S s)
    {
        this.saveCalledWithParameter = s;
        return null;
    }

    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable)
    {
        return null;
    }

    @Override
    public Optional<T> findById(D d)
    {
        return Optional.empty();
    }

    @Override
    public boolean existsById(D d)
    {
        return false;
    }

    @Override
    public void flush()
    {

    }

    @Override
    public <S extends T> S saveAndFlush(S s)
    {
        return null;
    }

    @Override
    public void deleteInBatch(Iterable<T> iterable)
    {

    }

    @Override
    public void deleteAllInBatch()
    {

    }

    @Override
    public T getOne(D d)
    {
        return null;
    }

    @Override
    public <S extends T> Optional<S> findOne(Example<S> example)
    {
        return Optional.empty();
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example)
    {
        return null;
    }

    @Override
    public <S extends T> List<S> findAll(Example<S> example, Sort sort)
    {
        return null;
    }

    @Override
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable)
    {
        return null;
    }

    @Override
    public <S extends T> long count(Example<S> example)
    {
        return 0;
    }

    @Override
    public <S extends T> boolean exists(Example<S> example)
    {
        return false;
    }
}
