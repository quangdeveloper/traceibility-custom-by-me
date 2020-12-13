package vn.vnpt.tracebility_custom.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
@SuperBuilder
@NoArgsConstructor
@Data
public abstract  class BaseId {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}
