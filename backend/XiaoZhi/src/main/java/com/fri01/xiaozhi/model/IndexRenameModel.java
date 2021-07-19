package com.fri01.xiaozhi.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class IndexRenameModel implements Serializable, HasNullAttribute {

    private static final long serialVersionUID = 1L;

    private String name;

    private List<String> labels;

    private List<String> names;

}
