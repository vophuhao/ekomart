package vn.iotstar.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Province {

    private Long id;

    private String slug;

    private String name;

    private int type;

}
