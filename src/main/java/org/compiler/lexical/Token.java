package org.compiler.lexical;

import lombok.*;
import org.compiler.lexical.enums.TokenType;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Token {
    private TokenType type;
    private String text;
}
