package com.jpa.kakao.domain;

import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public abstract class BaseTimeEntity {

    protected LocalDateTime regDate;

    protected LocalDateTime withDrawlDate;

}
