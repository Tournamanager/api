package com.fontys.api.mockrepositories;

import org.javatuples.Pair;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.*;
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

    @NotNull
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

    @NotNull
    @Override
    public List<T> findAll(@NotNull Sort sort)
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

    @NotNull
    @Override
    public Page<T> findAll(@NotNull Pageable pageable)
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

    @NotNull
    @Override
    public List<T> findAllById(@NotNull Iterable<D> iterable)
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
    public void deleteById(@NotNull D d)
    {
        this.deletedByIdCalledWithParameter = d;
    }

    private T deleteCalledWithParameter = null;
    public T getDeleteCalledWithParameter()
    {
        return this.deleteCalledWithParameter;
    }

    @Override
    public void delete(@NotNull T t)
    {
        this.deleteCalledWithParameter = t;
    }

    private Iterable<? extends T> deleteAllWithIterableCalledWithParameter = null;

    public Iterable<? extends T> getDeleteAllWithIterableCalledWithParameter()
    {
        return deleteAllWithIterableCalledWithParameter;
    }

    @Override
    public void deleteAll(@NotNull Iterable<? extends T> iterable)
    {
        this.deleteAllWithIterableCalledWithParameter = iterable;
    }

    @Override
    public void deleteAll()
    {

    }

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

    @NotNull
    @Override
    public <S extends T> S save(@NotNull S s)
    {
        this.saveCalledWithParameter = s;
        try{
            return (S) this.saveReturnValue;
        }
        catch (ClassCastException  e)
        {
            throw new AssertionError();
        }
    }

    private List<T> saveAllReturnValue = null;
    public void setSaveAllReturnValue(List<T> saveAllReturnValue)
    {
        this.saveAllReturnValue = saveAllReturnValue;
    }

    private Iterable<T> saveAllCalledWithParameter = null;
    public Iterable<T> getSaveAllCalledWithParameter()
    {
        return this.saveAllCalledWithParameter;
    }

    @NotNull
    @Override
    public <S extends T> List<S> saveAll(Iterable<S> iterable)
    {
        List<T> parameterList = new ArrayList<>();
        for(S item: iterable)
        {
            parameterList.add((T) item);
        }

        this.saveAllCalledWithParameter = parameterList;

        List<S> returnList = new ArrayList<>();
        for(T item: this.saveAllReturnValue)
        {
            try
            {
                returnList.add((S) item);
            }
            catch (ClassCastException e)
            {
                returnList.add(null);
            }

        }
        return returnList;
    }

    private T findByIdReturnValue = null;
    public void setFindByIdReturnValue(T findByIdReturnValue)
    {
        this.findByIdReturnValue = findByIdReturnValue;
    }

    private D findByIdCalledWithParameter = null;
    public D getFindByIdCalledWithParameter()
    {
        return this.findByIdCalledWithParameter;
    }

    @NotNull
    @Override
    public Optional<T> findById(@NotNull D d)
    {
        this.findByIdCalledWithParameter = d;
        return Optional.of(this.findByIdReturnValue);
    }

    private boolean existsByIdReturnValue = false;
    public void setExistsByIdReturnValue(boolean existsByIdReturnValue)
    {
        this.existsByIdReturnValue = existsByIdReturnValue;
    }

    private D existsByIdCalledWithParameter = null;
    public D getExistsByIdCalledWithParameter()
    {
        return this.existsByIdCalledWithParameter;
    }

    @Override
    public boolean existsById(@NotNull D d)
    {
        this.existsByIdCalledWithParameter = d;
        return existsByIdReturnValue;
    }

    @Override
    public void flush()
    {

    }

    private T saveAndFlushReturnValue = null;
    public void setSaveAndFlushReturnValue(T saveAndFlushReturnValue)
    {
        this.saveAndFlushReturnValue = saveAndFlushReturnValue;
    }

    private T saveAndFlushCalledWithParameter = null;
    public T getSaveAndFlushCalledWithParameter()
    {
        return this.saveAndFlushCalledWithParameter;
    }

    @NotNull
    @Override
    public <S extends T> S saveAndFlush(@NotNull S s)
    {
        this.saveAndFlushCalledWithParameter = s;
        try{
            return (S) this.saveAndFlushReturnValue;
        }
        catch (ClassCastException  e)
        {
            throw new AssertionError();
        }
    }

    private Iterable<T> deleteInBatchCalledWithParameter = null;

    public Iterable<T> getDeleteInBatchCalledWithParameter()
    {
        return this.deleteInBatchCalledWithParameter;
    }

    @Override
    public void deleteInBatch(@NotNull Iterable<T> iterable)
    {
        this.deleteInBatchCalledWithParameter = iterable;
    }

    @Override
    public void deleteAllInBatch()
    {

    }

    private T getOneReturnValue = null;

    public void setGetOneReturnValue(T getOneReturnValue)
    {
        this.getOneReturnValue = getOneReturnValue;
    }

    private D getOneCalledWithParameter = null;

    public D getGetOneCalledWithParameter()
    {
        return this.getOneCalledWithParameter;
    }

    @NotNull
    @Override
    public T getOne(@NotNull D d)
    {
        this.getOneCalledWithParameter = d;
        return this.getOneReturnValue;
    }

    private T findOneReturnValue = null;

    public void setFindOneReturnValue(T findOneReturnValue)
    {
        this.findOneReturnValue = findOneReturnValue;
    }

    private Example<T> findOneCalledWithParameter = null;

    public Example<T> getFindOneCalledWithParameter()
    {
        return this.findOneCalledWithParameter;
    }

    @NotNull
    @Override
    public <S extends T> Optional<S> findOne(@NotNull Example<S> example)
    {
        this.findOneCalledWithParameter = Example.of((T)example.getProbe());
        return Optional.of((S) this.findOneReturnValue);
    }


    private List<T> findAllWithExampleReturnValue = null;

    public void setFindAllReturnValueWithExample(List<T> findAllWithExampleReturnValue)
    {
        this.findAllWithExampleReturnValue = findAllWithExampleReturnValue;
    }

    private Example<T> findAllWithExampleCalledWithParameter = null;

    public Example<T> getFindAllWithExampleCalledWithParameter()
    {
        return this.findAllWithExampleCalledWithParameter;
    }

    @NotNull
    @Override
    public <S extends T> List<S> findAll(@NotNull Example<S> example)
    {
        this.findAllWithExampleCalledWithParameter = Example.of((T)example.getProbe());
        List<S> returnList = new ArrayList<>();
        for(T item: this.findAllWithExampleReturnValue)
        {
            try
            {
                returnList.add((S) item);
            }
            catch (ClassCastException e)
            {
                returnList.add(null);
            }

        }
        return returnList;
    }

    private List<T> findAllWithExampleAndSortReturnValue = null;

    public void setFindAllWithExampleAndSortReturnValue(List<T> findAllWithExampleAndSortReturnValue)
    {
        this.findAllWithExampleAndSortReturnValue = findAllWithExampleAndSortReturnValue;
    }

    private Pair<Example<T>, Sort> findAllWithExampleAndSortCalledWithParameter = null;

    public Pair<Example<T>, Sort> getFindAllWithExampleAndSortCalledWithParameter()
    {
        return this.findAllWithExampleAndSortCalledWithParameter;
    }

    @NotNull
    @Override
    public <S extends T> List<S> findAll(@NotNull Example<S> example, @NotNull Sort sort)
    {
        this.findAllWithExampleAndSortCalledWithParameter = Pair.with(Example.of((T) example.getProbe()), sort);
        List<S> returnList = new ArrayList<>();
        for(T item: this.findAllWithExampleAndSortReturnValue)
        {
            try
            {
                returnList.add((S) item);
            }
            catch (ClassCastException e)
            {
                returnList.add(null);
            }

        }
        return returnList;
    }

    private Page<T> findAllWithExampleAndPageableReturnValue = null;

    public void setFindAllWithExampleAndPageableReturnValue(Page<T> findAllWithExampleAndPageableReturnValue)
    {
        this.findAllWithExampleAndPageableReturnValue = findAllWithExampleAndPageableReturnValue;
    }

    private Pair<Example<T>, Pageable> findAllWithExampleAndPageableCalledWithParameter = null;

    public Pair<Example<T>, Pageable> getFindAllWithExampleAndPageableCalledWithParameter()
    {
        return this.findAllWithExampleAndPageableCalledWithParameter;
    }

    @NotNull
    @Override
    public <S extends T> Page<S> findAll(@NotNull Example<S> example, @NotNull Pageable pageable)
    {
        this.findAllWithExampleAndPageableCalledWithParameter = Pair.with(Example.of((T) example.getProbe()), pageable);
        List<S> convertedItems = new ArrayList<>();
        List<T> items = this.findAllWithExampleAndPageableReturnValue.getContent();
        for(T item: items)
        {
            try
            {
                convertedItems.add((S) item);
            }
            catch (ClassCastException e)
            {
                convertedItems.add(null);
            }
        }
        return new PageImpl<>(convertedItems);
    }

    private long countWithExampleReturnValue = -1;

    public void setCountWithExampleReturnValue(long countWithExampleReturnValue)
    {
        this.countWithExampleReturnValue = countWithExampleReturnValue;
    }

    private Example<T> countWithExampleCalledWithParameter = null;

    public Example<T> getCountWithExampleCalledWithParameter()
    {
        return this.countWithExampleCalledWithParameter;
    }

    @Override
    public <S extends T> long count(@NotNull Example<S> example)
    {
        this.countWithExampleCalledWithParameter = Example.of((T) example.getProbe());
        return this.countWithExampleReturnValue;
    }

    private boolean existsReturnValue = false;

    public void setExistsReturnValue(boolean existsReturnValue)
    {
        this.existsReturnValue = existsReturnValue;
    }

    private Example<T> existsCalledWithParameter = null;

    public Example<T> getExistsCalledWithParameter()
    {
        return existsCalledWithParameter;
    }

    @Override
    public <S extends T> boolean exists(@NotNull Example<S> example)
    {
        this.existsCalledWithParameter = Example.of((T) example.getProbe());
        return this.existsReturnValue;
    }
}
