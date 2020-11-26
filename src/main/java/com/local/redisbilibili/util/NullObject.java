package com.local.redisbilibili.util;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = {"handler","hibernateLazyInitializer","fieldHandler"})
public class NullObject implements Serializable {
}
