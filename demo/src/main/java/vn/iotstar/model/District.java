package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class District {

    private Long id;
    private Long provinceId;
    private String name;
    private int type;

}
