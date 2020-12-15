package com.convenient.iapp.entity;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class BaseEntity {

    private int pageNo;
    private int pageSize;
}
