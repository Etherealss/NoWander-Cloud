package com.nowander.auth.domain.auth.server.info;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotEmpty;

/**
 * @author wtk
 * @date 2022-10-14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServerAuthCommand {
    @NotEmpty
    String secret;
}
