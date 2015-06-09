package http.launcher;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

public class ArgumentParserTest {

    @Test
    public void argumentNotFound() {
        String[] args = {"-d", "someDirectory"};
        assertThat(new ArgumentParser(args).getArgument("-p").isPresent()).isFalse();
    }

    @Test
    public void argumentFound(){
        String[] args = {"-d", "someDirectory"};
        Optional<String> argument = new ArgumentParser(args).getArgument("-d");
        assertThat(argument.isPresent()).isTrue();
        assertThat(argument.get()).isEqualTo("someDirectory");
    }

}