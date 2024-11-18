package vn.iotstar.model;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ward {

    private Long id;

    private Long districtId;

    private String name;

    private int type;
}
