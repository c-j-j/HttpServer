package http.launcher;

import java.util.Optional;

public class ArgumentParser {
    private final String[] arguments;

    public ArgumentParser(String[] arguments) {
        this.arguments = arguments;
    }

    public Optional<String> getArgument(String argumentLabel){
        for(int i = 0; i < arguments.length; i++){
            if(arguments[i].equals(argumentLabel)){
                return Optional.of(arguments[i+1]);
            }
        }
       return Optional.empty();
    }
}
