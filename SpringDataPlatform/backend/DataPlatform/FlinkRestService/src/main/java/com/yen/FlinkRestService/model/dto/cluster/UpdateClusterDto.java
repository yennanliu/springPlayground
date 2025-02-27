package com.yen.FlinkRestService.model.dto.cluster;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UpdateClusterDto {

    private Integer id;
    private String url;
    private Integer port;
}
