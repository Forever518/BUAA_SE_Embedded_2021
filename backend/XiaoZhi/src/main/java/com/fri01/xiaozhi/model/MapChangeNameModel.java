package com.fri01.xiaozhi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class MapChangeNameModel implements Serializable, HasNullAttribute {

    private static final long serialVersionUID = 1L;

    private String mapPreviousName;

    private String mapNewName;

}
