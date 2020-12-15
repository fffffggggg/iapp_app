package com.convenient.iapp.entity;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Message{

    private int id;
    private String openId;
    private String userName;
    private String messageTitle;
    private String message;
    private String createTime;
    private String startTime;
    private String endTime;
}
