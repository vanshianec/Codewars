package operations;

import java.util.List;

public interface Ast {
    String getOperator();
    List<String> getIdentifiers();
}
