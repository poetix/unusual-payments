package spending;

import java.util.Comparator;
import java.util.stream.Collectors;

public class StandardUnusualSpendingEmailFormatter implements UnusualSpendingEmailFormatter {

    private static final String EMAIL_TEMPLATE = "Hello card user!\n" +
            "\n" +
            "We have detected unusually high spending on your card in these categories:\n" +
            "\n" +
            "<SUMMARY>\n" +
            "\n" +
            "Love,\n" +
            "\n" +
            "The Credit Card Company";

    @Override
    public EmailBody format(UnusualSpendingSummary summary) {
        String summaryString = summary.unusualSpending().entrySet().stream()
                .sorted(Comparator.comparing(e -> e.getKey().toString()))
                .map(e -> "* £" + e.getValue().amountGbp() + " on " + e.getKey().toString().toLowerCase())
                .collect(Collectors.joining("\n"));

        return EmailBody.of(EMAIL_TEMPLATE.replace("<SUMMARY>", summaryString));
    }
}
