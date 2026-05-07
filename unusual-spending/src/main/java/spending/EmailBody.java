package spending;

public record EmailBody(String value) {
    public static EmailBody of(String body) {
        return new EmailBody(body);
    }
}
