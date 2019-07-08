package com.inventory.core.util;

/**
 * @param <E>
 * @param <D>
 * @author manohar-td-003
 */
public interface IConvertable<E, D> {

    /***
     * Convert DTO to Entity
     *
     * @param dto
     * @return entity
     */
    E convertToEntity(D dto);

    /***
     * Convert Entity to DTO
     *
     * @param entity
     * @return entity
     */
    D convertToDto(E entity);

    /***
     * Copy DTO to Entity
     *
     * @param entity , dto
     * @return entity
     */

    E copyConvertToEntity(D dto, E entity);
}
