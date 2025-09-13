import org.junit.jupiter.api.Test;

public class TesteTest {

    @Test
    public void teste() {
        var client = OsuApiProvider.Companion.getApi();
        System.out.println("pegou a api");
    }
}
