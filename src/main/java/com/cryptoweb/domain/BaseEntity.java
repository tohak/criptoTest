package com.cryptoweb.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;


/**
 * базовый класс, что бы  в остальных не делать  поле ади,
 * меньше кода писать потом будет
 */
@Getter
@Setter
@NoArgsConstructor
@ToString
@MappedSuperclass  //  авто авторайд в другие классы
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //edintity для мускула
    private Long id;

}