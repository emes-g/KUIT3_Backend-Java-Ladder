package ladder.printer;

import ladder.Row;

public class LadderPrinter implements Printer {
    private final Row[] rows;

    public LadderPrinter(Row[] rows) {
        this.rows = rows;
    }

    @Override
    public String print() {
        StringBuilder ladderResult = new StringBuilder();
        for (Row row : rows) {
            ladderResult.append(row.printRow());
        }
        return ladderResult.toString();
    }
}
